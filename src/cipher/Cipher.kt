package cipher
import java.net.URL

class Cipher {

    /**
     * Calculates the position of upper case letters within ASCII range
     */
    private val UPPER_MODIFIER: Int = 65
    /**
     * Calculates the position of lower case letters within ASCII range
     */
    private val LOWER_MODIFIER: Int = 97
    /**
     * The number of letters in the alphabet for use with % function
     */
    private val NUM_LETTERS: Int = 26
    /**
     * Stores the dictionary against which each potential word will be checked
     */
    private val dictionary: ArrayList<String> = arrayListOf()

    /**
     * Loads dictionary for use in the decipher function
     */
    init {
        getDictionary()
    }

    /**
     * Rotates each character according to n then calls encipher recursively on the rest of the string
     * Throws an exception if n is not between 0 and 25
     */
    fun encipher(s: String, n: Int): String = when {
        n > 25 || n < 0 -> throw IllegalArgumentException("Number must be between 0 and 25")
        s.isBlank() -> s
        else -> rotate(s.first(), n).plus(encipher(s.drop(1), n))
    }

    /**
     * Helper function to rotate each char by n
     */
    private fun rotate(c: Char, n: Int): Char = when {
        c.isLetter() -> {
            var modifier: Int
            if (c.isUpperCase()) modifier = UPPER_MODIFIER else modifier = LOWER_MODIFIER
            (((c.toInt() - modifier + n) % NUM_LETTERS) + modifier).toChar()
        }
        else -> c
    }

    /**
     * For each position in the alphabet, tests a rotation
     * For each word that is generated within that rotation, checks to see if there is a match in the dictionary
     * Stores the rotation that produces the best match and returns the deciphered string
     * If no words can be found in the dictionary, returns the original string
     */
    fun decipher(s: String): String {
        var numMatchedWords = 0
        var bestMatch = ""
        for(num in 0 until NUM_LETTERS) {
            val rotated = encipher(s, num)
            val split = rotated.split(" ")
            var matchWords = split.size
            split.iterator().forEach{if(!dictionary.contains(it.toLowerCase())) matchWords--}
            if (matchWords > numMatchedWords) {
                bestMatch = rotated
                numMatchedWords = matchWords
            }
            if (matchWords == split.size) break
        }
        if (numMatchedWords == 0) bestMatch = s
        return bestMatch
    }

    /**
     * Retrieves online English dictionary from given URL in text form to test whether words exist.
     * eneko (2014) words.txt, online available at https://github.com/eneko/data-repository/ (accessed 8/3/2018)
     */
    private fun getDictionary() {
        try {
            val address = URL("https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt")
            val words = address.readText().split("\n")
            words.iterator().forEach { dictionary.add(it.toLowerCase()) }
        } catch (e: Exception) {
            println("Dictionary URL not found.")
        }
    }

}
