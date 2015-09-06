package marmor.com.stickman

import android.graphics.PointF
import android.util.Log

import java.util.ArrayList

public class Joint(var position: PointF, var name: String) {
    private val MOVEMENT_DEPTH = 2

    private var incomingLimbs: MutableList<Limb> = ArrayList()
    private var outgoingLimbs: MutableList<Limb> = ArrayList()

    public var angleSwitcher: Int = 1

    public fun switchAngleDirction(){
        angleSwitcher *= -1
    }

    public fun registerIncomingLimb(limb: Limb) {
        this.incomingLimbs.add(limb)
        limb.endJoint = this
    }

    public fun registerOutgoingLimb(limb: Limb) {
        this.outgoingLimbs.add(limb)
        limb.startJoint = this
    }

    public fun pull(destination: PointF, limbs: MutableList<Limb> = ArrayList()){
        if(limbs.size() < MOVEMENT_DEPTH - 1) {
            for (limb in incomingLimbs) {
                limb.pull(destination, limbs)
            }
        }
        else{
            //FIXME this should work for more then 2 later
            val firstLimb = limbs.get(0)
            if(incomingLimbs.size() > 0) {
                val secondLimb = incomingLimbs.get(0)
                reachDestination(destination, firstLimb, secondLimb)
            }


        }
    }

    private fun reachDestination(destination: PointF, firstLimb: Limb, secondLimb: Limb) {
        Log.d("Destination", "destination: " + destination.toString())

        var a = firstLimb.length
        var b = secondLimb.length
        var c = secondLimb.startJoint.distanceTo(destination.x, destination.y)

        if(c > a + b)
        {
            c = a + b
        }

        val angleCB = angleSwitcher * Math.acos((((b * b) + (c * c) - (a * a)) / (2 * b * c)).toDouble())
        val angleAB = angleSwitcher * Math.acos((((b * b) + (a * a) - (c * c)) / (2 * b * a)).toDouble())
        val angleAC = angleSwitcher * Math.PI - angleAB - angleCB


        val deltaX = destination.x - secondLimb.startJoint.position.x
        val deltaY = destination.y - secondLimb.startJoint.position.y

        val z = (Math.PI / 2) - Math.atan2(deltaY.toDouble(), deltaX.toDouble())

        val angleCBFinal = angleCB + z
        val y = z - angleAC

        val angleACFinal = y

        secondLimb.angle = angleCBFinal
        firstLimb.angle = angleACFinal
    }

    public fun distanceTo(x: Float, y: Float): Float {
        var deltaX = this.position.x - x
        var deltaY = this.position.y - y
        return Math.sqrt(Math.pow(deltaX.toDouble(), 2.0) + Math.pow(deltaY.toDouble(), 2.0)).toFloat()
    }
}
