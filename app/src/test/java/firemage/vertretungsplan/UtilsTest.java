package firemage.vertretungsplan;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Created by flose on 18.09.2017.
 */

public class UtilsTest {
    @Test
    public void getAllNumbersTest() {
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, Utils.getAllNumbers("This is a 1 and there's a 2 and 34").toArray());
    }

    @Test
    public void removeAllNumbersTest() {
        assertTrue(Utils.removeAllNumbers("I1ch bin 44").equals("Ich bin "));
    }

    @Test
    public void removeAllDotsTest() {
        assertTrue(Utils.removeAllDots("Ich. bin: 44.").equals("Ich bin: 44"));
    }

    @Test
    public void isNumberInRangeTest() {
        assertTrue(Utils.isNumberInRange(3, "1-6"));
        assertFalse(Utils.isNumberInRange(7, "1-6"));
        assertTrue(Utils.isNumberInRange(1, "1-6"));
        assertTrue(Utils.isNumberInRange(6, "1-6"));

        try {
            Utils.isNumberInRange(7, "hghgh");
            fail();
        } catch(IllegalArgumentException ex) {
            //expected
        }
        try {
            Utils.isNumberInRange(7, "1-?8");
            fail();
        } catch(IllegalArgumentException ex) {
            //expected
        }
        try {
            Utils.isNumberInRange(7, "1-4-8");
            fail();
        } catch(IllegalArgumentException ex) {
            //expected
        }
    }

    @Test
    public void isLetterInRange() {
        assertTrue(Utils.isLetterInRange('c', "a-d"));
        assertFalse(Utils.isLetterInRange('f', "a-d"));
        assertTrue(Utils.isLetterInRange('a', "a-d"));
        assertTrue(Utils.isLetterInRange('d', "a-d"));
    }

    @Test
    public void containsLettersTest() {
        assertTrue(Utils.containsLetters("Hello World!"));
        assertTrue(Utils.containsLetters("ü"));
        assertFalse(Utils.containsLetters("1 + 2 = 3"));
    }

    @Test
    public void isRangeTest() {
        assertTrue(Utils.isRange("3-4"));
        assertTrue(Utils.isRange("a-d"));
        assertFalse(Utils.isRange("agagag"));
        assertFalse(Utils.isRange("1-2-5"));
    }
}
