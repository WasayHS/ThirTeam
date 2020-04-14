package printFormat;

public class OptionsText extends BorderedStrings {

    /* topBotBorder(int)
     * An abstract method used by BorderedStrings
     * Creates the top and bottom border of the user input box
     *
     * @param len: Type int - the length of the border
     * @return void
     */

    public void topBotBorder(int len){
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < (len); i++) {
            sb.append("«");
        }
        System.out.println("++" + sb.toString() + "++");
    }

}
