package com.educacion.lifecyclelab.ui.screens

import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educacion.lifecyclelab.activities.DialogActivity
import com.educacion.lifecyclelab.activities.LogViewerActivity
import com.educacion.lifecyclelab.activities.SecondActivity
import com.educacion.lifecyclelab.activities.TheoryActivity
import com.educacion.lifecyclelab.tracker.LifecycleTracker
import com.educacion.lifecyclelab.ui.components.LifecycleItemCard
import com.educacion.lifecyclelab.ui.theme.ColorResume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    // Colección reactiva de eventos observada en vivo
    val events by LifecycleTracker.eventsFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Lifecycle Lab",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "MainActivity (En observación)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, TheoryActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.AutoStories,
                            contentDescription = "Guía Teórica"
                        )
                    }
                    IconButton(onClick = {
                        context.startActivity(Intent(context, LogViewerActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Ver archivo TXT"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Tarjeta informativa de estado actual
            item {
                StatusOverviewCard(eventCount = events.size)
            }

            // 2. Panel de botones didácticos
            item {
                Text(
                    text = "Experimentos didácticos (Disparadores)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ActionCard(
                            modifier = Modifier.weight(1f),
                            title = "Segunda Pantalla",
                            subtitle = "Provoca onPause -> onStop",
                            icon = Icons.Default.OpenInNew,
                            color = MaterialTheme.colorScheme.primary,
                            onClick = {
                                context.startActivity(Intent(context, SecondActivity::class.java))
                            }
                        )

                        ActionCard(
                            modifier = Modifier.weight(1f),
                            title = "Modo Diálogo",
                            subtitle = "onPause (SIN onStop)",
                            icon = Icons.Default.OpenInBrowser,
                            color = Color(0xFFE65100),
                            onClick = {
                                context.startActivity(Intent(context, DialogActivity::class.java))
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ActionCard(
                            modifier = Modifier.weight(1f),
                            title = "Recrear Pantalla",
                            subtitle = "Simula giro (recreate)",
                            icon = Icons.Default.Refresh,
                            color = Color(0xFF00796B),
                            onClick = {
                                activity?.recreate()
                            }
                        )

                        ActionCard(
                            modifier = Modifier.weight(1f),
                            title = "Minimizar (Home)",
                            subtitle = "Pasa a segundo plano",
                            icon = Icons.Default.Home,
                            color = Color(0xFF512DA8),
                            onClick = {
                                activity?.moveTaskToBack(true)
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ActionCard(
                            modifier = Modifier.weight(1f),
                            title = "Cerrar (finish)",
                            subtitle = "Provoca onDestroy",
                            icon = Icons.Default.PowerSettingsNew,
                            color = Color(0xFFC62828),
                            onClick = {
                                activity?.finish()
                            }
                        )

                        ActionCard(
                            modifier = Modifier.weight(1f),
                            title = "Ver TXT Persistente",
                            subtitle = "Lee desde disco",
                            icon = Icons.Default.Description,
                            color = MaterialTheme.colorScheme.secondary,
                            onClick = {
                                context.startActivity(Intent(context, LogViewerActivity::class.java))
                            }
                        )
                    }
                }
            }

            // 3. Encabezado de la lista en vivo con botón para limpiar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Historial en Vivo (Memoria y TXT)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedButton(
                        onClick = { LifecycleTracker.clearLogs(context) },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Limpiar",
                            tint = Color.Red,
                            modifier = Modifier.height(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Limpiar", color = Color.Red, fontSize = 12.sp)
                    }
                }
            }

            // 4. Lista de eventos en orden inverso (más recientes primero)
            if (events.isEmpty()) {
                item {
                    Text(
                        text = "Aún no hay eventos registrados.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            } else {
                items(events.reversed(), key = { it.id }) { entry ->
                    LifecycleItemCard(entry = entry)
                }
            }
        }
    }
}

@Composable
private fun StatusOverviewCard(eventCount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ESTADO ACTUAL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "RESUMED (Interactiva)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ColorResume
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$eventCount Eventos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cada método ejecutado es añadido al Vector en RAM y persistido en lifecycle_log.txt",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.height(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 14.sp
            )
        }
    }
}
