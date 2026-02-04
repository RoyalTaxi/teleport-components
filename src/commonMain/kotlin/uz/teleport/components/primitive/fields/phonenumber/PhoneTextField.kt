package uz.teleport.components.primitive.fields.phonenumber

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * UI state for [PhoneTextField].
 *
 * @param value Current raw phone digits (without formatting).
 * @param mask Format mask where [maskNumber] represents a digit placeholder.
 * @param maskNumber Character used as digit placeholder in [mask].
 */
data class PhoneTextFieldState(
    val value: String,
    val mask: String = "00 000 00 00",
    val maskNumber: Char = '0'
)

/**
 * One-time UI effects emitted by [PhoneTextField].
 */
sealed interface PhoneTextFieldEffect {
    /**
     * Emitted when the phone number changes.
     *
     * @param value The new raw digit string.
     */
    data class SetValue(val value: String) : PhoneTextFieldEffect
}

/**
 * Default configuration values for [PhoneTextField].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object PhoneTextFieldDefaults {

    /**
     * Color configuration for [PhoneTextField].
     *
     * @param border Border color.
     * @param content Text color.
     * @param container Background color.
     * @param secondaryContent Placeholder text color.
     */
    data class PhoneTextFieldColors(
        val border: Color,
        val content: Color,
        val container: Color,
        val secondaryContent: Color
    ) {
        @Composable
        fun asTextFieldDefaults() = TextFieldDefaults.colors(
            focusedTextColor = content,
            unfocusedTextColor = content,
            cursorColor = content,
            focusedContainerColor = container,
            unfocusedContainerColor = container,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    }

    @Composable
    fun colors(
        border: Color = System.color.brandGreen,
        content: Color = System.color.textDark,
        container: Color = System.color.uiSurface,
        secondaryContent: Color = System.color.textDisable
    ) = PhoneTextFieldColors(
        border = border,
        content = content,
        container = container,
        secondaryContent = secondaryContent
    )

    /**
     * Text style configuration for [PhoneTextField].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class PhoneTextFieldStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = PhoneTextFieldStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [PhoneTextField].
     *
     * @param border Border width.
     * @param radius Corner radius.
     */
    data class PhoneTextFieldDimens(
        val border: Dp,
        val radius: Dp
    )

    @Composable
    fun dimens(
        border: Dp = 1.5.dp,
        radius: Dp = 16.dp
    ) = PhoneTextFieldDimens(
        border = border,
        radius = radius
    )
}

/**
 * A phone number input field with a +998 prefix, digit-only input, and visual mask formatting.
 *
 * Filters non-digit characters and emits [PhoneTextFieldEffect.SetValue] with raw digits.
 *
 * @param state Current UI state holding the raw value and mask configuration.
 * @param onEffect Callback invoked when a [PhoneTextFieldEffect] is emitted.
 * @param modifier Modifier applied to the text field.
 * @param colors Color configuration; defaults provided by [PhoneTextFieldDefaults.colors].
 * @param style Text style configuration; defaults provided by [PhoneTextFieldDefaults.style].
 * @param dimens Dimension configuration; defaults provided by [PhoneTextFieldDefaults.dimens].
 */
@Composable
fun PhoneTextField(
    state: PhoneTextFieldState,
    onEffect: (PhoneTextFieldEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: PhoneTextFieldDefaults.PhoneTextFieldColors = PhoneTextFieldDefaults.colors(),
    style: PhoneTextFieldDefaults.PhoneTextFieldStyle = PhoneTextFieldDefaults.style(),
    dimens: PhoneTextFieldDefaults.PhoneTextFieldDimens = PhoneTextFieldDefaults.dimens(),
) {
    TextField(
        value = state.value,
        onValueChange = { newText ->
            onEffect(PhoneTextFieldEffect.SetValue(
                newText.filter { it.isDigit() }
            ))
        },
        visualTransformation = PhoneVisualTransformation(state.mask, state.maskNumber),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = dimens.border,
                color = colors.border,
                shape = RoundedCornerShape(dimens.radius)
            ),
        singleLine = true,
        textStyle = style.contentStyle.copy(color = colors.content),
        shape = RoundedCornerShape(dimens.radius),
        prefix = {
            Text(
                text = "+998 ",
                color = colors.content,
                style = style.contentStyle
            )
        },
        placeholder = {
            Text(
                text = state.mask,
                color = colors.secondaryContent,
                style = style.contentStyle
            )
        },
        colors = colors.asTextFieldDefaults()
    )
}

@Preview
@Composable
private fun PhoneTextFieldPreview() {
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(Color.Gray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        PhoneTextField(
            state = PhoneTextFieldState(
                value = phone,
                mask = "00 000 00 00",
                maskNumber = '0'
            ),
            onEffect = { effect ->
                when (effect) {
                    is PhoneTextFieldEffect.SetValue -> phone = effect.value
                }
            }
        )
    }
}
