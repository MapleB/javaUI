package shells;

import iui.manage.WidgetManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.charts.CategoryAxis;
import mx.charts.ColumnChart;
import mx.charts.effects.SeriesSlide;
import mx.charts.series.ColumnSeries;
import mx.controls.Button;

import org.eclipse.swt.widgets.Shell;

import flash.events.MouseEvent;
import flash.events.listeners.MouseEventListenerAdapter;
/**
 *  图表窗口
 */
public class ChartShell extends IUIShell {

	public ChartShell(Shell parent) {
		super(parent);
		this.setSize(400, 400);
	}

	@Override
	protected void loadComplete() {
         testChart();
	}
	 ColumnChart testChart(){
		//==========
		WidgetManager widgetManager=wm;
		final ColumnChart chart=widgetManager.create(ColumnChart.class);
		CategoryAxis axis=widgetManager.create(CategoryAxis.class);
		axis.setCategoryField("x");
		
		//CategoryAxis must implements IAxis
		chart.setHorizontalAxis(axis);chart.setPercentWidth(100);
		chart.setShowDataTips(true);chart.setPercentHeight(100);
		
		ColumnSeries series1=widgetManager.create(ColumnSeries.class);
		series1.setXField("x");series1.setYField("y1");
		//series1.sS_itemRenderer(styleVal);
		ColumnSeries series2=widgetManager.create(ColumnSeries.class);
		series2.setXField("x");series2.setYField("y2");
		ColumnSeries series3=widgetManager.create(ColumnSeries.class);
		series3.setXField("x");series3.setYField("y3");
		//
		SeriesSlide slideIn=widgetManager.create(SeriesSlide.class);
		slideIn.setDuration(1000);slideIn.setDirection("up");
		SeriesSlide slideOut=widgetManager.create(SeriesSlide.class);
		slideOut.setDuration(1000);slideOut.setDirection("down");
		Object[] series=new Object[]{series1,series2,series3};
		for(Object s:series){
			ColumnSeries  ser=(ColumnSeries)s;
			widgetManager.setStyle(ser.id, "showDataEffect", slideIn);
			widgetManager.setStyle(ser.id, "hideDataEffect", slideOut);
		}
		
		chart.setSeries(series);
	   
		List<Object> chartData=new ArrayList<Object>();
		for(int i=0;i<10;i++){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("x", "cat"+i);
			map.put("y1", i*2);map.put("y2", i*4);map.put("y3", i*10);
			chartData.add(map);
		}
		chart.setDataProvider(chartData);
		app.addChild(chart);
		
		Button button=widgetManager.create(Button.class);
		button.setLabel("动态改变数据");
		button.addClickListener(new MouseEventListenerAdapter(){
		    	@Override
		    	public void click(MouseEvent e) {
		    		Thread thread = new Thread() {
					public void run() {
						while(true){
						List<Object> chartData = new ArrayList<Object>();
						for (int i = 0; i < 10; i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("x", "cat" + i);
							map.put("y1", i * getRandomVal());
							map.put("y2", i * getRandomVal());
							map.put("y3", i * getRandomVal());
							chartData.add(map);
						}
						chart.setDataProvider(chartData);
						
						  try {
							Thread.sleep(5000);
						} catch (InterruptedException e) { }
						}
					}
				};
				thread.setDaemon(true);
				thread.start();
		    	}
		    	public int getRandomVal(){
		    		double val=Math.random()*10;
		    		return (int)val;
		    	}
		});
		app.addChild(button);
		
		return chart;
		//widgetManager.addChild(0, chart.id);
	}
}
