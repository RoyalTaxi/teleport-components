package uz.teleport.components.primitive.fields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * UI state for [PrimaryField].
 *
 * @param value Current text value.
 * @param prefix Optional text displayed before the input.
 * @param suffix Optional text displayed after the input.
 */
data class PrimaryFieldState(
    val value: String,
    val prefix: String? = null,
    val suffix: String? = null
)

/**
 * One-time UI effects emitted by [PrimaryField].
 */
sealed interface PrimaryFieldEffect {
    /**
     * Emitted when the text value changes.
     *
     * @param value The new text value.
     */
    data class SetValue(val value: String) : PrimaryFieldEffect
}

/**
 * Default configuration values for [PrimaryField].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object PrimaryFieldDefaults {

    /**
     * Color configuration for [PrimaryField].
     *
     * @param border Border color.
     * @param content Text color.
     * @param container Background color.
     * @param secondaryContent Prefix and suffix text color.
     */
    data class PrimaryFieldColors(
        val border: Color,
        val content: Color,
        val container: Color,
        val secondaryContent: Color
    ) {
        @Composable
        fun asCardColors() = CardDefaults.cardColors(
            contentColor = content,
            containerColor = container
        )
    }

    @Composable
    fun colors(
        border: Color = System.color.uiBorder,
        content: Color = System.color.textDark,
        container: Color = System.color.uiSurface,
        secondaryContent: Color = System.color.textDisable
    ) = PrimaryFieldColors(
        border = border,
        content = content,
        container = container,
        secondaryContent = secondaryContent
    )

    /**
     * Text style configuration for [PrimaryField].
     *
     * @param contentStyle Style applied to the input text.
     * @param secondaryContentStyle Style applied to prefix and suffix text.
     */
    data class PrimaryFieldStyle(
        val contentStyle: TextStyle,
        val secondaryContentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body,
        secondaryContentStyle: TextStyle = System.type.body
    ) = PrimaryFieldStyle(
        contentStyle = contentStyle,
        secondaryContentStyle = secondaryContentStyle
    )

    /**
     * Dimension configuration for [PrimaryField].
     *
     * @param border Border width.
     * @param radius Corner radius.
     * @param padding Content padding inside the card.
     */
    data class PrimaryFieldDimens(
        val border: Dp,
        val radius: Dp,
        val padding: PaddingValues
    )

    @Composable
    fun dimens(
        border: Dp = 1.dp,
        radius: Dp = 12.dp,
        padding: PaddingValues = PaddingValues(
            vertical = 8.dp,
            horizontal = 16.dp,
        )
    ) = PrimaryFieldDimens(
        border = border,
        radius = radius,
        padding = padding
    )
}

/**
 * A bordered single-line text input field with optional prefix and suffix.
 *
 * Wrapped in a card with rounded corners.
 * Emits [PrimaryFieldEffect.SetValue] on text input.
 *
 * @param state Current UI state of the field.
 * @param onEffect Callback invoked when a [PrimaryFieldEffect] is emitted.
 * @param modifier Modifier applied to the root layout.
 * @param colors Color configuration for the field.
 * @param style Text style configuration for the field.
 * @param dimens Dimension configuration for the field.
 */
@Composable
fun PrimaryField(
    state: PrimaryFieldState,
    onEffect: (PrimaryFieldEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: PrimaryFieldDefaults.PrimaryFieldColors = PrimaryFieldDefaults.colors(),
    style: PrimaryFieldDefaults.PrimaryFieldStyle = PrimaryFieldDefaults.style(),
    dimens: PrimaryFieldDefaults.PrimaryFieldDimens = PrimaryFieldDefaults.dimens(),
) {
    val focusRequester = remember { FocusRequester() }

    Card(
        modifier = modifier,
        colors = colors.asCardColors(),
        shape = RoundedCornerShape(size = dimens.radius),
        border = BorderStroke(width = dimens.border, color = colors.border),
        onClick = { focusRequester.requestFocus() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimens.padding)
        ) {
            state.prefix?.let {
                Text(
                    text = it,
                    color = colors.secondaryContent,
                    style = style.secondaryContentStyle
                )

                Spacer(modifier = Modifier.width(4.dp))
            }

            BasicTextField(
                value = state.value,
                onValueChange = { onEffect(PrimaryFieldEffect.SetValue(it)) },
                singleLine = true,
                textStyle = style.contentStyle.copy(color = colors.content),
                interactionSource = null,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
            ) { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = state.value,
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues.Zero,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            state.suffix?.let {
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = it,
                    color = colors.secondaryContent,
                    style = style.secondaryContentStyle
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrimaryFieldPreview() {
    var value by remember { mutableStateOf("") }

    Box {
        PrimaryField(
            state = PrimaryFieldState(
                value = value,
                prefix = "from",
                suffix = "dollars"
            ),
            onEffect = { effect ->
                when (effect) {
                    is PrimaryFieldEffect.SetValue -> value = effect.value
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}