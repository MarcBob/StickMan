package marmor.com.stickman

import android.graphics.PointF
import android.util.Log

import java.util.*

public class Limb(start: PointF, length: Float, angle: Float, var name: String) {
    private val FULL_CIRCLE = -2 * Math.PI

    public val recordedAngles: LinkedList<RecordedAngle> = LinkedList()

    private lateinit var _start: PointF
    public var start: PointF
        get() = _start
        set(value){
            _start = value
            calculateEndPoint()
        }

    private lateinit var _end: PointF
    public val end: PointF
        get() = _end

    private var _angle: Double = 0.0
    public  var angle: Double
        set(value){
            recordAngles(value)
            _angle = value
            calculateEndPoint()
        }
        get() = _angle

    private var _length: Float = 0f
    public var length: Float
        set(value){
            _length = value
            calculateEndPoint()
        }
        get() = _length

    public lateinit var startJoint: Joint
    public lateinit var endJoint: Joint

    init {
        _start = start
        _end = PointF()
        _angle = degreeToRadial(angle)
        _length = length;

        calculateEndPoint()
    }

    public fun update(start: PointF?, length: Float?, angle: Float?) {
        if (start != null) {
            _start = start
        }
        if (length != null) {
            _length = length
        }
        if (angle != null) {
            _angle = angle.toDouble()
        }
        calculateEndPoint()
    }

    private fun recordAngles(newAngle: Double) {
        var difference = newAngle - _angle
        recordedAngles.add(RecordedAngle(difference, System.currentTimeMillis()))
        Log.d("lalaland", "# Angles: " + recordedAngles.size + "   Timestamp: " + System.currentTimeMillis())
    }

    public fun setAngleInDegree(angle: Float) {
        _angle = degreeToRadial(angle)
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
        Log.d("Limb calcEndPoint: ", "angle: " + angle)

        val diffX = Math.sin(this.angle).toFloat() * length
        val diffY = Math.cos(this.angle).toFloat() * length

        _end.x = start.x + diffX
        _end.y = start.y + diffY
    }
}
