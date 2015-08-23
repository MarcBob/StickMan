package marmor.com.stickman

import android.graphics.PointF

import java.util.ArrayList

public class Joint {
    var position = PointF()
    var limbs: MutableList<Limb> = ArrayList()

    public fun registerLimbs(limb: Limb) {
        this.limbs.add(limb)
    }
}
