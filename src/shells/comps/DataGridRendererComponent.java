package shells.comps;

import flash.events.MouseEvent;
import flash.events.listeners.MouseEventListenerAdapter;
import mx.binding.utils.BindingUtils;
import mx.controls.Alert;
import mx.core.UIComponent;
import mx.events.CloseEvent;
import mx.events.ResizeEvent;
import mx.events.listeners.FlexMouseEventListenerAdapter;
import spark.components.Button;
import spark.components.Group;
import spark.components.Label;
import spark.layouts.VerticalLayout;
import iui.core.FlFunction;
import iui.core.SuperWidget;
import iui.widget.ItemRendererComponent;
import iui.widget.ItemRendererData;

public class DataGridRendererComponent extends ItemRendererComponent {

	public DataGridRendererComponent() {
		
	}

	@Override
	public void onDataChanged(Object data) {
//		if(this.label!=null){
//			FlexObject fO=(FlexObject)data;
//	        this.label.setText(fO.get("c3").toString());
//		}
	}

	@Override
	public void onListDataChanged(ItemRendererData listData) {
//		if(this.label!=null){
//	        this.label.setText(listData.label);
//		}
	}
	Label label;
	Button button=null;
	@Override
	public void createContent(SuperWidget parent) {
		try{
		Group group=(Group)parent;
		//group.setWidth(300);
		//ResizeEvent evt=new ResizeEvent();
		//group.dispatchEvent(evt);
	   VerticalLayout layout=wm.create(VerticalLayout.class);
	   layout.setVerticalAlign("middle");
	   layout.setHorizontalAlign("center");
	   group.setLayout(layout);
		
	   button=wm.create(Button.class);
		BindingUtils.bindProperty(wm, button, "toolTip", parent, new Object[]{"listData","label"},false,false);
		BindingUtils.bindProperty(wm, button, "label", parent, new Object[]{"listData","label"},false,false);
		button.addClickListener(new MouseEventListenerAdapter(){
			  @Override
			public void click(MouseEvent e) {
			      String info="you click:"+listData.label+" "+listData.columnIndex+" "+listData.rowIndex;
			      Alert.show(wm, info, "信息提示", Alert.OK|Alert.CANCEL,
			    		 (UIComponent)DataGridRendererComponent.this.listData.owner ,
			    		 new FlFunction(){
							public Object execute(Object[] params) {
								CloseEvent e=(CloseEvent)params[0];
								String msg=null;
								if(e.getDetail()==Alert.OK){
									msg="you select ok";
								}else{
									msg="you select cancel";
								}
								System.out.println(msg);
								return null;
							}
			             },
			    		 null, null, null);
			}
		});
		group.addElement(button);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
