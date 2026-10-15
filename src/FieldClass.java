import java.awt.*;

public class FieldClass {

    static final int Width = 720;
    static final int Height = 720;

    static final int NumField = ((Width / 20) * (Height / 20));
    static final Point[] FieldPosition = new Point[NumField];

    public static Point pcreate(int x, int y) {
        return new Point(x, y);
    }

    static void CreateField() {

        int num = 0;

        for (int g = 0; g < Height / 20; g++) {
            for (int i = 0; i < Width / 20; i++) {


                FieldPosition[num] = FieldClass.pcreate(20 * i, 20 * g);
                num++;


            }

        }

    }
}
