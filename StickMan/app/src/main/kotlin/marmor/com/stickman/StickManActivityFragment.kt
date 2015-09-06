package marmor.com.stickman

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.view.SurfaceHolder.Callback
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import butterknife.Bind
import butterknife.ButterKnife
import kotlin.properties.Delegates

/**
 * A placeholder fragment containing a simple view.
 */
public class StickManActivityFragment : Fragment() {
//    @Bind(R.id.surface)
    protected var surfaceView: SurfaceView by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stick_man, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)
        surfaceView = view?.findViewById(R.id.surface) as SurfaceView
    }

    override fun onResume() {
        super.onResume()


        surfaceView?.getViewTreeObserver()?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                var surfaceHolder = surfaceView?.getHolder()

                surfaceHolder?.addCallback(object : Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {


                        val stickMan = StickManFactory.createStickMan(5f, surfaceHolder)

                        stickMan.moveTo(surfaceView.getWidth()/2.0f, surfaceView.getHeight()/2.0f)

                        if(surfaceHolder != null)
                            stickMan.draw()

                        surfaceView.setOnTouchListener(stickMan)
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
