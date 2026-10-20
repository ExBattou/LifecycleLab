package com.educacion.lifecyclelab.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educacion.lifecyclelab.ui.theme.ColorPause

@Composable
fun DialogScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    // Fondo oscuro semitransparente para simular ventana modal de diálogo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "🪟 Actividad en Modo Diálogo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = ColorPause
                )

                Text(
                    text = "¡CONCEPTO CLAVE DE EXAMEN!",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp,
                    color = Color(0xFFC62828)
                )

                Text(
                    text = "Observa que MainActivity sigue visible en el fondo pero perdió el foco interactivo.\n\n" +
                           "• MainActivity ejecutó: onPause()\n" +
                           "• MainActivity NO ejecutó: onStop() (¡porque aún se ve!)\n\n" +
                           "Al cerrar este diálogo, MainActivity volverá directamente a onResume() sin pasar por onRestart() ni onStart().",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color(0xFF333333),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { activity?.finish() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorPause),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cerrar Diálogo y Volver")
                }
            }
        }
    }
}
