package shells;

import java.io.File;

import mx.controls.Image;
import mx.core.Application;

import org.eclipse.swt.widgets.Shell;

import cn.smartinvoke.gui.Environment;
import flash.events.MouseEvent;
import flash.events.listeners.MouseEventListenerAdapter;

import spark.components.Label;
import spark.components.TileGroup;
import spark.effects.Animate;
import spark.effects.animation.MotionPath;
import spark.effects.animation.SimpleMotionPath;
//演示Flex效果
public class EffectShell extends IUIShell {

	public EffectShell(Shell parent) {
		super(parent);
		this.setSize(400, 400);
	}

	@Override
	protected void loadComplete(){
		//application的对齐方式
		app.setStyle("verticalAlign", "middle");
		app.setStyle("horizontalAlign", "center");
		Label label=wm.create(Label.class);
		label.setText("点击图片执行效果");
		app.addChild(label);
		
		//创建TileGroup对象
		final TileGroup tileGroup=wm.create(TileGroup.class);
		//设置属性
    	tileGroup.setHorizontalGap(1);
    	tileGroup.setVerticalGap(1);
    	tileGroup.setStyle("direction", "ltr");
    	tileGroup.setColumnWidth(50);
    	tileGroup.setRowHeight(50);
    	tileGroup.setUseHandCursor(true);
    	tileGroup.setButtonMode(true);
    	//创建Animate效果
    	final Animate animate=wm.create(Animate.class);
    	animate.setDuration(750);animate.setRepeatBehavior("reverse");
    	animate.setTarget(tileGroup);animate.setRepeatCount(2);
    	
    	SimpleMotionPath p1=new SimpleMotionPath();
    	p1.setValueFrom(1);p1.setValueTo(20);p1.setProperty("horizontalGap");
    	
    	SimpleMotionPath p2=new SimpleMotionPath();
    	p2.setValueFrom(1);p2.setValueTo(20);p2.setProperty("verticalGap");
    	
    	SimpleMotionPath p3=new SimpleMotionPath();
    	p3.setValueFrom(0);p3.setValueTo(-50);p3.setProperty("z");
    	animate.setMotionPaths(new MotionPath[]{p1,p2,p3});
    	
    	//images
    	File folder=new File(Environment.getLocation()+"/images/tiles");
    	File[] subFiles=folder.listFiles();
    	for(File file:subFiles){
    		//创建图像
    		 Image image=wm.create(Image.class);
    		 String path=file.getAbsolutePath();
    		 image.setSource(path);
    		 image.setToolTip(path);
    		 //设置点击事件
    		 image.addClickListener(new MouseEventListenerAdapter(){
    			
    			public void click(MouseEvent e) {
    				 animate.play(null,false);
    			}
    		 });
    		 //将图像添加到TileGroup容器中
    		 tileGroup.addElement(image);
    	}
    	Application app=wm.create(Application.class);
		app.addChild(tileGroup);
	}

}
