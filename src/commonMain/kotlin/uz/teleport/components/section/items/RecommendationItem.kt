package uz.teleport.components.section.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * Data model for a recommendation list item.
 *
 * @param title Optional title text.
 * @param description Optional description text.
 * @param imageUrl Optional URL for the thumbnail image.
 */
data class RecommendationModel(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null
)

/**
 * UI state for [RecommendationItem].
 *
 * @param recommendationModel The data model to display.
 */
data class RecommendationState(
    val recommendationModel: RecommendationModel
)

/**
 * One-time UI effects emitted by [RecommendationItem].
 */
sealed interface RecommendationEffect {
    /**
     * Emitted when the recommendation item is clicked.
     *
     * @param recommendationModel The clicked recommendation's model.
     */
    data class Click(val recommendationModel: RecommendationModel) : RecommendationEffect
}

/**
 * Default configuration values for [RecommendationItem].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object RecommendationDefaults {
    /**
     * Color configuration for [RecommendationItem].
     *
     * @param title Title text color.
     * @param description Description text color.
     * @param icon Placeholder icon tint color.
     * @param container Background color.
     */
    data class RecommendationColors(
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
    ) = RecommendationColors(
        title = title,
        description = description,
        icon = icon,
        container = container
    )

    /**
     * Text style configuration for [RecommendationItem].
     *
     * @param title Style applied to the title text.
     * @param description Style applied to the description text.
     */
    data class RecommendationStyle(
        val title: TextStyle,
        val description: TextStyle
    )

    @Composable
    fun style(
        title: TextStyle = System.type.h2,
        description: TextStyle = System.type.caption
    ) = RecommendationStyle(
        title = title,
        description = description
    )

    /**
     * Dimension configuration for [RecommendationItem].
     *
     * @param radius Corner radius of the thumbnail image.
     */
    data class RecommendationDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = RecommendationDimens(
        radius = radius
    )
}

/**
 * A clickable row displaying a thumbnail image, title, and description.
 * Emits [RecommendationEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the item.
 * @param colors Color configuration, defaults to [RecommendationDefaults.colors].
 * @param style Text style configuration, defaults to [RecommendationDefaults.style].
 * @param dimens Dimension configuration, defaults to [RecommendationDefaults.dimens].
 */
@Composable
fun RecommendationItem(
    state: RecommendationState,
    onEffect: (RecommendationEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: RecommendationDefaults.RecommendationColors = RecommendationDefaults.colors(),
    style: RecommendationDefaults.RecommendationStyle = RecommendationDefaults.style(),
    dimens: RecommendationDefaults.RecommendationDimens = RecommendationDefaults.dimens(),
) {
    Surface (
        modifier = modifier.fillMaxWidth(),
        color = colors.container,
        onClick = { onEffect(RecommendationEffect.Click(state.recommendationModel)) }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Card(
                shape = RoundedCornerShape(dimens.radius),
                modifier = Modifier.size(40.dp),
            ) {
                CoilImage(
                    imageModel = { state.recommendationModel.imageUrl },
                    modifier = Modifier.fillMaxSize(),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }

            Column {
                state.recommendationModel.title?.let {
                    Text(
                        text = state.recommendationModel.title,
                        color = colors.title,
                        style = style.title
                    )

                }

                state.recommendationModel.description?.let {
                    Text(
                        text = state.recommendationModel.description,
                        color = colors.description,
                        style = style.description
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecommendationItemPreview() {
    Box(modifier = Modifier
        .background(Color.Gray)
        .padding(12.dp)
    ) {
        RecommendationItem(
            state = RecommendationState(
                recommendationModel = RecommendationModel(
                    title = "Address address",
                    description = "Region",
                )
            ),
            onEffect = { effect ->
                when (effect) {
                    is RecommendationEffect.Click -> {
                        println("Clicked ${effect.recommendationModel.title}")
                    }
                }
            }
        )
    }
}