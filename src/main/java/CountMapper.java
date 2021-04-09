import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        try {
            Position position = new Position(value.toString());
            AreaDictionary dictionary = AreaDictionary.getInstance();
            Area area = dictionary.getArea(position.getX(), position.getY());
            word.set(area.name);
            context.write(word, one);
        }catch (Exception e){
            context.getCounter(CounterType.MALFORMED).increment(1);
        }

    }
}
