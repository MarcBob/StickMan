package marmor.com.stickman

import android.graphics.PointF

public object StickManFactory {

    public fun createStickMan(): StickMan {
        val stickMan = StickMan()

        val neck = PointF(0f, 0f)

        stickMan.torso = Limb(neck, StickMan.LENGTH_TORSO, 180f)

        stickMan.leftUpperArm = Limb(neck, StickMan.LENGTH_UPPER_ARM, 210f)
        stickMan.leftForeArm = Limb(stickMan.leftUpperArm.end, StickMan.LENGTH_FORE_ARM, 195f)
        stickMan.rightUpperArm = Limb(neck, StickMan.LENGTH_UPPER_ARM, 150f)
        stickMan.rightForeArm = Limb(stickMan.rightUpperArm.end, StickMan.LENGTH_FORE_ARM, 175f)

        stickMan.leftUpperLeg = Limb(stickMan.torso.end, StickMan.LENGTH_UPPER_LEG, 200f)
        stickMan.leftLowerLeg = Limb(stickMan.leftUpperLeg.end, StickMan.LENGTH_LOWER_LEG, 185f)
        stickMan.rightUpperLeg = Limb(stickMan.torso.end, StickMan.LENGTH_UPPER_LEG, 160f)
        stickMan.rightLowerLeg = Limb(stickMan.rightUpperLeg.end, StickMan.LENGTH_LOWER_LEG, 175f)

        return stickMan
    }
}
