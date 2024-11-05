import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Xinci Ma
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    public void testGCD_8_12() {
        NaturalNumber n = new NaturalNumber2(8);
        NaturalNumber m = new NaturalNumber2(12);
        NaturalNumber expected = new NaturalNumber2(4);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(expected, n);
        assertEquals(new NaturalNumber2(0), m);
    }

    @Test
    public void testGCD_19_23() {
        NaturalNumber n = new NaturalNumber2(19);
        NaturalNumber m = new NaturalNumber2(23);
        NaturalNumber expected = new NaturalNumber2(1);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(expected, n);
        assertEquals(new NaturalNumber2(0), m);
    }

    @Test
    public void testIsEven_2() {
        NaturalNumber n = new NaturalNumber2(2);
        boolean expected = true;
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(expected, result);
    }

    @Test
    public void testIsEven_3() {
        NaturalNumber n = new NaturalNumber2(3);
        boolean expected = false;
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(expected, result);
    }

    @Test
    public void testPowerMod_2_3_5() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber p = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(5);
        NaturalNumber expected = new NaturalNumber2(3);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(expected, n);
        assertEquals(new NaturalNumber2(3), p);
        assertEquals(new NaturalNumber2(5), m);
    }

    @Test
    public void testPowerMod_7_6_13() {
        NaturalNumber n = new NaturalNumber2(7);
        NaturalNumber p = new NaturalNumber2(6);
        NaturalNumber m = new NaturalNumber2(13);
        NaturalNumber expected = new NaturalNumber2(12);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(expected, n);
        assertEquals(new NaturalNumber2(6), p);
        assertEquals(new NaturalNumber2(13), m);
    }

    @Test
    public void testIsPrime2_7() {
        NaturalNumber n = new NaturalNumber2(7);
        assertTrue(CryptoUtilities.isPrime2(n));
    }

    @Test
    public void testIsPrime2_25() {
        NaturalNumber n = new NaturalNumber2(25);
        assertTrue(!CryptoUtilities.isPrime2(n));
    }

    @Test
    public void testGenerateNextLikelyPrime_7() {
        NaturalNumber n = new NaturalNumber2(7);
        NaturalNumber expected = new NaturalNumber2(11);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(expected, n);
    }

    @Test
    public void testGenerateNextLikelyPrime_21() {
        NaturalNumber n = new NaturalNumber2(21);
        NaturalNumber expected = new NaturalNumber2(23);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(expected, n);
    }

}