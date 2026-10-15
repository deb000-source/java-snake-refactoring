import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Класс, отвечающий за поведение змейки
class Snake {
    // Размер одного кусочка змейки
    public static final int SIZE = 20;

    // Скорость перемещения змейки
    public static final int DISTANCE = SIZE;
    private static final int SCREEN_WIDTH = SnakeGame.WIDTH;
    private static final int SCREEN_HEIGHT = SnakeGame.HEIGHT;

    // Начальное направление змейки
    static int direction = Direction.UP;

    private final ArrayList<Point> snakeParts = new ArrayList<>();
    // Объявление переменной growing
    private boolean growing;


    // Конструктор класса Snake
    public Snake() {
        // Добавляем два кусочка змейки
        snakeParts.add(new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2));
        snakeParts.add(new Point(SCREEN_WIDTH / 2 - 1, SCREEN_HEIGHT / 2 - 1));
    }

    // Метод, обрабатывающий событие нажатия клавиши
    public static void keyPressed(KeyEvent e) {
        // Определяем, какая клавиша была нажата
        int key = e.getKeyCode();

        // Изменяем направление змейки в зависимости от нажатой клавиши
        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && direction != Direction.RIGHT) {
            direction = Direction.LEFT;
        }
        if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
        }
        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && direction != Direction.DOWN) {
            direction = Direction.UP;
        }
        if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && direction != Direction.UP) {
            direction = Direction.DOWN;
        }
    }

    // Метод, отвечающий за перемещение змейки
    public void move() {
        // Получаем голову змейки
        Point head = snakeParts.get(0);

        // Создаем новую точку, расположенную на DISTANCE пикселей в текущем направлении от головы
        Point newPoint = new Point(head.x, head.y);
        switch (direction) {
            case Direction.LEFT -> newPoint.x -= DISTANCE;
            case Direction.RIGHT -> newPoint.x += DISTANCE;
            case Direction.UP -> newPoint.y -= DISTANCE;
            case Direction.DOWN -> newPoint.y += DISTANCE;
        }

        // Добавляем новую точку в список кусочков змейки
        snakeParts.add(0, newPoint);

        // Если змейка не увеличилась, удаляем последний кусочек
        if (!growing) {
            snakeParts.remove(snakeParts.size() - 1);
        } else {
            // Иначе сбрасываем флаг роста
            growing = false;
        }
    }

    // Метод, отвечающий за увеличение змейки
    public void grow() {
        growing = true;
    }

    // Метод, проверяющий, не ударилась ли змейка о саму себя
    public boolean checkBitten() {
        // Получаем голову змейки
        Point head = snakeParts.get(0);

        for (int i = 1; i < snakeParts.size(); i++) {
            Point point = snakeParts.get(i);
            if (point.equals(head)) {
                return true;
            }
        }

        return false;
    }

    // Метод, отрисовывающий змейку
    public void paint(Graphics g) {
        boolean headcheck = true;


        // Отрисовываем каждый кусочек змейки
        for (Point point : snakeParts) {
            setsnakeColor(g);
            g.fillRect(point.x, point.y, SIZE, SIZE);
            if (headcheck) {
                g.setColor(Color.BLACK);
                g.fillOval(point.x+5, point.y+5, SIZE/4,SIZE/4);
                g.fillOval(point.x+11, point.y+5, SIZE/4,SIZE/4);
                headcheck = false;
            }
        }
    }

    private void setsnakeColor(Graphics g) {
        switch (FontsColors.curretSnakeColor) {
            case 1 -> g.setColor(FontsColors.snakeMain);
            case 2 -> g.setColor(FontsColors.snakeBlue);
            case 3 -> g.setColor(FontsColors.snakeSkin);
            case 4 -> g.setColor(FontsColors.snakeWhite);
        }
    }

    // Метод, возвращающий координату X головы змейки
    public int getX() {
        return snakeParts.get(0).x;
    }

    // Метод, возвращающий координату Y головы змейки
    public int getY() {
        return snakeParts.get(0).y;
    }

    public int getSize() {
        return Snake.SIZE;
    }

}
