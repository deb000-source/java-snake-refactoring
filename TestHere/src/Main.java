import java.awt.*;

public class Main {

    public static void CreateField(Point[] FieldPosition) {

        int num = 0;

        for (int g = 0; g <= 720 / 20; g++) {

            for (int i = 0; i <= 720 / 20; i++) {

                FieldPosition[num].x = 20 * i;
                FieldPosition[num].y = 20 * g;

                System.out.println(FieldPosition[num].x + " " + FieldPosition[num].y);
                num++;
            }

        }

    }
    public static void main(String[] args) {

        int NumField = (720 / 20) * (720 / 20);
        Point[] FieldPosition = new Point[1000000];

        CreateField(FieldPosition);

    }
}