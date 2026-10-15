public class Score {
    public int score;
    public String name;

    public Score(String line) {
        String[] temp = line.split(" ");
        this.score = Integer.parseInt(temp[0]);
        this.name = temp[1];
    }

    public boolean isGross(Score other) {
        return this.score > other.score;
    }

    @Override
    public String toString() {
        return score + " " + name;
    }
}
