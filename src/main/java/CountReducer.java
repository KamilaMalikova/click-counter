import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
/**
 * Reducer: gets area name and number of ones in {@link CountMapper}.
 * Calculates temperature {@link TemperatureDictionary}.
 * Returns area and temperature
 * */
public class CountReducer extends Reducer<Text, IntWritable, Text, Text> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int clicks = 0;
        while (values.iterator().hasNext()){
            clicks+=values.iterator().next().get();
        }
        String value = TemperatureDictionary.getInstance().getTemperature(clicks);
        context.write(key, new Text(value));
    }
}
