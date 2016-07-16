package shells;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Shell;

import iui.IUIConstants;
import iui.binding.FlBindableObject;
import iui.manage.WidgetManager;
import mx.core.Application;
import test.IUIDemoTester;
import cn.smartinvoke.FlashContainer;
import cn.smartinvoke.ILoadCompleteListener;
import cn.smartinvoke.ObjectPool;
import cn.smartinvoke.gui.FlashViewer;
import cn.smartinvoke.gui.shell.FlashShell;
/**
 *  所有打开的窗体都集成本类，本类的作用是进行一些必要的初始化工作
 */
public abstract class IUIShell extends FlashShell{
	//public static String mainSwf="E:/IT/SIWorkspace/InvokeUI_/bin-debug/InvokeUI.swf";
	//flex端控件的管理类型，通过本类构建和访问flex控件
	protected WidgetManager wm;
	//flex的application对象
	protected Application app;
	
	//------
	public FlBindableObject statusObject;//显示当前窗口的状态对象
	//
	public IUIShell() {
		 this.setAppPath(IUIDemoTester.appPath);
	}
	public IUIShell(Shell parent) {
		   super(parent,SWT.SHELL_TRIM);
	       this.setAppPath(IUIDemoTester.appPath);
	}
	
	@Override
	public void open() {
		super.open();
		FlashContainer container=this.getFlashViewer().getFlashContainer();
		container.addListener(new ILoadCompleteListener(){
			public void run() {
				FlashViewer viewer=getFlashViewer();
				wm=(WidgetManager)viewer.getWidgetManager();
				//初始化flex Application对象
				app=wm.create(Application.class); 
				loadComplete();
			} 
		});
		//为非主窗体添加事件监听逻辑
		if(!this.getClass().getName().equals("shells.MainShell")){
		this.addShellListener(new ShellListener(){
            void dispatchStatus(String val){
            	statusObject.changeProperty("status", val);
            }
			@Override
			public void shellActivated(ShellEvent arg0) {
				dispatchStatus("激活");
			}

			@Override
			public void shellClosed(ShellEvent arg0) {
				dispatchStatus("关闭");
			}

			@Override
			public void shellDeactivated(ShellEvent arg0) {
				dispatchStatus("shellDeactivated");
			}

			@Override
			public void shellDeiconified(ShellEvent arg0) {
				dispatchStatus("shellDeiconified");
			}

			@Override
			public void shellIconified(ShellEvent arg0) {
				dispatchStatus("shellIconified");
			}
			
		});
		}
	}
	/**
	 *  子类实现本方法，提供窗口加载完毕后的执行逻辑
	 */
	protected abstract void loadComplete();
	
	
}