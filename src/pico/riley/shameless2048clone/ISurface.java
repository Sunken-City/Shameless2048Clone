package pico.riley.shameless2048clone;

import android.graphics.Canvas;

public interface ISurface {
	void onInitialize();
	void onDraw(Canvas canvas);
	void onUpdate(long gameTime);
}
