package colourmemory.vicente.com.colourmemory.service;

import android.content.Context;

import java.util.List;

import colourmemory.vicente.com.colourmemory.dao.DaoFactory;
import colourmemory.vicente.com.colourmemory.model.ScoreViewModel;
import colourmemory.vicente.com.colourmemory.model.repo.Score;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Asus on 4/23/2016.
 */
public class ScoreService {

    private Context context;

    public ScoreService(Context context){
        this.context = context;
    }

    public void saveScore(ScoreViewModel currentScore){
        Realm realm = DaoFactory.getInstance(context);
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.beginTransaction();

        // Add a person
        Score score = realm.createObject(Score.class);
        score.setName(currentScore.getName());
        score.setScore(currentScore.getScore());
        // When the transaction is committed, all changes a synced to disk.
        realm.commitTransaction();
    }

    public List<Score> getAllScore(){
        Realm realm = DaoFactory.getInstance(context);
        // Or alternatively do the same all at once (the "Fluent interface"):
        RealmResults<Score> sortedScore = realm.where(Score.class).findAllSorted("score",Sort.DESCENDING);
        return sortedScore;
    }

}
