package com.educacion.lifecyclelab.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.educacion.lifecyclelab.ui.screens.TheoryScreen
import com.educacion.lifecyclelab.ui.theme.LifecycleLabTheme

/**
 * Actividad que presenta el módulo teórico y la guía didáctica de estudio.
 */
class TheoryActivity : BaseTrackedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifecycleLabTheme {
                TheoryScreen()
            }
        }
    }
}
