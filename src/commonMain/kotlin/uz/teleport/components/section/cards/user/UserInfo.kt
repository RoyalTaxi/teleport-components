package uz.teleport.components.section.cards.user

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
import uz.teleport.components.section.model.UserModel
import uz.teleport.components.section.model.UserType
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.ic_star
import uz.teleport.resources.ic_user

/**
 * UI state for [UserInfo].
 *
 * @param userModel The user data to display.
 */
data class UserInfoState(
    val userModel: UserModel
)

/**
 * Default configuration values for [UserInfo].
 *
 * Provides theme-aware defaults for [colors] and [style] that can be overridden.
 */
object UserInfoDefaults {
    /**
     * Color configuration for [UserInfo].
     *
     * @param text Primary text color (name and rating).
     * @param textDisable Secondary text color (car description).
     * @param star Rating star icon tint color.
     * @param icon User placeholder icon tint color.
     * @param iconBackground Circular background color behind the user icon.
     */
    data class UserInfoColors(
        val text: Color,
        val textDisable: Color,
        val star: Color,
        val icon: Color,
        val iconBackground: Color
    )

    @Composable
    fun colors(
        text: Color = System.color.textDark,
        textDisable: Color = System.color.textDisable,
        star: Color = System.color.brandYellow,
        icon: Color =  System.color.uiIconMuted,
        iconBackground: Color = System.color.uiSurfaceSubtle
    ) = UserInfoColors(
        text = text,
        textDisable = textDisable,
        star = star,
        icon = icon,
        iconBackground = iconBackground
    )

    /**
     * Text style configuration for [UserInfo].
     *
     * @param primaryStyle Style applied to the user name and rating text.
     * @param secondaryStyle Style applied to the car description text.
     */
    data class UserInfoStyle(
        val primaryStyle: TextStyle,
        val secondaryStyle: TextStyle
    )

    @Composable
    fun style(
        primaryStyle: TextStyle = System.type.button,
        secondaryStyle: TextStyle = System.type.caption
    ) = UserInfoStyle(
        primaryStyle = primaryStyle,
        secondaryStyle = secondaryStyle
    )
}

/**
 * A horizontal row displaying a user avatar placeholder, name with rating star,
 * and optionally the car description for [UserType.DRIVER] users.
 *
 * @param state Current UI state.
 * @param modifier Modifier applied to the row.
 * @param colors Color configuration, defaults to [UserInfoDefaults.colors].
 * @param style Text style configuration, defaults to [UserInfoDefaults.style].
 */
@Composable
fun UserInfo(
    state: UserInfoState,
    modifier: Modifier = Modifier,
    colors: UserInfoDefaults.UserInfoColors = UserInfoDefaults.colors(),
    style: UserInfoDefaults.UserInfoStyle = UserInfoDefaults.style(),
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
                painter = painterResource(Res.drawable.ic_user),
                contentDescription = null,
                tint = colors.icon,
                modifier = Modifier.size(24.dp),
            )
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = state.userModel.name,
                    color = colors.text,
                    style = style.primaryStyle
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_star),
                    contentDescription = null,
                    tint = colors.star,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "${state.userModel.rating}",
                    color = colors.text,
                    style = style.primaryStyle
                )
            }

            if (state.userModel.userType == UserType.DRIVER) {
                state.userModel.car?.let {
                    Text(
                        text = state.userModel.car,
                        color = colors.textDisable,
                        style = style.secondaryStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserInfoPreview() {
    Box(
        modifier = Modifier.background(Color.Gray)
    ) {
        UserInfo(
            state = UserInfoState(
                userModel = UserModel(
                    name = "Aliyev Aziz",
                    rating = 4.9f,
                    userType = UserType.DRIVER,
                    car = "Chevrolet Cobalt"
                )
            ),
            modifier = Modifier.padding(8.dp)
        )
    }
}
