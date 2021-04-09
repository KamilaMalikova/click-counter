import java.util.ArrayList;
import java.util.List;

public class AreaDictionary {

    private final Area UNKNOWN = new Area(-1, -1, -1, -1, "UNKNOWN");
    private List<Area> areas;

    private static AreaDictionary areaDictionary;


    private AreaDictionary() {
        areas = new ArrayList<>();
    }
    public static AreaDictionary getInstance(){
        if (areaDictionary == null){
            areaDictionary = new AreaDictionary();
        }
        return areaDictionary;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void generateAreas(){
        areas.add(new Area(0, 500, 0, 500, "area 1"));
        areas.add(new Area(0, 500, 501, 1000, "area 2"));
        areas.add(new Area(501, 1000, 0, 500, "area 3"));
        areas.add(new Area(501, 1000, 501, 1000, "area 4"));
    }

    public Area getArea(int x, int y){
        for (Area area: areas) {
            if (area.isInArea(x, y)) return area;
        }
        return this.UNKNOWN;
    }
}
