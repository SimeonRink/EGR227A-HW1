/*
 * EGR227 HW1 HTML Validator
 *
 * Written by Simeon Rinkenberger
 *
 * The HtmlValidator class provides a simple way to verify the validity of HTML files
 * with simple, stack-based tag matching. Output is indented based on tag depth, and errors
 * are printed in-line.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HtmlValidator {

    private static final String INDENTATION_MARKER = "    ";

    private Queue<HtmlTag> tags;

    // An empty constructor that only initializes tags
    public HtmlValidator() {
        tags = new LinkedList<>();
    }

    // A constructor that initializes tags with the passed list
    public HtmlValidator(Queue<HtmlTag> tags) {
        if (tags == null) throw new IllegalArgumentException("Initial tags cannot be null.");
        // Create a deep copy of the input queue
        this.tags = new LinkedList<>(tags);
    }

    // adds a tag to the end of the HtmlValidator
    public void addTag(HtmlTag tag) {
        if (tag == null) throw new IllegalArgumentException("Tag to be added cannot be null");
        tags.add(tag);
    }

    // Returns a deep copy of the tags in the HtmlValidator
    public Queue<HtmlTag> getTags() {
        return new LinkedList<>(tags);
    }

    // Removes all tags matching element
    public void removeAll(String element) {
        if (element == null) throw new IllegalArgumentException("Tag to be removed cannot be null");

        tags.removeIf(tag -> tag.getElement().equalsIgnoreCase(element));
    }

    // Prints the formatted HTML tags with indentation. Error messages are not indented
    public void validate() {
        Stack<HtmlTag> openTags = new Stack<>();
        int indentationLevel = 0;
        int tagsSize = tags.size();

        for (int i = 0; i < tagsSize; i++) {
            HtmlTag tag = tags.remove();

            if (tag.isOpenTag() && !tag.isSelfClosing()) {
                openTags.push(tag);
                printWithIndentation(tag, indentationLevel);
                indentationLevel += 1;
            } else if (!tag.isOpenTag() && !openTags.empty()) {
                if (tag.matches(openTags.peek())) {
                    openTags.pop();
                    indentationLevel -= 1;
                    printWithIndentation(tag, indentationLevel);
                } else {
                    System.out.println("ERROR unexpected tag: " + tag);
                }
            } else if (openTags.empty() && !tag.isOpenTag()) {
                System.out.println("ERROR unexpected tag: " + tag);
            } else {
                printWithIndentation(tag, indentationLevel);
            }
        }

        // print an error for each unclosed tag
        while (!openTags.isEmpty()) {
            HtmlTag tag = openTags.pop();
            System.out.println("ERROR unclosed tag: " + tag.toString());
        }
    }

    // Helper function to make printing at given indentation more convenient
    private static void printWithIndentation(HtmlTag tag, int indentationLevel) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++){
            sb.append(INDENTATION_MARKER);
        }
        sb.append(tag.toString());
        System.out.println(sb.toString());
    }
}