package uz.teleport.components.primitive.fields.phonenumber

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A [VisualTransformation] that formats phone number input according to a character mask.
 *
 * Inserts separator characters from [mask] between digit groups as the user types.
 *
 * @param mask Format pattern where [maskNumber] represents a digit placeholder (e.g. `"00 000 00 00"`).
 * @param maskNumber Character used as digit placeholder in [mask].
 */
class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        return maskNumber == other.maskNumber
    }

    override fun hashCode(): Int {
        var result = mask.hashCode()
        result = 31 * result + maskNumber.hashCode()
        return result
    }

    private inner class PhoneOffsetMapper :
        OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {
            var noneDigitCount = 0
            var i = 0
            while (i < offset + noneDigitCount) {
                if (mask[i++] != maskNumber) noneDigitCount++
            }
            return offset + noneDigitCount
        }

        override fun transformedToOriginal(offset: Int): Int =
            offset - mask.take(offset).count { it != maskNumber }
    }
}