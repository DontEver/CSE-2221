import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    @Test
    public void testCombination() {
        String str1 = "Hello, world";
        String str2 = "orld! How are you?";
        int overlap = 4;
        String expectedCombination = "Hello, world! How are you?";
        String actualCombination = StringReassembly.combination(str1, str2,
                overlap);
        assertEquals(expectedCombination, actualCombination);
    }

    @Test
    public void testAddToSetAvoidingSubstrings() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Hello, world!");
        strSet.add("How are you?");
        String str = "world";
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(2, strSet.size());
        assertTrue(strSet.contains("Hello, world!"));
        assertTrue(strSet.contains("How are you?"));
    }

    @Test
    public void testPrintWithLineSeparators() {
        SimpleWriter out = new SimpleWriter1L("output.txt");
        SimpleReader in = new SimpleReader1L("output.txt");
        String str = "Hello~world~!";
        String expect = "Hello" + "\n" + "world" + "\n" + "!";
        StringReassembly.printWithLineSeparators(str, out);
        String str1 = in.nextLine();
        String str2 = in.nextLine();
        String str3 = in.nextLine();
        assertEquals(expect, str1 + "\n" + str2 + "\n" + str3);
        in.close();
        out.close();
    }

    @Test
    public void testLinesFromInput() {
        String inputStr = "abcde\nbcd\na\nb\nbc\ndef\nghij\nhi\njklm\nop\nopqrs\nst\nt\nuv\nw\nxy\nz\n";
        SimpleReader input = new SimpleReader1L(inputStr);
        Set<String> expected = new Set1L<>();
        expected.add("abcde");
        expected.add("def");
        expected.add("ghij");
        expected.add("jklm");
        expected.add("opqrs");
        expected.add("uv");
        expected.add("w");
        expected.add("xy");
        expected.add("z");
        Set<String> actual = StringReassembly.linesFromInput(input);
        System.out.println("Actual set of lines: " + actual);
        assertEquals(expected, actual);
        //never got it to work. worked on this for abt 50min.
    }

}
