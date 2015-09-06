package marmor.com.stickman

import android.graphics.PointF
import java.util.*
import kotlin.properties.Delegates

public class Limb(start: PointF, length: Float, angle: Float, var name: String) {
    private val FULL_CIRCLE = -2 * Math.PI

    public var start: PointF
    set(value){
        $start = value
        calculateEndPoint()
    }

    public var end: PointF
    private set

    public  var angle: Double = 0.0
    set(value){
        $angle = value
        calculateEndPoint()
    }

    public var length: Float = 0f
    set(value){
        $length = value
        calculateEndPoint()
    }

    public var startJoint: Joint by Delegates.notNull()
    public var endJoint: Joint by Delegates.notNull()

    init {
        $start = start
        $end = PointF()
        $angle = degreeToRadial(angle)
        $length = length;

        calculateEndPoint()
    }

    public fun update(start: PointF?, length: Float?, angle: Float?) {
        if (start != null) {
            $start = start
        }
        if (length != null) {
            $length = length
        }
        if (angle != null) {
            $angle = angle.toDouble()
        }
        calculateEndPoint()
    }

    public fun setAngleInDegree(angle: Float) {
        $angle = degreeToRadial(angle)
        calculateEndPoint()
    }

    public fun pull(destination: PointF, limbs: MutableList<Limb>)
    {
        limbs.add(this)
        startJoint.pull(destination, limbs)
    }

    private fun degreeToRadial(angle: Float): Double {
        return (angle / 360) * FULL_CIRCLE + Math.PI
    }


    private fun calculateEndPoint() {
        val diffX = Math.sin(this.angle).toFloat() * length
        val diffY = Math.cos(this.angle).toFloat() * length

        $end.x = start.x + diffX
        $end.y = start.y + diffY
    }
}
