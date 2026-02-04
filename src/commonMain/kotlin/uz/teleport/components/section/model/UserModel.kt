package uz.teleport.components.section.model

/**
 * Represents the type of user in the system.
 */
enum class UserType {
    /** A passenger requesting a ride. */
    CLIENT,
    /** A driver offering a ride. */
    DRIVER
}

/**
 * Data model describing a user profile.
 *
 * @param name Display name of the user.
 * @param rating User's average rating.
 * @param userType Whether the user is a [UserType.CLIENT] or [UserType.DRIVER].
 * @param car Vehicle description, shown only for [UserType.DRIVER].
 */
data class UserModel(
    val name: String,
    val rating: Float,
    val userType: UserType,
    val car: String? = null
)