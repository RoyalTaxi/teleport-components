package uz.teleport.components.primitive.topbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_arrow_left

/**
 * UI state for [PrimaryTopBar].
 *
 * @param title Optional title text displayed in the center.
 * @param icon Optional navigation icon displayed on the left.
 */
data class PrimaryTopBarState(
    val title: String? = null,
    val icon: Painter? = null
)

/**
 * One-time UI effects emitted by [PrimaryTopBar].
 */
sealed interface PrimaryTopBarEffect {
    /** Emitted when the navigation icon is clicked. */
    data object BackClick : PrimaryTopBarEffect
}

/**
 * Default configuration values for [PrimaryTopBar].
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object PrimaryTopBarDefaults {
    /**
     * Color configuration for [PrimaryTopBar].
     *
     * @param text Title text color.
     * @param icon Navigation icon tint color.
     * @param container Background color.
     */
    data class PrimaryTopBarColors(
        val text: Color,
        val icon: Color,
        val container: Color
    )

    @Composable
    fun colors(
        text: Color = System.color.textDark,
        icon: Color = System.color.uiDark,
        container: Color = Color.Transparent
    ) = PrimaryTopBarColors(
        text = text,
        icon = icon,
        container = container
    )

    /**
     * Text style configuration for [PrimaryTopBar].
     *
     * @param contentStyle Style applied to the title text.
     */
    data class PrimaryTopBarStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.body
    ) = PrimaryTopBarStyle(
        contentStyle = contentStyle
    )
}

/**
 * A center-aligned top app bar with an optional title and navigation icon.
 * Emits [PrimaryTopBarEffect.BackClick] when the navigation icon is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the top bar.
 * @param colors Color configuration, defaults to [PrimaryTopBarDefaults.colors].
 * @param style Text style configuration, defaults to [PrimaryTopBarDefaults.style].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTopBar(
    state: PrimaryTopBarState,
    onEffect: (PrimaryTopBarEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: PrimaryTopBarDefaults.PrimaryTopBarColors = PrimaryTopBarDefaults.colors(),
    style: PrimaryTopBarDefaults.PrimaryTopBarStyle = PrimaryTopBarDefaults.style(),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            state.title?.let {
                Text(
                    text = it,
                    style = style.contentStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.text
                )
            }
        },
        navigationIcon = {
            state.icon?.let {
                IconButton(onClick = { onEffect(PrimaryTopBarEffect.BackClick) }) {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        tint = colors.icon
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.container
        )
    )
}

@Preview
@Composable
private fun PrimaryTopBarPreview() {
    MaterialTheme {
        PrimaryTopBar(
            state = PrimaryTopBarState(
                title = "Укажите местоположение",
                icon = painterResource(Res.drawable.ic_arrow_left)
            ),
            onEffect = {}
        )
    }
}
