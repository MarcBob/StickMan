package marmor.com.stickman

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import java.util.ArrayList
import kotlin.properties.Delegates

public class StickMan(var surfaceHolde: SurfaceHolder) : View.OnTouchListener {
    private val SELECTION_DISTANCE: Float = 100f

    private val position = PointF(0f, 0f)

    public var scaleFactor: Float = 1f

    public var leftForeArm: Limb by Delegates.notNull()
    public var leftUpperArm: Limb by Delegates.notNull()

    public var rightForeArm: Limb by Delegates.notNull()
    public var rightUpperArm: Limb by Delegates.notNull()

    public var torso: Limb by Delegates.notNull()

    public var leftUpperLeg: Limb by Delegates.notNull()
    public var leftLowerLeg: Limb by Delegates.notNull()

    public var rightUpperLeg: Limb by Delegates.notNull()
    public var rightLowerLeg: Limb by Delegates.notNull()

    public var limbs: Array<Limb> by Delegates.notNull()

    public var joints: ArrayList<Joint> = ArrayList()

    private var selectedJoint: Joint? = null

    public fun addJoints(vararg joint: Joint)
    {
        joints.addAll(joint)
    }

    public fun moveTo(x: Float, y: Float) {
        val deltaX = x - position.x
        val deltaY = y - position.y

        createLimbArray()

        for(joint in joints){
            joint.position.x += deltaX
            joint.position.y += deltaY
        }

    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {

        val x = event.getX()
        val y = event.getY()

        when(event.getAction())
        {
            MotionEvent.ACTION_DOWN -> {
                var minDistance = Float.MAX_VALUE
                for(joint in joints){
                    var distance = joint.distanceTo(x, y)
                    if(distance <= SELECTION_DISTANCE && minDistance > distance){
                        minDistance = distance
                        selectedJoint = joint
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                selectedJoint = null
            }

            MotionEvent.ACTION_MOVE -> {
                moveSelectedJointTo(x, y)
            }
        }

        draw()

        return true
    }

    private fun moveSelectedJointTo(x: Float, y: Float) {
        selectedJoint?.pull(PointF(x, y))
    }

    public fun draw() {
        val canvas = this.surfaceHolde.lockCanvas()

        val line = Paint(Paint.ANTI_ALIAS_FLAG)
        line.setColor(Color.BLUE)
        line.setStrokeWidth(4f * scaleFactor)

        canvas.drawColor(Color.WHITE)
        for (limb in limbs) {
            canvas.drawLine(limb.start.x, limb.start.y, limb.end.x, limb.end.y, line)
            Log.d("StickMan", limb.start.toString() + " " + limb.end.toString())
        }

        val dot = Paint(Paint.ANTI_ALIAS_FLAG)
        dot.setColor(Color.RED)

        val selectedDot = Paint(Paint.ANTI_ALIAS_FLAG)
        selectedDot.setColor(Color.YELLOW)

        for (joint in joints){
            canvas.drawCircle(joint.position.x, joint.position.y, 3f * scaleFactor, if(joint.equals(selectedJoint)) selectedDot else dot)
        }

        val neck = torso.end
        val radius = HEAD_RADIUS * scaleFactor
        canvas.drawCircle(neck.x, neck.y - radius, radius, line)

        this.surfaceHolde.unlockCanvasAndPost(canvas)
    }

    private fun createLimbArray() {
        val newLimbs = arrayOf(leftForeArm, leftUpperArm, leftUpperLeg, leftLowerLeg, torso, rightForeArm, rightUpperArm, rightUpperLeg, rightLowerLeg)

        limbs = newLimbs
    }

    companion object {
        public val HEAD_RADIUS: Float = 20f
        public val LENGTH_FORE_ARM: Float = 40f
        public val LENGTH_UPPER_ARM: Float = 50f
        public val LENGTH_UPPER_LEG: Float = 60f
        public val LENGTH_LOWER_LEG: Float = 50f
        public val LENGTH_TORSO: Float = 70f
    }
}
