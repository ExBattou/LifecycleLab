package com.educacion.lifecyclelab

import android.app.Application
import com.educacion.lifecyclelab.tracker.LifecycleTracker

/**
 * Clase Application para inicializar componentes globales como el LifecycleTracker
 * al arrancar el proceso de la app.
 */
class LifecycleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicializamos el tracker con el contexto de la aplicación
        LifecycleTracker.init(this)
    }
}
