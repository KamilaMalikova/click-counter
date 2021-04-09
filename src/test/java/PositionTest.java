import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
public class PositionTest {
    @Test
    public void testDate(){
        String line = "959 306 1003 2021-04-01 00:02:00.0";
        Position position = new Position(line);
        assertEquals(position.getTime(), LocalDateTime.parse("2021-04-01 00:02:00.0", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s")));
        assertEquals(position.getX(), 959);
        assertEquals(position.getY(), 306);
        assertEquals(position.getUid(), 1003);
    }
}
