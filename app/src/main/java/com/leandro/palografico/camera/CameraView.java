package com.leandro.palografico.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private MyCamera camera;

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.holder = getHolder();
        this.holder.addCallback(this);
    }

    public CameraView(Context context, MyCamera camera)
    {
        super(context);

        if (isInEditMode())
            return ;

        this.holder = getHolder();
        this.holder.addCallback(this);

        this.camera = camera;
    }

    public void setCamera(MyCamera camera) {
        this.camera = camera;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera.startPreview(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (this.holder.getSurface() != null)
            camera.changePreview(holder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
}
