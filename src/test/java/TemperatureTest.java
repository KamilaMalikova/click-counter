import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemperatureTest {
    @Test
    public void testGetTemperature(){
        List<IntWritable> values = new ArrayList<IntWritable>();

        for (int i = 0; i < 5000; i++) {
            values.add(new IntWritable(1));
        }
        String value = TemperatureDictionary.getInstance().getTemperature(values.size());
        assertEquals("MIDDLE", value);
    }

    @Test
    public void patternTest(){
        String line = "200000 - LESS";
        TemperatureDictionary dictionary = TemperatureDictionary.getInstance();
        dictionary.addTemperature(line);
        assertTrue(dictionary.getTemperatures().containsKey("LESS"));
        assertEquals(new Integer(200000), dictionary.getTemperatures().get("LESS"));
    }
    @Test
    public void generateAreasTest(){
        String line = "1000 - LOW\n" +
                "5000 - MIDDLE\n" +
                "2000000 - HIGH";
        TemperatureDictionary dictionary = TemperatureDictionary.getInstance(line);
        assertEquals(3, dictionary.getTemperatures().size());
    }
}
