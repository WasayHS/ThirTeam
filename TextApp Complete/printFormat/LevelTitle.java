package printFormat;

/**
 * Class extends BorderedStrings 
 * Creates different borders for level titles
 * @author Bonnie's Computer
 *
 */
public class LevelTitle extends BorderedStrings {

    /**
     * An abstract method used by BorderedStrings
     * Creates the top and bottom border of new level titles
     *
     * @param len: Type int - the length of the border
     */

    public void topBotBorder(int len){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len/2 - 1; i++) {
            sb.append("#=");
        }
        System.out.println("<|>" + sb.toString() + "<|>");
    }
}
