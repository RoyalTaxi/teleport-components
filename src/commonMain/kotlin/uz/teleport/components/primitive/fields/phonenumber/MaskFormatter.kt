package uz.teleport.components.primitive.fields.phonenumber

/**
 * Utility for formatting raw text according to a character mask.
 */
object MaskFormatter {
    /**
     * Formats [text] by inserting non-digit mask characters from [mask].
     *
     * @param text Raw input text to format.
     * @param mask Format pattern where [maskNumber] represents a digit placeholder.
     * @param maskNumber Character used as digit placeholder in [mask].
     * @return Formatted string with mask separators inserted.
     */
    fun format(
        text: String,
        mask: String,
        maskNumber: Char = '_'
    ): String {
        if (text.isEmpty()) return ""

        val maxLength = mask.count { it == maskNumber }
        val trimmed = text.take(maxLength)

        return buildString {
            var textIndex = 0
            for (maskChar in mask)
                if (maskChar == maskNumber) {
                    if (textIndex < trimmed.length) append(trimmed[textIndex++])
                    else break
                } else {
                    append(maskChar)
                }
        }
    }
}