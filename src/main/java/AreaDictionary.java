import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Singleton class
 * Used to keep area dictionary
 * */
public class AreaDictionary {

    private final Area UNKNOWN = new Area(-1, -1, -1, -1, "UNKNOWN");
    private final List<Area> areas;

    private static AreaDictionary areaDictionary = null;

    private AreaDictionary() {
        areas = new ArrayList<>();
    }

    /**Get default instance*/
    public static AreaDictionary getInstance(){
        if (areaDictionary == null){
            areaDictionary = new AreaDictionary();
            areaDictionary.generateAreas();
        }
        return areaDictionary;
    }

    /**Get instance*/
    public static AreaDictionary getInstance(String text){
        if (areaDictionary == null){
            synchronized (AreaDictionary.class){
                areaDictionary = new AreaDictionary();
                areaDictionary.generateAreas(text);
            }
        }
        return areaDictionary;
    }

    public List<Area> getAreas() {
        return areas;
    }
    /**Generated default area*/
    public void generateAreas(){
        areas.add(new Area(0, 500, 0, 500, "area 1"));
        areas.add(new Area(0, 500, 501, 1000, "area 2"));
        areas.add(new Area(501, 1000, 0, 500, "area 3"));
        areas.add(new Area(501, 1000, 501, 1000, "area 4"));
    }
    /**Generated areas from file*/
    public void generateAreas(String text){
        Arrays.stream(text.split("\n"))
                .forEach(s -> areas.add(new Area(s)));
    }

    /**Determines area position belongs to*/
    public Area getArea(int x, int y){
        for (Area area: areas) {
            if (area.isInArea(x, y)) return area;
        }
        return UNKNOWN;
    }
}
