
package com.shuorigf.bluetooth.streetlamp.ui.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.shuorigf.bluetooth.streetlamp.R;


/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

	private TextView tvContent;

	public MyMarkerView(Context context, int layoutResource) {
		super(context, layoutResource);
		tvContent = (TextView) findViewById(R.id.tv_maker_view_content);
	}

	// callbacks everytime the MarkerView is redrawn, can be used to update the
	// content (user-interface)
	@Override
	public void refreshContent(Entry e, Highlight highlight) {
		tvContent.setText("" + highlight.getY());
		super.refreshContent(e, highlight);
	}

	@Override
	public MPPointF getOffset() {
		return new MPPointF(15, -getHeight()/2);
	}
}