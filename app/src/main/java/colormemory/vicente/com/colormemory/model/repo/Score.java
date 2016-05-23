package colormemory.vicente.com.colormemory.model.repo;

import io.realm.RealmObject;

/**
 * Created by Asus on 4/23/2016.
 */
public class Score extends RealmObject {

    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
