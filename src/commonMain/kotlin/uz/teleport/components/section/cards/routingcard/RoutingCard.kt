package uz.teleport.components.section.cards.routingcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.components.primitive.route.VerticalRoute
import uz.teleport.components.primitive.route.VerticalRouteDefaults
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_arrow_right

/**
 * UI state for [RoutingCard].
 *
 * @param startAddressPrimary Primary text of the start (origin) address.
 * @param startAddressSecondary Optional secondary text for the start address (e.g. district).
 * @param finishAddressPrimary Primary text of the finish (destination) address.
 * @param finishAddressSecondary Optional secondary text for the finish address.
 */
data class RoutingCardState(
    val startAddressPrimary: String,
    val startAddressSecondary: String? = null,
    val finishAddressPrimary: String,
    val finishAddressSecondary: String? = null
)

/**
 * One-time UI effects emitted by [RoutingCard].
 */
sealed interface RoutingCardEffect {
    /** Emitted when the start location row is clicked. */
    data object ClickStartLocation : RoutingCardEffect
    /** Emitted when the finish location row is clicked. */
    data object ClickFinishLocation : RoutingCardEffect
}

/**
 * Default configuration values for [RoutingCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object RoutingCardDefaults {
    /**
     * Color configuration for [RoutingCard].
     *
     * @param container Card background color.
     * @param addressPrimary Primary address text color.
     * @param addressSecondary Secondary address text color.
     * @param icon Trailing arrow icon tint color.
     */
    data class RoutingCardColors(
        val container: Color,
        val addressPrimary: Color,
        val addressSecondary: Color,
        val icon: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        addressPrimary: Color = System.color.textDark,
        addressSecondary: Color = System.color.textDisable,
        icon: Color = System.color.uiDark
    ) = RoutingCardColors(
        container = container,
        addressPrimary = addressPrimary,
        addressSecondary = addressSecondary,
        icon = icon
    )

    /**
     * Text style configuration for [RoutingCard].
     *
     * @param contentStyle Style applied to all address texts.
     */
    data class RoutingCardStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = RoutingCardStyle(
        contentStyle
    )

    /**
     * Dimension configuration for [RoutingCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class RoutingCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = RoutingCardDimens(
        radius = radius
    )
}

/**
 * A clickable card displaying start and finish addresses with a [VerticalRoute] indicator.
 * Each address row is independently clickable. Emits [RoutingCardEffect.ClickStartLocation]
 * or [RoutingCardEffect.ClickFinishLocation] when the respective row is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [RoutingCardDefaults.colors].
 * @param style Text style configuration, defaults to [RoutingCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [RoutingCardDefaults.dimens].
 */
@Composable
fun RoutingCard(
    state: RoutingCardState,
    onEffect: (RoutingCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: RoutingCardDefaults.RoutingCardColors = RoutingCardDefaults.colors(),
    style: RoutingCardDefaults.RoutingCardStyle = RoutingCardDefaults.style(),
    dimens: RoutingCardDefaults.RoutingCardDimens = RoutingCardDefaults.dimens(),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEffect(RoutingCardEffect.ClickStartLocation) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(start = 32.dp, end = 12.dp, top = 8.dp, bottom = 4.dp)
                    ) {
                        Column {
                            Text(
                                text = state.startAddressPrimary,
                                color = colors.addressPrimary,
                                style = style.contentStyle
                            )

                            state.startAddressSecondary?.let {
                                Text(
                                    text = it,
                                    color = colors.addressSecondary,
                                    style = style.contentStyle
                                )
                            }
                        }

                        Icon(
                            painter = painterResource(Res.drawable.ic_arrow_right),
                            contentDescription = null,
                            tint = colors.icon,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {onEffect(RoutingCardEffect.ClickFinishLocation)}
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(start = 32.dp, end = 12.dp, top = 4.dp, bottom = 8.dp)
                    ) {
                        Column {
                            Text(
                                text = state.finishAddressPrimary,
                                color = colors.addressPrimary,
                                style = style.contentStyle
                            )

                            state.finishAddressSecondary?.let {
                                Text(
                                    text = state.finishAddressSecondary,
                                    color = colors.addressSecondary,
                                    style = style.contentStyle
                                )
                            }
                        }

                        Icon(
                            painter = painterResource(Res.drawable.ic_arrow_right),
                            contentDescription = null,
                            tint = colors.icon,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.width(32.dp),
                contentAlignment = Alignment.Center
            ) {
                VerticalRoute(
                    dimens = VerticalRouteDefaults.dimens(
                        lineHeight = 32.dp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun RoutingCardPreview() {
    Box(modifier = Modifier.background(Color.Gray)) {
        RoutingCard(
            state = RoutingCardState(
                startAddressPrimary = "Toshkent sh.",
                startAddressSecondary = "Yunusobod tumani",
                finishAddressPrimary = "Samarqand sh.",
                finishAddressSecondary = "Markaziy hudud",
            ),
            onEffect = {
                when (it) {
                    RoutingCardEffect.ClickStartLocation -> {}
                    RoutingCardEffect.ClickFinishLocation -> {}
                }
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}
