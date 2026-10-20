package com.educacion.lifecyclelab.tracker

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Representa una entrada individual en el registro del ciclo de vida.
 *
 * @param id Identificador secuencial del evento.
 * @param timestamp Fecha y hora exacta en que ocurrió el evento (en milisegundos).
 * @param activityName Nombre de la actividad que experimentó el cambio.
 * @param stage Nombre del método de ciclo de vida (ej. onCreate, onResume, etc.).
 * @param explanation Explicación didáctica sobre el significado de este estado.
 * @param extraInfo Información adicional relevante (ej: si savedInstanceState era null o no).
 */
data class LifecycleEntry(
    val id: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val activityName: String,
    val stage: LifecycleStage,
    val explanation: String,
    val extraInfo: String = ""
) {
    val formattedTime: String
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
            return formatter.format(Date(timestamp))
        }

    /**
     * Formato en el que se almacena en el archivo TXT para lectura limpia.
     */
    fun toTxtLine(): String {
        val extra = if (extraInfo.isNotEmpty()) " [$extraInfo]" else ""
        return "[$formattedTime] [#$id] [$activityName] -> ${stage.methodName}()$extra | ${stage.shortDescription}"
    }
}

/**
 * Enumeración didáctica de los estados y métodos del ciclo de vida de una Activity en Android.
 */
enum class LifecycleStage(
    val methodName: String,
    val shortDescription: String,
    val isAlive: Boolean,
    val isVisible: Boolean,
    val isForeground: Boolean
) {
    ON_CREATE(
        methodName = "onCreate",
        shortDescription = "La actividad se crea e inicializa su UI y estado.",
        isAlive = true,
        isVisible = false,
        isForeground = false
    ),
    ON_START(
        methodName = "onStart",
        shortDescription = "La actividad se hace visible pero aún no interactuable.",
        isAlive = true,
        isVisible = true,
        isForeground = false
    ),
    ON_RESUME(
        methodName = "onResume",
        shortDescription = "La actividad está en primer plano y lista para interactuar.",
        isAlive = true,
        isVisible = true,
        isForeground = true
    ),
    ON_PAUSE(
        methodName = "onPause",
        shortDescription = "Pierde el foco (puede seguir parcialmente visible, ej: diálogo).",
        isAlive = true,
        isVisible = true,
        isForeground = false
    ),
    ON_STOP(
        methodName = "onStop",
        shortDescription = "Ya no es visible para el usuario (pasó a segundo plano u otra pantalla).",
        isAlive = true,
        isVisible = false,
        isForeground = false
    ),
    ON_RESTART(
        methodName = "onRestart",
        shortDescription = "Estaba detenida (onStop) y vuelve a iniciarse antes de onStart.",
        isAlive = true,
        isVisible = false,
        isForeground = false
    ),
    ON_DESTROY(
        methodName = "onDestroy",
        shortDescription = "La actividad se destruye (por finish o recreación del sistema).",
        isAlive = false,
        isVisible = false,
        isForeground = false
    ),
    ON_SAVE_INSTANCE_STATE(
        methodName = "onSaveInstanceState",
        shortDescription = "El sistema guarda el estado UI antes de destruir la actividad temporalmente.",
        isAlive = true,
        isVisible = false,
        isForeground = false
    ),
    ON_RESTORE_INSTANCE_STATE(
        methodName = "onRestoreInstanceState",
        shortDescription = "El sistema restaura el estado UI guardado tras recrear la actividad.",
        isAlive = true,
        isVisible = true,
        isForeground = false
    )
}
