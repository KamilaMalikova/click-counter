import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
/**
 * Generates input files based on screen Dimension using {@link Toolkit}
 * All records (including malformed) are generated based on {@link Random} class and limited by dimensions
 * */
public class InputGenerator {
    private static final Logger log = Logger.getLogger(InputGenerator.class);

    public static void main(String[] args) throws IOException {
        String format = "%d %d %d %s\n";
        Dimension size
                = Toolkit.getDefaultToolkit().getScreenSize();
        int i = 0;
        Random randomX = new Random();
        Random randomY = new Random();
        Random randomUID = new Random();
        Random randomMALFORMED = new Random();
        LocalDateTime prevTimeStamp = LocalDateTime.of(2021, 4, 1, 0, 0, 0);
        while(i < 100_000){
            int x = randomX.nextInt(size.width);
            int y = randomY.nextInt(size.height);
            int uid = randomUID.nextInt(10)+1000;
            LocalDateTime timeStamp = prevTimeStamp.plusMinutes(2);
            String line = String.format(format, x, y, uid, Timestamp.valueOf(timeStamp));
            if (randomMALFORMED.nextInt()%925 == 45){
                line = String.join(format, Integer.toString(y), Timestamp.valueOf(timeStamp).toString(), Integer.toString(randomMALFORMED.nextInt()));
            }
            log.info(line);
            i++;
        }
    }
}
