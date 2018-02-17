package cipher
import java.net.URL

class Cipher {

    private val UPPER_MODIFIER: Int = 65
    private val LOWER_MODIFIER: Int = 97
    private val NUM_LETTERS: Int = 26
    private val dictionary: ArrayList<String> = arrayListOf()

    init {
        getDictionary()
    }

    fun encipher(s: String, n: Int): String = when {
        n > 25 || n < 0 -> throw IllegalArgumentException("Number has to be between 0 and 25")
        s.isBlank() -> s
        else -> rotate(s.first(), n).plus(encipher(s.drop(1), n))
    }
    fun rotate(c: Char, n: Int): Char = when {
        c.isLetter() -> {
            var modifier = 0
            if (c.isUpperCase()) modifier = UPPER_MODIFIER else modifier = LOWER_MODIFIER
            ((((c.toInt() - modifier) + n) % NUM_LETTERS) + modifier).toChar()
        }
        else -> c
    }
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

    fun getDictionary(): Boolean {
        val address = URL("https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt")
        val text = address.readText().split("\n")
        text.iterator().forEach { dictionary.add(it.toLowerCase()) }
        return true
    }

}