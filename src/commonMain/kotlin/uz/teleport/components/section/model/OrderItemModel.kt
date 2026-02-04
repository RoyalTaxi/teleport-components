package uz.teleport.components.section.model

/**
 * Identifies the screen context in which an order is displayed.
 */
enum class Screen {
    /** The main home screen. */
    HOME,
    /** The user's trip history screen. */
    MY_TRIPS
}

/**
 * Data model representing a single order (trip) item.
 *
 * @param id Unique identifier of the order.
 * @param time Departure time label.
 * @param day Day label (e.g. "Today", "Tomorrow").
 * @param status Short status badge text (e.g. "Active").
 * @param state Detailed state description shown on the [Screen.MY_TRIPS] screen.
 * @param screen The screen context that controls additional UI elements.
 * @param startAddress Origin address text.
 * @param finishAddress Destination address text.
 * @param seat Number of seats requested or available.
 * @param price Price per seat.
 * @param userModel The [UserModel] associated with this order.
 */
data class OrderItemModel(
    val id: Int,
    val time: String,
    val day: String,
    val status: String,
    val state: String,
    val screen: Screen,
    val startAddress: String,
    val finishAddress: String,
    val seat: Int,
    val price: Int,
    val userModel: UserModel
)