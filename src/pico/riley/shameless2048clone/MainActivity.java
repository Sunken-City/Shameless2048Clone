package pico.riley.shameless2048clone;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

	static final String logTag = "ActivitySwipeDetector";
	static final int MIN_DISTANCE = 100;
    private RelativeLayout lowestLayout;
    private GridView gameView;
    private Game game;
    private TileAdapter gameAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        game = new Game();

        gameAdapter = new TileAdapter(this, R.layout.tilelayout, game);
        ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(gameAdapter, game);
        lowestLayout = (RelativeLayout)this.findViewById(R.id.lowestLayout);
        lowestLayout.setOnTouchListener(activitySwipeDetector);
        gameView = (GridView)this.findViewById(R.id.grid_view);
        gameView.setOnTouchListener(activitySwipeDetector);
        gameView.setAdapter(gameAdapter);
    }
}
