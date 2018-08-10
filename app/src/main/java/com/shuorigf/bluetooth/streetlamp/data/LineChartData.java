package com.shuorigf.bluetooth.streetlamp.data;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: clx on 17/10/26.
 */
public class LineChartData {
	public List<LineInfoData> mLinDataList;
	
	public LineChartData() {
		mLinDataList = new ArrayList<>();
	}

	public void addLine(List<Float> yVal,int color)
	{
		if(yVal == null)
			return;
		LineInfoData data = new LineInfoData(yVal,color);
		mLinDataList.add(data);
	}


	public void drawLine(LineChart lineChart) {
		if(lineChart == null)
			return;
		ArrayList<ILineDataSet> dataSets = new ArrayList<>();
		for(int i = 0;i < mLinDataList.size(); i++)
		{
			LineInfoData lineInfoData = mLinDataList.get(i);
			ArrayList<Entry> values = new ArrayList<>();
			for(int j=0;j<lineInfoData.mYVal.size();j++)
			{
				values.add(new Entry(j, lineInfoData.mYVal.get(j)));
			}
			LineDataSet set = new LineDataSet(values, "DataSet"+i);


			set.setDrawIcons(false);

			// set the line to be drawn like this "- - - - - -"设置线为虚线
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
			set.setColor(lineInfoData.mLineColor);
			set.setCircleColor(lineInfoData.mLineColor);
			set.setLineWidth(1f);
			set.setCircleRadius(3f);
			set.setCircleHoleRadius(2f);
			set.setDrawCircleHole(true);//设置是否空心圆
			set.setValueTextSize(9f);
			set.setDrawValues(false);


			//设置线下面的阴影颜色
			set.setDrawFilled(false);
//			set.setFillColor(data.mLineColor);

			dataSets.add(set); // add the datasets
		}


		LineData lineData = new LineData(dataSets);
		// set data
		lineChart.setData(lineData);
		lineChart.invalidate();
	}

}
