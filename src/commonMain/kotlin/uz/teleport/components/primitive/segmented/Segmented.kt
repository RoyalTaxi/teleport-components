package uz.teleport.components.primitive.segmented

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * Represents a single tab item in a [Segmented] control.
 *
 * @param title The text displayed on the tab.
 */
data class TabItem(
    val title: String
)

/**
 * UI state for [Segmented].
 *
 * @param selectedTabIndex Index of the currently selected tab.
 * @param tabs List of tab items displayed in the control.
 */
data class SegmentedState(
    val selectedTabIndex: Int,
    val tabs: List<TabItem>
)

/**
 * One-time UI effects emitted by [Segmented].
 */
sealed interface SegmentedEffect {
    /**
     * Emitted when a tab is clicked.
     *
     * @param selectedTabIndex Index of the clicked tab.
     */
    data class Click(val selectedTabIndex: Int) : SegmentedEffect
}

/**
 * Default configuration values for [Segmented].
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object SegmentedDefaults {
    /**
     * Color configuration for [Segmented].
     *
     * @param selectedText Text color of the selected tab.
     * @param unselectedText Text color of unselected tabs.
     * @param container Background color of the control.
     * @param selectedTab Background color of the selected tab indicator.
     */
    data class SegmentedColors(
        val selectedText: Color,
        val unselectedText: Color,
        val container: Color,
        val selectedTab: Color
    )

    @Composable
    fun colors(
        selectedText: Color = System.color.brandGreen,
        unselectedText: Color = System.color.textDisable,
        container: Color = System.color.uiSurfaceSubtle,
        selectedTab: Color = System.color.uiSurface
    ) = SegmentedColors(
        selectedText = selectedText,
        unselectedText = unselectedText,
        container = container,
        selectedTab = selectedTab
    )

    /**
     * Text style configuration for [Segmented].
     *
     * @param contentStyle Style applied to tab labels.
     */
    data class SegmentedStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.caption
    ) = SegmentedStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [Segmented].
     *
     * @param radius Corner radius.
     * @param spacing Inner content padding.
     */
    data class SegmentedDimens(
        val radius: Dp,
        val spacing: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp,
        spacing: Dp = 4.dp
    ) = SegmentedDimens(
        radius = radius,
        spacing = spacing
    )
}

/**
 * A horizontal segmented tab control with an animated selection indicator.
 * Highlights the selected tab and animates indicator movement between tabs.
 * Emits [SegmentedEffect.Click] when a tab is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the control.
 * @param colors Color configuration, defaults to [SegmentedDefaults.colors].
 * @param style Text style configuration, defaults to [SegmentedDefaults.style].
 * @param dimens Dimension configuration, defaults to [SegmentedDefaults.dimens].
 */
@Composable
fun Segmented(
    state: SegmentedState,
    onEffect: (SegmentedEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: SegmentedDefaults.SegmentedColors = SegmentedDefaults.colors(),
    style: SegmentedDefaults.SegmentedStyle = SegmentedDefaults.style(),
    dimens: SegmentedDefaults.SegmentedDimens = SegmentedDefaults.dimens(),
) {
    if (state.tabs.isEmpty()) return

    BoxWithConstraints(
        modifier = modifier
            .clip(RoundedCornerShape(dimens.radius))
            .background(colors.container)
            .padding(dimens.spacing)
    ) {
        val itemWidth = maxWidth / state.tabs.size

        val indicatorOffset by animateDpAsState(
            targetValue = itemWidth * state.selectedTabIndex,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
        )

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(itemWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(dimens.radius))
                .background(colors.selectedTab)
        )

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            state.tabs.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    elevation = CardDefaults.cardElevation(0.dp),
                    onClick = {
                        onEffect(SegmentedEffect.Click(index))
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .width(itemWidth)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.title,
                            style = style.contentStyle,
                            color = if (state.selectedTabIndex == index) colors.selectedText else colors.unselectedText,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SegmentedPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(16.dp)
        ) {
            var selectedIndex by remember { mutableStateOf(0) }

            Segmented(
                state = SegmentedState(
                    selectedTabIndex = selectedIndex,
                    tabs = listOf(
                        TabItem("Erkak"),
                        TabItem("Ayol")
                    )
                ),
                onEffect = {
                    when (it) {
                        is SegmentedEffect.Click -> {
                            selectedIndex = it.selectedTabIndex
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}