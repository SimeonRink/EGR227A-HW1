// This is JUnit test program stub
// 1) You are to reproduce test1-test8 given and the expected output
// 2) You are to add your own JUnit test for testing your removeAll method

import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class HtmlValidatorTest {

    private static final String EXPECTED_TEMPLATE = "expected_output/validate_result_for_test%d.txt";
    private static final String INPUT_TEMPLATE = "input_html/test%d.html";

    private static void testAgainstFiles(int testNumber) {
        testValidatorWithFiles(String.format(EXPECTED_TEMPLATE, testNumber), String.format(INPUT_TEMPLATE, testNumber));
    }

    // this method makes sure that the validator method's output is what it is expected to be
    private static void testValidatorWithFiles(String expectedOutputFilePath, String validatorInputFilePath) {
        String rawInput = dumpFileContentsToString(validatorInputFilePath);
        String expected = dumpFileContentsToString(expectedOutputFilePath);
        HtmlValidator validator = new HtmlValidator(HtmlTag.tokenize(rawInput));

        String validatorOutput = captureValidatorOutput(validator);

        Assert.assertEquals("Validator output must match expected value", expected, validatorOutput);
    }

    // this method converts the output generated by the validator method into a string
    private static String captureValidatorOutput(HtmlValidator validator) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        validator.validate();

        System.out.flush();
        System.setOut(oldOut);
        return baos.toString().replace("\r", "");
    }

    // this method converts the contents of a file into a string
    private static String dumpFileContentsToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Could not load file: " + filePath);
            return null;
        }
    }

    // Testing the validator method with test1.html
    @Test
    public void test1() {
        testAgainstFiles(1);
    }

    // Testing the validator method with test2.html
    @Test
    public void test2() {
        testAgainstFiles(2);
    }

    // Testing the validator method with test3.html
    @Test
    public void test3() {
        testAgainstFiles(3);
    }

    // Testing the validator method with test4.html
    @Test
    public void test4() {
        testAgainstFiles(4);
    }

    // Testing the validator method with test5.html
    @Test
    public void test5() {
        testAgainstFiles(5);
    }

    // Testing the validator method with test6.html
    @Test
    public void test6() {
        testAgainstFiles(6);
    }

    // Testing the validator method with test7.html
    @Test
    public void test7() {
        testAgainstFiles(7);
    }

    // Testing the validator method with test8.html
    @Test
    public void test8() {
        testAgainstFiles(8);
    }

    // Testing the addTag method in HtmlValidator
    @Test
    public void addTagTest() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();

        tags.forEach(validator::addTag);

        Assert.assertEquals("New elements must be added", validator.getTags(), tags);
    }

    // This method tests that the addTag method throws an exception when passed a null value
    @Test(expected = IllegalArgumentException.class)
    public void addNullTagTest() {
        HtmlValidator validator = new HtmlValidator();
        validator.addTag(null);
    }

    // This method tests that the first element can be removed
    @Test
    public void removeAllTest1() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There"), new HtmlTag("Human")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        validator.removeAll("Hello");
        Queue<HtmlTag> removedQ = new LinkedList<>();
        removedQ.add(new HtmlTag("There"));
        removedQ.add(new HtmlTag("Human"));

        Assert.assertEquals("\"Hello\" should not be in the validator.", validator.getTags(), removedQ);
    }

    // This method tests that the middle element can be removed
    @Test
    public void removeAllTest2() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There"), new HtmlTag("Human")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        validator.removeAll("There");
        Queue<HtmlTag> removedQ = new LinkedList<>();
        removedQ.add(new HtmlTag("Hello"));
        removedQ.add(new HtmlTag("Human"));

        Assert.assertEquals("\"There\" should not be in the validator.", validator.getTags(), removedQ);
    }

    // This method tests that the last element can be removed
    @Test
    public void removeAllTest3() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There"), new HtmlTag("Human")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        validator.removeAll("Human");
        Queue<HtmlTag> removedQ = new LinkedList<>();
        removedQ.add(new HtmlTag("Hello"));
        removedQ.add(new HtmlTag("There"));

        Assert.assertEquals("\"Human\" should not be in the validator.", validator.getTags(), removedQ);
    }

    // This method tests that multiple elements can be removed
    @Test
    public void removeAllTest4() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        validator.addTag(new HtmlTag("Remove"));
        validator.addTag(new HtmlTag("Remove"));
        validator.removeAll("Remove");

        Assert.assertEquals("\"Remove\" should not be in the validator.", validator.getTags(), tags);
    }

    // This method tests that no elements will be removed if there are no elements that match
    @Test
    public void removeAllTest5() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        validator.removeAll("Remove");

        Assert.assertEquals("No tags should have been removed", validator.getTags(), tags);
    }

    // this method tests that the removeAll method will throw an exception when passed a null value
    @Test(expected = IllegalArgumentException.class)
    public void removeAllNullTest() {
        HtmlValidator validator = new HtmlValidator();
        validator.removeAll(null);
    }
}
