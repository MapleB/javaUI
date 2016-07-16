package test;

import java.io.File;

import org.eclipse.swt.widgets.Display;

import shells.IUIShell;
import shells.MainShell;
import cn.smartinvoke.core.SuperApp;
import cn.smartinvoke.gui.Environment;
import cn.smartinvoke.gui.shell.FlashShell;

public class IUIDemoTester extends SuperApp {
    public static String appPath;
	public IUIDemoTester(String[] args) {
		this.init(args);
	}
	public String getMainSWF(){
		
		return appPath;
	}
	@Override
	protected FlashShell createMainShell() {
		IUIShell shell=new MainShell();
		return shell;
	}
	/**
	 * application main method
	 * @param args
	 */
	public static void main(String[] args) {
		String appLocal=new File("swfs/InvokeUI.swf").getAbsolutePath();
		appLocal=appLocal.replace(File.separatorChar, '/');
		System.out.println("swf启动位置："+appLocal);
		IUIDemoTester.appPath=appLocal;
		final Display display = new Display();
		//
		IUIDemoTester app =new IUIDemoTester(args);
		FlashShell shell = app.getMainShell();
		//shell.setSize(550,400);
		shell.center();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}	
}
