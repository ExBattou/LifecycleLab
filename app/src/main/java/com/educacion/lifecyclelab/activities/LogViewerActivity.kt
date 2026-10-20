package com.educacion.lifecyclelab.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.educacion.lifecyclelab.ui.screens.LogViewerScreen
import com.educacion.lifecyclelab.ui.theme.LifecycleLabTheme

/**
 * Actividad encargada de leer el archivo plano 'lifecycle_log.txt' directamente desde
 * el almacenamiento interno y permitir compartirlo o exportarlo.
 */
class LogViewerActivity : BaseTrackedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifecycleLabTheme {
                LogViewerScreen()
            }
        }
    }
}
