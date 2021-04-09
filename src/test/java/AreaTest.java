import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AreaTest {
    @Test
    public void areaIsInTest(){
        int x = 20;
        int y = 35;
        Area area = new Area(0, 100, 0, 100, "zero area");
        assertTrue(area.isInArea(x, y));
    }

    @Test
    public void areaIsNotInTest(){
        int x = 150;
        int y = 35;
        Area area = new Area(0, 100, 0, 100, "zero area");
        assertFalse(area.isInArea(x, y));
    }

}
