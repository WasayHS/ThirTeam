package printFormat;

public class OptionsText extends BorderedStrings {

    public void topBotBorder(int len){
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < (len); i++) {
            sb.append("«");
        }
        System.out.println("++" + sb.toString() + "++");
    }

}
