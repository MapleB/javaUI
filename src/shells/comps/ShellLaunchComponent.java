package shells.comps;

import java.lang.reflect.Constructor;

import org.eclipse.swt.widgets.Shell;

import flash.events.MouseEvent;
import flash.events.listeners.MouseEventListenerAdapter;
import mx.binding.utils.BindingUtils;
import mx.events.PropertyChangeEvent;
import mx.events.listeners.PropertyChangeEventListenerAdapter;
import shells.IUIShell;
import shells.MainShell;
import spark.components.Button;
import spark.components.Group;
import spark.layouts.VerticalLayout;
import iui.binding.FlBindableObject;
import iui.core.FlClass;
import iui.core.SuperWidget;
import iui.widget.ItemRendererComponent;
import iui.widget.ItemRendererData;

//启动窗口的itemRenderer
public class ShellLaunchComponent extends ItemRendererComponent {
	Button button=null;
	@Override
	public void createContent(SuperWidget parent) {
		   Group container=(Group)parent;
		   //设置布局方式
		   VerticalLayout layout=wm.create(VerticalLayout.class);
		   layout.setVerticalAlign("middle");
		   layout.setHorizontalAlign("center");
		   container.setLayout(layout);
		   //创建启动按钮
		   button=wm.create(Button.class);
		  // button.setStyle("emphasizedSkin", new FlClass("mx.skins.spark.DefaultButtonSkin"));
		   button.setStyle("skinClass", new FlClass("spark.skins.spark.ButtonSkin"));
		   button.setStyle("fontFamily","微软雅黑,黑体");
		   button.setStyle("fontSize",13);
		   button.setLabel("运行");
		   //绑定tooltip显示
		   BindingUtils.bindProperty(wm, button, "toolTip", parent, new Object[]{"listData","label"},false,false);
		   //监听单机事件
		   button.addClickListener(new MouseEventListenerAdapter(){
			 @Override
			 public void click(MouseEvent e) {
				try {
					FlBindableObject bindableObj=(FlBindableObject)data;
					String clsName=bindableObj.getProperty("launch")+"";
					Class<?> shellCls=Class.forName(clsName);
					Constructor< ?> cons=shellCls.getConstructor(Shell.class);
					IUIShell shell=(IUIShell)cons.newInstance(MainShell.Instance); 
					shell.setText(bindableObj.getProperty("describe")+"");
					//设置状态对象，这样shell窗口状态的改变会在DataGrid中有所显示
					shell.statusObject=bindableObj;
					shell.center();
					shell.open();
					bindableObj.changeProperty("status", "打开");
					//将启动按钮设为不可用，直到对应窗口关闭
					button.setEnabled(false);
					//监听bindableObj属性的改变，如果status属性值变为关闭，则表明对应窗口已经关闭
					bindableObj.addPropertyChangeListener(new PropertyChangeEventListenerAdapter(){
						@Override
						public void propertyChange(PropertyChangeEvent e) {
						     String property=e.getProperty()+"";
						     String newVal=e.getNewValue()+"";
						     if(property.equals("status")&& newVal.equals("关闭")){
						    	 button.setEnabled(true);
						     }
						}
					});
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			 }
		   });
		   
		   container.addElement(button);
	}

	@Override
	public void onDataChanged(Object data) {
		System.out.println("onDataChanged:  "+data);
	}

	@Override
	public void onListDataChanged(ItemRendererData listData) {
		System.out.println("onListDataChanged: "+listData);
	}

}
