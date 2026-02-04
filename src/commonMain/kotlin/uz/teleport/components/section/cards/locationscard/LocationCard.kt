package uz.teleport.components.section.cards.locationscard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_from
import uz.teleport.resources.ic_arrow_up

/**
 * UI state for [LocationCard].
 *
 * @param text The address text. When `null`, the [placeholder] is shown in a disabled color.
 * @param placeholder Fallback text shown when [text] is `null`.
 * @param icon Optional leading icon (e.g. arrow direction indicator).
 */
data class LocationCardState(
    val text: String? = null,
    val placeholder: String,
    val icon: Painter? = null
)

/**
 * One-time UI effects emitted by [LocationCard].
 */
sealed interface LocationCardEffect {
    /** Emitted when the card is clicked. */
    data object Click : LocationCardEffect
}

/**
 * Default configuration values for [LocationCard].
 *
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object LocationCardDefaults {
    /**
     * Color configuration for [LocationCard].
     *
     * @param text Address text color when [LocationCardState.text] is set.
     * @param placeholder Text color when showing the placeholder.
     * @param icon Leading icon tint color.
     * @param container Background color.
     */
    data class LocationCardColors(
        val text: Color,
        val placeholder: Color,
        val icon: Color,
        val container: Color
    )

    @Composable
    fun colors(
        text: Color = System.color.textDark,
        placeholder: Color = System.color.textDisable,
        icon: Color = System.color.uiIconMuted,
        container: Color = System.color.uiSurface
    ) = LocationCardColors(
        text = text,
        placeholder = placeholder,
        icon = icon,
        container = container
    )

    /**
     * Text style configuration for [LocationCard].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class LocationCardStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.bodySemiBold
    ) = LocationCardStyle(
        contentStyle = contentStyle
    )
}

/**
 * A clickable surface displaying an optional icon and address text.
 * When [LocationCardState.text] is `null`, the [LocationCardState.placeholder]
 * is shown in a disabled color. Emits [LocationCardEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the surface.
 * @param colors Color configuration, defaults to [LocationCardDefaults.colors].
 * @param style Text style configuration, defaults to [LocationCardDefaults.style].
 */
@Composable
fun LocationCard(
    state: LocationCardState,
    onEffect: (LocationCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: LocationCardDefaults.LocationCardColors = LocationCardDefaults.colors(),
    style: LocationCardDefaults.LocationCardStyle = LocationCardDefaults.style(),
) {
    Surface(
        onClick = {onEffect(LocationCardEffect.Click)},
        modifier = modifier,
        color = colors.container
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp),
        ) {
            state.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier.size(24.dp),
                )
            }

            Text(
                text = state.text ?: state.placeholder,
                style = style.contentStyle,
                color = if (state.text == null) colors.placeholder else colors.text,
            )
        }
    }
}

@Preview
@Composable
private fun LocationCardPreview() {
    LocationCard(
        state = LocationCardState(
            text = "Tashkent, Uzbekistan",
            placeholder = stringResource(Res.string.common_from),
            icon = painterResource(Res.drawable.ic_arrow_up),
        ),
        onEffect = {  },
    )
}