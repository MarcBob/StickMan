package marmor.com.stickman

import android.graphics.PointF
import android.view.SurfaceHolder

public object StickManFactory {

    public fun createStickMan(scaleFactor : Float, surfaceHolder : SurfaceHolder): StickMan {
        val stickMan = StickMan(surfaceHolder)

        stickMan.scaleFactor = scaleFactor

        var hip = Joint(PointF(0f, 0f), "hip", false)
        var neck = buildTorso(hip, scaleFactor, stickMan)

        buildHead(neck, scaleFactor, stickMan);

        buildLeftArm(neck, scaleFactor, stickMan)

        buildRightArm(neck, scaleFactor, stickMan)

        buildLeftLeg(hip, scaleFactor, stickMan)

        buildRightLeg(hip, scaleFactor, stickMan)

        return stickMan
    }

    private fun buildTorso(hip: Joint, scaleFactor: Float, stickMan: StickMan): Joint {
        stickMan.torso = Limb(hip.position, StickMan.LENGTH_TORSO * scaleFactor, 0f, "torso")
        hip.registerOutgoingLimb(stickMan.torso)

        var neck = Joint(stickMan.torso.end, "neck", false)
        neck.registerIncomingLimb(stickMan.torso)

        stickMan.addJoints(hip, neck)
        return neck
    }

    private fun buildHead(neck: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.head = Limb(neck.position, StickMan.HEAD_RADIUS * 2.0f * scaleFactor, 0f, "head")
        neck.registerOutgoingLimb(stickMan.head)

        var forehead = Joint(stickMan.head.end, "forehead", false)
        forehead.registerIncomingLimb(stickMan.head)

        stickMan.addJoints(forehead)
    }

    private fun buildRightLeg(hip: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.rightUpperLeg = Limb(hip.position, StickMan.LENGTH_UPPER_LEG * scaleFactor, 160f, "rightUpperLeg")
        hip.registerOutgoingLimb(stickMan.rightUpperLeg)

        var rightKnee = Joint(stickMan.rightUpperLeg.end, "rightKnee", true)
        rightKnee.registerIncomingLimb(stickMan.rightUpperLeg)

        stickMan.rightLowerLeg = Limb(rightKnee.position, StickMan.LENGTH_LOWER_LEG * scaleFactor, 175f, "rightLowerLeg")
        rightKnee.registerOutgoingLimb(stickMan.rightLowerLeg)

        var rightFoot = Joint(stickMan.rightLowerLeg.end, "rightFoot", false)
        rightFoot.registerIncomingLimb(stickMan.rightLowerLeg)

        stickMan.addJoints(rightKnee, rightFoot)
    }

    private fun buildLeftLeg(hip: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.leftUpperLeg = Limb(hip.position, StickMan.LENGTH_UPPER_LEG * scaleFactor, 200f, "leftUpperLeg")
        hip.registerOutgoingLimb(stickMan.leftUpperLeg)

        var leftKnee = Joint(stickMan.leftUpperLeg.end, "leftKnee", true)
        leftKnee.registerIncomingLimb(stickMan.leftUpperLeg)

        stickMan.leftLowerLeg = Limb(leftKnee.position, StickMan.LENGTH_LOWER_LEG * scaleFactor, 185f, "leftLowerLeg")
        leftKnee.registerOutgoingLimb(stickMan.leftLowerLeg)

        var leftFoot = Joint(stickMan.leftLowerLeg.end, "leftFoot", false)
        leftFoot.registerIncomingLimb(stickMan.leftLowerLeg)

        stickMan.addJoints(leftKnee, leftFoot)

        leftKnee.switchAngleDirection()
    }

    private fun buildRightArm(neck: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.rightUpperArm = Limb(neck.position, StickMan.LENGTH_UPPER_ARM * scaleFactor, 150f, "rightUpperArm")
        neck.registerOutgoingLimb(stickMan.rightUpperArm)

        var rightElbow = Joint(stickMan.rightUpperArm.end, "rightElbow", true)
        rightElbow.registerIncomingLimb(stickMan.rightUpperArm)

        stickMan.rightForeArm = Limb(rightElbow.position, StickMan.LENGTH_FORE_ARM * scaleFactor, 165f, "rightForeArm")
        rightElbow.registerOutgoingLimb(stickMan.rightForeArm)

        var rightHand = Joint(stickMan.rightForeArm.end, "rightHand", false)
        rightHand.registerIncomingLimb(stickMan.rightForeArm)

        stickMan.addJoints(rightElbow, rightHand)

        rightElbow.switchAngleDirection()
    }

    private fun buildLeftArm(neck: Joint, scaleFactor: Float, stickMan: StickMan) {
        stickMan.leftUpperArm = Limb(neck.position, StickMan.LENGTH_UPPER_ARM * scaleFactor, 210f, "leftUpperArm")
        neck.registerOutgoingLimb(stickMan.leftUpperArm)

        var leftElbow = Joint(stickMan.leftUpperArm.end, "leftElbow", true)
        leftElbow.registerIncomingLimb(stickMan.leftUpperArm)

        stickMan.leftForeArm = Limb(leftElbow.position, StickMan.LENGTH_FORE_ARM * scaleFactor, 195f, "leftForeArm")
        leftElbow.registerOutgoingLimb(stickMan.leftForeArm)

        var leftHand = Joint(stickMan.leftForeArm.end, "leftHand", false)
        leftHand.registerIncomingLimb(stickMan.leftForeArm)

        stickMan.addJoints(leftElbow, leftHand)
    }
}
