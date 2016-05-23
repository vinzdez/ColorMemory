package colormemory.vicente.com.colormemory.model;

/**
 * Created by Asus on 4/23/2016.
 */
public class ScoreViewModel {
    private int rank;
    private String name;
    private int score;

    public ScoreViewModel(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }


    public int getScore() {
        return score;
    }

}
