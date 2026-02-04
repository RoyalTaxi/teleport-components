package uz.teleport.components.primitive.fields.phonenumber

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A [VisualTransformation] that formats numeric input according to a character mask.
 *
 * Uses [MaskFormatter] to insert separator characters from [mask] into the raw digit string.
 *
 * @param mask Format pattern where [maskNumber] represents a digit placeholder.
 * @param maskNumber Character used as digit placeholder in [mask].
 */
class NumberVisualTransformation(
    private val mask: String,
    private val maskNumber: Char
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val formatted = MaskFormatter.format(
            text = text.text,
            mask = mask,
            maskNumber = maskNumber
        )

        val transformed = AnnotatedString(formatted)

        return TransformedText(transformed, PhoneOffsetMapper())
    }

    private inner class PhoneOffsetMapper : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            var transformed = 0
            var digits = 0
            for (ch in mask) {
                if (digits == offset) break
                if (ch == maskNumber) digits++
                transformed++
            }
            return transformed
        }

        override fun transformedToOriginal(offset: Int): Int =
            mask.take(offset).count { it == maskNumber }
    }
}