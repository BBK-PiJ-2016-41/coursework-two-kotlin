package cipherTest
import cipher.Cipher
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CipherTest {

    lateinit var cipher: Cipher

    @Before
    fun before() {
        cipher = Cipher()
    }

    @Test
    fun encipherMixedUpperLowerTest() {
        assertEquals(cipher.encipher("Ian", 2), "Kcp")
    }

    @Test
    fun encipherAllLowerTest() {
        assertEquals(cipher.encipher("ian", 2), "kcp")
    }

    @Test
    fun encipherSpaceTest() {
        assertEquals(cipher.encipher("Ia n", 2), "Kc p")
    }

    @Test
    fun encipherSentenceTest() {
        assertEquals(cipher.encipher("The cat sat on the mat.", 1), "Uif dbu tbu po uif nbu.")
    }

    @Test
    fun encipherOneCharTest() {
        assertEquals(cipher.encipher("I", 2), "K")
    }

    @Test
    fun encipherCircularTest() {
        assertEquals(cipher.encipher("Kathryn", 5), "Pfymwds")
    }

    @Test
    fun encipherCircularCapsTest() {
        assertEquals(cipher.encipher("UVWXYZ", 10), "EFGHIJ")
    }

    @Test
    fun encipherPunctuationNumbersTest() {
        assertEquals(cipher.encipher("1U&VWXY@Z", 10), "1E&FGHI@J")
    }

    @Test(expected = IllegalArgumentException::class)
    fun encipherNegativeTest() {
        cipher.encipher("Kathryn", -2)
    }

    @Test(expected = IllegalArgumentException::class)
    fun encipherTwentySixTest() {
        cipher.encipher("Kathryn", 26)
    }

    @Test
    fun encipherZeroTest() {
        assertEquals(cipher.encipher("Kathryn", 0), "Kathryn")
    }

    @Test
    fun encipherTwentyFiveTest() {
        assertEquals(cipher.encipher("Kathryn", 25), "Jzsgqxm")
    }

    @Test
    fun decipherMultiWordTest() {
        assertEquals(cipher.decipher("uif dbu tbu po uif nbu"), "the cat sat on the mat")
    }

    @Test
    fun decipherMixedNonExistentTest() {
        assertEquals(cipher.decipher("uif dbu jfajhjd po uif nbu"), "the cat iezigic on the mat")
    }

    @Test
    fun decipherNonExistentTest() {
        assertEquals(cipher.decipher("jfajhjd"), "jfajhjd")
    }

    @Test
    fun decipherSingleWordTest() {
        assertEquals(cipher.decipher("nbuf"), "mate")
    }

    @Test
    fun decipherPunctuationTest() {
        assertEquals(cipher.decipher("Uif dbu tbu po 2 nbut!"), "The cat sat on 2 mats!")
    }

}