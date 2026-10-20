package com.educacion.lifecyclelab.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educacion.lifecyclelab.tracker.LifecycleStage
import com.educacion.lifecyclelab.ui.components.LifecycleBadge
import com.educacion.lifecyclelab.ui.components.getStageColor

data class TheoryTopic(
    val stage: LifecycleStage,
    val whenIsCalled: String,
    val goodPractices: String,
    val badPractices: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    val topics = listOf(
        TheoryTopic(
            stage = LifecycleStage.ON_CREATE,
            whenIsCalled = "Se ejecuta una única vez cuando el sistema operativo instancia la Activity en memoria.",
            goodPractices = "Inicializar variables, configurar vistas/Compose, instanciar ViewModels, observar StateFlows.",
            badPractices = "Realizar operaciones pesadas en el hilo principal (I/O, llamadas a red) o llamadas bloqueantes."
        ),
        TheoryTopic(
            stage = LifecycleStage.ON_START,
            whenIsCalled = "La actividad pasa a ser visible para el usuario, pero aún no tiene el foco para interactuar.",
            goodPractices = "Registrar listeners que solo tengan sentido mientras la UI sea visible (ej. sensores de brillo).",
            badPractices = "Asumir que el usuario ya puede presionar botones o escribir texto."
        ),
        TheoryTopic(
            stage = LifecycleStage.ON_RESUME,
            whenIsCalled = "La actividad está en primer plano (foreground), visible y con el foco total del usuario.",
            goodPractices = "Iniciar animaciones, activar la cámara, reanudar reproducción o actualización de geolocalización.",
            badPractices = "Cargar datos masivos aquí que bloqueen la fluidez de entrada de la pantalla."
        ),
        TheoryTopic(
            stage = LifecycleStage.ON_PAUSE,
            whenIsCalled = "La actividad pierde el foco. Puede seguir parcialmente visible (ej: diálogo modal o multiventana).",
            goodPractices = "Pausar animaciones o consumo de batería intensivo, liberar acceso exclusivo a periféricos (cámara).",
            badPractices = "Guardar datos pesados en base de datos o hacer llamadas HTTP largas (¡onPause debe ser ultrarrápido!)."
        ),
        TheoryTopic(
            stage = LifecycleStage.ON_STOP,
            whenIsCalled = "La actividad ya no es visible en absoluto para el usuario (pasó a segundo plano u otra pantalla completa la tapó).",
            goodPractices = "Guardar borradores en base de datos, desuscribirse de sensores pesados, liberar recursos gráficos.",
            badPractices = "Mantener referencias a vistas que eviten que el Garbage Collector libere memoria si se requiere."
        ),
        TheoryTopic(
            stage = LifecycleStage.ON_RESTART,
            whenIsCalled = "La actividad estaba en segundo plano (onStop) y el usuario vuelve a ella antes de que sea destruida.",
            goodPractices = "Preparar la interfaz antes de que se vuelva a disparar onStart().",
            badPractices = "Recrear objetos que ya existían en memoria desde onCreate()."
        ),
        TheoryTopic(
            stage = LifecycleStage.ON_DESTROY,
            whenIsCalled = "La actividad finaliza por completo (finish()) o el sistema la destruye por falta de RAM o rotación.",
            goodPractices = "Liberar hilos en ejecución, cancelar corrutinas activas, cerrar conexiones o cursores de base de datos.",
            badPractices = "Asumir que siempre se llamará si el sistema operativo mata el proceso bruscamente por memoria."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guía del Ciclo de Vida") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "📚 Fundamentos para la clase",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF0D47A1)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Una Activity en Android no tiene el control absoluto de su tiempo de vida; es el Sistema Operativo Android quien decide cuándo crearla, pausarla o destruirla según los recursos del dispositivo y la interacción del usuario.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1A237E)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Métodos del Ciclo de Vida en Detalle:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(topics) { topic ->
                TheoryCard(topic = topic)
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🧪 Experimentos sugeridos para los alumnos",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFFE65100)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "1. Abrir diálogo vs Segunda pantalla:\n" +
                                   "   Noten que en diálogo se llama onPause() pero NO onStop(). En la segunda pantalla se llaman ambos.\n\n" +
                                   "2. Rotar el dispositivo:\n" +
                                   "   Observen en el TXT la destrucción completa y reconstrucción inmediata con onSaveInstanceState.\n\n" +
                                   "3. Presionar botón Home y volver:\n" +
                                   "   Comprobarán la secuencia: onPause -> onStop y luego onRestart -> onStart -> onResume.\n\n" +
                                   "4. Cerrar la app desde el botón o swipe:\n" +
                                   "   Vuelvan a abrir la app, vayan al Visor TXT y verifiquen que todo el historial sobrevivió en el archivo.",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = Color(0xFF5D4037),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TheoryCard(topic: TheoryTopic) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LifecycleBadge(stage = topic.stage)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "📌 Cuándo se invoca:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = topic.whenIsCalled,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "✅ Buenas prácticas:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF2E7D32)
            )
            Text(
                text = topic.goodPractices,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "⚠️ Qué evitar:",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFFC62828)
            )
            Text(
                text = topic.badPractices,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF333333)
            )
        }
    }
}
