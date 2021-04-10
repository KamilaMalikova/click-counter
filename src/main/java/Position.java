import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;

/**
 * Input file
 * @x - x position
 * @y - y position
 * @uid - user id
 * @time - parsed timestamp
 * */
public class Position {
    private final int x;
    private final int y;
    private final int uid;
    private LocalDateTime time;

    private final String datePattern = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1})$";
    private final String digitPattern = "(\\d+)";

    /**Inits from input record*/
    public Position(String line){
        Matcher matcher = RegexMatcher.getMatcher(line, datePattern);
        this.time = LocalDateTime.parse(RegexMatcher.getSubString(matcher, line), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s"));
        matcher = RegexMatcher.getMatcher(line, digitPattern);
        this.x = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        this.y = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        this.uid = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getUid() {
        return uid;
    }
}
