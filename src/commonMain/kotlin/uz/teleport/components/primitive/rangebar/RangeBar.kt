package uz.teleport.components.primitive.rangebar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * UI state for [RangeBar].
 *
 * @param min Minimum value of the range.
 * @param max Maximum value of the range.
 * @param values Currently selected range.
 */
data class RangeBarState(
    val min: Float,
    val max: Float,
    val values: ClosedFloatingPointRange<Float>
)

/**
 * Default configuration values for [RangeBar].
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object RangeBarDefaults {
    /**
     * Color configuration for [RangeBar].
     *
     * @param active Color of the selected range track and thumbs.
     * @param inactive Color of the unselected track.
     */
    data class RangeBarColors(
        val active: Color,
        val inactive: Color
    )

    @Composable
    fun colors(
        active: Color = System.color.brandGreen,
        inactive: Color = System.color.uiBorder
    ) = RangeBarColors(
        active = active,
        inactive = inactive
    )

    /**
     * Dimension configuration for [RangeBar].
     *
     * @param height Overall height.
     * @param thumbRadius Radius of the thumb circles.
     * @param trackHeight Thickness of the track line.
     */
    data class RangeBarDimens(
        val height: Dp,
        val thumbRadius: Dp,
        val trackHeight: Dp
    )

    @Composable
    fun dimens(
        height: Dp = 32.dp,
        thumbRadius: Dp = 10.dp,
        trackHeight: Dp = 4.dp
    ) = RangeBarDimens(
        height = height,
        thumbRadius = thumbRadius,
        trackHeight = trackHeight
    )
}

/**
 * A draggable dual-thumb range slider drawn on a Canvas.
 * Allows selecting a value range between [RangeBarState.min] and [RangeBarState.max].
 *
 * @param state Current UI state.
 * @param onValueChange Callback invoked with the new selected range on drag.
 * @param modifier Modifier applied to the range bar.
 * @param colors Color configuration, defaults to [RangeBarDefaults.colors].
 * @param dimens Dimension configuration, defaults to [RangeBarDefaults.dimens].
 */
@Composable
fun RangeBar(
    state: RangeBarState,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    colors: RangeBarDefaults.RangeBarColors = RangeBarDefaults.colors(),
    dimens: RangeBarDefaults.RangeBarDimens = RangeBarDefaults.dimens(),
) {
    var start by remember(state.values) { mutableStateOf(state.values.start) }
    var end by remember(state.values) { mutableStateOf(state.values.endInclusive) }

    Canvas(
        modifier = modifier
            .height(dimens.height)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val width = size.width
                    val percent = (change.position.x / width).coerceIn(0f, 1f)
                    val value = state.min + percent * (state.max - state.min)

                    if (abs(value - start) < abs(value - end)) {
                        start = value.coerceIn(state.min, end)
                    } else {
                        end = value.coerceIn(start, state.max)
                    }

                    onValueChange(start..end)
                }
            }
    ) {
        val centerY = size.height / 2
        val startX = ((start - state.min) / (state.max - state.min)) * size.width
        val endX = ((end - state.min) / (state.max - state.min)) * size.width

        drawLine(
            color = colors.inactive,
            start = Offset(0f, centerY),
            end = Offset(size.width, centerY),
            strokeWidth = dimens.trackHeight.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = colors.active,
            start = Offset(startX, centerY),
            end = Offset(endX, centerY),
            strokeWidth = dimens.trackHeight.toPx(),
            cap = StrokeCap.Round
        )

        drawCircle(
            color = colors.active,
            radius = dimens.thumbRadius.toPx(),
            center = Offset(startX, centerY)
        )
        drawCircle(
            color = colors.active,
            radius = dimens.thumbRadius.toPx(),
            center = Offset(endX, centerY)
        )
    }
}

@Preview
@Composable
private fun RangeBarPreview() {
    var range by remember { mutableStateOf(50_000f..300_000f) }

    val state = RangeBarState(
        min = 50_000f,
        max = 500_000f,
        values = range
    )

    fun roundTo1000(value: Float): Int =
        (value / 1000).roundToInt() * 1000

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "${roundTo1000(range.start)} – ${roundTo1000(range.endInclusive)} сум",
            style = System.type.body,
            color = System.color.uiDark
        )

        Spacer(modifier = Modifier.height(16.dp))

        RangeBar(
            state = state,
            onValueChange = { range = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
