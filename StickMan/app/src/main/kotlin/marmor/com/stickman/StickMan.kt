package marmor.com.stickman

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import java.util.*

public class StickMan(var surfaceHolder: SurfaceHolder) : View.OnTouchListener {
    private val SELECTION_DISTANCE: Float = 100f

    private val position = PointF(0f, 0f)

    public var scaleFactor: Float = 1f

    public lateinit var leftForeArm: Limb
    public lateinit var leftUpperArm: Limb

    public lateinit var rightForeArm: Limb
    public lateinit var rightUpperArm: Limb

    public lateinit var torso: Limb

    public lateinit var head: Limb

    public lateinit var leftUpperLeg: Limb
    public lateinit var leftLowerLeg: Limb

    public lateinit var rightUpperLeg: Limb
    public lateinit var rightLowerLeg: Limb

    public lateinit var limbs: Array<Limb>

    public var joints: ArrayList<Joint> = ArrayList()

    public val rightElbow: Joint
        get() = rightUpperArm.endJoint

    public val rightKnee: Joint
        get() = rightUpperLeg.endJoint

    public val leftElbow: Joint
        get() = leftUpperArm.endJoint

    public val leftKnee: Joint
        get() = leftUpperLeg.endJoint

    private var selectedJoint: Joint? = null

    public fun addJoints(vararg joints: Joint) {
        for (newJoint in joints)
            this.joints.add(newJoint)
    }

    public fun turnDirection(direction: Direction)
    {
        when(direction){
            Direction.FRONT -> {
                rightKnee.angleSwitcher = 1
                rightElbow.angleSwitcher = -1
                leftKnee.angleSwitcher = -1
                leftElbow.angleSwitcher = 1
            }
            Direction.LEFT -> {
                rightKnee.angleSwitcher = -1
                rightElbow.angleSwitcher = 1
                leftKnee.angleSwitcher = -1
                leftElbow.angleSwitcher = 1
            }
            Direction.RIGHT -> {
                rightKnee.angleSwitcher = 1
                rightElbow.angleSwitcher = -1
                leftKnee.angleSwitcher = 1
                leftElbow.angleSwitcher = -1
            }
        }
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
        val x = event.x
        val y = event.y

        when(event.action)
        {
            MotionEvent.ACTION_DOWN -> {
                var minDistance = Double.MAX_VALUE
                for (joint in joints) {
                    var distance = joint.distanceTo(x.toDouble(), y.toDouble())
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
        val canvas = this.surfaceHolder.lockCanvas()

        val line = Paint(Paint.ANTI_ALIAS_FLAG)
        line.color = Color.BLUE
        line.strokeWidth = 4f * scaleFactor

        canvas.drawColor(Color.WHITE)
        for (limb in limbs) {
            canvas.drawLine(limb.start.x, limb.start.y, limb.end.x, limb.end.y, line)
            Log.d("StickMan", limb.start.toString() + " " + limb.end.toString())
        }

        val dot = Paint(Paint.ANTI_ALIAS_FLAG)
        dot.color = Color.RED

        val selectedDot = Paint(Paint.ANTI_ALIAS_FLAG)
        selectedDot.color = Color.YELLOW

        for (joint in joints){
            canvas.drawCircle(joint.position.x, joint.position.y, 3f * scaleFactor, if(joint.equals(selectedJoint)) selectedDot else dot)
        }

        val neck = torso.end
        val forehead = head.end
        val radius = HEAD_RADIUS * scaleFactor
        val posHeadX = neck.x + (forehead.x - neck.x) / 2;
        val posHeadY = neck.y + (forehead.y - neck.y) / 2;

        canvas.drawCircle(posHeadX, posHeadY, radius, line)

        this.surfaceHolder.unlockCanvasAndPost(canvas)
    }

    private fun createLimbArray() {
        val newLimbs = arrayOf(torso, head, leftUpperArm, leftForeArm, leftUpperLeg, leftLowerLeg, rightUpperArm, rightForeArm, rightUpperLeg, rightLowerLeg)

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

    public enum class Direction {
        LEFT, RIGHT, FRONT
    }

    public fun playRecordings() {
        var player = Thread(Runnable() {
            run {
                var angles: LinkedList<RecordedAngle> = LinkedList()
                var angles2: LinkedList<RecordedAngle> = LinkedList()
                angles.addAll(rightUpperArm.recordedAngles)
                angles2.addAll(rightForeArm.recordedAngles)
                var time: Long
                var waitTime: Long = 16
                for (i: Int in 0..angles.size-1) {
                    time = System.currentTimeMillis() + waitTime
                    rightUpperArm.angle += angles[i].angle
                    rightForeArm.angle += angles2[i].angle
                    draw()
                    time = time - System.currentTimeMillis()
                    if (time < 0)
                        time = 0;
                    Thread.sleep(time);
                }
                rightUpperArm.recordedAngles.clear()
                rightForeArm.recordedAngles.clear()
            }
        })
        player.start()
    }
}
