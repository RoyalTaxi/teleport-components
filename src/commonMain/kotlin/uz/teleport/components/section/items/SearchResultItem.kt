package uz.teleport.components.section.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_location_point

/**
 * Data model for a search result list item.
 *
 * @param title Optional title text.
 * @param description Optional description text.
 * @param imageUrl Optional URL for the avatar image. When `null` or empty, a location icon is shown.
 */
data class SearchResultModel(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null
)

/**
 * UI state for [SearchResultItem].
 *
 * @param searchResultModel The data model to display.
 */
data class SearchResultState(
    val searchResultModel: SearchResultModel
)

/**
 * One-time UI effects emitted by [SearchResultItem].
 */
sealed interface SearchResultEffect {
    /**
     * Emitted when the search result item is clicked.
     *
     * @param searchResultModel The clicked result's model.
     */
    data class Click(val searchResultModel: SearchResultModel) : SearchResultEffect
}

/**
 * Default configuration values for [SearchResultItem].
 *
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object SearchResultDefaults {
    /**
     * Color configuration for [SearchResultItem].
     *
     * @param title Title text color.
     * @param description Description text color.
     * @param icon Location placeholder icon tint color.
     * @param container Background color.
     */
    data class SearchResultColors(
        val title: Color,
        val description: Color,
        val icon: Color,
        val container: Color,
    )

    @Composable
    fun colors(
        title: Color = System.color.textDark,
        description: Color = System.color.textDisable,
        icon: Color = System.color.uiIconMuted,
        container: Color = Color.Transparent
    ) = SearchResultColors(
        title = title,
        description = description,
        icon = icon,
        container = container
    )

    /**
     * Text style configuration for [SearchResultItem].
     *
     * @param titleStyle Style applied to the title text.
     * @param descriptionStyle Style applied to the description text.
     */
    data class SearchResultStyle(
        val titleStyle: TextStyle,
        val descriptionStyle: TextStyle
    )

    @Composable
    fun style(
        titleStyle: TextStyle = System.type.body,
        descriptionStyle: TextStyle = System.type.caption
    ) = SearchResultStyle(
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle
    )
}

/**
 * A clickable row displaying a circular avatar (or location icon placeholder),
 * title, and description. When [SearchResultModel.imageUrl] is set, the image
 * is loaded; otherwise a default location icon is shown.
 * Emits [SearchResultEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the item.
 * @param colors Color configuration, defaults to [SearchResultDefaults.colors].
 * @param style Text style configuration, defaults to [SearchResultDefaults.style].
 */
@Composable
fun SearchResultItem(
    state: SearchResultState,
    onEffect: (SearchResultEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SearchResultDefaults.SearchResultColors = SearchResultDefaults.colors(),
    style: SearchResultDefaults.SearchResultStyle = SearchResultDefaults.style(),
) {
    Surface (
        modifier = modifier.fillMaxWidth(),
        color = colors.container,
        onClick = { onEffect(SearchResultEffect.Click(state.searchResultModel)) }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            if (!state.searchResultModel.imageUrl.isNullOrEmpty()) {
                CoilImage(
                    imageModel = { state.searchResultModel.imageUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    painter = painterResource(Res.drawable.ic_location_point),
                    contentDescription = null,
                    tint = colors.icon,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                state.searchResultModel.title?.let {
                    Text(
                        text = state.searchResultModel.title,
                        color = colors.title,
                        style = style.titleStyle
                    )

                }

                state.searchResultModel.description?.let {
                    Text(
                        text = state.searchResultModel.description,
                        color = colors.description,
                        style = style.descriptionStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchResultItemPreview() {
    Box(modifier = Modifier
        .background(Color.Gray)
        .padding(12.dp)
    ) {
        SearchResultItem(
            state = SearchResultState(
                searchResultModel = SearchResultModel(
                    title = "Address address",
                    description = "Region",
                )
            ),
            onEffect = { effect ->
                when (effect) {
                    is SearchResultEffect.Click -> {
                        println("Clicked ${effect.searchResultModel.description}")
                    }
                }
            }
        )
    }
}