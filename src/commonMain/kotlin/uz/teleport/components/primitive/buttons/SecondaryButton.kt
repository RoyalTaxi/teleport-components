package uz.teleport.components.primitive.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
 * UI state for [SecondaryButton].
 *
 * @param icon Optional leading icon painter displayed before the text.
 * @param text Label displayed inside the button.
 * @param enabled Whether the button is interactive. When `false`, disabled colors are applied.
 */
data class SecondaryButtonState(
    val icon: Painter? = null,
    val text: String,
    val enabled: Boolean = true
)

/** One-time UI effects emitted by [SecondaryButton]. */
sealed interface SecondaryButtonEffect {
    /** The user tapped the button. */
    data object Click : SecondaryButtonEffect
}

/**
 * Default configuration values for [SecondaryButton].
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object SecondaryButtonDefaults {
    /**
     * Color configuration for [SecondaryButton].
     *
     * @param container Background color of the button in the enabled state.
     * @param content Content (text and icon) color in the enabled state.
     * @param disabledContainer Background color of the button in the disabled state.
     * @param disabledContent Content (text and icon) color in the disabled state.
     */
    data class SecondaryButtonColors(
        val container: Color,
        val content: Color,
        val disabledContainer: Color,
        val disabledContent: Color
    ) {
        @Composable
        fun asButtonColors() = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = content,
            disabledContainerColor = disabledContainer,
            disabledContentColor = disabledContent
        )
    }

    @Composable
    fun colors(
        container: Color = System.color.uiDark,
        content: Color = System.color.uiSurface,
        disabledContainer: Color = System.color.uiBorder,
        disabledContent: Color = System.color.textDisable,
    ) = SecondaryButtonColors(
        container = container,
        content = content,
        disabledContainer = disabledContainer,
        disabledContent = disabledContent
    )

    /**
     * Typography configuration for [SecondaryButton].
     *
     * @param contentStyle Text style applied to the button label.
     */
    data class SecondaryButtonStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = SecondaryButtonStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [SecondaryButton].
     *
     * @param radius Corner radius of the button shape.
     * @param padding Inner content padding of the button.
     */
    data class SecondaryButtonDimens(
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
    ) = SecondaryButtonDimens(
        radius = radius,
        padding = padding
    )
}

/**
 * A secondary action button with optional leading icon and text.
 * Supports enabled/disabled states. Emits [SecondaryButtonEffect.Click] when tapped.
 *
 * @param state Current UI state driving the button content and enabled flag.
 * @param onEffect Callback invoked when a [SecondaryButtonEffect] is emitted.
 * @param modifier Modifier applied to the root button element.
 * @param colors Color scheme for enabled and disabled states.
 * @param style Typography configuration for the button label.
 * @param dimens Dimension values controlling corner radius and padding.
 */
@Composable
fun SecondaryButton(
    state: SecondaryButtonState,
    onEffect: (SecondaryButtonEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SecondaryButtonDefaults.SecondaryButtonColors = SecondaryButtonDefaults.colors(),
    style: SecondaryButtonDefaults.SecondaryButtonStyle = SecondaryButtonDefaults.style(),
    dimens: SecondaryButtonDefaults.SecondaryButtonDimens = SecondaryButtonDefaults.dimens(),
) {
    Button(
        onClick = { onEffect(SecondaryButtonEffect.Click) },
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        enabled = state.enabled,
        colors = colors.asButtonColors(),
        contentPadding = dimens.padding,
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
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        SecondaryButton(
            state = SecondaryButtonState(
                text = "Lorem",
                enabled = true
            ),
            onEffect = {},
        )

        SecondaryButton(
            state = SecondaryButtonState(
                text = "Lorem",
                icon = painterResource(Res.drawable.ic_plus),
                enabled = true
            ),
            onEffect = {}
        )

        SecondaryButton(
            state = SecondaryButtonState(
                text = "Lorem",
                enabled = false
            ),
            onEffect = {}
        )

        SecondaryButton(
            state = SecondaryButtonState(
                text = "Lorem",
                enabled = false
            ),
            colors = SecondaryButtonDefaults.colors(
                disabledContent = Color.Red
            ),
            onEffect = {}
        )

        SecondaryButton(
            state = SecondaryButtonState(
                text = "Lorem",
                enabled = true
            ),
            onEffect = {},
            colors = SecondaryButtonDefaults.colors(
                content = System.color.uiSurface,
                container = System.color.uiDark
            )
        )
    }
}
