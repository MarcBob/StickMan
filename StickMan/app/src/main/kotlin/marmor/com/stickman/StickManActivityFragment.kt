package marmor.com.stickman

import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener

import butterknife.Bind
import butterknife.ButterKnife

/**
 * A placeholder fragment containing a simple view.
 */
public class StickManActivityFragment : Fragment() {
    @Bind(R.id.surface)
    protected var surfaceView: SurfaceView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stick_man, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)
    }

    override fun onResume() {
        super.onResume()


        surfaceView?.getViewTreeObserver()?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                var surfaceHolder = surfaceView?.getHolder()

                surfaceHolder?.addCallback(object : Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {


                        val stickMan = StickManFactory.createStickMan()

                        stickMan.moveTo(500f, 500f)

                        if(surfaceHolder != null)
                            stickMan.draw(surfaceHolder!!)
                    }

                    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                    }
                })
            }
        })
    }
}
