package shells;

import iui.IUIConstants;
import iui.binding.FlBindableObject;
import iui.widget.RendererType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import cn.smartinvoke.gui.Environment;
import cn.smartinvoke.module.FlexObject;

import shells.comps.ShellLaunchComponent;
import spark.components.Group;
import spark.components.HGroup;
import spark.components.NavigatorContent;
import spark.primitives.Graphic;
import spark.primitives.Rect;
import mx.containers.TabNavigator;
import mx.controls.DataGrid;
import mx.controls.Image;
import mx.controls.Text;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.graphics.GradientEntry;
import mx.graphics.LinearGradient;

//程序启动时显示的主窗口
public class MainShell extends IUIShell {
	//主窗口的单实例句柄
	public static MainShell Instance;
	public MainShell(){
		Instance=this;
		this.setSize(680, 650);
		this.setText("SmartInvoke的InvokeUI框架界面演示程序");
	}
	@Override
	protected void loadComplete() {
			//设置Application对象的布局
			app.setLayout("vertical");
			//垂直元素间的距离为0
			app.setStyle("verticalGap", 0);
			
			createTitleBar();
			createContent();
		   //this.createDatagrid();//创建表格
		   // wm.addChild(app.id, grid.id);//添加到Application中
	}
	/**
	 *  创建标题栏
	 * @return
	 */
	void createTitleBar(){
		 Group res=wm.create(Group.class);
		 res.setHeight(72);//350像素高
		 res.setPercentWidth(100);//100%宽
		
		 
		 //添加logo图片
		 Image logo=wm.create(Image.class);
		 String path=Environment.getLocation()+"/images/logo.png";
		 logo.setSource(path);
		 logo.setX(3); logo.setY(3);
		 res.addElement(logo);
		 
		 //将Group添加到application
		 app.addChild(res);
	}
	/**
	 *  填充内容
	 */
	void createContent(){
		TabNavigator navigator=wm.create(TabNavigator.class);
		//设置百分比布局
		navigator.setPercentHeight(100);
		navigator.setPercentWidth(100);
		navigator.setStyle("paddingTop", 0);
		//添加演示功能点表格
		NavigatorContent demoContent=wm.create(NavigatorContent.class);
		demoContent.setLabel("演示功能点");
		demoContent.addElement(this.createDatagrid());
		navigator.addChild(demoContent);
		//添加说明Text
		NavigatorContent describeContent=wm.create(NavigatorContent.class);
		describeContent.setLabel("关于InvokeUI");
		describeContent.addElement(this.createInvokeUIDescribe());
		navigator.addChild(describeContent);
		
		//将navigator添加到Application对象以显示出来
		app.addChild(navigator);
	}
	Image createInvokeUIDescribe(){
		Image image=wm.create(Image.class);
		String path=Environment.getLocation()+"/images/des.png";
		image.setSource(path);
		image.setX(8);image.setY(8);
		return image;
	}
	/**
	 *   创建演示窗体列表Grid
	 * @return
	 */
	DataGrid createDatagrid(){
		final DataGrid dataGrid=wm.create(DataGrid.class);
		dataGrid.setPercentHeight(100);//设置宽度为100%
		dataGrid.setPercentWidth(100);//设置高度为100%
		dataGrid.setRowHeight(30);//设置行高为30像素
		dataGrid.setStyle("fontSize", 13);
        //第一列
		DataGridColumn c1=wm.create(DataGridColumn.class);
		c1.setHeaderText("功能点");//设置表格头
		c1.setDataField("name");//设置显示的数据字段名称
		c1.setWidth(100);
		//第二列
		DataGridColumn c2=wm.create(DataGridColumn.class);
		c2.setHeaderText("功能点描述");
		c2.setDataField("describe");c2.setWidth(260);
		//c2.setItemRenderer(new RendererType(MyRendererComponent.class));
		//第三列
		DataGridColumn c3=wm.create(DataGridColumn.class);
		c3.setHeaderText("窗口状态");
		c3.setDataField("status");
		//第四列
		DataGridColumn c4=wm.create(DataGridColumn.class);
		c4.setHeaderText("点击运行");
		c4.setDataField("launch");
		c4.setItemRenderer(new RendererType(ShellLaunchComponent.class));
		dataGrid.setColumns(new Object[]{c1,c2,c3,c4});
		//初始化数据
		dataGrid.setDataProvider(this.createDataGridData());
		
		return dataGrid;
    }
	
	/**
	 *  创建表格数据
	 */
	List<FlBindableObject> createDataGridData(){
		List<FlBindableObject> res=new ArrayList<FlBindableObject>();
		FlexObject metaObj=new FlexObject();//itemRender的meta信息，只有这里设置了itemRenderer与列的对应关系表格才能正确显示
		metaObj.put("launch", ShellLaunchComponent.class.getName());
		//注册表格窗口
		FlBindableObject shellDes=
			createShellDataObject("表格", "演示IUI调用flex的DataGrid功能点", DataGridShell.class.getName());
		shellDes.addProperty(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
		res.add(shellDes);
		//注册图表窗口
		shellDes=
			createShellDataObject("图表", "演示IUI调用flex的Chart功能点", ChartShell.class.getName());
		shellDes.addProperty(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
		res.add(shellDes);
		//注册外部API窗口
		shellDes=
			createShellDataObject("外部API加载",
					"演示IUI加载外部地图API，并显示地图", ExternalAPIShell.class.getName());
		shellDes.addProperty(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
		res.add(shellDes);
		//注册效果窗口
		shellDes=
			createShellDataObject("Flex效果",
					"演示IUI加载Flex过渡效果", EffectShell.class.getName());
		shellDes.addProperty(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
		res.add(shellDes);
		//注册摄像头
		shellDes=
			createShellDataObject("摄像头",
					"演示IUI调用摄像头", CameraShell.class.getName());
		shellDes.addProperty(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
		res.add(shellDes);
		//注册动态换肤窗口
		shellDes=
			createShellDataObject("动态换肤",
					"演示IUI实现动态换肤",SkinSwitchShell.class.getName());
		shellDes.addProperty(IUIConstants.META_TYPE_PROPERTY_NAME, metaObj);
		res.add(shellDes);
		return res;
	}
	/**
	 *   构造FlBindableObject对象，FlBindableObject类型对象的属性变化都是可以被java和flex监听的
	 * @param name
	 * @param describe
	 * @param clsName
	 * @return
	 */
	FlBindableObject createShellDataObject(String name,String describe,String clsName){
		FlBindableObject res=new FlBindableObject();
		res.addProperty("name", name);
		res.addProperty("describe", describe);
		res.addProperty("status", "未启动");
		res.addProperty("launch", clsName);//DataGridShell的完整类路径
		return res;
	}
}
