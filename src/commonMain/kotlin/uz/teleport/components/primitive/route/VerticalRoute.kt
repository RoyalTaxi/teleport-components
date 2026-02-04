package uz.teleport.components.primitive.route

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import uz.teleport.design.theme.System

/**
 * Default configuration values for [VerticalRoute].
 * Provides theme-aware defaults for [colors] and [dimens] that can be overridden.
 */
object VerticalRouteDefaults {

    /**
     * Color configuration for [VerticalRoute].
     *
     * @param dot Color of the endpoint dots.
     * @param line Color of the connecting line.
     */
    data class VerticalRouteColors(
        val dot: Color,
        val line: Color
    )

    @Composable
    fun colors(
        dot: Color = System.color.brandGreen,
        line: Color = System.color.uiBorder
    ) = VerticalRouteColors(
        dot = dot,
        line = line
    )

    /**
     * Dimension configuration for [VerticalRoute].
     *
     * @param dotSize Diameter of the endpoint dots.
     * @param lineHeight Height of the connecting line between dots.
     */
    data class VerticalRouteDimens(
        val dotSize: Dp,
        val lineHeight: Dp
    )

    @Composable
    fun dimens(
        dotSize: Dp = 10.dp,
        lineHeight: Dp = 20.dp
    ) = VerticalRouteDimens(
        dotSize = dotSize,
        lineHeight = lineHeight
    )
}

/**
 * A vertical route indicator displaying two dots connected by a line,
 * typically used to represent origin and destination points.
 *
 * @param modifier Modifier applied to the route.
 * @param colors Color configuration, defaults to [VerticalRouteDefaults.colors].
 * @param dimens Dimension configuration, defaults to [VerticalRouteDefaults.dimens].
 */
@Composable
fun VerticalRoute(
    modifier: Modifier = Modifier,
    colors: VerticalRouteDefaults.VerticalRouteColors = VerticalRouteDefaults.colors(),
    dimens: VerticalRouteDefaults.VerticalRouteDimens = VerticalRouteDefaults.dimens()
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(dimens.dotSize)
                .background(colors.dot, CircleShape)
        )

        Box(
            modifier = Modifier
                .width(2.dp)
                .height(dimens.lineHeight)
                .padding(vertical = 4.dp)
                .background(colors.line)
        )

        Box(
            modifier = Modifier
                .size(dimens.dotSize)
                .background(colors.dot, CircleShape)
        )
    }
}

@Preview
@Composable
private fun VerticalRoutePreview() {
    VerticalRoute(
        modifier = Modifier.padding(16.dp),
        dimens = VerticalRouteDefaults.dimens(
            lineHeight = 32.dp
        )
    )
}
