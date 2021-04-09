import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Position {
    private final int x;
    private final int y;
    private final int uid;
    private LocalDateTime time;

    private final String datePattern = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1})$";
    private final String digitPattern = "(\\d+)";

    // 959 306 1003 2021-04-01 00:02:00.0
    public Position(String line){
        Matcher matcher = getMatcher(line, datePattern);
        this.time = LocalDateTime.parse(getSubString(matcher, line), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s"));
        matcher = getMatcher(line, digitPattern);
        this.x = Integer.parseInt(getSubString(matcher, line));
        this.y = Integer.parseInt(getSubString(matcher, line));
        this.uid = Integer.parseInt(getSubString(matcher, line));
    }

    private String getSubString(Matcher matcher,String line){
        if (matcher.find()){
            return line.substring(matcher.start(), matcher.end());
        }
        else throw new IllegalStateException();
    }

    private Matcher getMatcher(String line, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher;
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
