package printFormat;

/**
 * Abstract class for borders as strings for the console
 * @author Bonnie's Computer
 *
 */
public abstract class BorderedStrings {

    /**
     * An abstract method from it's subclasses
     * Creates the top and bottom border of the text box
     *
     * @param len: Type int - length of the box
     */

    public abstract void topBotBorder(int len);

    /**
     * Fills the strings with empty spaces until the
     * string's length is equal to the border's length
     *
     * @param len: Type int - length of the box
     */

    public String fill(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Prints strings with a box surrounding the text
     * Every string parameter is printed as a new line
     *
     * @param strings: Type String... - a list of strings
     */

    public void printBox(String... strings) {
        int len = Integer.MIN_VALUE;

        for (String str : strings) {
            len = Math.max(str.length(), len);
        }
        topBotBorder(len+2);

        for (String str : strings) {
            StringBuilder sb = new StringBuilder(str);
            System.out.printf("| %s |%n", sb.append(fill(len-str.length()+1)).toString());
        }
        topBotBorder(len+2);
    }
}
