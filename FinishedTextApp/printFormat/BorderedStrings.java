package printFormat;

public abstract class BorderedStrings {

    public abstract void topBotBorder(int len);

    public String fill(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

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
