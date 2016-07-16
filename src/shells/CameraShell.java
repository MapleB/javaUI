package shells;

import mx.core.UIComponent;

import org.eclipse.swt.widgets.Shell;

import flash.media.Camera;
import flash.media.Video;
//摄像头窗口
public class CameraShell extends IUIShell {
	int width=320,height=240;
	public CameraShell(Shell parent) {
		super(parent);
		this.setSize(400, 400);
	}

	@Override
	protected void loadComplete() {
		//调用flex创建UIComponent对象
        UIComponent parent=wm.create(UIComponent.class);
        //设置大小
        parent.setWidth(width);
        parent.setHeight(height);
        app.addChild(parent);
        //创建Video对象
        Video video=wm.create(Video.class);
        video.setWidth(width);
        video.setHeight(height);
        parent.addChild(video);
        //添加摄像头
        Camera camera=Camera.getCamera(wm, null);
        //camera.setMode(width,height,15,true);
       // camera.setQuality(144,85 ); 
        video.attachCamera(camera);
	}

}
