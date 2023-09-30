package dev.kevalkanapriya.customtextfield.ui.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp

@ExperimentalMaterial3Api
@Composable
fun CustomFilledContainerBox(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: InteractionSource,
    containerColor: Color,
    indicatorColor: Color,
    shape: Shape = TextFieldDefaults.filledShape,
) {
    Box(
        Modifier
            .background(containerColor, shape)
            .customIndicatorLine(enabled, isError, interactionSource, indicatorColor = indicatorColor))
}

@ExperimentalMaterial3Api
fun Modifier.customIndicatorLine(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: InteractionSource,
    indicatorColor: Color,
    focusedIndicatorLineThickness: Dp = TextFieldDefaults.UnfocusedBorderThickness,
) = composed(inspectorInfo = debugInspectorInfo {

}) {

    val stroke = BorderStroke(focusedIndicatorLineThickness, SolidColor(indicatorColor))
    Modifier.drawIndicatorLine(stroke)
}

fun Modifier.drawIndicatorLine(indicatorBorder: BorderStroke): Modifier {
    val strokeWidthDp = indicatorBorder.width
    return drawWithContent {
        drawContent()
        if (strokeWidthDp == Dp.Hairline) return@drawWithContent
        val strokeWidth = strokeWidthDp.value * density
        val y = size.height - strokeWidth / 2
        drawLine(
            indicatorBorder.brush,
            Offset(0f, y),
            Offset(size.width, y),
            strokeWidth
        )
    }
}