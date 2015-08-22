package marmor.com.stickman;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class Joint
{
    PointF position = new PointF();
    List<Limb> limbs = new ArrayList<>();

    public void registerLimbs(Limb limb)
    {
        this.limbs.add(limb);
    }
}
