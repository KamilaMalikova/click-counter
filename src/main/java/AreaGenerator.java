import java.awt.*;
import java.io.File;
//    (справочник областей экрана: <координаты области> - <название области>,
public class AreaGenerator {
    private static int dimensions = 4;
    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("Require a path name");
        }
        File file = new File(args[1]);
        if (args.length == 3){
            dimensions = Integer.parseInt(args[2]);
        }
        Dimension size
                = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 0;
        int y = 0;
    }
}
