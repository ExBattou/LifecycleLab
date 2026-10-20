package com.educacion.lifecyclelab.ui.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.educacion.lifecyclelab.tracker.LifecycleTracker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogViewerScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    var fileContent by remember {
        mutableStateOf(LifecycleTracker.readTxtFileContent(context))
    }

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    fun reload() {
        fileContent = LifecycleTracker.readTxtFileContent(context)
    }

    fun shareTxtFile() {
        val file = LifecycleTracker.getLogFile(context)
        if (!file.exists() || file.length() == 0L) {
            Toast.makeText(context, "El archivo aún no contiene registros", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val authority = "${context.packageName}.fileprovider"
            val uri = FileProvider.getUriForFile(context, authority, file)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "Registro de Ciclos de Vida Android")
                putExtra(Intent.EXTRA_TEXT, "Adjunto el registro de ciclos de vida generado por la app LifecycleLab.")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Compartir archivo TXT con..."))
        } catch (e: Exception) {
            Toast.makeText(context, "Error al compartir: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Visor de Archivo TXT") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { reload() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Recargar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF37474F),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Fila de botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { shareTxtFile() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Compartir")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Exportar TXT")
                }

                OutlinedButton(
                    onClick = {
                        LifecycleTracker.clearLogs(context)
                        reload()
                        Toast.makeText(context, "Registro borrado", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Borrar TXT", color = Color.Red)
                }
            }

            Text(
                text = "Ruta interna: ${LifecycleTracker.getLogFile(context).absolutePath}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                fontSize = 10.sp
            )

            // Contenedor tipo Terminal / Bloc de notas para ver el TXT
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF1E1E1E))
                    .padding(12.dp)
            ) {
                Text(
                    text = fileContent,
                    color = Color(0xFFD4D4D4),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier
                        .verticalScroll(verticalScrollState)
                        .horizontalScroll(horizontalScrollState)
                )
            }
        }
    }
}
