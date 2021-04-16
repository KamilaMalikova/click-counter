import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper: gets position from text value.
 * Determines area position belongs to {@link AreaDictionary}.
 * Returns area name and one
 * If area is not found returns UNKNOWN area
 * */
public class CountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) {
        try {
            Position position = new Position(value.toString());
            AreaDictionary areaDictionary = AreaDictionary.getInstance();
            Area area = areaDictionary.getArea(position.getX(), position.getY());
            word.set(area.getName());
            context.write(word, one);
        }catch (Exception e){
            context.getCounter(CounterType.MALFORMED).increment(1);
        }

    }
}
