package pico.riley.shameless2048clone;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

//Using this tutorial for the foundation of the project:
//http://mobile.dzone.com/articles/beginning-android-game

public class MainActivity extends ActionBarActivity {

	static final String logTag = "ActivitySwipeDetector";
	private Activity activity;
	static final int MIN_DISTANCE = 100;
	private float downX, downY, upX, upY;
    private ImageView imageView;
    private RelativeLayout lowestLayout;
    
    private RelativeLayout.LayoutParams lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.bulbasaur);
        //setContentView(new AndroidTutorialPanel(this));
        ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
        lowestLayout = (RelativeLayout)this.findViewById(R.id.lowestLayout);
        lowestLayout.setOnTouchListener(activitySwipeDetector);
    	lp = (LayoutParams) imageView.getLayoutParams();
    }
    
    public void onLeftSwipe()
    {
        Toast.makeText(activity, "Left Swipe!", Toast.LENGTH_LONG).show();
    	//lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    }

	public void onRightSwipe() 
	{

	    Toast.makeText(activity, "Right Swipe!", Toast.LENGTH_LONG).show();
    	//lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
	}
	
	public void onDownSwipe() 
	{

	    Toast.makeText(activity, "Down Swipe!", Toast.LENGTH_LONG).show();
    	//lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		
	}
	
	public void onUpSwipe() 
	{

	    Toast.makeText(activity, "Up Swipe!", Toast.LENGTH_LONG).show();
		//lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
	}

}
