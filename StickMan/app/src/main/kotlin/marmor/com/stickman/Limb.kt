package marmor.com.stickman

import android.graphics.PointF

public class Limb(start: PointF, length: Float, angle: Float) {
    public var start: PointF
    set(value){
        $start = value
        calculateEndPoint()
    }
    public var end: PointF
    public var angle: Double = 0.0
    public var length: Float = 0f
    set(value){
        this.length = value
        calculateEndPoint()
    }



    init {
        $start = PointF(start.x, start.y)
        this.end = PointF()
        this.angle = degreeToRadial(angle)
        this.length = length;

        calculateEndPoint()
    }

    public fun update(start: PointF?, length: Float?, angle: Float?) {
        if (start != null) {
            this.start = start
        }
        if (length != null) {
            this.length = length
        }
        if (angle != null) {
            this.angle = angle.toDouble()
        }
        calculateEndPoint()
    }

    public fun setAngle(angle: Float) {
        this.angle = degreeToRadial(angle)
        calculateEndPoint()
    }

//    public fun setLength(length: Float) {
//        this.length = length
//        calculateEndPoint()
//    }

    private fun degreeToRadial(angle: Float): Double {
        return (angle / 360) * fullCircle + Math.PI
    }


    private fun calculateEndPoint() {
        val diffX = Math.sin(this.angle).toFloat() * length
        val diffY = Math.cos(this.angle).toFloat() * length

        this.end = PointF(start.x + diffX, start.y + diffY)
    }

    companion object {
        private val fullCircle = -2 * Math.PI
    }
}
