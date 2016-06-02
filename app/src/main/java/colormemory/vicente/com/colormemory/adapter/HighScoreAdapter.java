package colormemory.vicente.com.colormemory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import colormemory.vicente.com.colormemory.R;
import colormemory.vicente.com.colormemory.model.ScoreViewModel;
import java.util.List;

/**
 * Created by Asus on 4/23/2016.
 */
public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.MyViewHolder> {

  private List<ScoreViewModel> scoreList;
  private Context context;

  public HighScoreAdapter(Context context) {
    this.context = context;
  }

  @Override
  public HighScoreAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_item, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(HighScoreAdapter.MyViewHolder holder, int position) {
    ScoreViewModel score = getScoreList().get(position);
    holder.rank.setText(String.valueOf(score.getRank() + 1));
    holder.name.setText(score.getName());
    holder.score.setText(String.valueOf(score.getScore()));
  }

  @Override
  public int getItemCount() {
    return getScoreList().size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView rank;
    TextView name;
    TextView score;

    public MyViewHolder(View itemView) {
      super(itemView);
      this.rank = (TextView) itemView.findViewById(R.id.id_high_score_rank);
      this.name = (TextView) itemView.findViewById(R.id.id_high_score_name);
      this.score = (TextView) itemView.findViewById(R.id.id_high_score);
    }
  }

  public List<ScoreViewModel> getScoreList() {
    return scoreList;
  }

  public void setScoreList(List<ScoreViewModel> scoreList) {
    this.scoreList = scoreList;
  }
}
