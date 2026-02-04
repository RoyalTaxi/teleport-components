package uz.teleport.components.section.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_whole_salon

/**
 * UI state for [SwitchableItem].
 *
 * @param text Label text displayed next to the switch.
 * @param isSwitchEnabled Whether the switch is currently toggled on.
 */
data class SwitchableItemState(
    val text: String,
    val isSwitchEnabled: Boolean
)

/**
 * One-time UI effects emitted by [SwitchableItem].
 */
sealed interface SwitchableItemEffect {
    /** Emitted when the switch or the item row is clicked. */
    data object ClickSwitch : SwitchableItemEffect
}

/**
 * Default configuration values for [SwitchableItem].
 *
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object SwitchableItemDefaults {
    /**
     * Color configuration for [SwitchableItem].
     *
     * @param container Row background color.
     * @param text Label text color.
     * @param switch Track color when the switch is enabled.
     * @param switchDisable Track color when the switch is disabled.
     * @param switchThumb Thumb color when the switch is enabled.
     * @param switchThumbDisable Thumb color when the switch is disabled.
     */
    data class SwitchableItemColors(
        val container: Color,
        val text: Color,
        val switch: Color,
        val switchDisable: Color,
        val switchThumb: Color,
        val switchThumbDisable: Color,
    ) {
        @Composable
        fun asSwitchColors() = SwitchDefaults.colors(
            checkedThumbColor = switchThumb,
            uncheckedThumbColor = switchThumbDisable,
            checkedTrackColor = switch,
            uncheckedTrackColor = switchDisable,
            checkedBorderColor = switch,
            uncheckedBorderColor = switchDisable
        )
    }

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        text: Color = System.color.textDark,
        switch: Color = System.color.brandGreen,
        switchDisable: Color = System.color.uiSurfaceSubtle,
        switchThumb: Color = System.color.uiSurface,
        switchThumbDisable: Color = System.color.uiSurface,
        ) = SwitchableItemColors(
        container = container,
        text = text,
        switch = switch,
        switchDisable = switchDisable,
        switchThumb = switchThumb,
        switchThumbDisable = switchThumbDisable
    )

    /**
     * Text style configuration for [SwitchableItem].
     *
     * @param contentStyle Style applied to the label text.
     */
    data class SwitchableItemStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = SwitchableItemStyle(
        contentStyle
    )
}

/**
 * A full-width row displaying a text label and a toggle switch.
 * Emits [SwitchableItemEffect.ClickSwitch] when the row or the switch is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the item.
 * @param colors Color configuration, defaults to [SwitchableItemDefaults.colors].
 * @param style Text style configuration, defaults to [SwitchableItemDefaults.style].
 */
@Composable
fun SwitchableItem(
    state: SwitchableItemState,
    onEffect: (SwitchableItemEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SwitchableItemDefaults.SwitchableItemColors = SwitchableItemDefaults.colors(),
    style: SwitchableItemDefaults.SwitchableItemStyle = SwitchableItemDefaults.style(),
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        onClick = { onEffect(SwitchableItemEffect.ClickSwitch) },
        colors = CardDefaults.cardColors(
            containerColor = colors.container
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = state.text,
                style = style.contentStyle,
                color = colors.text
            )

            Switch(
                checked = state.isSwitchEnabled,
                onCheckedChange = { onEffect(SwitchableItemEffect.ClickSwitch) },
                colors = colors.asSwitchColors()
            )
        }
    }
}

@Preview
@Composable
private fun SwitchableItemPreview() {
    var isEnabled by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.background(Color.LightGray)
    ) {
        SwitchableItem(
            state = SwitchableItemState(
                text = stringResource(Res.string.common_whole_salon),
                isSwitchEnabled = isEnabled
            ),
            onEffect = { isEnabled = !isEnabled },
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
