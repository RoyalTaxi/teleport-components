package uz.teleport.components.primitive.buttons.segented

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * UI state for [SegmentedRadio].
 *
 * @param text The label text.
 * @param isSelected Whether this radio option is currently selected.
 */
data class SegmentedRadioState(
    val text: String,
    val isSelected: Boolean = false
)

/**
 * One-time UI effects emitted by [SegmentedRadio].
 */
sealed interface SegmentedRadioEffect {
    /** Emitted when the radio is clicked. */
    data object Click : SegmentedRadioEffect
}

/**
 * Default configuration values for [SegmentedRadio].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object SegmentedRadioDefaults {
    /**
     * Color configuration for [SegmentedRadio].
     *
     * @param content The text color when the radio is selected.
     * @param container The background color when the radio is selected.
     * @param disabledContent The text color when the radio is unselected.
     * @param disabledContainer The background color when the radio is unselected.
     */
    data class SegmentedRadioColors(
        val content: Color,
        val container: Color,
        val disabledContent: Color,
        val disabledContainer: Color
    )

    @Composable
    fun colors(
        content: Color = System.color.uiSurface,
        container: Color = System.color.brandGreen,
        disabledContent: Color = System.color.textDisable,
        disabledContainer: Color = System.color.uiSurfaceSubtle
    ) = SegmentedRadioColors(
        content = content,
        container = container,
        disabledContent = disabledContent,
        disabledContainer = disabledContainer
    )

    /**
     * Text style configuration for [SegmentedRadio].
     *
     * @param contentStyle The text style applied to the label.
     */
    data class SegmentedRadioStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.button
    ) = SegmentedRadioStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [SegmentedRadio].
     *
     * @param radius The corner radius of the radio chip.
     * @param padding The inner padding around the label text.
     */
    data class SegmentedRadioDimens(
        val radius: Dp,
        val padding: PaddingValues
    )

    @Composable
    fun dimens(
        radius: Dp = 8.dp,
        padding: PaddingValues = PaddingValues(
            vertical = 4.dp,
            horizontal = 16.dp
        )
    ) = SegmentedRadioDimens(
        radius = radius,
        padding = padding
    )
}

/**
 * A selectable radio-style chip that toggles between selected and unselected states.
 *
 * Changes appearance based on [SegmentedRadioState.isSelected].
 * Emits [SegmentedRadioEffect.Click] when tapped.
 *
 * @param state The current UI state of the radio chip.
 * @param onEffect Callback invoked when a [SegmentedRadioEffect] is emitted.
 * @param modifier Modifier applied to the root layout.
 * @param colors Color configuration; defaults to [SegmentedRadioDefaults.colors].
 * @param style Text style configuration; defaults to [SegmentedRadioDefaults.style].
 * @param dimens Dimension configuration; defaults to [SegmentedRadioDefaults.dimens].
 */
@Composable
fun SegmentedRadio(
    state: SegmentedRadioState,
    onEffect: (SegmentedRadioEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SegmentedRadioDefaults.SegmentedRadioColors = SegmentedRadioDefaults.colors(),
    style: SegmentedRadioDefaults.SegmentedRadioStyle = SegmentedRadioDefaults.style(),
    dimens: SegmentedRadioDefaults.SegmentedRadioDimens = SegmentedRadioDefaults.dimens(),
) {
    Surface(
        shape = RoundedCornerShape(dimens.radius),
        color = if (state.isSelected) colors.container else colors.disabledContainer,
        contentColor = if (state.isSelected) colors.content else colors.disabledContent,
        onClick = { onEffect(SegmentedRadioEffect.Click) },
        modifier = modifier,
    ) {
        Text(
            text = state.text,
            style = style.contentStyle,
            modifier = Modifier.padding(dimens.padding),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun SegmentedRadioPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SegmentedRadio(
            state = SegmentedRadioState(
                text = "Today",
                isSelected = true,
            ),
            onEffect = {},
        )

        SegmentedRadio(
            state = SegmentedRadioState(
                text = "Tomorrow",
                isSelected = false,
            ),
            onEffect = {},
        )
    }
}
