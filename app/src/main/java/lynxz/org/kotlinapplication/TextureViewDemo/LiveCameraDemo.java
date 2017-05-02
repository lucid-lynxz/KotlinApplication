package lynxz.org.kotlinapplication.TextureViewDemo;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.TextureView;

import java.io.IOException;

import lynxz.org.kotlinapplication.activity.BaseActivity;

/**
 * Created by lynxz on 2017/1/10.
 * 测试textureView播放视频功能
 */
public class LiveCameraDemo extends BaseActivity implements TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private TextureView mTextureView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_textureview);
        mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        setContentView(mTextureView);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        mCamera = Camera.open();
        try {
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }
}
