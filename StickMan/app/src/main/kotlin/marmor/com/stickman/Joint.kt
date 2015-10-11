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
            else{
                reachDestination(destination, firstLimb);
            }


        }
    }

    private fun reachDestination(destination: PointF, limb: Limb){
        val angle = (Math.PI / 2) - Math.atan2((destination.y - position.y).toDouble(), (destination.x - position.x).toDouble())
        propagateAngleToOutgoingLimbs(angle, limb)
        limb.angle = angle
    }


    private fun reachDestination(destination: PointF, firsIncomingtLimb: Limb, secondIncomingLimb: Limb) {
        Log.d("Destination", "destination: " + destination.toString())

        var a = firsIncomingtLimb.length.toDouble()
        var b = secondIncomingLimb.length.toDouble()
        var c = secondIncomingLimb.startJoint.distanceTo(destination.x.toDouble(), destination.y.toDouble())

        if(c > a + b)
        {
            c = a + b
        }

        Log.d("Joint: ", "CB: " + (((b * b) + (c * c) - (a * a)) / (2 * b * c)))
        Log.d("Joint: ", "AB: " + (((b * b) + (a * a) - (c * c)) / (2 * b * a)))
        val angleCB = angleSwitcher * Math.acos(Math.min(1.0,(((b * b) + (c * c) - (a * a)) / (2 * b * c))))
        val angleAB = angleSwitcher * Math.acos(Math.min(1.0,(((b * b) + (a * a) - (c * c)) / (2 * b * a))))
        val angleAC = angleSwitcher * Math.PI - angleAB - angleCB


        val deltaX = destination.x - secondIncomingLimb.startJoint.position.x
        val deltaY = destination.y - secondIncomingLimb.startJoint.position.y

        Log.d("Destination", "deltaX: " + deltaX)
        Log.d("Destination", "deltaY: " + deltaY)

        val z = (Math.PI / 2) - Math.atan2(deltaY.toDouble(), deltaX.toDouble())

        val angleCBFinal = angleCB + z
        val y = z - angleAC

        val angleACFinal = y

        propagateAngleToOutgoingLimbs(angleACFinal, firsIncomingtLimb)
        propagateAngleToOutgoingLimbs(angleCBFinal, secondIncomingLimb, firsIncomingtLimb)

        firsIncomingtLimb.angle = angleACFinal
        secondIncomingLimb.angle = angleCBFinal
    }

    private fun propagateAngleToOutgoingLimbs(angle: Double, limb: Limb, exceptionLimb: Limb? = null) {
        val angleDiff = limb.angle - angle

        for (limb: Limb in limb.endJoint.outgoingLimbs) {
            if(limb != exceptionLimb) {
                limb.angle -= angleDiff
                for (nextLimb: Limb in limb.endJoint.outgoingLimbs) {
                    nextLimb.angle -= angleDiff
                }
            }
        }
    }

    public fun distanceTo(x: Double, y: Double): Double {
        var deltaX = this.position.x - x
        var deltaY = this.position.y - y
        return Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0))
    }
}
