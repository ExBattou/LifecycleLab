package com.educacion.lifecyclelab.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import com.educacion.lifecyclelab.ui.screens.DialogScreen
import com.educacion.lifecyclelab.ui.theme.LifecycleLabTheme

/**
 * Actividad translúcida con aspecto de Diálogo.
 * Demuestra la diferencia vital entre onPause() y onStop():
 * La actividad anterior sigue parcialmente visible, por lo que SOLO pasa a onPause().
 */
class DialogActivity : BaseTrackedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifecycleLabTheme {
                DialogScreen()
            }
        }
    }
}
