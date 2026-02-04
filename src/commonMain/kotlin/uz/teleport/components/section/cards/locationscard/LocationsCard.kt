package uz.teleport.components.section.cards.locationscard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_from
import uz.teleport.resources.common_to
import uz.teleport.resources.ic_arrow_down
import uz.teleport.resources.ic_arrow_up
import uz.teleport.resources.ic_plus

/**
 * UI state for [LocationsCard].
 *
 * @param pickupAddress Pickup location text. When `null`, a placeholder is shown.
 * @param destinationAddress Destination location text. When `null`, a placeholder is shown.
 * @param iconSwitchDirection Optional icon for the direction-swap button. When `null`, the button is hidden.
 */
data class LocationsCardState(
    val pickupAddress: String? = null,
    val destinationAddress: String? = null,
    val iconSwitchDirection: Painter? = null
)

/**
 * One-time UI effects emitted by [LocationsCard].
 */
sealed interface LocationsCardEffect {
    /** Emitted when the pickup location row is clicked. */
    data object ClickPickup : LocationsCardEffect
    /** Emitted when the destination location row is clicked. */
    data object ClickDestination : LocationsCardEffect
    /** Emitted when the direction-swap button is clicked. */
    data object ClickSwitchDirection : LocationsCardEffect
}

/**
 * Default configuration values for [LocationsCard].
 *
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object LocationsCardDefaults {
    /**
     * Color configuration for [LocationsCard].
     *
     * @param container Card background color.
     * @param border Divider and swap-button border color.
     */
    data class LocationsCardColors(
        val container: Color,
        val border: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        border: Color = System.color.uiBorder
    ) = LocationsCardColors(
        container = container,
        border = border
    )

    /**
     * Dimension configuration for [LocationsCard].
     *
     * @param radius Corner radius of the outer card shape.
     * @param border Width of dividers and the swap-button border.
     */
    data class LocationsCardDimens(
        val radius: Dp,
        val border: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp,
        border: Dp = 1.dp
    ) = LocationsCardDimens(
        radius = radius,
        border = border
    )
}

/**
 * A card containing two stacked [LocationCard] rows (pickup and destination)
 * separated by a divider, with an optional circular direction-swap button.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the container.
 * @param colors Color configuration, defaults to [LocationsCardDefaults.colors].
 * @param dimens Dimension configuration, defaults to [LocationsCardDefaults.dimens].
 */
@Composable
fun LocationsCard(
    state: LocationsCardState,
    onEffect: (LocationsCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: LocationsCardDefaults.LocationsCardColors = LocationsCardDefaults.colors(),
    dimens: LocationsCardDefaults.LocationsCardDimens = LocationsCardDefaults.dimens(),
) {
    Box(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(size = dimens.radius),
            colors = CardDefaults.cardColors(colors.container)
        ) {
            LocationCard(
                state = LocationCardState(
                    text = state.pickupAddress,
                    placeholder = stringResource(Res.string.common_from),
                    icon = painterResource(Res.drawable.ic_arrow_up),
                ),
                onEffect = {onEffect(LocationsCardEffect.ClickPickup)},
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(
                color = colors.border,
                modifier = Modifier.height(dimens.border)
            )

            LocationCard(
                state = LocationCardState(
                    text = state.destinationAddress,
                    placeholder = stringResource(Res.string.common_to),
                    icon = painterResource(Res.drawable.ic_arrow_down),
                ),
                onEffect = {onEffect(LocationsCardEffect.ClickDestination)},
                modifier = Modifier.fillMaxWidth()
            )
        }

        state.iconSwitchDirection?.let {
            Card(
                onClick = { onEffect(LocationsCardEffect.ClickSwitchDirection) },
                shape = CircleShape,
                border = BorderStroke(
                    width = dimens.border,
                    color = colors.border
                ),
                colors = CardDefaults.cardColors(
                    containerColor = colors.container
                ),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .size(32.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocationsCardPreview() {
    Column {
        LocationsCard(
            state = LocationsCardState(
                pickupAddress = "Picked address",
                destinationAddress = "Destination address",
                iconSwitchDirection = painterResource(Res.drawable.ic_plus)
            ),
            onEffect = {
                when (it) {
                    LocationsCardEffect.ClickPickup -> {}
                    LocationsCardEffect.ClickDestination -> {}
                    LocationsCardEffect.ClickSwitchDirection -> {}
                }
            },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        LocationsCard(
            state = LocationsCardState(
                pickupAddress = "Picked address",
            ),
            onEffect = {
                when (it) {
                    LocationsCardEffect.ClickPickup -> {}
                    LocationsCardEffect.ClickDestination -> {}
                    LocationsCardEffect.ClickSwitchDirection -> {}
                }
            },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        LocationsCard(
            state = LocationsCardState(
                destinationAddress = "Destination address"
            ),
            onEffect = {
                when (it) {
                    LocationsCardEffect.ClickPickup -> {}
                    LocationsCardEffect.ClickDestination -> {}
                    LocationsCardEffect.ClickSwitchDirection -> {}
                }
            },
            modifier = Modifier
        )
    }
}