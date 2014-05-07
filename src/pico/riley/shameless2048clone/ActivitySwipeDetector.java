package pico.riley.shameless2048clone;

import pico.riley.shameless2048clone.Game.Direction;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//Using code from http://stackoverflow.com/questions/937313/android-basic-gesture-detection
public class ActivitySwipeDetector implements View.OnTouchListener {

static final String logTag = "ActivitySwipeDetector";
private Activity activity;
static final int MIN_DISTANCE = 100;
private float downX, downY, upX, upY;
private Game g;

public ActivitySwipeDetector(Activity activity, Game game){
    this.activity = activity;
    this.g = game;
}

public void onLeftSwipe(){
    Log.i(logTag, "Left Swipe!");
    g.makeMove(Direction.LEFT);
}

public void onRightSwipe(){
    Log.i(logTag, "Right Swipe!");
    g.makeMove(Direction.RIGHT);
}

public void onDownSwipe(){
    Log.i(logTag, "Down Swipe!");
    g.makeMove(Direction.DOWN);
}

public void onUpSwipe(){
    Log.i(logTag, "Up Swipe!");
    g.makeMove(Direction.UP);
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