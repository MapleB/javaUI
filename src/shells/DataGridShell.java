package shells;

import iui.IUIConstants;
import iui.core.FlFunction;
import iui.widget.RendererType;

import java.util.*;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import shells.comps.DataGridRendererComponent;

import cn.smartinvoke.module.FlexObject;

import mx.controls.*;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.core.Application;
import mx.events.FlexEvent;
import mx.events.ListEvent;
import mx.events.listeners.FlexEventListenerAdapter;
import mx.events.listeners.ListEventListenerAdapter;

public class DataGridShell extends IUIShell {
	public DataGridShell(Shell parent){
		super(parent);
		this.setSize(500, 500);
	}
	@Override
	protected void loadComplete() {
		try {
			DataGrid dataGrid = this.createDatagrid();
			app.addChild(dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	DataGrid createDatagrid(){
		   //创建表格并设置样式
			final DataGrid dataGrid=wm.create(DataGrid.class);
			dataGrid.setPercentHeight(100);
			dataGrid.setPercentWidth(100);
			dataGrid.setRowHeight(30);
			//设置itemRenderer的meta信息
			List<Object> data=new ArrayList<Object>();
			FlexObject metaObj=new FlexObject();
			metaObj.put("c2", DataGridRendererComponent.class.getName());
			//构造数据
			for(int i=0;i<800;i++){
			    Map<String,Object> map=new HashMap<String, Object>();
			    map.put("c1", ""+i);
			    map.put("c2", "第二列_"+i);
			    map.put("c3", "第三列_"+i);
			    map.put(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
				data.add(map);
			}
			dataGrid.setDataProvider(data);
			//设置第一列
			DataGridColumn c1=wm.create(DataGridColumn.class);
			c1.setHeaderText("c1Text");
			c1.setDataField("c1");c1.setWidth(200);
			c1.setLabelFunction(new FlFunction(){
				@Override
				public Object execute(Object[] params) {
					//Object[] pars=(Object[])params[0];
					FlexObject obj=(FlexObject)params[0];
					String idVal=obj.get("c1")+"";
					return "编号:"+idVal;
				}
			});
			//设置第二列
			DataGridColumn c2=wm.create(DataGridColumn.class);
			c2.setHeaderText("c2Text");
			c2.setDataField("c2");
			c2.setItemRenderer(new RendererType(DataGridRendererComponent.class));
			//设置第三列
			DataGridColumn c3=wm.create(DataGridColumn.class);
			c3.setHeaderText("c3Text");
			c3.setDataField("c3");
			dataGrid.setColumns(new Object[]{c1,c2,c3});
			//添加事件
			dataGrid.addCreationCompleteListener(new FlexEventListenerAdapter(){
				@Override
				public void creationComplete(FlexEvent e) {
					   DataGrid grid=(DataGrid)e.getCurrentTarget();
					   System.out.println(grid+"创建完毕...");
				}
			});
			dataGrid.addChangeListener(new ListEventListenerAdapter(){
				public void change(ListEvent e) {
					 System.out.println(e);
				}
			});
			
			return dataGrid;
	    }
}
