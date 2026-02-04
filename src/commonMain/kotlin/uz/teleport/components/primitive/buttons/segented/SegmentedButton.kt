package uz.teleport.components.primitive.buttons.segented

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import uz.teleport.resources.ic_minus
import uz.teleport.resources.ic_plus

/**
 * Represents a single item in a [SegmentedButtonList] with an icon and text label.
 *
 * @param text The text label displayed next to the icon.
 * @param icon The icon displayed alongside the text.
 */
data class SegmentedButtonModel(
    val text: String,
    val icon: Painter
)

/**
 * UI state for [SegmentedButton].
 *
 * @param segmentedButtonModel The model containing icon and text data.
 */
data class SegmentedButtonState(
    val segmentedButtonModel: SegmentedButtonModel
)

/**
 * One-time UI effects emitted by [SegmentedButton].
 */
sealed interface SegmentedButtonEffect {
    /**
     * Emitted when a segmented button is clicked.
     *
     * @param segmentedButtonModel The clicked item's model.
     */
    data class Click(val segmentedButtonModel: SegmentedButtonModel) : SegmentedButtonEffect
}

/**
 * Default configuration values for [SegmentedButton].
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object SegmentedButtonDefaults {

    /**
     * Color configuration for [SegmentedButton].
     *
     * @param icon The tint color applied to the icon.
     * @param text The color applied to the text label.
     * @param container The background color of the segmented button container.
     */
    data class SegmentedButtonColors(
        val icon: Color,
        val text: Color,
        val container: Color
    )

    @Composable
    fun colors(
        icon: Color = System.color.uiSurface,
        text: Color = System.color.textLight,
        container: Color = System.color.uiDark,
    ) = SegmentedButtonColors(
        icon = icon,
        text = text,
        container = container,
    )

    /**
     * Text style configuration for [SegmentedButton].
     *
     * @param contentStyle The text style applied to the button label.
     */
    data class SegmentedButtonStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.button
    ) = SegmentedButtonStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [SegmentedButton].
     *
     * @param radius The corner radius of the button shape.
     * @param spacing The horizontal spacing between adjacent buttons.
     * @param padding The content padding inside each button.
     */
    data class SegmentedButtonDimens(
        val radius: Dp,
        val spacing: Dp,
        val padding: PaddingValues
    )

    @Composable
    fun dimens(
        radius: Dp = 8.dp,
        spacing: Dp = 4.dp,
        padding: PaddingValues = PaddingValues(
            vertical = 8.dp,
            horizontal = 12.dp
        )
    ) = SegmentedButtonDimens(
        radius = radius,
        spacing = spacing,
        padding = padding
    )
}

/**
 * A single segmented button displaying an icon and text label.
 * Emits [SegmentedButtonEffect.Click] when tapped.
 *
 * @param state The current UI state holding the button model.
 * @param onEffect Callback invoked when a UI effect is emitted.
 * @param modifier The modifier to be applied to this button.
 * @param colors The color configuration for this button.
 * @param style The text style configuration for this button.
 * @param dimens The dimension configuration for this button.
 */
@Composable
fun SegmentedButton(
    state: SegmentedButtonState,
    onEffect: (SegmentedButtonEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SegmentedButtonDefaults.SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    style: SegmentedButtonDefaults.SegmentedButtonStyle = SegmentedButtonDefaults.style(),
    dimens: SegmentedButtonDefaults.SegmentedButtonDimens = SegmentedButtonDefaults.dimens(),
) {
    TextButton(
        modifier = modifier
            .padding(horizontal = dimens.spacing),
        onClick = { onEffect(SegmentedButtonEffect.Click(state.segmentedButtonModel)) },
        shape = RoundedCornerShape(dimens.radius),
        colors = ButtonDefaults.textButtonColors(
            contentColor = colors.text
        ),
        contentPadding = dimens.padding
    ) {
        Icon(
            painter = state.segmentedButtonModel.icon,
            contentDescription = null,
            tint = colors.icon,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = state.segmentedButtonModel.text,
            style = style.contentStyle,
            color = colors.text
        )
    }
}

/**
 * A horizontal row of [SegmentedButton] items separated by vertical dividers,
 * wrapped in a rounded surface container.
 *
 * @param items The list of [SegmentedButtonModel] items to display.
 * @param onEffect Callback invoked when a UI effect is emitted.
 * @param modifier The modifier to be applied to the container.
 * @param colors The color configuration for the buttons.
 * @param style The text style configuration for the buttons.
 * @param dimens The dimension configuration for the buttons.
 */
@Composable
fun SegmentedButtonList(
    items: List<SegmentedButtonModel>,
    onEffect: (SegmentedButtonEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SegmentedButtonDefaults.SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    style: SegmentedButtonDefaults.SegmentedButtonStyle = SegmentedButtonDefaults.style(),
    dimens: SegmentedButtonDefaults.SegmentedButtonDimens = SegmentedButtonDefaults.dimens()
) {
    Surface(
        shape = RoundedCornerShape(dimens.radius),
        color = colors.container,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                SegmentedButton(
                    state = SegmentedButtonState(item),
                    onEffect = onEffect,
                    colors = colors,
                    dimens = dimens,
                    style = style
                )

                if (index != items.lastIndex) {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(18.dp)
                            .background(colors.text)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SegmentedButtonListPreview() {
    val items = listOf(
        SegmentedButtonModel(
            text = "Filter",
            icon = painterResource(Res.drawable.ic_minus)
        ),
        SegmentedButtonModel(
            text = "Sort",
            icon = painterResource(Res.drawable.ic_plus)
        ),
        SegmentedButtonModel(
            text = "Sort2",
            icon = painterResource(Res.drawable.ic_plus)
        )
    )

    Box(
        modifier = Modifier
            .background(System.color.uiSurface)
            .padding(16.dp)
    ) {
        SegmentedButtonList(
            items = items,
            onEffect = {
                when (it) {
                    is SegmentedButtonEffect.Click -> {
                        println(it.segmentedButtonModel.text)
                    }
                }
            }
        )
    }
}
