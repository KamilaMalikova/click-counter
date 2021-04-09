import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TemperatureDictionary {
    private Map<String, Integer> temperatures;
    private static TemperatureDictionary temperatureDictionary;

    private TemperatureDictionary(){
        temperatures = new HashMap<>();
    }

    public static TemperatureDictionary getInstance(){
        if (temperatureDictionary == null){
            temperatureDictionary = new TemperatureDictionary();
        }
        return temperatureDictionary;
    }

    public Map<String, Integer> getTemperatures() {
        return temperatures;
    }

    public void generateTemperatures(){
        temperatures.put("LOW", 50);
        temperatures.put("MIDDLE", 200);
        temperatures.put("HIGH", Integer.MAX_VALUE);
    }

    public String getTemperature(int clicks){
        Optional<Map.Entry<String, Integer>> value = temperatures.entrySet().stream()
                .filter(entry -> entry.getValue() >= clicks)
                .min(Map.Entry.comparingByValue());
        return value.get().getKey();
    }
}
