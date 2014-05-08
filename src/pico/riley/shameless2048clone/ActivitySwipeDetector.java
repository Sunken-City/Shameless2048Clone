package pico.riley.shameless2048clone;

import pico.riley.shameless2048clone.Game.Direction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

//Using code from http://stackoverflow.com/questions/937313/android-basic-gesture-detection
//I submitted a bug fix for the solution and got it approved, so that's why they look identical now. 
public class ActivitySwipeDetector implements View.OnTouchListener {

static final String logTag = "ActivitySwipeDetector";
private TileAdapter gameAdapter;
static final int MIN_DISTANCE = 100;
private float downX, downY, upX, upY;
private Game game;
private MainActivity activity;

public ActivitySwipeDetector(TileAdapter gameGridAdapter, Game game, MainActivity activity){
    this.gameAdapter = gameGridAdapter;
    this.game = game;
    this.activity = activity;
}

public void move(Direction dir)
{
    if(!game.makeMove(dir))
    {
    	activity.loseGame();
    }
    if(game.hasWon())
    {
    	activity.winGame();
    }
    gameAdapter.notifyDataSetChanged();
}

public void onLeftSwipe(){
    Log.i(logTag, "Left Swipe!");
    move(Direction.LEFT);
}

public void onRightSwipe(){
    Log.i(logTag, "Right Swipe!");
    move(Direction.RIGHT);
}

public void onDownSwipe(){
    Log.i(logTag, "Down Swipe!");
    move(Direction.DOWN);
}

public void onUpSwipe(){
    Log.i(logTag, "Up Swipe!");
    move(Direction.UP);
}

public boolean onTouch(View v, MotionEvent event) {
    switch(event.getAction()){
        case MotionEvent.ACTION_DOWN: {
            downX = event.getX();
            downY = event.getY();
            return true;
        }
        case MotionEvent.ACTION_UP: {
            upX = event.getX();
            upY = event.getY();

            float deltaX = downX - upX;
            float deltaY = downY - upY;

            // swipe horizontal?
            if(Math.abs(deltaX) > Math.abs(deltaY))
            {
            	if(Math.abs(deltaX) > MIN_DISTANCE){
                    // left or right
                    if(deltaX < 0) { this.onRightSwipe(); return true; }
                    if(deltaX > 0) { this.onLeftSwipe(); return true; }
                }
                else {
                        Log.i(logTag, "Horizontal Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
                        return false; // We don't consume the event
                }
            }
            // swipe vertical?
            else 
            {
	            if(Math.abs(deltaY) > MIN_DISTANCE){
	                // top or down
	                if(deltaY < 0) { this.onDownSwipe(); return true; }
	                if(deltaY > 0) { this.onUpSwipe(); return true; }
	            }
	            else {
	                    Log.i(logTag, "Vertical Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
	                    return false; // We don't consume the event
	            }
            }

            return true;
        }
    }
    return false;
}

}