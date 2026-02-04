package uz.teleport.components.primitive.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_arrow_up

/**
 * UI state for [SearchLocationField].
 *
 * @param value Current text value.
 */
data class SearchLocationFieldState(
    val value: String
)

/**
 * One-time UI effects emitted by [SearchLocationField].
 */
sealed interface SearchLocationFieldEffect {
    /**
     * Emitted when the text value changes.
     *
     * @param value The new text value.
     */
    data class SetValue(val value: String) : SearchLocationFieldEffect
}

/**
 * Default configuration values for [SearchLocationField].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object SearchLocationFieldDefaults {

    /**
     * Color configuration for [SearchLocationField].
     *
     * @param primary Accent color for border and cursor.
     * @param content Text color.
     * @param container Background color.
     * @param secondaryContent Leading icon tint color.
     */
    data class SearchLocationFieldColors(
        val primary: Color,
        val content: Color,
        val container: Color,
        val secondaryContent: Color
    ) {
        @Composable
        fun asTextFieldDefaults() = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = container,
            unfocusedContainerColor = container,
            disabledContainerColor = container,
            errorContainerColor = container,
            focusedBorderColor = primary,
            unfocusedBorderColor = primary,
            cursorColor = primary
        )
    }

    @Composable
    fun colors(
        primary: Color = System.color.brandGreen,
        content: Color = System.color.textDark,
        container: Color = System.color.uiSurface,
        secondaryContent: Color = System.color.uiIconMuted
    ) = SearchLocationFieldColors(
        primary = primary,
        content = content,
        container = container,
        secondaryContent = secondaryContent
    )

    /**
     * Text style configuration for [SearchLocationField].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class SearchLocationFieldStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = SearchLocationFieldStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [SearchLocationField].
     *
     * @param border Border width.
     * @param radius Corner radius.
     */
    data class SearchLocationFieldDimens(
        val border: Dp,
        val radius: Dp
    )

    @Composable
    fun dimens(
        border: Dp = 1.dp,
        radius: Dp = 12.dp
    ) = SearchLocationFieldDimens(
        border = border,
        radius = radius
    )
}

/**
 * A single-line outlined text field with a leading search icon, designed for location input.
 *
 * Emits [SearchLocationFieldEffect.SetValue] on text input.
 *
 * @param state Current UI state holding the text value.
 * @param onEffect Callback invoked when a [SearchLocationFieldEffect] is emitted.
 * @param modifier Modifier applied to the root layout.
 * @param colors Color configuration; defaults from [SearchLocationFieldDefaults.colors].
 * @param style Text style configuration; defaults from [SearchLocationFieldDefaults.style].
 * @param dimens Dimension configuration; defaults from [SearchLocationFieldDefaults.dimens].
 */
@Composable
fun SearchLocationField(
    state: SearchLocationFieldState,
    onEffect: (SearchLocationFieldEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SearchLocationFieldDefaults.SearchLocationFieldColors = SearchLocationFieldDefaults.colors(),
    style: SearchLocationFieldDefaults.SearchLocationFieldStyle = SearchLocationFieldDefaults.style(),
    dimens: SearchLocationFieldDefaults.SearchLocationFieldDimens = SearchLocationFieldDefaults.dimens(),
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { onEffect(SearchLocationFieldEffect.SetValue(it)) },
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_up),
                contentDescription = null,
                tint = colors.secondaryContent,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(dimens.radius),
        textStyle = style.contentStyle.copy(color = colors.content),
        colors = colors.asTextFieldDefaults()
    )
}

@Preview
@Composable
private fun SearchLocationFieldPreview() {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchLocationField(
            state = SearchLocationFieldState(
                value = "Tashkent, Uzbekistan"
            ),
            onEffect = {},
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )

        SearchLocationField(
            state = SearchLocationFieldState(
                value = "Tashkent, Uzbekistan"
            ),
            onEffect = {},
            modifier = Modifier.focusRequester(focusRequester)
        )

        Button(
            onClick = { focusRequester.requestFocus() }
        ) {
            Text("Focus Search Field")
        }
    }
}