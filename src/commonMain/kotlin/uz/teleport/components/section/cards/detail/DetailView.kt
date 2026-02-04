package uz.teleport.components.section.cards.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_car

/**
 * UI state for [DetailView].
 *
 * @param title Primary text displayed next to the icon.
 * @param description Secondary text displayed below the title.
 */
data class DetailViewState(
    val title: String,
    val description: String
)

/**
 * Default configuration values for [DetailView].
 *
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object DetailViewDefaults {

    /**
     * Color configuration for [DetailView].
     *
     * @param text Title text color.
     * @param textDisable Description text color.
     * @param icon Icon tint color.
     * @param iconBackground Circular background color behind the icon.
     */
    data class DetailViewColors(
        val text: Color,
        val textDisable: Color,
        val icon: Color,
        val iconBackground: Color,
    )

    @Composable
    fun colors(
        textColor: Color = System.color.textDark,
        textDisableColor: Color = System.color.textDisable,
        iconTint: Color = System.color.uiIconMuted,
        iconBackground: Color = System.color.uiSurfaceSubtle
    ) = DetailViewColors(
        text = textColor,
        textDisable = textDisableColor,
        icon = iconTint,
        iconBackground = iconBackground
    )

    /**
     * Text style configuration for [DetailView].
     *
     * @param titleStyle Style applied to the title text.
     * @param descriptionStyle Style applied to the description text.
     */
    data class DetailViewStyle(
        val titleStyle: TextStyle,
        val descriptionStyle: TextStyle
    )

    @Composable
    fun style(
        titleStyle: TextStyle = System.type.button,
        descriptionStyle: TextStyle = System.type.caption
    ) = DetailViewStyle(
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle
    )
}

/**
 * A horizontal row displaying a circular icon, a title, and a description.
 * Used as a reusable detail element inside [DetailBox] and [DetailCard].
 *
 * @param state Current UI state.
 * @param modifier Modifier applied to the row.
 * @param colors Color configuration, defaults to [DetailViewDefaults.colors].
 * @param style Text style configuration, defaults to [DetailViewDefaults.style].
 */
@Composable
fun DetailView(
    state: DetailViewState,
    modifier: Modifier = Modifier,
    colors: DetailViewDefaults.DetailViewColors = DetailViewDefaults.colors(),
    style: DetailViewDefaults.DetailViewStyle = DetailViewDefaults.style(),
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .background(colors.iconBackground, CircleShape)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_car),
                contentDescription = null,
                tint = colors.icon,
                modifier = Modifier.size(24.dp)
            )
        }

        Column {
            Text(
                text = state.title,
                color = colors.text,
                style = style.titleStyle
            )
            Text(
                text = state.description,
                color = colors.textDisable,
                style = style.descriptionStyle
            )
        }
    }
}

@Preview
@Composable
private fun DetailViewPreview() {
    val state = DetailViewState(
        title = "Chevrolet Cobalt",
        description = "Qora"
    )

    DetailView(
        state = state,
        modifier = Modifier.padding(16.dp)
    )
}
