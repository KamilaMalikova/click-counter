import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapReduceTest {
    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
    private ReduceDriver<Text, IntWritable, Text, Text> reduceDriver;
    private MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, Text> mapReduceDriver;

    private final String testPosition = "120 160 1004 2021-04-01 00:02:00.00" ;
    private Position position;
    private Area area;
    @Before
    public void setUp() {
        CountMapper mapper = new CountMapper();
        CountReducer reducer = new CountReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        position = new Position(testPosition);
        AreaDictionary areaDictionary = AreaDictionary.getInstance();
        TemperatureDictionary temperatureDictionary = TemperatureDictionary.getInstance();

        area = AreaDictionary.getInstance().getArea(position.getX(), position.getY());
    }

    @Test
    public void testMapper() throws IOException {
        mapDriver
                .withInput(new LongWritable(), new Text(testPosition))
                .withOutput(new Text(area.getName()), new IntWritable(1))
                .runTest();
    }

    @Test
    public void testReducer() throws IOException {
        List<IntWritable> values = new ArrayList<IntWritable>();
        values.add(new IntWritable(1));
        values.add(new IntWritable(1));

        String value = TemperatureDictionary.getInstance().getTemperature(values.size());
        reduceDriver
                .withInput(new Text(testPosition), values)
                .withOutput(new Text(testPosition), new Text(value))
                .runTest();
    }

    @Test
    public void testBigReducer() throws IOException {
        List<IntWritable> values = new ArrayList<IntWritable>();

        for (int i = 0; i < 5000; i++) {
            values.add(new IntWritable(1));
        }

        String value = TemperatureDictionary.getInstance().getTemperature(values.size());
        reduceDriver
                .withInput(new Text(testPosition), values)
                .withOutput(new Text(testPosition), new Text(value))
                .runTest();
    }

    @Test
    public void testMapReduce() throws IOException {
        mapReduceDriver
                .withInput(new LongWritable(), new Text(testPosition))
                .withInput(new LongWritable(), new Text(testPosition))
                .withOutput(new Text(area.getName()), new Text("LOW"))
                .runTest();
    }
}
