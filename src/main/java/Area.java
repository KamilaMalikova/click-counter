import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Area {
    private final int x0;
    private final int x1;
    private final int y0;
    private final int y1;
    private final String name;

    private final String stringPattern = "[^-]*$";
    private final String digitPattern = "(\\d+)";

    public Area(String line){
        Matcher matcher = RegexMatcher.getMatcher(line, digitPattern);
        int a1 = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        int a2 = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        int b1 = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        int b2 = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        matcher = RegexMatcher.getMatcher(line, stringPattern);
        String text = RegexMatcher.getSubString(matcher, line);
        if (text.startsWith(" ")) text = text.substring(1);
        this.x0 = Math.min(a1, a2);
        this.x1 = Math.max(a1, a2);
        this.y0 = Math.min(b1, b2);
        this.y1 = Math.max(b1, b2);
        this.name = text;
    }

    public Area(int x0, int x1, int y0, int y1, String name) {
        this.x0 = Math.min(x0, x1);
        this.x1 = Math.max(x0, x1);
        this.y0 = Math.min(y0, y1);
        this.y1 = Math.max(y0, y1);
        this.name = name;
    }

    public boolean isInArea(int X, int Y){
        return isX(X) && isY(Y);
    }

    private boolean isX(int X){
        return isIn(x0, x1, X);
    }

    private boolean isY(int Y){
        return isIn(y0, y1, Y);
    }

    private boolean isIn(int min, int max, int c){
        if (c > min && c < max) return true;
        return c == min || c == max;
    }

    public String getName() {
        return name;
    }

    public int getX0() {
        return x0;
    }

    public int getX1() {
        return x1;
    }

    public int getY0() {
        return y0;
    }

    public int getY1() {
        return y1;
    }
}
