package uz.teleport.components.section.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import uz.teleport.components.primitive.route.VerticalRoute
import uz.teleport.components.section.cards.user.UserInfo
import uz.teleport.components.section.cards.user.UserInfoState
import uz.teleport.components.section.model.OrderItemModel
import uz.teleport.components.section.model.Screen
import uz.teleport.components.section.model.UserModel
import uz.teleport.components.section.model.UserType
import uz.teleport.design.theme.System
import uz.teleport.resources.Res
import uz.teleport.resources.common_for_a_place
import uz.teleport.resources.common_sums
import uz.teleport.resources.ic_clock

/**
 * UI state for [OrderCard].
 *
 * @param orderItemModel The order data model to display.
 */
data class OrderCardState(
    val orderItemModel: OrderItemModel
)

/**
 * One-time UI effects emitted by [OrderCard].
 */
sealed interface OrderCardEffect {
    /**
     * Emitted when the order card is clicked.
     *
     * @param orderItemModel The clicked order's model.
     */
    data class Click(val orderItemModel: OrderItemModel) : OrderCardEffect
}

/**
 * Default configuration values for [OrderCard].
 *
 * Provides theme-aware defaults for [colors], [style], and [dimens] that can be overridden.
 */
object OrderCardDefaults {
    /**
     * Color configuration for [OrderCard].
     *
     * @param container Card background color.
     * @param text Primary text color.
     * @param textDisable Secondary (disabled) text color.
     * @param dot Separator dot color between time and day.
     * @param divider Horizontal divider color.
     * @param brandPrimary Brand color for driver seat availability.
     * @param brandSecondary Brand color for client seat requests and trip state.
     */
    data class OrderCardColors(
        val container: Color,
        val text: Color,
        val textDisable: Color,
        val dot: Color,
        val divider: Color,
        val brandPrimary: Color,
        val brandSecondary: Color
    )

    @Composable
    fun colors(
        container: Color = System.color.uiSurface,
        text: Color = System.color.textDark,
        textDisable: Color = System.color.textDisable,
        dot: Color = System.color.uiDark,
        divider: Color = System.color.uiBorder,
        brandPrimary: Color = System.color.brandGreen,
        brandSecondary: Color = System.color.brandYellow
    ) = OrderCardColors(
        container = container,
        text = text,
        textDisable = textDisable,
        dot = dot,
        divider = divider,
        brandPrimary = brandPrimary,
        brandSecondary = brandSecondary
    )

    /**
     * Text style configuration for [OrderCard].
     *
     * @param primary Style for captions (day, seat info).
     * @param secondary Style for time and state labels.
     * @param address Style for start and finish address texts.
     * @param price Style for the price text.
     * @param status Style for the floating status badge.
     */
    data class OrderCardStyle(
        val primary: TextStyle,
        val secondary: TextStyle,
        val address: TextStyle,
        val price: TextStyle,
        val status: TextStyle
    )

    @Composable
    fun style(
        primary: TextStyle = System.type.caption,
        secondary: TextStyle = System.type.button,
        address: TextStyle = System.type.bodySemiBold,
        price: TextStyle = System.type.h2,
        status: TextStyle = System.type.small
    ) = OrderCardStyle(
        primary = primary,
        secondary = secondary,
        address = address,
        price = price,
        status = status
    )

    /**
     * Dimension configuration for [OrderCard].
     *
     * @param radius Corner radius of the card shape.
     */
    data class OrderCardDimens(
        val radius: Dp
    )

    @Composable
    fun dimens(
        radius: Dp = 12.dp
    ) = OrderCardDimens(
        radius = radius
    )
}

/**
 * A rich order card displaying time, day, seat availability, origin/destination
 * addresses with a [VerticalRoute], price, user info, and a floating status badge.
 * Shows an additional state header on the [Screen.MY_TRIPS] screen.
 * Emits [OrderCardEffect.Click] when tapped.
 *
 * @param state Current UI state.
 * @param onEffect Callback for one-time UI effects.
 * @param modifier Modifier applied to the card.
 * @param colors Color configuration, defaults to [OrderCardDefaults.colors].
 * @param style Text style configuration, defaults to [OrderCardDefaults.style].
 * @param dimens Dimension configuration, defaults to [OrderCardDefaults.dimens].
 */
