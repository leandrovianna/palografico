package com.leandro.palografico.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

public class CameraBorderView extends TextureView implements TextureView.SurfaceTextureListener {

    public CameraBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setSurfaceTextureListener(this);
        this.setAlpha(1.0f);
    }

    private void drawBorder(Canvas canvas) {
        Paint strokePaint = new Paint();
        strokePaint.setARGB(100, 0, 0, 0);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(canvas.getWidth()*0.2f);

        Rect r = canvas.getClipBounds();
        Rect outline = new Rect(1, 1, r.right-1, r.bottom-1);
        canvas.drawRect(outline, strokePaint);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Canvas canvas = lockCanvas();
        drawBorder(canvas);
        unlockCanvasAndPost(canvas);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
}
