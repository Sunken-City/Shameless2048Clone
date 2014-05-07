package pico.riley.shameless2048clone;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class TileAdapter extends BaseAdapter {

     private LayoutInflater mInflater;   
     private int mResource;
	 private Context mContext;
	 private Game game;

	    public TileAdapter(Context c, int resource, Game newGame) {
	        mContext = c;
			this.mResource = resource;
			this.game = newGame;
			this.mInflater = LayoutInflater.from(mContext);
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
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	View v;
	        if (convertView == null) {
	            v = mInflater.inflate(mResource, parent, false);
	        } else {
	            v = convertView;
	        }
	        
	        TextView text = (TextView) v.findViewById(R.id.text);
	        text.setText(Integer.toString(game.getTile(position)));
	        return v;
	    }
}
