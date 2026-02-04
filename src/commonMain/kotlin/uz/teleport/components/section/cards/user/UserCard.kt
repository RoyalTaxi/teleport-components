package uz.teleport.components.section.cards.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.components.section.model.UserModel
import uz.teleport.components.section.model.UserType
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_call
import uz.teleport.resources.ic_call
import uz.teleport.resources.ic_chat
import uz.teleport.resources.search_result_message

/**
 * UI state for [UserCard].
 *
 * @param userModel The user data to display.
 * @param callEnable Whether the call action button is enabled.
 * @param messageEnable Whether the message action button is enabled.
 */
data class UserCardState(
    val userModel: UserModel,
    val callEnable: Boolean = true,
    val messageEnable: Boolean = true
)

/**
 * One-time UI effects emitted by [UserCard].
 */
sealed interface UserCardEffect {
    /** Emitted when the call button is clicked. */
    data object ClickCall : UserCardEffect
    /** Emitted when the message button is clicked. */
    data object ClickMessage : UserCardEffect
}

/**
 * Default configuration values for [UserCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object UserCardDefaults {
    /**
     * Color configuration for [UserCard].
     *
     * @param container Card background color.
     * @param primary Text and icon color for enabled action buttons.
     * @param secondary Text and icon color for disabled action buttons.
     * @param border Divider color between the user info and action buttons.
     */
    data class UserCardColors(
        val container: Color,
        val primary: Color,
        val secondary: Color,
        val border: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        primary: Color = System.color.textDark,
        secondary: Color = System.color.textDisable,
        border: Color = System.color.uiBorder,
    ) = UserCardColors(
        container = container,
        primary = primary,
        secondary = secondary,
        border = border
    )

    /**
     * Text style configuration for [UserCard].
     *
     * @param contentStyle Style applied to the action button labels.
     */
    data class UserCardStyle(
        val contentStyle: TextStyle
    )

    @Composable
    fun style(
        contentStyle: TextStyle = System.type.button
    ) = UserCardStyle(
        contentStyle = contentStyle
    )

    /**
     * Dimension configuration for [UserCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class UserCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 16.dp
    ) = UserCardDimens(
        radius = radius
    )
}

/**
 * A card displaying user information with call and message action buttons at the bottom.
 * Emits [UserCardEffect.ClickCall] or [UserCardEffect.ClickMessage] when the respective
 * button is tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [UserCardDefaults.colors].
 * @param style Text style configuration, defaults to [UserCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [UserCardDefaults.dimens].
 */
@Composable
fun UserCard(
    state: UserCardState,
    onEffect: (UserCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: UserCardDefaults.UserCardColors = UserCardDefaults.colors(),
    style: UserCardDefaults.UserCardStyle = UserCardDefaults.style(),
    dimens: UserCardDefaults.UserCardDimens = UserCardDefaults.dimens(),
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimens.radius),
        colors = CardDefaults.cardColors(colors.container),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            UserInfo(state = UserInfoState(state.userModel))
        }

        HorizontalDivider(
            color = colors.border,
            modifier = Modifier.height(1.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = state.callEnable,
                onClick = { onEffect(UserCardEffect.ClickCall) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.container)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_call),
                        contentDescription = null,
                        tint = if (state.callEnable) colors.primary else colors.secondary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(Res.string.common_call),
                        color = if (state.callEnable) colors.primary else colors.secondary,
                        style = style.contentStyle
                    )
                }
            }

            VerticalDivider(
                color = System.color.uiBorder,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .padding(vertical = 8.dp)
            )

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                enabled = state.messageEnable,
                onClick = { onEffect(UserCardEffect.ClickMessage) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.container)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chat),
                        contentDescription = null,
                        tint = if (state.messageEnable) colors.primary else colors.secondary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(Res.string.search_result_message),
                        color = if (state.messageEnable) colors.primary else colors.secondary,
                        style = style.contentStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserCardPreview() {
    Box(
        modifier = Modifier.background(Color.Gray)
    ) {
        UserCard(
            state = UserCardState(
                userModel = UserModel(
                    name = "Aliyev Aziz",
                    rating = 4.9f,
                    userType = UserType.CLIENT,
                    car = "Chevrolet Cobalt"
                ),
                callEnable = false,
                messageEnable = true
            ),
            onEffect = {
                when (it) {
                    UserCardEffect.ClickCall -> {}
                    UserCardEffect.ClickMessage -> {}
                }
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}
