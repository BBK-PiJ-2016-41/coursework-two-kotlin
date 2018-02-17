package cipherTest
import cipher.Cipher
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CipherTest {

    var cipher: Cipher? = null

    @Before
    fun before() {
        cipher = Cipher()
    }

    @Test
    fun recursiveTest() {
        assertEquals(cipher!!.encipher("Ian", 2), "Kcp")
    }

    @Test
    fun recursiveTestSpace() {
        assertEquals(cipher!!.encipher("Ia n", 2), "Kc p")
    }

    @Test
    fun recursiveTestCircular() {
        assertEquals(cipher!!.encipher("Kathryn", 5), "Pfymwds")
    }

    @Test
    fun recursiveTestCircularCaps() {
        assertEquals(cipher!!.encipher("UVWXYZ", 10), "EFGHIJ")
    }

    @Test
    fun recursiveTestPunctuation() {
        assertEquals(cipher!!.encipher("U&VWXY@Z", 10), "E&FGHI@J")
    }

    @Test(expected = IllegalArgumentException::class)
    fun negativeTest() {
        cipher!!.encipher("Kathryn", -2)
    }


    @Test
    fun encipherTest() {
        assertEquals(cipher!!.decipher("uif dbu tbu po uif nbu"), "the cat sat on the mat")
    }

}