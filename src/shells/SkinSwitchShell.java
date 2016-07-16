package shells;

import java.util.Collection;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import mx.core.Application;

import cn.smartinvoke.gui.Environment;
import cn.smartinvoke.gui.FlashViewer;
import iui.core.FlClass;
import iui.core.SuperWidget;
import iui.manage.WidgetManager;
import flash.events.MouseEvent;
import flash.events.listeners.MouseEventListenerAdapter;
import spark.components.ToggleButton;

/**
 *   演示动态换肤
 */
public class SkinSwitchShell extends IUIShell {
	public SkinSwitchShell(Shell parent) {
		super(parent);
		this.setSize(300, 300);//设置窗口大小
	}
	@Override
	protected void loadComplete() {
		//设置Application布局
		app.setStyle("verticalAlign", "middle");
		//创建按钮
		 ToggleButton toggleButton=wm.create(ToggleButton.class);
		 toggleButton.setLabel("点击改变程序外观");
		 toggleButton.setWidth(200);
		 toggleButton.setHeight(50);
		 //toggleButton.setStyle("skinClass", new FlClass("spark.skins.spark.ButtonSkin"));
		 
		 //添加click监听器
		 toggleButton.addClickListener(new MouseEventListenerAdapter(){
			 //这里的皮肤swf由flash builder导出
			String path=Environment.getLocation()+"/styles/spark_cobalt.swf";
			public void click(MouseEvent e) {
				 ToggleButton target=(ToggleButton)e.getCurrentTarget();
				 
				 //获取当前所有的WidgetManager对象，每个打开窗口都有一个WidgetManager对象对应
				 List<FlashViewer> flashViewers=FlashViewer.getViewers();
				 //循环所有WidgetManager对象，设置皮肤
				 for(FlashViewer viewer:flashViewers){
					 WidgetManager wm=(WidgetManager)viewer.getWidgetManager();
				   //获取WidgetManager的flex Application顶层对象
				   Application app=wm.create(Application.class);
				   //获得样式管理器
				   SuperWidget  styleManager=app.getStyleManager();
				   //加载样式
				   if(target.getSelected()){
					 
				     wm.asyncCallFunction(styleManager.id, "loadStyleDeclarations", new Object[]{path});
				   }else{
					 wm.asyncCallFunction(styleManager.id, "unloadStyleDeclarations", new Object[]{path});
				   }
				 }
			}
			
		 });
		 //添加到application显示列表
		 app.addChild(toggleButton);
	}

}
