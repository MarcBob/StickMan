package marmor.com.stickman;

import android.graphics.PointF;

public class StickManFactory
{


    public static StickMan createStickMan()
    {
        StickMan stickMan = new StickMan();

        PointF neck = new PointF(0, 0);

        stickMan.torso = new Limb(neck, StickMan.LENGTH_TORSO, 180);

        stickMan.leftUpperArm = new Limb(neck, StickMan.LENGTH_UPPER_ARM, 210);
        stickMan.leftForeArm = new Limb(stickMan.leftUpperArm.end, StickMan.LENGTH_FORE_ARM, 195);
        stickMan.rightUpperArm = new Limb(neck, StickMan.LENGTH_UPPER_ARM, 150);
        stickMan.rightForeArm = new Limb(stickMan.rightUpperArm.end, StickMan.LENGTH_FORE_ARM, 175);

        stickMan.leftUpperLeg = new Limb(stickMan.torso.end, StickMan.LENGTH_UPPER_LEG, 200);
        stickMan.leftLowerLeg = new Limb(stickMan.leftUpperLeg.end, StickMan.LENGTH_LOWER_LEG, 185);
        stickMan.rightUpperLeg = new Limb(stickMan.torso.end, StickMan.LENGTH_UPPER_LEG, 160);
        stickMan.rightLowerLeg = new Limb(stickMan.rightUpperLeg.end, StickMan.LENGTH_LOWER_LEG, 175);

        return stickMan;
    }
}
