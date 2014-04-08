package pico.riley.shameless2048clone;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class AnimationThread extends Thread
{
	private SurfaceHolder surfaceHolder;
	private ISurface panel;
	private boolean run = false;
	
	public AnimationThread(SurfaceHolder surfaceHolder, ISurface panel) 
	{
		this.surfaceHolder = surfaceHolder;
		this.panel = panel;
		
		panel.onInitialize();
	}
	
	public void setRunning(boolean value) 
	{
		run = value;
	}
	
	private long timer;
	
	@SuppressLint("WrongCall") @Override
	public void run() 
	{
		Canvas c;
		while(run) 
		{
			c = null;
			timer = System.currentTimeMillis();
			panel.onUpdate(timer);
			
			try
			{
				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					panel.onDraw(c);
				}
			} 
			
			finally 
			{
				if (c != null)
				{
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			
		}
	}
}
