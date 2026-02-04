package uz.teleport.components.section.cards.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * UI state for [DetailBox].
 *
 * @param title Primary text displayed in the detail row.
 * @param description Secondary text displayed below the title.
 */
data class DetailBoxState(
    val title: String,
    val description: String
)

/**
 * A simple container that wraps a [DetailView] with standard padding,
 * without a card background.
 *
 * @param state Current UI state.
 */
@Composable
fun DetailBox(
    state: DetailBoxState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        DetailView(
            state = DetailViewState(
                title = state.title,
                description = state.description,
            )
        )
    }
}

@Preview
@Composable
private fun DetailBoxPreview() {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        DetailBox(
            DetailBoxState(
                title = "Foydalanuvchi",
                description = "Admin huquqlari mavjud"
            )
        )
    }
}