package com.educacion.lifecyclelab.tracker

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.Vector
import java.util.concurrent.atomic.AtomicLong

/**
 * Gestor centralizado del registro de eventos del Ciclo de Vida.
 *
 * Cumple con dos propósitos pedagógicos:
 * 1. Mantiene un Vector en memoria con los eventos para actualizar la interfaz en tiempo real.
 * 2. Persiste inmediatamente cada evento en un archivo plano 'lifecycle_log.txt' en el
 *    almacenamiento interno privado de la aplicación.
 *
 * De este modo, si la aplicación es cerrada por el usuario o finalizada por el sistema
 * operativo (onDestroy / process kill), los datos permanecen intactos en el TXT.
 */
object LifecycleTracker {

    private const val TAG = "LifecycleLab"
    private const val LOG_FILE_NAME = "lifecycle_log.txt"

    private val counter = AtomicLong(0)

    /**
     * Vector en memoria para almacenar los eventos de manera segura ante concurrencia.
     * Usamos java.util.Vector tal como se solicitó para estructuras clásicas.
     */
    private val memoryLogVector: Vector<LifecycleEntry> = Vector()

    /**
     * Flujo reactivo (StateFlow) para que la interfaz Jetpack Compose
     * se actualice automáticamente ante cualquier nuevo evento.
     */
    private val _eventsFlow = MutableStateFlow<List<LifecycleEntry>>(emptyList())
    val eventsFlow: StateFlow<List<LifecycleEntry>> = _eventsFlow.asStateFlow()

    private var appContext: Context? = null

    /**
     * Inicializa el tracker con el contexto de la aplicación.
     */
    fun init(context: Context) {
        if (appContext == null) {
            appContext = context.applicationContext
            ensureHeaderInLogFile()
            loadExistingLogsIntoMemory()
        }
    }

    /**
     * Registra un nuevo evento de ciclo de vida en el Vector en memoria y en el archivo TXT.
     */
    @Synchronized
    fun logEvent(
        activityName: String,
        stage: LifecycleStage,
        extraInfo: String = ""
    ): LifecycleEntry {
        val entry = LifecycleEntry(
            id = counter.incrementAndGet(),
            timestamp = System.currentTimeMillis(),
            activityName = activityName,
            stage = stage,
            explanation = stage.shortDescription,
            extraInfo = extraInfo
        )

        // 1. Guardar en el Vector en memoria
        memoryLogVector.add(entry)
        _eventsFlow.value = memoryLogVector.toList()

        // 2. Imprimir en el Logcat de Android Studio
        Log.d(TAG, entry.toTxtLine())

        // 3. Escribir de inmediato en el archivo TXT local
        writeEntryToDisk(entry)

        return entry
    }

    /**
     * Retorna una copia de los eventos almacenados en el Vector en memoria.
     */
    fun getMemoryEvents(): List<LifecycleEntry> {
        return memoryLogVector.toList()
    }

    /**
     * Retorna el archivo File correspondiente al TXT de registro.
     */
    fun getLogFile(context: Context): File {
        return File(context.filesDir, LOG_FILE_NAME)
    }

    /**
     * Lee directamente el contenido del archivo TXT desde el disco.
     */
    fun readTxtFileContent(context: Context): String {
        val file = getLogFile(context)
        return if (file.exists()) {
            try {
                file.readText()
            } catch (e: IOException) {
                "Error al leer el archivo de registros: ${e.localizedMessage}"
            }
        } else {
            "Aún no hay registros en el archivo TXT."
        }
    }

    /**
     * Limpia tanto el Vector en memoria como el contenido del archivo TXT.
     */
    @Synchronized
    fun clearLogs(context: Context) {
        memoryLogVector.clear()
        counter.set(0)
        _eventsFlow.value = emptyList()

        val file = getLogFile(context)
        if (file.exists()) {
            file.delete()
        }
        ensureHeaderInLogFile()
    }

    /**
     * Escribe la línea del evento en el archivo TXT de forma inmediata (append).
     */
    private fun writeEntryToDisk(entry: LifecycleEntry) {
        val context = appContext ?: return
        try {
            val file = getLogFile(context)
            FileWriter(file, true).use { writer ->
                writer.append(entry.toTxtLine()).append("\n")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error escribiendo en el archivo TXT", e)
        }
    }

    /**
     * Escribe un encabezado explicativo si el archivo TXT es nuevo.
     */
    private fun ensureHeaderInLogFile() {
        val context = appContext ?: return
        val file = getLogFile(context)
        if (!file.exists() || file.length() == 0L) {
            try {
                FileWriter(file, false).use { writer ->
                    writer.append("====================================================================\n")
                    writer.append("        REGISTRO DE CICLOS DE VIDA DE ANDROID - LIFECYCLE LAB       \n")
                    writer.append("====================================================================\n")
                    writer.append("Formato: [FECHA Y HORA] [#EVENTO] [ACTIVIDAD] -> METODO() | EXPLICACION\n")
                    writer.append("====================================================================\n\n")
                }
            } catch (e: IOException) {
                Log.e(TAG, "Error creando encabezado en archivo TXT", e)
            }
        }
    }

    /**
     * Si la aplicación se reinició, cargamos la información que ya existía en el TXT
     * para que no se pierda la historia previa en la UI.
     */
    private fun loadExistingLogsIntoMemory() {
        val context = appContext ?: return
        val file = getLogFile(context)
        if (!file.exists()) return

        try {
            val lines = file.readLines()
            var count = 0L
            for (line in lines) {
                if (line.startsWith("[") && line.contains("] -> ")) {
                    count++
                }
            }
            counter.set(count)
        } catch (e: Exception) {
            Log.e(TAG, "Error calculando contador inicial desde TXT", e)
        }
    }
}
