import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to parse input data
 * */
public class RegexMatcher {
    public static String getSubString(Matcher matcher, String line){
        if (matcher.find()){
            return line.substring(matcher.start(), matcher.end());
        }
        else throw new IllegalStateException();
    }

    public static Matcher getMatcher(String line, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher;
    }
}
