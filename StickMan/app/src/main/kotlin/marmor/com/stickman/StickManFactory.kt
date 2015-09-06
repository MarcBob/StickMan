package marmor.com.stickman

import android.graphics.PointF
import android.view.SurfaceHolder

public object StickManFactory {

    public fun createStickMan(scaleFactor : Float, surfaceHolder : SurfaceHolder): StickMan {
        val stickMan = StickMan(surfaceHolder)

        stickMan.scaleFactor = scaleFactor

        var hip = Joint(PointF(0f, 0f), "hip")
        var neck = buildTorso(hip, scaleFactor, stickMan)

        buildLeftArm(neck, scaleFactor, stickMan)

        buildRightArm(neck, scaleFactor, stickMan)

        buildLeftLeg(hip, scaleFactor, stickMan)

        buildRightLeg(hip, scaleFactor, stickMan)

        return stickMan
    }

    private fun buildRightLeg(hip: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.rightUpperLeg = Limb(hip.position, StickMan.LENGTH_UPPER_LEG * scaleFactor, 160f, "rightUpperLeg")
        hip.registerOutgoingLimb(stickMan.rightUpperLeg)

        var rightKnee = Joint(stickMan.rightUpperLeg.end, "rightKnee")
        rightKnee.registerIncomingLimb(stickMan.rightUpperLeg)

        stickMan.rightLowerLeg = Limb(rightKnee.position, StickMan.LENGTH_LOWER_LEG * scaleFactor, 175f, "rightLowerLeg")
        rightKnee.registerOutgoingLimb(stickMan.rightLowerLeg)

        var rightFoot = Joint(stickMan.rightLowerLeg.end, "rightFoot")
        rightFoot.registerIncomingLimb(stickMan.rightLowerLeg)

        stickMan.addJoints(rightKnee, rightFoot)
    }

    private fun buildLeftLeg(hip: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.leftUpperLeg = Limb(hip.position, StickMan.LENGTH_UPPER_LEG * scaleFactor, 200f, "leftUpperLeg")
        hip.registerOutgoingLimb(stickMan.leftUpperLeg)

        var leftKnee = Joint(stickMan.leftUpperLeg.end, "leftKnee")
        leftKnee.registerIncomingLimb(stickMan.leftUpperLeg)

        stickMan.leftLowerLeg = Limb(leftKnee.position, StickMan.LENGTH_LOWER_LEG * scaleFactor, 185f, "leftLowerLeg")
        leftKnee.registerOutgoingLimb(stickMan.leftLowerLeg)

        var leftFoot = Joint(stickMan.leftLowerLeg.end, "leftFoot")
        leftFoot.registerIncomingLimb(stickMan.leftLowerLeg)

        stickMan.addJoints(leftKnee, leftFoot)

        leftKnee.switchAngleDirction()
    }

    private fun buildTorso(hip: Joint, scaleFactor: Float, stickMan: StickMan): Joint {
        stickMan.torso = Limb(hip.position, StickMan.LENGTH_TORSO * scaleFactor, 0f, "torso")
        hip.registerOutgoingLimb(stickMan.torso)

        var neck = Joint(stickMan.torso.end, "neck")
        neck.registerIncomingLimb(stickMan.torso)

        stickMan.addJoints(hip, neck)
        return neck
    }

    private fun buildRightArm(neck: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.rightUpperArm = Limb(neck.position, StickMan.LENGTH_UPPER_ARM * scaleFactor, 150f, "rightUpperArm")
        neck.registerOutgoingLimb(stickMan.rightUpperArm)

        var rightElbow = Joint(stickMan.rightUpperArm.end, "rightElbow")
        rightElbow.registerIncomingLimb(stickMan.rightUpperArm)

        stickMan.rightForeArm = Limb(rightElbow.position, StickMan.LENGTH_FORE_ARM * scaleFactor, 165f, "rightForeArm")
        rightElbow.registerOutgoingLimb(stickMan.rightForeArm)

        var rightHand = Joint(stickMan.rightForeArm.end, "rightHand")
        rightHand.registerIncomingLimb(stickMan.rightForeArm)

        stickMan.addJoints(rightElbow, rightHand)

        rightElbow.switchAngleDirction()
    }

    private fun buildLeftArm(neck: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.leftUpperArm = Limb(neck.position, StickMan.LENGTH_UPPER_ARM * scaleFactor, 210f, "leftUpperArm")
        neck.registerOutgoingLimb(stickMan.leftUpperArm)

        var leftElbow = Joint(stickMan.leftUpperArm.end, "leftElbow")
        leftElbow.registerIncomingLimb(stickMan.leftUpperArm)

        stickMan.leftForeArm = Limb(leftElbow.position, StickMan.LENGTH_FORE_ARM * scaleFactor, 195f, "leftForeArm")
        leftElbow.registerOutgoingLimb(stickMan.leftForeArm)

        var leftHand = Joint(stickMan.leftForeArm.end, "leftHand")
        leftHand.registerIncomingLimb(stickMan.leftForeArm)

        stickMan.addJoints(leftElbow, leftHand)
    }
}
