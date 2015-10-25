package marmor.com.stickman

import android.graphics.PointF
import android.util.Log

import java.util.ArrayList

public class Joint(var position: PointF, var name: String, var canPropagate: Boolean) {
    private val MOVEMENT_DEPTH = 2

    private var incomingLimbs: MutableList<Limb> = ArrayList()
    private var outgoingLimbs: MutableList<Limb> = ArrayList()

    public var angleSwitcher: Int = 1

    public fun switchAngleDirection(){
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
            //FIXME this should work for more than 2 later
            val firstLimb = limbs[0]
            if(canPropagate && incomingLimbs.size > 0) {
                val secondLimb = incomingLimbs[0]
                reachDestination(destination, firstLimb, secondLimb)
            }
            else
                reachDestination(destination, firstLimb);
        }
    }

    private fun reachDestination(destination: PointF, limb: Limb){
        val angle = (Math.PI / 2) - Math.atan2((destination.y - position.y).toDouble(), (destination.x - position.x).toDouble())
        propagateAngleToOutgoingLimbs(angle, limb)
        limb.angle = angle
    }

    private fun reachDestination(destination: PointF, incomingLimb: Limb, predecessorLimb: Limb) {
        Log.d("Destination", "destination: " + destination.toString())

        var limbLength = incomingLimb.length.toDouble()
        var predLimbLength = predecessorLimb.length.toDouble()
        var distance = predecessorLimb.startJoint.distanceTo(destination.x.toDouble(), destination.y.toDouble())

        if(distance > limbLength + predLimbLength)
            distance = limbLength + predLimbLength

        val angleCB = angleSwitcher * Math.acos(Math.min(1.0,(((predLimbLength * predLimbLength) + (distance * distance) - (limbLength * limbLength)) / (2 * predLimbLength * distance))))
        val angleAB = angleSwitcher * Math.acos(Math.min(1.0,(((predLimbLength * predLimbLength) + (limbLength * limbLength) - (distance * distance)) / (2 * predLimbLength * limbLength))))
        val angleAC = angleSwitcher * Math.PI - angleAB - angleCB

        val deltaX = destination.x - predecessorLimb.startJoint.position.x
        val deltaY = destination.y - predecessorLimb.startJoint.position.y

        val z = (Math.PI / 2) - Math.atan2(deltaY.toDouble(), deltaX.toDouble())

        val angleCBFinal = angleCB + z
        val y = z - angleAC

        val angleACFinal = y

        propagateAngleToOutgoingLimbs(angleACFinal, incomingLimb)
        propagateAngleToOutgoingLimbs(angleCBFinal, predecessorLimb, incomingLimb)

        incomingLimb.angle = angleACFinal
        predecessorLimb.angle = angleCBFinal
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
