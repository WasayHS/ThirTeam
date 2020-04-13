package printFormat;

public class LevelTitle extends BorderedStrings {

    public void topBotBorder(int len){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len/2 - 1; i++) {
            sb.append("#=");
        }
        System.out.println("<|>" + sb.toString() + "<|>");
    }
}
