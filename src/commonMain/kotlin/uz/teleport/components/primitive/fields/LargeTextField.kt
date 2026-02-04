package uz.teleport.components.primitive.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * UI state for [LargeTextField].
 *
 * @param value Current text value.
 * @param placeholder Hint text shown when [value] is empty.
 */
data class LargeTextFieldState(
    val value: String,
    val placeholder: String
)

/**
 * One-time UI effects emitted by [LargeTextField].
 */
sealed interface LargeTextFieldEffect {
    /**
     * Emitted when the text value changes.
     *
     * @param value The new text value.
     */
    data class SetValue(val value: String) : LargeTextFieldEffect
}

/**
 * Default configuration values for [LargeTextField].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object LargeTextFieldDefaults {

    /**
     * Color configuration for [LargeTextField].
     *
     * @param content Text color.
     * @param container Background color.
     * @param secondaryContent Placeholder text color.
     */
    data class LargeTextFieldColors(
        val content: Color,
        val container: Color,
        val secondaryContent: Color
    ) {
        @Composable
        fun asTextFieldDefaults() = TextFieldDefaults.colors(
            focusedContainerColor = container,
            unfocusedContainerColor = container,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    }

    @Composable
    fun colors(
        content: Color = System.color.textDark,
        container: Color = System.color.uiSurface,
        secondaryContent: Color = System.color.textDisable
    ) = LargeTextFieldColors(
        content = content,
        container = container,
        secondaryContent = secondaryContent
    )

    /**
     * Text style configuration for [LargeTextField].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class LargeTextFieldStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.caption,
    ) = LargeTextFieldStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [LargeTextField].
     *
     * @param radius Corner radius.
     * @param minHeight Minimum height of the text field.
     */
    data class LargeTextFieldDimens(
        val radius: Dp,
        val minHeight: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp,
        minHeight: Dp = 84.dp
    ) = LargeTextFieldDimens(
        radius = radius,
        minHeight = minHeight
    )
}

/**
 * A multiline text field with rounded corners and a minimum height.
 *
 * Displays a placeholder when empty. Emits [LargeTextFieldEffect.SetValue] on text input.
 *
 * @param state Current UI state holding the text value and placeholder.
 * @param onEffect Callback invoked when an effect is emitted.
 * @param modifier Modifier applied to the text field.
 * @param colors Color configuration for the text field.
 * @param style Text style configuration for the text field.
 * @param dimens Dimension configuration for the text field.
 */
@Composable
fun LargeTextField(
    state: LargeTextFieldState,
    onEffect: (LargeTextFieldEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: LargeTextFieldDefaults.LargeTextFieldColors = LargeTextFieldDefaults.colors(),
    style: LargeTextFieldDefaults.LargeTextFieldStyle = LargeTextFieldDefaults.style(),
    dimens: LargeTextFieldDefaults.LargeTextFieldDimens = LargeTextFieldDefaults.dimens(),
) {
    TextField(
        value = state.value,
        onValueChange = { onEffect(LargeTextFieldEffect.SetValue(it)) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = dimens.minHeight),
        shape = RoundedCornerShape(dimens.radius),
        singleLine = false,
        textStyle = style.contentStyle,
        placeholder = {
            Text(
                text = state.placeholder,
                color = colors.secondaryContent,
                style = style.contentStyle
            )
        },
        colors = colors.asTextFieldDefaults()
    )
}

@Preview
@Composable
private fun LargeTextFieldPreview() {
    var value by remember { mutableStateOf("") }

    LargeTextField(
        state = LargeTextFieldState(
            value = value,
            placeholder = "Izoh qoldiring"
        ),
        onEffect = { effect ->
            when (effect) {
                is LargeTextFieldEffect.SetValue -> value = effect.value
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}
