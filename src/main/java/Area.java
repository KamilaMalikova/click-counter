import java.util.List;

public class Area {
    int x0;
    int x1;
    int y0;
    int y1;
    String name;

    public Area(int x0, int x1, int y0, int y1, String name) {
        this.x0 = Math.min(x0, x1);
        this.x1 = Math.max(x0, x1);
        this.y0 = Math.min(y0, y1);
        this.y1 = Math.max(y0, y1);
        this.name = name;
    }

    public boolean isInArea(int X, int Y){
        return isX(X) && isY(Y);
    }
    private boolean isX(int X){
        return X > x0 && X < x1;
    }
    private boolean isY(int Y){
        return Y > y0 && Y < y1;
    }
}
