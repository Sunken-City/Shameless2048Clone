package pico.riley.shameless2048clone;


import java.util.Map;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TileAdapter extends BaseAdapter {

     private LayoutInflater mInflater;   
     private int mResource;
	 private Context mContext;
	 private Game game;
	 private SparseIntArray colorMap = new SparseIntArray();

	    public TileAdapter(Context c, int resource, Game newGame) {
	        mContext = c;
			this.mResource = resource;
			this.game = newGame;
			this.mInflater = LayoutInflater.from(mContext);
			initializeMap();
	    }
	    
	    @Override
	    public void notifyDataSetChanged()
	    {
	    	super.notifyDataSetChanged();
	    	((MainActivity)mContext).updateScore();
	    }
	    
	    private void initializeMap() {
	    	colorMap.append(0, Integer.valueOf(0xFFCCC0B3));
	    	colorMap.append(2, Integer.valueOf(0xFFEEE4DA));
	    	colorMap.append(4, Integer.valueOf(0xFFEDE0C8));
	    	colorMap.append(8, Integer.valueOf(0xFFf2b179));
	    	colorMap.append(16, Integer.valueOf(0xFFf59563));
	    	colorMap.append(32, Integer.valueOf(0xFFf67c5f));
	    	colorMap.append(64, Integer.valueOf(0xFFf65e3b));
	    	colorMap.append(128, Integer.valueOf(0xFFedcf72));
	    	colorMap.append(256, Integer.valueOf(0xFFedcc61));
	    	colorMap.append(512, Integer.valueOf(0xFFedc850));
	    	colorMap.append(1024, Integer.valueOf(0xFFedc53f));
	    	colorMap.append(2048, Integer.valueOf(0xFFedc22e));
		}

		public int getCount() {
	        return 16;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) public View getView(int position, View convertView, ViewGroup parent) {
	    	View v;
	        if (convertView == null) {
	            v = mInflater.inflate(mResource, parent, false);
	        } else {
	            v = convertView;
	        }
	        
	        TextView text = (TextView) v.findViewById(R.id.text); //"#EEE4DA"
	        int tileNumber = game.getTile(position);
	        if (tileNumber == 0)
	        	text.setText("");
	        else
	        	text.setText(Integer.toString(tileNumber));
	        
	        if(tileNumber > 4)
	        	text.setTextColor(0xFFF9F6F2);
	        else
	        	text.setTextColor(0xFF776E65);

	        
	        RelativeLayout tile = (RelativeLayout) v.findViewById(R.id.tileBackground);
	        Drawable background = tile.getBackground();
	        background.setColorFilter(colorMap.get(tileNumber), PorterDuff.Mode.SRC_ATOP);
	        tile.setBackground(background);
	        return v;
	    }
}
