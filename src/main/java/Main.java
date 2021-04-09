//a(3), b(1), c(8)
/*Программа, которая строит тепловую карту кликов по странице
Входные данные: координаты нажатия x,y, userId, timestamp
    (справочник областей экрана: <координаты области> - <название области>,
    справочник температур: <диапазон значений> - <температура (высокая, средняя, низкая)>)
Выходные данные: название области экрана, количество нажатий (температура)*/

//Output
//CSV файл

//Количество редьюсеров должно быть больше 1. Приложить скриншот успешного завершения MapReduce Job с количеством редьюсеров больше 1.

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

@Log4j
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        if (args.length < 4) {
            throw new RuntimeException("You should specify input, output folders, area and temperature dictionaries!");
        }
        Configuration conf = new Configuration();

        conf.set("mapreduce.output.textoutputformat.separator", ",");

        AreaDictionary areaDictionary = AreaDictionary.getInstance();
        areaDictionary.generateAreas();

        TemperatureDictionary temperatureDictionary = TemperatureDictionary.getInstance();
        temperatureDictionary.generateTemperatures();

        Job job = Job.getInstance(conf, "browser count");
        job.setJarByClass(Main.class);
        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setNumReduceTasks(3);
        Path outputDirectory = new Path(args[1]);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, outputDirectory);
        log.info("=====================JOB STARTED=====================");
        job.waitForCompletion(true);
        log.info("=====================JOB ENDED=====================");
        // проверяем статистику по счётчикам
        Counter counter = job.getCounters().findCounter(CounterType.MALFORMED);
        log.info("=====================COUNTERS " + counter.getName() + ": " + counter.getValue() + "=====================");
    }
}
