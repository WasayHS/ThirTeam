package printFormat;

/**
 * Class extends BorderedString for different borders
 * 
 * @author Bonnie's Computer
 *
 */
public class OptionsText extends BorderedStrings {

    /**
     * An abstract method used by BorderedStrings
     * Creates the top and bottom border of the user input box
     *
     * @param len: Type int - the length of the border
     */

    public void topBotBorder(int len){
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < (len); i++) {
            sb.append("«");
        }
        System.out.println("++" + sb.toString() + "++");
    }

}
