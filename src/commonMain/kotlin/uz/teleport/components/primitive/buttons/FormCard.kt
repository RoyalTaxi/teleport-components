package uz.teleport.components.primitive.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_from
import uz.teleport.resources.common_to
import uz.teleport.resources.home_place
import uz.teleport.resources.ic_calender
import uz.teleport.resources.ic_user

/**
 * UI state for [FormCard].
 *
 * @param text The displayed text. When `null`, [placeholder] is shown in a disabled color.
 * @param placeholder Fallback text shown when [text] is `null`.
 * @param icon Optional leading icon.
 */
data class FormCardState(
    val text: String? = null,
    val placeholder: String,
    val icon: Painter? = null
)

/**
 * One-time UI effects emitted by [FormCard].
 */
sealed interface FormCardEffect {
    /** Emitted when the card is clicked. */
    data object Click : FormCardEffect
}

/**
 * Default configuration values for [FormCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object FormCardDefaults {
    /**
     * Color configuration for [FormCard].
     *
     * @param content Text color when [FormCardState.text] is set.
     * @param container Card background color.
     * @param disabledContent Text and icon color when showing placeholder.
     */
    data class FormCardColors(
        val content: Color,
        val container: Color,
        val disabledContent: Color
    ) {
        @Composable
        fun asCardColors() = CardDefaults.cardColors(
            containerColor = container,
        )
    }

    @Composable
    fun colors(
        content: Color = System.color.textDark,
        container: Color = System.color.uiSurface,
        disabledContent: Color = System.color.textDisable
    ) = FormCardColors(
        content = content,
        container = container,
        disabledContent = disabledContent
    )

    /**
     * Text style configuration for [FormCard].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class FormCardStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.bodySemiBold
    ) = FormCardStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [FormCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class FormCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = FormCardDimens(
        radius = radius
    )
}

/**
 * A clickable card that displays an optional leading icon and text content.
 * When [FormCardState.text] is `null`, the [FormCardState.placeholder] is shown
 * in a disabled color. Emits [FormCardEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [FormCardDefaults.colors].
 * @param style Text style configuration, defaults to [FormCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [FormCardDefaults.dimens].
 */
@Composable
fun FormCard(
    state: FormCardState,
    onEffect: (FormCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: FormCardDefaults.FormCardColors = FormCardDefaults.colors(),
    style: FormCardDefaults.FormCardStyle = FormCardDefaults.style(),
    dimens: FormCardDefaults.FormCardDimens = FormCardDefaults.dimens(),
) {
    Card(
        onClick = { onEffect(FormCardEffect.Click) },
        shape = RoundedCornerShape(dimens.radius),
        colors = colors.asCardColors(),
        modifier = modifier
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
                    tint = colors.disabledContent,
                    modifier = Modifier.size(24.dp),
                )
            }

            Text(
                text = state.text ?: state.placeholder,
                style = style.contentStyle,
                color = if (state.text == null) colors.disabledContent else colors.content,
            )
        }
    }
}

@Preview
@Composable
private fun FormCardPreview() {
    Column {
        FormCard(
            state = FormCardState(
                placeholder = stringResource(Res.string.common_from),
                icon = painterResource(Res.drawable.ic_calender)
            ),
            onEffect = {}
        )

        FormCard(
            state = FormCardState(
                placeholder = stringResource(Res.string.common_to),
                icon = null
            ),
            onEffect = {},
        )

        FormCard(
            state = FormCardState(
                text = "Toshkent",
                placeholder = stringResource(Res.string.home_place),
                icon = painterResource(Res.drawable.ic_user),
            ),
            onEffect = {},
        )
    }
}
