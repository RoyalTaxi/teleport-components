package uz.teleport.components.section.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_arrow_right
import uz.teleport.resources.ic_location_point

/**
 * UI state for [NavigableCard].
 *
 * @param iconLeading Optional icon displayed at the start of the card.
 * @param iconTrailing Optional icon displayed at the end of the card.
 * @param text The primary text content.
 */
data class NavigableCardState(
    val iconLeading: Painter? = null,
    val iconTrailing: Painter? = null,
    val text: String
)

/**
 * One-time UI effects emitted by [NavigableCard].
 */
sealed interface NavigableCardEffect {
    /** Emitted when the card is clicked. */
    data object Click : NavigableCardEffect
}

/**
 * Default configuration values for [NavigableCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object NavigableCardDefaults {
    /**
     * Color configuration for [NavigableCard].
     *
     * @param container Card background color.
     * @param iconLeading Tint color for the leading icon.
     * @param iconTrailing Tint color for the trailing icon.
     * @param text Text content color.
     */
    data class NavigableCardColors(
        val container: Color,
        val iconLeading: Color,
        val iconTrailing: Color,
        val text: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        iconLeading: Color = System.color.uiIconMuted,
        iconTrailing: Color = System.color.uiDark,
        text: Color = System.color.textDark
    ) = NavigableCardColors(
        container = container,
        iconLeading = iconLeading,
        iconTrailing = iconTrailing,
        text = text
    )

    /**
     * Text style configuration for [NavigableCard].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class NavigableCardStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = NavigableCardStyle(
        contentStyle
    )

    /**
     * Dimension configuration for [NavigableCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class NavigableCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = NavigableCardDimens(
        radius = radius
    )
}

/**
 * A clickable card with optional leading and trailing icons and text content.
 * Emits [NavigableCardEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [NavigableCardDefaults.colors].
 * @param style Text style configuration, defaults to [NavigableCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [NavigableCardDefaults.dimens].
 */
@Composable
fun NavigableCard(
    state: NavigableCardState,
    onEffect: (NavigableCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: NavigableCardDefaults.NavigableCardColors = NavigableCardDefaults.colors(),
    style: NavigableCardDefaults.NavigableCardStyle = NavigableCardDefaults.style(),
    dimens: NavigableCardDefaults.NavigableCardDimens = NavigableCardDefaults.dimens(),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container),
        onClick = { onEffect(NavigableCardEffect.Click) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            state.iconLeading?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = colors.iconLeading,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = state.text,
                color = colors.text,
                style = style.contentStyle,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            state.iconTrailing?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = colors.iconTrailing,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun NavigableCardPreview() {
    Box(modifier = Modifier.background(Color.Gray)) {
        NavigableCard(
            state = NavigableCardState(
                iconLeading = painterResource(Res.drawable.ic_location_point),
                iconTrailing = painterResource(Res.drawable.ic_arrow_right),
                text = "Text text",
            ),
            onEffect = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}