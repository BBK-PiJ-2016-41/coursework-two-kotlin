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
     * Calls getDictionary() helper function in init statement for later use.
     */
    init {
        getDictionary()
    }

    /**
     * Throws an exception if n is not between 0 and 25
     * Rotates each character according to n then calls encipher recursively on the rest of the string
     */
    fun encipher(s: String, n: Int): String = when {
        n > 25 || n < 0 -> throw IllegalArgumentException("Number has to be between 0 and 25")
        s.isBlank() -> s
        else -> rotate(s.first(), n).plus(encipher(s.drop(1), n))
    }

    /**
     * Helper function to rotate each char by n
     */
    fun rotate(c: Char, n: Int): Char = when {
        c.isLetter() -> {
            var modifier = 0
            if (c.isUpperCase()) modifier = UPPER_MODIFIER else modifier = LOWER_MODIFIER
            ((((c.toInt() - modifier) + n) % NUM_LETTERS) + modifier).toChar()
        }
        else -> c
    }

    /**
     * For each position in the alphabet, tests a rotation
     * For each word that is generated within that rotation, checks to see if there is a match in the dictionary
     * If it finds that all words match, this rotator position is used to decode the phrase
     */
    fun decipher(s: String): String {
        var rotator = 0
        for(i in 0 until NUM_LETTERS) {
            val rotated = this.encipher(s, i).split(" ")
            var matchWords = rotated.size
            rotated.iterator().forEach{x -> if(!dictionary.contains(x.toLowerCase())) matchWords-- }
            if (matchWords == rotated.size) rotator = i
        }
        return encipher(s, rotator)
    }

    /**
     * Retrieves online English dictionary from given URL in text form to test whether words exist
     */
    fun getDictionary() {
        val address = URL("https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt")
        val text = address.readText().split("\n")
        text.iterator().forEach { dictionary.add(it.toLowerCase()) }
    }

}