import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Scores {

    public int playerScore = 0;
    public boolean canBeSaved = false;
    String playerName = "";
    int repeater = 1;
    String record;
    String second;
    String third;
    String fourth;

    public void keyType(char e) {

        if (Character.isAlphabetic(e)) {
            e = Character.toUpperCase(e);
        }
        if (playerName.length() < 4) {
            playerName = playerName + e;
        }
        if (playerName.length() == 4) {
            canBeSaved = true;
        }
    }

    public void setNameAndScore() throws IOException {
        File file = new File("ScoreSnakeGame.txt");
        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }
        FileReader read = new FileReader("ScoreSnakeGame.txt");
        BufferedReader br = new BufferedReader(read);
        String line = br.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        try {
            Files.write(Paths.get(file.toURI()), (playerScore + " " + playerName + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ignored) {
        }
    }

    public void draw(Graphics g, SnakeGame snakeGame) {
        if (canBeSaved) {
            try {

                if (snakeGame.NotSetted) {
                    setNameAndScore();
                    snakeGame.NotSetted = false;
                }

                if (snakeGame.NotAnalyzed) {
                    analyzeAndBroadcast();
                    snakeGame.NotAnalyzed = false;
                }

                TimeUnit.SECONDS.sleep(3);

                g.setColor(FontsColors.background);
                g.fillRect(0, 0, SnakeGame.WIDTH, SnakeGame.HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(FontsColors.aBold);
                g.drawString("Наш Рекордсмен:", SnakeGame.WIDTH / 2 - 250, SnakeGame.HEIGHT / 2 - 230);
                g.setFont(FontsColors.aItalic);
                g.setColor(new Color(215, 59, 48));
                g.drawString(record, SnakeGame.WIDTH / 2 + 10, SnakeGame.HEIGHT / 2 - 230);
                g.setColor(Color.WHITE);
                g.setFont(FontsColors.aBold);
                g.drawString("Второе место:", SnakeGame.WIDTH / 2 - 250, SnakeGame.HEIGHT / 2 - 130);
                g.setFont(FontsColors.aItalic);
                g.setColor(FontsColors.scoreColor);
                g.drawString(second, SnakeGame.WIDTH / 2 + 10, SnakeGame.HEIGHT / 2 - 130);
                g.setColor(Color.WHITE);
                g.setFont(FontsColors.aBold);
                g.drawString("Третье место:", SnakeGame.WIDTH / 2 - 250, SnakeGame.HEIGHT / 2 - 30);
                g.setFont(FontsColors.aItalic);
                g.setColor(FontsColors.scoreColor);
                g.drawString(third, SnakeGame.WIDTH / 2 + 10, SnakeGame.HEIGHT / 2 - 30);
                g.setColor(Color.WHITE);
                g.setFont(FontsColors.aBold);
                g.drawString("Четвертое место:", SnakeGame.WIDTH / 2 - 250, SnakeGame.HEIGHT / 2 + 70);
                g.setFont(FontsColors.aItalic);
                g.setColor(FontsColors.scoreColor);
                g.drawString(fourth, SnakeGame.WIDTH / 2 + 10, SnakeGame.HEIGHT / 2 + 70);
                g.setFont(FontsColors.aBold);
                g.setColor(Color.WHITE);
                g.drawString("Ваш Счет:", SnakeGame.WIDTH / 2 - 250, SnakeGame.HEIGHT / 2 + 170);
                g.setFont(FontsColors.aItalic);
                g.setColor(new Color(88, 185, 40));
                g.drawString(playerName + " - " + playerScore, SnakeGame.WIDTH / 2 + 10, SnakeGame.HEIGHT / 2 + 170);

                if (SnakeGame.notChoosed) {
                    ButtonCreator.createAndShowGUI();
                    SnakeGame.ButtonActive = true;
                    SnakeGame.notChoosed = false;
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void analyzeAndBroadcast() throws IOException {
        Scanner file = new Scanner(new File("ScoreSnakeGame.txt"));
        Score[] scores = new Score[3000];
        int k = 0;

        while (file.hasNextLine()) {
            String line = file.nextLine();

            scores[k] = new Score(line);
            k++;
        }
        if (k > 0) {
            while (repeater > 0) {
                repeater = 0;
                for (int i = 1; i < k; i++) {
                    if (scores[i].isGross(scores[i - 1])) {
                        Score timely = scores[i];
                        scores[i] = scores[i - 1];
                        scores[i - 1] = timely;
                        repeater++;
                    }
                }
            }
            record = scores[0].name + " - " + scores[0].score;
            if (scores[1] == null) {
                second = "Не определен";
            } else {
                second = scores[1].name + " - " + scores[1].score;
            }
            if (scores[2] == null) {
                third = "Не определен";
            } else {
                third = scores[2].name + " - " + scores[2].score;
            }
            if (scores[3] == null) {
                fourth = "Не определен";
            } else {
                fourth = scores[3].name + " - " + scores[3].score;
            }
        }
    }
}
