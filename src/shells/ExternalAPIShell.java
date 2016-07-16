package shells;

import flash.system.ApplicationDomain;
import iui.core.SuperWidget;

import mx.events.ModuleEvent;
import mx.events.StyleEvent;
import mx.events.listeners.ModuleEventListenerAdapter;
import mx.events.listeners.StyleEventListenerAdapter;
import mx.modules.ModuleManager;

import org.eclipse.swt.widgets.Shell;

import cn.smartinvoke.gui.Environment;
/**
 *   加载外部地图API的窗口
 *   
 */
public class ExternalAPIShell extends IUIShell {
    
	public ExternalAPIShell() {
		
	}

	public ExternalAPIShell(Shell parent) {
		super(parent);
		this.setSize(500, 500);
	}

	@Override
	protected void loadComplete() {
		 SuperWidget  styleManager=app.getStyleManager();
		//获得地图API的样式文件 
		final String path=Environment.getLocation()+"/map/styles.swf";
		SuperWidget  evtDispatch=(SuperWidget)wm.
		callFunction(styleManager.id, "loadStyleDeclarations2", new Object[]{path,true,ApplicationDomain.getCurrentDomain(wm)});
		//样式加载进度监听器
		StyleEventListenerAdapter listener=new StyleEventListenerAdapter(){
			public void error(StyleEvent e) {
				System.out.println("样式文件"+path+" 加载出错");
			}
			public void complete(StyleEvent e) {
				System.out.println("样式文件"+path+" 加载完毕");
				//加载外部API中的类型定义swf
				String url=Environment.getLocation()+"/map/ESRI_API.swf";
			  	SuperWidget moduleInfo=(SuperWidget)ModuleManager.getModule(wm, url);
			  	moduleInfo.addListener(ModuleEvent.READY, new ModuleEventListenerAdapter(){
			    	 @Override
			    	public void ready(ModuleEvent e) {
			    		   //API加载完毕后调用对应的API显示地图
			    		   SuperWidget map=wm.create("com.esri.ags.Map");//创建map地图对象
			    		   SuperWidget layer=wm.create("com.esri.ags.layers.ArcGISTiledMapServiceLayer");//创建一个地图图层
			    		   wm.setProp(layer.id,"url","http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer" );
			    		   wm.callFunction(map.id, "addLayer",new Object[]{layer});//添加图层
			    		   
			    		   wm.addChild(app.id, map.id);
			    	}
			    	public void error(ModuleEvent e) {
			    		System.out.println("模块加载错误");
			    	}
			    });
			   //加载模块
			   wm.callFunction(moduleInfo.id, "load", 
					   new Object[]{ApplicationDomain.getCurrentDomain(wm)});
			   
			}
		};
		evtDispatch.addListener(StyleEvent.COMPLETE, listener );
		evtDispatch.addListener(StyleEvent.ERROR, listener);
	}

}
