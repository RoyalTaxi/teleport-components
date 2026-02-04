package uz.teleport.components.primitive.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_plus

/**
 * UI state for [PrimaryButton].
 *
 * @param icon Optional leading icon displayed before the text label.
 * @param text The text label displayed on the button.
 * @param enabled Whether the button is interactive; when `false`, disabled colors are applied.
 */
data class PrimaryButtonState(
    val icon: Painter? = null,
    val text: String,
    val enabled: Boolean = true,
)

/**
 * One-time UI effects emitted by [PrimaryButton].
 */
sealed interface PrimaryButtonEffect {
    /** Emitted when the button is clicked. */
    data object Click : PrimaryButtonEffect
}

/**
 * Default configuration values for [PrimaryButton].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object PrimaryButtonDefaults {
    /**
     * Color configuration for [PrimaryButton].
     *
     * @param content Text and icon color in the enabled state.
     * @param container Background color in the enabled state.
     * @param disabledContent Text and icon color in the disabled state.
     * @param disabledContainer Background color in the disabled state.
     */
    data class PrimaryButtonColors(
        val content: Color,
        val container: Color,
        val disabledContent: Color,
        val disabledContainer: Color
    ) {
        @Composable
        fun asButtonColors() = ButtonDefaults.buttonColors(
            contentColor = content,
            containerColor = container,
            disabledContentColor = disabledContent,
            disabledContainerColor = disabledContainer
        )
    }

    @Composable
    fun colors(
        content: Color = System.color.uiSurface,
        container: Color = System.color.brandGreen,
        disabledContent: Color = System.color.textDisable,
        disabledContainer: Color = System.color.uiBorder
    ) = PrimaryButtonColors(
        content = content,
        container = container,
        disabledContent = disabledContent,
        disabledContainer = disabledContainer
    )

    /**
     * Text style configuration for [PrimaryButton].
     *
     * @param contentStyle Style applied to the button text label.
     */
    data class PrimaryButtonStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = PrimaryButtonStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [PrimaryButton].
     *
     * @param radius Corner radius of the button shape.
     * @param padding Content padding inside the button.
     */
    data class PrimaryButtonDimens(
        val radius: Dp,
        val padding: PaddingValues
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp,
        padding: PaddingValues = PaddingValues(
            vertical = 12.dp,
            horizontal = 32.dp,
        )
    ) = PrimaryButtonDimens(
        radius = radius,
        padding = padding
    )
}

/**
 * A primary action button that displays an optional leading icon and a text label.
 * Supports enabled and disabled states with distinct color schemes.
 * Emits [PrimaryButtonEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the button.
 * @param colors Color configuration, defaults to [PrimaryButtonDefaults.colors].
 * @param style Text style configuration, defaults to [PrimaryButtonDefaults.style].
 * @param dimens Dimension configuration, defaults to [PrimaryButtonDefaults.dimens].
 */
@Composable
fun PrimaryButton(
    state: PrimaryButtonState,
    onEffect: (PrimaryButtonEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: PrimaryButtonDefaults.PrimaryButtonColors = PrimaryButtonDefaults.colors(),
    style: PrimaryButtonDefaults.PrimaryButtonStyle = PrimaryButtonDefaults.style(),
    dimens: PrimaryButtonDefaults.PrimaryButtonDimens = PrimaryButtonDefaults.dimens(),
) {
    Button(
        shape = RoundedCornerShape(dimens.radius),
        enabled = state.enabled,
        colors = colors.asButtonColors(),
        contentPadding = dimens.padding,
        onClick = { onEffect(PrimaryButtonEffect.Click) },
        modifier = modifier
    ) {
        state.icon?.let {
            Icon(
                painter = it,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = state.text,
            style = style.contentStyle,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        PrimaryButton(
            state = PrimaryButtonState(
                text = "Lorem",
                enabled = true
            ),
            onEffect = {}
        )

        PrimaryButton(
            state = PrimaryButtonState(
                text = "Lorem",
                icon = painterResource(Res.drawable.ic_plus),
                enabled = true
            ),
            onEffect = {}
        )

        PrimaryButton(
            state = PrimaryButtonState(
                text = "Lorem",
                enabled = false
            ),
            onEffect = {}
        )
    }
}