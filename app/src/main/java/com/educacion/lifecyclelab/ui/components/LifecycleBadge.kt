package com.educacion.lifecyclelab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.educacion.lifecyclelab.tracker.LifecycleStage
import com.educacion.lifecyclelab.ui.theme.ColorCreate
import com.educacion.lifecyclelab.ui.theme.ColorDestroy
import com.educacion.lifecyclelab.ui.theme.ColorPause
import com.educacion.lifecyclelab.ui.theme.ColorRestart
import com.educacion.lifecyclelab.ui.theme.ColorResume
import com.educacion.lifecyclelab.ui.theme.ColorSaveState
import com.educacion.lifecyclelab.ui.theme.ColorStart
import com.educacion.lifecyclelab.ui.theme.ColorStop

fun getStageColor(stage: LifecycleStage): Color {
    return when (stage) {
        LifecycleStage.ON_CREATE -> ColorCreate
        LifecycleStage.ON_START -> ColorStart
        LifecycleStage.ON_RESUME -> ColorResume
        LifecycleStage.ON_PAUSE -> ColorPause
        LifecycleStage.ON_STOP -> ColorStop
        LifecycleStage.ON_RESTART -> ColorRestart
        LifecycleStage.ON_DESTROY -> ColorDestroy
        LifecycleStage.ON_SAVE_INSTANCE_STATE,
        LifecycleStage.ON_RESTORE_INSTANCE_STATE -> ColorSaveState
    }
}

@Composable
fun LifecycleBadge(
    stage: LifecycleStage,
    modifier: Modifier = Modifier
) {
    val badgeColor = getStageColor(stage)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(badgeColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "${stage.methodName}()",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
        )
    }
}
