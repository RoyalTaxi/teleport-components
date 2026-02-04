package uz.teleport.components.section.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_checked_rectangle
import uz.teleport.resources.ic_no_checked_rectangle

/**
 * Data model for a single checkable list item.
 *
 * @param id Unique identifier.
 * @param text Display text label.
 * @param isChecked Whether the item is currently checked.
 */
data class CheckableItemModel(
    val id: Int,
    val text: String,
    val isChecked: Boolean = false
)

/**
 * UI state for [CheckableItem].
 *
 * @param checkableItemModel The data model to display.
 * @param isReverse When `true`, the checkbox icon is placed before the text; otherwise after.
 */
data class CheckableItemState(
    val checkableItemModel: CheckableItemModel,
    val isReverse: Boolean = false,
)

/**
 * One-time UI effects emitted by [CheckableItem].
 */
sealed interface CheckableItemEffect {
    /**
     * Emitted when the item is clicked.
     *
     * @param checkableItemModel The clicked item's model.
     */
    data class Click(val checkableItemModel: CheckableItemModel) : CheckableItemEffect
}

/**
 * Default configuration values for [CheckableItem].
 *
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object CheckableItemDefaults {
    /**
     * Color configuration for [CheckableItem].
     *
     * @param text Label text color.
     * @param container Background color.
     */
    data class CheckableItemColors(
        val text: Color,
        val container: Color
    )

    @Composable
    fun colors(
        text: Color = System.color.textDark,
        container: Color = Color.Transparent
    ) = CheckableItemColors(
        text = text,
        container = container
    )

    /**
     * Text style configuration for [CheckableItem].
     *
     * @param contentStyle Style applied to the label text.
     */
    data class CheckableItemStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.caption
    ) = CheckableItemStyle(
        contentStyle = contentStyle
    )
}

/**
 * A clickable row displaying a text label and a checkbox icon that reflects the checked state.
 * The layout direction can be reversed via [CheckableItemState.isReverse].
 * Emits [CheckableItemEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the item.
 * @param colors Color configuration, defaults to [CheckableItemDefaults.colors].
 * @param style Text style configuration, defaults to [CheckableItemDefaults.style].
 */
@Composable
fun CheckableItem(
    state: CheckableItemState,
    onEffect: (CheckableItemEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: CheckableItemDefaults.CheckableItemColors = CheckableItemDefaults.colors(),
    style: CheckableItemDefaults.CheckableItemStyle = CheckableItemDefaults.style(),
) {
    Surface (
        onClick = { onEffect(CheckableItemEffect.Click(state.checkableItemModel)) },
        color = colors.container,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            if (state.isReverse) {
                Icon(
                    painter = if (state.checkableItemModel.isChecked) painterResource(Res.drawable.ic_checked_rectangle) else painterResource(Res.drawable.ic_no_checked_rectangle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )

                Text(
                    text = state.checkableItemModel.text,
                    style = style.contentStyle,
                    color = colors.text,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            } else {
                Text(
                    text = state.checkableItemModel.text,
                    style = style.contentStyle,
                    color = colors.text,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = if (state.checkableItemModel.isChecked) painterResource(Res.drawable.ic_checked_rectangle) else painterResource(Res.drawable.ic_no_checked_rectangle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun CheckableItemPreview() {
    val items = remember {
        mutableStateListOf(
            CheckableItemModel(id = 1, text = "Whole salon", isChecked = true),
            CheckableItemModel(id = 2, text = "Private room", isChecked = false),
            CheckableItemModel(id = 3, text = "VIP zone", isChecked = false)
        )
    }

    Box(
        modifier = Modifier
            .background(Color.Gray)
            .padding(12.dp)
    ) {
        Column {
            items.forEachIndexed { index, item ->
                CheckableItem(
                    state = CheckableItemState(
                        checkableItemModel = item,
                        isReverse = index % 2 == 0
                    ),
                    onEffect = { effect ->
                        when (effect) {
                            is CheckableItemEffect.Click -> {
                                val index1 = items.indexOfFirst { it.id == effect.checkableItemModel.id }
                                if (index1 != -1) {
                                    val currentItem = items[index1]
                                    items[index1] = currentItem.copy(
                                        isChecked = !currentItem.isChecked
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
