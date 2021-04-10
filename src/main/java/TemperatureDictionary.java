import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * Singleton class
 * Used to keep temperature dictionary
 * */
public class TemperatureDictionary {
    private final Map<String, Integer> temperatures;
    private static TemperatureDictionary temperatureDictionary = null;
    private String UNKNOWN = "UNKNOWN";
    private final String stringPattern = "[^-]*$";
    private final String digitPattern = "^(\\d+)";
    private TemperatureDictionary(){
        temperatures = new HashMap<>();
    }

    /**Get default instance*/
    public static TemperatureDictionary getInstance(){
        if (temperatureDictionary == null){
            synchronized (TemperatureDictionary.class){
                temperatureDictionary = new TemperatureDictionary();
                temperatureDictionary.generateTemperatures();
            }
        }
        return temperatureDictionary;
    }
    /**Get instance*/
    public static TemperatureDictionary getInstance(String path){
        if (temperatureDictionary == null){
            synchronized (TemperatureDictionary.class){
                temperatureDictionary = new TemperatureDictionary();
                temperatureDictionary.generateTemperatures(path);
            }
        }
        return temperatureDictionary;
    }

    public Map<String, Integer> getTemperatures() {
        return temperatures;
    }

    /**Adds new temperature from string*/
    public void addTemperature(String line){
        Matcher matcher = RegexMatcher.getMatcher(line, digitPattern);
        int value = Integer.parseInt(RegexMatcher.getSubString(matcher, line));
        matcher = RegexMatcher.getMatcher(line, stringPattern);
        String key = RegexMatcher.getSubString(matcher, line);
        if (key.startsWith(" ")) key = key.substring(1);
        temperatures.put(key, value);
    }

    /**Generated default temperatures*/
    public void generateTemperatures(){
        temperatures.put("LOW", 1000);
        temperatures.put("MIDDLE", 5000);
        temperatures.put("HIGH", Integer.MAX_VALUE);
    }

    /**Generated default temperatures from file*/
    public void generateTemperatures(String text){
        Arrays.stream(text.split("\n"))
                .forEach(this::addTemperature);
    }

    /**Determines temperature from number of clicks*/
    public String getTemperature(int clicks){
        Optional<Map.Entry<String, Integer>> value = temperatures.entrySet().stream()
                .filter(entry -> entry.getValue() >= clicks)
                .min(Map.Entry.comparingByValue());
        if (!value.isPresent()){
            return UNKNOWN;
        }else return value.get().getKey();
    }

}
