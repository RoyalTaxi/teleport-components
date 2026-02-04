package uz.teleport.components.section.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.components.primitive.buttons.PrimaryButton
import uz.teleport.components.primitive.buttons.PrimaryButtonState
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_action_create
import uz.teleport.resources.home_create_trip
import uz.teleport.resources.home_create_trip_details
import uz.teleport.resources.ic_plus

/**
 * UI state for [PrimaryActionCard].
 *
 * @param title Heading text displayed at the top of the card.
 * @param description Optional subtitle text shown below the title.
 * @param actionIcon Optional icon displayed inside the action button.
 * @param actionTitle Text label of the primary action button.
 */
data class PrimaryActionCardState(
    val title: String,
    val description: String? = null,
    val actionIcon: Painter? = null,
    val actionTitle: String
)

/**
 * One-time UI effects emitted by [PrimaryActionCard].
 */
sealed interface PrimaryActionCardEffect {
    /** Emitted when the primary action button is clicked. */
    data object ClickAction : PrimaryActionCardEffect
}

/**
 * Default configuration values for [PrimaryActionCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object PrimaryActionCardDefaults {
    /**
     * Color configuration for [PrimaryActionCard].
     *
     * @param container Card background color.
     * @param title Title text color.
     * @param description Description text color.
     */
    data class PrimaryActionCardColors(
        val container: Color,
        val title: Color,
        val description: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        title: Color = System.color.textDark,
        description: Color = System.color.textDisable
    ) = PrimaryActionCardColors(
        container = container,
        title = title,
        description = description
    )

    /**
     * Text style configuration for [PrimaryActionCard].
     *
     * @param titleStyle Style applied to the title text.
     * @param descriptionStyle Style applied to the description text.
     */
    data class PrimaryActionCardStyle(
        val titleStyle: TextStyle,
        val descriptionStyle: TextStyle
    )

    @Composable
    fun style(
        titleStyle: TextStyle = System.type.h2,
        descriptionStyle: TextStyle = System.type.caption
    ) = PrimaryActionCardStyle(
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle
    )

    /**
     * Dimension configuration for [PrimaryActionCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class PrimaryActionCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 16.dp
    ) = PrimaryActionCardDimens(
        radius = radius
    )
}

/**
 * A card displaying a title, optional description, and a full-width [PrimaryButton].
 * Emits [PrimaryActionCardEffect.ClickAction] when the button is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [PrimaryActionCardDefaults.colors].
 * @param style Text style configuration, defaults to [PrimaryActionCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [PrimaryActionCardDefaults.dimens].
 */
@Composable
fun PrimaryActionCard(
    state: PrimaryActionCardState,
    onEffect: (PrimaryActionCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: PrimaryActionCardDefaults.PrimaryActionCardColors = PrimaryActionCardDefaults.colors(),
    style: PrimaryActionCardDefaults.PrimaryActionCardStyle = PrimaryActionCardDefaults.style(),
    dimens: PrimaryActionCardDefaults.PrimaryActionCardDimens = PrimaryActionCardDefaults.dimens(),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = state.title,
                style = style.titleStyle,
                color = colors.title
            )

            Spacer(modifier = Modifier.height(4.dp))

            state.description?.let {
                Text(
                    text = state.description,
                    style = style.descriptionStyle,
                    color = colors.description
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton(
                state = PrimaryButtonState(
                    icon = state.actionIcon,
                    text = state.actionTitle
                ),
                onEffect = { onEffect(PrimaryActionCardEffect.ClickAction) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun PrimaryActionCardPreview() {
    Box(modifier = Modifier.background(Color.LightGray)) {
        PrimaryActionCard(
            state = PrimaryActionCardState(
                title = stringResource(Res.string.home_create_trip),
                description = stringResource(Res.string.home_create_trip_details),
                actionTitle = stringResource(Res.string.common_action_create),
                actionIcon = painterResource(Res.drawable.ic_plus),
            ),
            onEffect = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}