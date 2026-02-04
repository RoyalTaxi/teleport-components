package uz.teleport.components.section.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_cancel

/**
 * Data model for a single disposable option chip.
 *
 * @param id Unique identifier.
 * @param name Display label of the option.
 */
data class DisposableOptionModel(
    val id: Int,
    val name: String
)

/**
 * UI state for [DisposableOption].
 *
 * @param disposableOptionModel The data model to display.
 */
data class DisposableOptionState(
    val disposableOptionModel: DisposableOptionModel
)

/**
 * One-time UI effects emitted by [DisposableOption].
 */
sealed interface DisposableOptionEffect {
    /**
     * Emitted when the dismiss (cancel) button is clicked.
     *
     * @param disposableOptionModel The option that was dismissed.
     */
    data class Click(val disposableOptionModel: DisposableOptionModel) : DisposableOptionEffect
}

/**
 * Default configuration values for [DisposableOption].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object DisposableOptionDefaults {
    /**
     * Color configuration for [DisposableOption].
     *
     * @param text Label text color.
     * @param icon Dismiss icon tint color.
     * @param iconBackground Circular background color behind the dismiss icon.
     * @param container Chip background color.
     */
    data class DisposableOptionColors(
        val text: Color,
        val icon: Color,
        val iconBackground: Color,
        val container: Color
    )

    @Composable
    fun colors(
        text: Color = System.color.textDark,
        icon: Color = System.color.uiDark,
        iconBackground: Color = System.color.uiBorder,
        container: Color = System.color.uiSurface
    ) = DisposableOptionColors(
        text = text,
        icon = icon,
        iconBackground = iconBackground,
        container = container
    )

    /**
     * Text style configuration for [DisposableOption].
     *
     * @param contentStyle Style applied to the label text.
     */
    data class DisposableOptionStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.caption
    ) = DisposableOptionStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [DisposableOption].
     *
     * @param radius Corner radius of the chip shape.
     */
    data class DisposableOptionDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 6.dp
    ) = DisposableOptionDimens(
        radius = radius
    )
}

/**
 * A compact chip displaying a label and a circular dismiss button.
 * Emits [DisposableOptionEffect.Click] when the dismiss icon is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the chip.
 * @param colors Color configuration, defaults to [DisposableOptionDefaults.colors].
 * @param style Text style configuration, defaults to [DisposableOptionDefaults.style].
 * @param dimens Dimension configuration, defaults to [DisposableOptionDefaults.dimens].
 */
@Composable
fun DisposableOption(
    state: DisposableOptionState,
    onEffect: (DisposableOptionEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: DisposableOptionDefaults.DisposableOptionColors = DisposableOptionDefaults.colors(),
    style: DisposableOptionDefaults.DisposableOptionStyle = DisposableOptionDefaults.style(),
    dimens: DisposableOptionDefaults.DisposableOptionDimens = DisposableOptionDefaults.dimens(),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = state.disposableOptionModel.name,
                color = colors.text,
                style = style.contentStyle
            )

            Spacer(modifier = Modifier.width(4.dp))

            Surface(
                onClick = { onEffect(DisposableOptionEffect.Click(state.disposableOptionModel)) },
                shape = CircleShape,
                color = colors.iconBackground,
                modifier = Modifier.size(16.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_cancel),
                        contentDescription = null,
                        tint = colors.icon,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun DisposableOptionPreview() {
    val options = remember {
        mutableStateListOf(
            DisposableOptionModel(id = 1, name = "Option A"),
            DisposableOptionModel(id = 2, name = "Option B"),
            DisposableOptionModel(id = 3, name = "Option C")
        )
    }

    Box(
        modifier = Modifier
            .background(Color.Gray)
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.forEach { option ->
                DisposableOption(
                    state = DisposableOptionState(
                        option
                    ),
                    onEffect = { effect ->
                        when (effect) {
                            is DisposableOptionEffect.Click -> {
                                options.remove(effect.disposableOptionModel)
                            }
                        }
                    }
                )
            }
        }
    }
}