package uz.teleport.components.primitive.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_minus
import uz.teleport.resources.ic_plus

/**
 * UI state for [SquircleButton].
 *
 * @param icon Icon displayed inside the button.
 * @param enabled Whether the button is enabled.
 */
data class SquircleButtonState(
    val icon: Painter,
    val enabled: Boolean = true,
)

/**
 * One-time UI effects emitted by [SquircleButton].
 */
sealed interface SquircleButtonEffect {
    /** Emitted when the button is clicked. */
    data object Click : SquircleButtonEffect
}

/**
 * Default configuration values for [SquircleButton].
 *
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object SquircleButtonDefaults {
    /**
     * Color configuration for [SquircleButton].
     *
     * @param content Color of the icon when the button is enabled.
     * @param container Background color of the button when enabled.
     * @param disabledContent Color of the icon when the button is disabled.
     * @param disabledContainer Background color of the button when disabled.
     */
    data class SquircleButtonColors(
        val content: Color,
        val container: Color,
        val disabledContent: Color,
        val disabledContainer: Color
    ) {
        @Composable
        fun asIconColors() = IconButtonDefaults.iconButtonColors(
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
        disabledContent: Color = System.color.uiDark,
        disabledContainer: Color = System.color.uiSurfaceSubtle
    ) = SquircleButtonColors(
        content = content,
        container = container,
        disabledContent = disabledContent,
        disabledContainer = disabledContainer
    )

    /**
     * Dimension configuration for [SquircleButton].
     *
     * @param radius Corner radius of the button shape.
     */
    data class SquircleButtonDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 8.dp
    ) = SquircleButtonDimens(
        radius = radius
    )
}

/**
 * A squircle-shaped icon button with rounded corners.
 *
 * Supports enabled/disabled states. Emits [SquircleButtonEffect.Click] when tapped.
 *
 * @param state Current UI state of the button.
 * @param onEffect Callback invoked when a [SquircleButtonEffect] is emitted.
 * @param modifier Modifier applied to the button.
 * @param colors Color configuration for the button.
 * @param dimens Dimension configuration for the button.
 */
@Composable
fun SquircleButton(
    state: SquircleButtonState,
    onEffect: (SquircleButtonEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SquircleButtonDefaults.SquircleButtonColors = SquircleButtonDefaults.colors(),
    dimens: SquircleButtonDefaults.SquircleButtonDimens = SquircleButtonDefaults.dimens(),
) {
    IconButton(
        onClick = { onEffect(SquircleButtonEffect.Click) },
        enabled = state.enabled,
        shape = RoundedCornerShape(dimens.radius),
        colors = colors.asIconColors(),
        modifier = modifier
    ) {
        Icon(
            painter = state.icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview
@Composable
private fun SquircleButtonPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SquircleButton(
            state = SquircleButtonState(
                icon = painterResource(Res.drawable.ic_plus),
                enabled = true
            ),
            onEffect = {}
        )

        SquircleButton(
            state = SquircleButtonState(
                icon = painterResource(Res.drawable.ic_minus),
                enabled = false
            ),
            onEffect = {}
        )
    }
}
