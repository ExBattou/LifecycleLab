package com.educacion.lifecyclelab.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.educacion.lifecyclelab.ui.screens.MainScreen
import com.educacion.lifecyclelab.ui.theme.LifecycleLabTheme

/**
 * Actividad Principal: actúa como el "Laboratorio" donde el alumno interactúa
 * y observa los cambios del ciclo de vida en vivo.
 */
class MainActivity : BaseTrackedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifecycleLabTheme {
                MainScreen()
            }
        }
    }
}
