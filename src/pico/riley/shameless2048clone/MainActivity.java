package pico.riley.shameless2048clone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	static final String logTag = "ActivitySwipeDetector";
	static final int MIN_DISTANCE = 100;
	static final String preference = "SHAMELESS_2048_CLONE";
	static final String bestScoreKey = "BEST_SCORE";
	static final String scoreKey = "2048_SCORE";
	static final String boardKey = "2048_BOARD";
	static final String interruptedKey = "2048_WAS_INTERRUPTED";
	static final String firstTimeKey = "2048_FIRST_TIME";
    private RelativeLayout lowestLayout;
    private RelativeLayout gameOverView;
    private GridView gameView;
    private Game game;
    private TileAdapter gameAdapter;
    private TextView scoreText;
    private TextView bestText;
    private TextView newGame;
    private TextView tryAgain;
    private TextView howToPlay;
    private TextView gameOverText;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferenceEditor;
    private int score = 0;
    private int bestScore = 0;
    private boolean gameLost = false;
    private Context mContext;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        game = new Game();
        preferences = getApplicationContext().getSharedPreferences(preference, 0); 
        preferenceEditor = preferences.edit();
        //Grab the best score before we get started.
        bestScore = preferences.getInt(bestScoreKey, 0);

        mContext = this;
        gameAdapter = new TileAdapter(this, R.layout.tilelayout, game);
        lowestLayout = (RelativeLayout)this.findViewById(R.id.lowestLayout);
        gameOverView = (RelativeLayout)this.findViewById(R.id.endGame);
        scoreText = (TextView)this.findViewById(R.id.scoreText);
        bestText = (TextView)this.findViewById(R.id.bestScoreText);
        newGame = (TextView)this.findViewById(R.id.newGame);
        tryAgain = (TextView)this.findViewById(R.id.tryAgain);
        howToPlay = (TextView)this.findViewById(R.id.howToPlay);
        gameOverText = (TextView)this.findViewById(R.id.gameOverText);
        gameView = (GridView)this.findViewById(R.id.grid_view);

    	scoreText.setText(Integer.toString(score));
		bestText.setText(Integer.toString(bestScore));
        ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(gameAdapter, game, this);
        lowestLayout.setOnTouchListener(activitySwipeDetector);
        gameView.setOnTouchListener(activitySwipeDetector);
        gameView.setAdapter(gameAdapter);

        //If it's your first time, show the help message
        if (preferences.getBoolean(firstTimeKey, true))
        {
        	howToPlay.setVisibility(View.VISIBLE);
        	preferenceEditor.putBoolean(firstTimeKey, false).commit();
        }
        
        //If your game ended in a recoverable state, go and load it back up
        if (!preferences.getBoolean(interruptedKey, true))
        {
	        game.setScore(preferences.getInt(scoreKey, 0));
	        game.parseBoard(preferences.getString(boardKey, ""));
    	    gameAdapter.notifyDataSetChanged();
    	    updateScore();
        }

        newGame.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		((MainActivity)mContext).newGame();
        	}
        });

        tryAgain.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		((MainActivity)mContext).newGame();
        	}
        });
    }
    
    public void newGame()
    {
		game.newGame();
		gameLost = false;
	    gameAdapter.notifyDataSetChanged();
    	gameOverView.setVisibility(View.GONE);
    	gameOverView.getBackground().setAlpha(255);
    	preferenceEditor.putInt(bestScoreKey, bestScore);
        preferenceEditor.putInt(scoreKey, score);
    	preferenceEditor.putString(boardKey, game.toString());
    	preferenceEditor.commit();
    }
    
    @Override
    protected void onStop() 
    {
    	super.onStop();
    	preferenceEditor.putInt(bestScoreKey, bestScore);
    	preferenceEditor.putBoolean(interruptedKey, gameLost);
        preferenceEditor.putInt(scoreKey, score);
    	preferenceEditor.putString(boardKey, game.toString());
    	preferenceEditor.commit();
    }
    
    public void loseGame()
    {
    	gameLost = true;
    	gameOverView.setVisibility(View.VISIBLE);
    	gameOverText.setText(R.string.gameOver);
    	gameOverView.getBackground().setAlpha(128);
    }
    
    public void winGame()
    {
    	gameLost = true;
    	gameOverView.setVisibility(View.VISIBLE);
    	gameOverText.setText(R.string.youWin);
    	gameOverView.getBackground().setAlpha(128);
    }
    
    public void updateScore()
    {
    	score = game.getScore();
    	scoreText.setText(Integer.toString(score));
    	if (score > bestScore)
    	{
    		bestScore = score;
    		bestText.setText(Integer.toString(bestScore));
    	}
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	  switch (item.getItemId()) {
    	    case R.id.resetBestButton:
    	      resetBestScore();
    	      return true;
    	    case R.id.developer_button:
    		  Toast.makeText(getBaseContext(), "Hello There! :D", Toast.LENGTH_LONG).show();
    	      return true;
    	    case R.id.showHelpButton:
        	  howToPlay.setVisibility(View.VISIBLE);
        	  preferenceEditor.putBoolean(firstTimeKey, true).commit();
        	  return true;
    	    default:
    	      return super.onOptionsItemSelected(item);
    	  }
    	}

	private void resetBestScore() {
		bestScore = score;
		bestText.setText(Integer.toString(bestScore));
	    Toast.makeText(getBaseContext(), "Best Score Reset!", Toast.LENGTH_LONG).show();
	}
}
