package com.educacion.lifecyclelab.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.educacion.lifecyclelab.ui.screens.SecondScreen
import com.educacion.lifecyclelab.ui.theme.LifecycleLabTheme

/**
 * Segunda Actividad (Pantalla completa).
 * Al abrirse completamente, tapa a MainActivity provocando su onPause() y luego onStop().
 */
class SecondActivity : BaseTrackedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifecycleLabTheme {
                SecondScreen()
            }
        }
    }
}
