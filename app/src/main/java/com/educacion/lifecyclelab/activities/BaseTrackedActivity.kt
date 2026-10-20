package com.educacion.lifecyclelab.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.educacion.lifecyclelab.tracker.LifecycleStage
import com.educacion.lifecyclelab.tracker.LifecycleTracker

/**
 * Actividad base abstracta que intercepta TODOS los métodos del ciclo de vida
 * de Android y los registra automáticamente en el Vector en memoria y en el archivo TXT.
 *
 * Heredar de esta clase permite que cualquier Activity quede automáticamente monitoreada.
 */
abstract class BaseTrackedActivity : ComponentActivity() {

    protected open val activityTag: String
        get() = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extra = if (savedInstanceState == null) {
            "Primer inicio (savedInstanceState == null)"
        } else {
            "Recreada tras rotación o muerte (savedInstanceState != null)"
        }
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_CREATE, extra)
    }

    override fun onStart() {
        super.onStart()
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_START, "La interfaz se hace visible")
    }

    override fun onResume() {
        super.onResume()
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_RESUME, "Tiene el foco del usuario y es interactiva")
    }

    override fun onPause() {
        super.onPause()
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_PAUSE, "Pierde el foco interactivo")
    }

    override fun onStop() {
        super.onStop()
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_STOP, "Ya no es visible en pantalla")
    }

    override fun onRestart() {
        super.onRestart()
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_RESTART, "Se reinicia antes de volver a onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        val extra = if (isFinishing) "Cerrada voluntariamente con finish()" else "Destruida por el sistema (ej. rotación de pantalla)"
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_DESTROY, extra)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_SAVE_INSTANCE_STATE, "Guardando estado UI antes de posible destrucción")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LifecycleTracker.logEvent(activityTag, LifecycleStage.ON_RESTORE_INSTANCE_STATE, "Restaurando estado UI tras recreación")
    }
}
