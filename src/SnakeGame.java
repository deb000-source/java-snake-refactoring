import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;


public class SnakeGame extends JFrame implements ActionListener {


    // Размеры игрового поля
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    // Объект класса Snake, отвечающий за поведение змейки
    public Scores scores = new Scores();
    boolean NotSetted = true;
    boolean NotAnalyzed = true;

    // Статики для проверки некоторых условий
    static boolean notChoosed = true;
    static boolean restart = false;
    static boolean Choosing = false;
    static boolean ButtonActive = false;


    // Переменная для хранения текущего состояния игры
    private boolean inGame = true;
    // Объект класса Food, отвечающий за поведение "еды" в игре
    public Food food;
    public Snake snake;


    public SnakeGame() {
        // Устанавливаем заголовок окна

        super("Змейка");

        SnakePanel snakePanel = new SnakePanel(true, this);
        add(snakePanel);
        addKeyListener(snakePanel);

        FieldClass.CreateField();

        // Создаем объекты классов Snake и Food
        snake = new Snake();
        food = new Food();

        // Устанавливаем размеры игрового поля
        setSize(WIDTH, HEIGHT);

        // Отключаем возможность изменения размеров окна
        setResizable(false);

        // Устанавливаем окно по центру экрана
        setLocationRelativeTo(null);

        // Устанавливаем действие при закрытии окна (завершение программы)
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Создаем таймер и устанавливаем скорость обновления игрового поля
        // Таймер для управления скоростью игры
        Timer timer = new Timer(380, this);
        timer.start();
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createMenuColors());
        setJMenuBar(menuBar);

        // Делаем окно видимым
        setVisible(true);
        addActionListener(this);
        new Scores();
        ImageIcon icon = new ImageIcon("Images/snake.png");
        setIconImage(icon.getImage());

    }

    public static void main(String[] args) {
        // Создаем объект класса SnakeGame
        new SnakeGame();
    }

    // Метод, вызываемый таймером
    public void actionPerformed(ActionEvent e) {

        if (!Choosing) {

            if (restart) {
                inGame = true;
                restart = false;

                scores = new Scores();
                food = new Food();
                snake = new Snake();
                Snake.direction = Direction.UP;

                scores.playerScore = 0;
                scores.playerName = "";

                NotSetted = true;
                NotAnalyzed = true;
                notChoosed = true;
            }
            // Если игра активна, обновляем состояние игры
            if (inGame) {
                snake.move();
                checkCollisions();
            }
            // Перерисовываем игровое поле
            repaint();
        }

    }

    // Метод, проверяющий столкновения змейки с препятствиями
    private void checkCollisions() {
        // Проверяем, не ударилась ли змейка об стену
        if (snake.getX() < 0 || snake.getX() > WIDTH - Snake.SIZE || snake.getY() < 0 || snake.getY() > HEIGHT - Snake.SIZE) {
            inGame = false;
        }

        // Проверяем, не ударилась ли змейка о саму себя
        if (snake.checkBitten()) {
            inGame = false;
        }

        // Проверяем, не съела ли змейка "еду"
        if (food.isEaten(snake.getX(), snake.getY(), snake.getSize())) {
            // Если "еда" съедена, то создаем новую "еду" и увеличиваем длину змейки
            food = new Food();
            scores.playerScore = scores.playerScore + 7;
            snake.grow();
        }
    }

    private JMenu createMenuColors() {
        JMenu menuColors = new JMenu("Цвет Змейки");
        JMenuItem green = new JMenuItem("Зеленая / Оригинал");
        JMenuItem blue = new JMenuItem("Синяя");
        JMenuItem skin = new JMenuItem("Бежевый");
        JMenuItem white = new JMenuItem("Белая");
        menuColors.add(green);
        menuColors.addSeparator();
        menuColors.add(blue);
        menuColors.addSeparator();
        menuColors.add(skin);
        menuColors.addSeparator();
        menuColors.add(white);
        green.addActionListener(e -> FontsColors.curretSnakeColor = 1);
        blue.addActionListener(e -> FontsColors.curretSnakeColor = 2);
        skin.addActionListener(e -> FontsColors.curretSnakeColor = 3);
        white.addActionListener(e -> FontsColors.curretSnakeColor = 4);
        return menuColors;
    }

    public static void requestRestart() {
        restart = true;
    }

    class SnakePanel extends JPanel implements KeyListener {
        SnakeGame snakeGame;

        public SnakePanel(boolean isDoubleBuffered, SnakeGame snakeGame) {
            super(isDoubleBuffered);
            this.snakeGame = snakeGame;
            requestFocus();
        }

        int repAgain = 0;

        @Override

        public void paint(Graphics g) {


            if (ButtonActive) {
                if (scores.canBeSaved) {
                    scores.draw(g, this.snakeGame);
                }

            } else {

                if (!inGame) {

                    g.setColor(FontsColors.background);
                    g.fillRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);

                    // Отрисовываем змейку
                    snake.paint(g);

                    // Отрисовываем "еду"
                    food.paint(g);


                    g.setColor(Color.WHITE);
                    g.setFont(FontsColors.aBold);
                    g.drawString("Game Over", SnakeGame.WIDTH / 2 - 50, SnakeGame.HEIGHT / 2 - 30);
                    g.drawString("Ваш счет: ", SnakeGame.WIDTH / 2 - 70, SnakeGame.HEIGHT / 2);

                    if (scores.playerScore < 5) {
                        g.setColor(Color.RED);
                    } else if (scores.playerScore < 9) {
                        g.setColor(Color.YELLOW);
                    } else {
                        g.setColor(Color.GREEN);
                    }
                    g.drawString(String.valueOf(scores.playerScore), SnakeGame.WIDTH / 2 + 70, SnakeGame.HEIGHT / 2);
                    g.setColor(Color.WHITE);
                    g.drawString("Введите ваше имя:", SnakeGame.WIDTH / 2 - 160, SnakeGame.HEIGHT / 2 + 30);
                    g.setColor(FontsColors.scoreColor);
                    g.setFont(new Font("Arial", Font.ITALIC, 25));
                    g.drawString(scores.playerName, SnakeGame.WIDTH / 2 + 60, SnakeGame.HEIGHT / 2 + 30);


                    if (scores.canBeSaved && repAgain == 1) {
                        g.drawString(scores.playerName, SnakeGame.WIDTH / 2 + 60, SnakeGame.HEIGHT / 2 + 30);
                        scores.draw(g, this.snakeGame);
                    } else {
                        if (scores.canBeSaved) {
                            repaint();
                            repAgain = 1;
                        }
                    }

                } else {

                    // Заливаем фон черным цветом
                    g.setColor(FontsColors.background);
                    g.fillRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);

                    // Отрисовываем змейку
                    snake.paint(g);

                    // Отрисовываем "еду"
                    food.paint(g);

                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (!inGame) {
                scores.keyType(e.getKeyChar());
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            Snake.keyPressed(e);

            if (AdmDebugger.debuggerEnabled && inGame && e.getKeyCode() == 'P') {
                scores.playerScore += 20;
            }
            if (AdmDebugger.debuggerEnabled && inGame && e.getKeyCode() == 'O') {
                scores.playerScore -= 5;
            }
            if (AdmDebugger.debuggerEnabled && inGame && e.getKeyCode() == 'I') {
                snake.grow();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}

