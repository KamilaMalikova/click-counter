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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This service creates heat map of clicks
 * Requires:
 * @input - path to input files directory
 * @output - path to output files directory
 * @area - path to area dictionary
 * @temperatures - path to temperatures dictionary
 *
 * Usage
 * hadoop jar /click-counter/job/click-counter-1.0-SNAPSHOT.jar Main /input /output @area @temperature
 * */
@Log4j
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length < 2) {
            throw new RuntimeException("You should specify input, output folders, area and temperature dictionaries!");
        }
        Configuration conf = new Configuration();

        conf.set("mapreduce.output.textoutputformat.separator", ",");

        if (args.length > 2){
            AreaDictionary.getInstance(readFromFile(args[2]));
        }else AreaDictionary.getInstance();

        if (args.length > 3){
            TemperatureDictionary.getInstance(readFromFile(args[3]));
        }else TemperatureDictionary.getInstance();

        Job job = Job.getInstance(conf, "click counter");
        job.setJarByClass(Main.class);
        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);
        job.setNumReduceTasks(4);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);
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

    private static String readFromFile(String path){
        String content = "";
        try
        {
            content = new String (Files.readAllBytes( Paths.get(path)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}