@Composable
fun OrderCard(
    state: OrderCardState,
    onEffect: (OrderCardEffect) -> Unit,
    modifier: Modifier = Modifier,
    colors: OrderCardDefaults.OrderCardColors = OrderCardDefaults.colors(),
    style: OrderCardDefaults.OrderCardStyle = OrderCardDefaults.style(),
    dimens: OrderCardDefaults.OrderCardDimens = OrderCardDefaults.dimens(),
) {
    Box {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(dimens.radius),
            colors = CardDefaults.cardColors(colors.container),
            onClick = { onEffect(OrderCardEffect.Click(state.orderItemModel)) }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (state.orderItemModel.screen == Screen.MY_TRIPS) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_clock),
                            contentDescription = null,
                            tint = colors.brandSecondary,
                            modifier = Modifier.size(20.dp),
                        )

                        Text(
                            text = state.orderItemModel.state,
                            style = style.secondary,
                            color = colors.brandSecondary
                        )
                    }

                    HorizontalDivider(
                        color = colors.divider,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(1.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.orderItemModel.time,
                            color = colors.text,
                            style = style.secondary
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(colors.dot, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = state.orderItemModel.day,
                            color = colors.text,
                            style = style.primary
                        )
                    }

                    when (state.orderItemModel.userModel.userType) {
                        UserType.CLIENT -> {
                            Text(
                                text = "${state.orderItemModel.seat} ta joy kerak",
                                color = colors.brandSecondary,
                                style = style.primary
                            )
                        }

                        UserType.DRIVER -> {
                            Text(
                                text = "${state.orderItemModel.seat} ta bo'sh joy",
                                color = colors.brandPrimary,
                                style = style.primary
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VerticalRoute()

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = state.orderItemModel.startAddress,
                                color = colors.text,
                                style = style.address
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = state.orderItemModel.finishAddress,
                                color = colors.text,
                                style = style.address
                            )
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ){
                        Text(
                            text = "${state.orderItemModel.price} ${stringResource(Res.string.common_sums)}",
                            color = colors.text,
                            style = style.price
                        )

                        Text(
                            text = stringResource(Res.string.common_for_a_place),
                            color = colors.textDisable,
                            style = style.primary
                        )
                    }
                }

                HorizontalDivider(
                    color = colors.divider,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                )

                UserInfo(UserInfoState(state.orderItemModel.userModel))
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-10).dp)
                .padding(end = 16.dp)
                .background(
                    color = colors.brandPrimary,
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = state.orderItemModel.status,
                color = colors.container,
                style = style.status
            )
        }
    }
}

@Preview
@Composable
private fun OrderCardPreview() {
    Column(modifier = Modifier
        .background(Color.Gray)
        .padding(12.dp)
    ) {
        OrderCard(
            state = OrderCardState(
                orderItemModel = OrderItemModel(
                    id = 1,
                    time = "09:30",
                    day = "Bugun",
                    status = "Active",
                    screen = Screen.HOME,
                    state = "Tasdiqlanishini kuting",
                    startAddress = "Toshkent",
                    finishAddress = "Fergana",
                    seat = 3,
                    price = 25000,
                    userModel = UserModel(
                        name = "Akmal",
                        rating = 4.8f,
                        userType = UserType.CLIENT,
                        car = "Chevrolet Cobalt"
                    )
                ),
            ),
            onEffect = { effect ->
                when (effect) {
                    is OrderCardEffect.Click -> {
                        println("Clicked ${effect.orderItemModel.id}")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OrderCard(
            state = OrderCardState(
                orderItemModel = OrderItemModel(
                    id = 1,
                    time = "09:30",
                    day = "Bugun",
                    status = "Active",
                    screen = Screen.MY_TRIPS,
                    state = "Tasdiqlanishini kuting",
                    startAddress = "Toshkent",
                    finishAddress = "Fergana",
                    seat = 1,
                    price = 25000,
                    userModel = UserModel(
                        name = "Akmal",
                        rating = 4.8f,
                        userType = UserType.CLIENT,
                        car = "Chevrolet Cobalt"
                    )
                ),
            ),
            onEffect = { effect ->
                when (effect) {
                    is OrderCardEffect.Click -> {
                        println("Clicked ${effect.orderItemModel.id}")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OrderCard(
            state = OrderCardState(
                orderItemModel = OrderItemModel(
                    id = 1,
                    time = "09:30",
                    day = "Bugun",
                    status = "Active",
                    screen = Screen.HOME,
                    state = "Tasdiqlanishini kuting",
                    startAddress = "Toshkent",
                    finishAddress = "Fergana",
                    seat = 2,
                    price = 25000,
                    userModel = UserModel(
                        name = "Akmal",
                        rating = 4.8f,
                        userType = UserType.DRIVER,
                        car = "Chevrolet Cobalt"
                    )
                ),
            ),
            onEffect = { effect ->
                when (effect) {
                    is OrderCardEffect.Click -> {
                        println("Clicked ${effect.orderItemModel.id}")
                    }
                }
            }
        )
    }
}