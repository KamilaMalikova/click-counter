import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AreaTest {
    @Test
    public void areaIsInTest(){
        int x = 501;
        int y = 500;
        Area area = new Area(501, 1000, 0, 500, "area 3");
        assertTrue(area.isInArea(x, y));
    }

    @Test
    public void areaIsNotInTest(){
        int x = 150;
        int y = 35;
        Area area = new Area(0, 100, 0, 100, "zero area");
        assertFalse(area.isInArea(x, y));
    }

    @Test
    public void patternTest(){
        String line = "0:499 - 0:499 - area 1";
        Area area = new Area(line);
        assertEquals(area.getX0(),0);
        assertEquals(area.getX1(),499);
        assertEquals(area.getY0(),0);
        assertEquals(area.getY1(),499);
        assertEquals(area.getX0(),0);
        assertEquals("area 1", area.getName());
    }

    @Test
    public void getAreaTest(){
        int x = 501;
        int y = 499;
        AreaDictionary areaDictionary = AreaDictionary.getInstance();
        Area area = areaDictionary.getArea(x, y);
        assertEquals("area 3", area.getName());
    }

    @Test
    public void generateAreasTest(){
        String line = "0:499 - 0:499 - area 1\n" +
                "0:499 - 500:1000 - area 2\n" +
                "500:1000 - 0:499 - area 3\n" +
                "500:1000 - 500:1000 - area 4";
        AreaDictionary dictionary = AreaDictionary.getInstance(line);
        assertEquals(4, dictionary.getAreas().size());
    }

}
