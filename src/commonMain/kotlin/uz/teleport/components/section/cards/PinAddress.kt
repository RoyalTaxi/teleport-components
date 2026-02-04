package uz.teleport.components.section.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import uz.teleport.design.theme.System

/**
 * UI state for [PinAddress].
 *
 * @param title Primary address text. When `null`, an empty string is displayed.
 * @param description Secondary region or area text. When `null`, an empty string is displayed.
 */
data class PinAddressState(
    val title: String? = null,
    val description: String? = null
)

/**
 * Default configuration values for [PinAddress].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object PinAddressDefaults {
    /**
     * Color configuration for [PinAddress].
     *
     * @param title Title text color.
     * @param description Description text color.
     * @param container Card background color.
     */
    data class PinAddressColors(
        val title: Color,
        val description: Color,
        val container: Color
    )

    @Composable
    fun colors(
        title: Color = System.color.textLight,
        description: Color = System.color.textDisable,
        container: Color = System.color.uiDark
    ) = PinAddressColors(
        title = title,
        description = description,
        container = container
    )

    /**
     * Text style configuration for [PinAddress].
     *
     * @param titleStyle Style applied to the title text.
     * @param descriptionStyle Style applied to the description text.
     */
    data class PinAddressStyle(
        val titleStyle: TextStyle,
        val descriptionStyle: TextStyle
    )

    @Composable
    fun style(
        titleStyle: TextStyle = System.type.caption,
        descriptionStyle: TextStyle = System.type.small,
    ) = PinAddressStyle(
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle
    )

    /**
     * Dimension configuration for [PinAddress].
     *
     * @param radius Corner radius of the card shape.
     */
    data class PinAddressDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = PinAddressDimens(
        radius = radius
    )
}

/**
 * A compact dark-themed card displaying a pin address with title and description,
 * typically overlaid on a map view.
 *
 * @param state Current UI state.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [PinAddressDefaults.colors].
 * @param style Text style configuration, defaults to [PinAddressDefaults.style].
 * @param dimens Dimension configuration, defaults to [PinAddressDefaults.dimens].
 */
@Composable
fun PinAddress(
    state: PinAddressState,
    modifier: Modifier = Modifier,
    colors: PinAddressDefaults.PinAddressColors = PinAddressDefaults.colors(),
    style: PinAddressDefaults.PinAddressStyle = PinAddressDefaults.style(),
    dimens: PinAddressDefaults.PinAddressDimens = PinAddressDefaults.dimens(),
) {
    Card(
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp),
        ) {
            Text(
                text = state.title ?: "",
                color = colors.title,
                style = style.titleStyle
            )

            Text(
                text = state.description ?: "",
                color = colors.description,
                style = style.descriptionStyle
            )
        }
    }
}

@Preview
@Composable
private fun PinAddressPreview() {
    Box(modifier = Modifier
        .background(Color.Gray)
    ) {
        PinAddress(
            state = PinAddressState(
                title = "Address address",
                description = "Region"
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}