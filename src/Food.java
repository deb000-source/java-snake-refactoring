import java.awt.*;

public class Food {
    static final int size = 10;
    private final Color color;
    int LastPosition = FieldClass.NumField;
    private int x;
    private int y;

    public Food() {
        color = new Color(215, 59, 48);
        int RandomPosition = (int) rnd(LastPosition + 1);
        setRandomPosition(RandomPosition);
    }

    public static double rnd(final double max) {
        double a1 = Math.random() * max;
        if ((int) a1 > 72 && a1 < 1224) {
            return a1;
        } else {
            return rnd(max);
        }
    }

    // Метод, устанавливающий случайную позицию еды
    public void setRandomPosition(int randomPosition) {
        x = FieldClass.FieldPosition[randomPosition].x;
        y = FieldClass.FieldPosition[randomPosition].y;
    }

    // Метод, отрисовывающий еду
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(x + 5, y + 5, size, size);
    }

    public boolean isEaten(int snakeX, int snakeY, int snakeSize) {
        System.out.println(x + " " + y + " - Координаты яблока");
        System.out.println(snakeX + " " + snakeY + " - Координаты Змеи");
        Rectangle foodRect = new Rectangle(x, y, size, size);
        Rectangle snakeRect = new Rectangle(snakeX, snakeY, snakeSize, snakeSize);
        System.out.println(foodRect.intersects(snakeRect));
        return foodRect.intersects(snakeRect);
    }
}

