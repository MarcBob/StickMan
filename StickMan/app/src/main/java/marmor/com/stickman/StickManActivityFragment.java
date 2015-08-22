package marmor.com.stickman;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class StickManActivityFragment extends Fragment
{
    @Bind(R.id.surface)
    protected SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;

    public StickManActivityFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_stick_man, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume()
    {
        super.onResume();


        surfaceView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                surfaceHolder = surfaceView.getHolder();
                surfaceHolder.addCallback(new Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder holder)
                    {


                        StickMan stickMan = StickManFactory.createStickMan();

                        stickMan.moveTo(500, 500);

                        stickMan.draw(surfaceHolder);
                    }
                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
                    {

                    }
                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder)
                    {

                    }
                });
            }
        });
    }
}
