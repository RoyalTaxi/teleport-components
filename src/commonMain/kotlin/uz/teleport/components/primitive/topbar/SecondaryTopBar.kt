package uz.teleport.components.primitive.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * UI state for [SecondaryTopBar].
 *
 * @param title Title text.
 * @param description Optional description text shown below the title.
 * @param action Optional action button text shown on the right.
 */
data class SecondaryTopBarState(
    val title: String,
    val description: String? = null,
    val action: String? = null
)

/**
 * One-time UI effects emitted by [SecondaryTopBar].
 */
sealed interface SecondaryTopBarEffect {
    /** Emitted when the action button is clicked. */
    data object ActionClick : SecondaryTopBarEffect
}

/**
 * Default configuration values for [SecondaryTopBar].
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object SecondaryTopBarDefaults {
    /**
     * Color configuration for [SecondaryTopBar].
     *
     * @param title Title text color.
     * @param description Description text color.
     * @param action Action button text color.
     * @param container Background color.
     */
    data class SecondaryTopBarColors(
        val title: Color,
        val description: Color,
        val action: Color,
        val container: Color
    )

    @Composable
    fun colors(
        title: Color = System.color.textDark,
        description: Color = System.color.textDisable,
        action: Color = System.color.brandGreen,
        container: Color = System.color.uiSurface,
    ) = SecondaryTopBarColors(
        title = title,
        description = description,
        action = action,
        container = container
    )

    /**
     * Text style configuration for [SecondaryTopBar].
     *
     * @param contentStyle Style applied to the text content.
     */
    data class SecondaryTopBarStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = SecondaryTopBarStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [SecondaryTopBar].
     *
     * @param spacing Vertical and horizontal spacing used between elements.
     * This value is applied as top/bottom spacing for title and description
     * when the action button is not present.
     *
     * @param padding Padding applied to the action button content.
     */
    data class SecondaryTopBarDimens(
        val spacing: Dp,
        val padding: PaddingValues
    )

    @Composable
    fun dimens(
        spacing: Dp = 12.dp,
        padding: PaddingValues = PaddingValues(
            vertical = 8.dp,
            horizontal = 12.dp,
        )
    ) = SecondaryTopBarDimens(
        spacing = spacing,
        padding = padding
    )
}

/**
 * A section header bar with a title, optional description, and an optional action button.
 * Emits [SecondaryTopBarEffect.ActionClick] when the action button is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the top bar.
 * @param colors Color configuration, defaults to [SecondaryTopBarDefaults.colors].
 * @param style Text style configuration, defaults to [SecondaryTopBarDefaults.style].
 * @param dimens Dimension configuration, defaults to [SecondaryTopBarDefaults.dimens].
 */
@Composable
fun SecondaryTopBar(
    state: SecondaryTopBarState,
    onEffect: (SecondaryTopBarEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SecondaryTopBarDefaults.SecondaryTopBarColors = SecondaryTopBarDefaults.colors(),
    style: SecondaryTopBarDefaults.SecondaryTopBarStyle = SecondaryTopBarDefaults.style(),
    dimens: SecondaryTopBarDefaults.SecondaryTopBarDimens = SecondaryTopBarDefaults.dimens()
) {
    Column(
        modifier = modifier
                .background(colors.container)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = state.title,
                color = colors.title,
                style = style.contentStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    top = if (state.action == null) dimens.spacing else 0.dp,
                    start = dimens.spacing
                )
            )

            state.action?.let {
                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = { onEffect(SecondaryTopBarEffect.ActionClick) },
                    shape = CircleShape,
                    colors =
                        ButtonDefaults.textButtonColors(
                            contentColor = colors.action,
                        ),
                    contentPadding = dimens.padding,
                ) {
                    Text(
                        text = it,
                        style = style.contentStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }

        state.description?.let {
            Text(
                text = it,
                color = colors.description,
                style = style.contentStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(
                        top = if (state.action == null) dimens.spacing else 0.dp,
                        bottom = dimens.spacing,
                        start = dimens.spacing,
                        end = dimens.spacing
                    )
            )
        }
    }
}

@Preview
@Composable
private fun SecondaryTopBarPreview() {
    Column {
        SecondaryTopBar(
            state = SecondaryTopBarState(
                title = "Title",
                description = "description",
                action = "action"
            ),
            onEffect = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SecondaryTopBar(
            state = SecondaryTopBarState(
                title = "Title",
                description = "description"
            ),
            colors = SecondaryTopBarDefaults.colors(
                title = Color.Red,
                container = Color.Gray,
            ),
            onEffect = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
