package uz.teleport.components.section.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_arrow_right
import uz.teleport.resources.ic_user

/**
 * UI state for [ProfileCard].
 *
 * @param name User display name.
 * @param phone Formatted phone number.
 * @param avatarUrl Optional URL for the user's avatar image. When `null` or empty, a placeholder icon is shown.
 */
data class ProfileCardState(
    val name: String,
    val phone: String,
    val avatarUrl: String? = null
)

/**
 * One-time UI effects emitted by [ProfileCard].
 */
sealed interface ProfileCardEffect {
    /** Emitted when the card is clicked. */
    data object Click : ProfileCardEffect
}

/**
 * Default configuration values for [ProfileCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object ProfileCardDefaults {
    /**
     * Color configuration for [ProfileCard].
     *
     * @param container Card background color.
     * @param text Name and phone text color.
     * @param avatarPlaceholder Tint color for the placeholder icon when no avatar is set.
     * @param avatarPlaceholderBackground Background color of the avatar placeholder circle.
     * @param navigationIcon Tint color for the trailing navigation arrow.
     */
    data class ProfileCardColors(
        val container: Color,
        val text: Color,
        val avatarPlaceholder: Color,
        val avatarPlaceholderBackground: Color,
        val navigationIcon: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.brandGreen,
        text: Color = System.color.textLight,
        avatarPlaceholder: Color = System.color.uiIconMuted,
        avatarPlaceholderBackground: Color = System.color.uiSurfaceSubtle,
        navigationIcon: Color = System.color.uiSurface,
    ) = ProfileCardColors(
        container = container,
        text = text,
        avatarPlaceholder = avatarPlaceholder,
        avatarPlaceholderBackground = avatarPlaceholderBackground,
        navigationIcon = navigationIcon,
    )

    /**
     * Text style configuration for [ProfileCard].
     *
     * @param name Style applied to the user name text.
     * @param phone Style applied to the phone number text.
     */
    data class ProfileCardStyle(
        val name: TextStyle,
        val phone: TextStyle
    )

    @Composable
    fun style(
        name: TextStyle = System.type.button,
        phone: TextStyle = System.type.caption
    ) = ProfileCardStyle(
        name = name,
        phone = phone
    )

    /**
     * Dimension configuration for [ProfileCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class ProfileCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = ProfileCardDimens(
        radius = radius
    )
}

/**
 * A branded profile card showing a user avatar (or placeholder), name, phone number,
 * and a trailing navigation arrow. Emits [ProfileCardEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [ProfileCardDefaults.colors].
 * @param style Text style configuration, defaults to [ProfileCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [ProfileCardDefaults.dimens].
 */
@Composable
fun ProfileCard(
    state: ProfileCardState,
    onEffect: (ProfileCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: ProfileCardDefaults.ProfileCardColors = ProfileCardDefaults.colors(),
    style: ProfileCardDefaults.ProfileCardStyle = ProfileCardDefaults.style(),
    dimens: ProfileCardDefaults.ProfileCardDimens = ProfileCardDefaults.dimens(),
) {
    Card(
        onClick = { onEffect(ProfileCardEffect.Click) },
        modifier = modifier,
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colors.avatarPlaceholderBackground)
                    .size(32.dp)
            ) {
                if (!state.avatarUrl.isNullOrEmpty()) {
                    CoilImage(
                        imageModel = { state.avatarUrl },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        ),
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.ic_user),
                        contentDescription = null,
                        tint = colors.avatarPlaceholder
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = state.name,
                    style = style.name,
                    color = colors.text
                )

                Text(
                    text = state.phone,
                    style = style.phone,
                    color = colors.text
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(Res.drawable.ic_arrow_right),
                contentDescription = null,
                tint = colors.navigationIcon,
            )
        }
    }
}

@Preview
@Composable
private fun ProfileCardPreview() {
    ProfileCard(
        state = ProfileCardState(
            name = "John Doe",
            phone = "+998 90 123 45 67"
        ),
        onEffect = { },
        modifier = Modifier.padding(8.dp)
    )
}