package uz.teleport.components.section.cards.routingcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.components.primitive.route.VerticalRoute
import uz.teleport.components.primitive.route.VerticalRouteDefaults
import uz.teleport.design.theme.System

/**
 * UI state for [RoutingInfoCard].
 *
 * @param startAddressPrimary Primary text of the start (origin) address.
 * @param startAddressSecondary Optional secondary text for the start address.
 * @param finishAddressPrimary Primary text of the finish (destination) address.
 * @param finishAddressSecondary Optional secondary text for the finish address.
 */
data class RoutingInfoCardState(
    val startAddressPrimary: String,
    val startAddressSecondary: String? = null,
    val finishAddressPrimary: String,
    val finishAddressSecondary: String? = null
)

/**
 * Default configuration values for [RoutingInfoCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object RoutingInfoCardDefaults {
    /**
     * Color configuration for [RoutingInfoCard].
     *
     * @param container Card background color.
     * @param addressPrimary Primary address text color.
     * @param addressSecondary Secondary address text color.
     * @param icon Icon tint color.
     */
    data class RoutingInfoCardColors(
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
    ) = RoutingInfoCardColors(
        container = container,
        addressPrimary = addressPrimary,
        addressSecondary = addressSecondary,
        icon = icon
    )

    /**
     * Text style configuration for [RoutingInfoCard].
     *
     * @param contentStyle Style applied to all address texts.
     */
    data class RoutingInfoCardStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = RoutingInfoCardStyle(
        contentStyle
    )

    /**
     * Dimension configuration for [RoutingInfoCard].
     *
     * @param radius Corner radius of the card shape.
     * @param routeLineHeight Height of the vertical route line between the two address points.
     */
    data class RoutingInfoCardDimens(
        val radius: Dp,
        val routeLineHeight: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp,
        routeLineHeight: Dp = 32.dp
    ) = RoutingInfoCardDimens(
        radius = radius,
        routeLineHeight = routeLineHeight
    )
}

/**
 * A read-only card displaying start and finish addresses with a [VerticalRoute] indicator.
 * Unlike [RoutingCard], the address rows are not individually clickable.
 *
 * @param state Current UI state.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [RoutingInfoCardDefaults.colors].
 * @param style Text style configuration, defaults to [RoutingInfoCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [RoutingInfoCardDefaults.dimens].
 */
@Composable
fun RoutingInfoCard(
    state: RoutingInfoCardState,
    modifier: Modifier = Modifier,
    colors: RoutingInfoCardDefaults.RoutingInfoCardColors = RoutingInfoCardDefaults.colors(),
    style: RoutingInfoCardDefaults.RoutingInfoCardStyle = RoutingInfoCardDefaults.style(),
    dimens: RoutingInfoCardDefaults.RoutingInfoCardDimens = RoutingInfoCardDefaults.dimens(),
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 12.dp, top = 8.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = state.startAddressPrimary,
                        color = colors.addressPrimary,
                        style = style.contentStyle
                    )

                    state.startAddressSecondary?.let {
                        Text(
                            text = state.startAddressSecondary,
                            color = colors.addressSecondary,
                            style = style.contentStyle
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 12.dp, top = 4.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = state.finishAddressPrimary,
                        color = colors.addressPrimary,
                        style = style.contentStyle
                    )

                    state.finishAddressSecondary?.let {
                        Text(
                            text = it,
                            color = colors.addressSecondary,
                            style = style.contentStyle
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
                        lineHeight = dimens.routeLineHeight
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun RoutingInfoCardPreview() {
    Box(modifier = Modifier
        .background(Color.Gray)
    ) {
        RoutingInfoCard(
            state = RoutingInfoCardState(
                startAddressPrimary = "Toshkent sh.",
                startAddressSecondary = "Yunusobod tumani",
                finishAddressPrimary = "Samarqand sh.",
                finishAddressSecondary = "Markaziy hudud"
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}