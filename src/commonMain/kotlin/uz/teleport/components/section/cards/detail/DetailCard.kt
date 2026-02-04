package uz.teleport.components.section.cards.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * UI state for [DetailCard].
 *
 * @param title Primary text displayed in the detail row.
 * @param description Secondary text displayed below the title.
 */
data class DetailCardState(
    val title: String,
    val description: String
)

/**
 * Default configuration values for [DetailCard].
 *
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object DetailCardDefaults {
    /**
     * Color configuration for [DetailCard].
     *
     * @param container Card background color.
     */
    data class DetailCardColors(
        val container: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface
    ) = DetailCardColors(
        container = container
    )

    /**
     * Dimension configuration for [DetailCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class DetailCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = DetailCardDimens(
        radius = radius
    )
}

/**
 * A rounded card that wraps a [DetailView] showing an icon, title, and description.
 *
 * @param state Current UI state.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [DetailCardDefaults.colors].
 * @param dimens Dimension configuration, defaults to [DetailCardDefaults.dimens].
 */
@Composable
fun DetailCard(
    state: DetailCardState,
    modifier: Modifier = Modifier,
    colors: DetailCardDefaults.DetailCardColors = DetailCardDefaults.colors(),
    dimens: DetailCardDefaults.DetailCardDimens = DetailCardDefaults.dimens(),
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            DetailView(
                state = DetailViewState(
                    title = state.title,
                    description = state.description,
                )
            )
        }
    }
}

@Preview
@Composable
private fun DetailCardPreview() {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        DetailCard(
            state = DetailCardState(
                title = "Chevrolet Cobalt",
                description = "Qora"
            ),
        )
    }
}