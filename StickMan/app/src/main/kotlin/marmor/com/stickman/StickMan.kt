package marmor.com.stickman

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.SurfaceHolder
import kotlin.properties.Delegates

public class StickMan {

    private val position = PointF(0f, 0f)

    public var scaleFactor: Float = 1f

    public var leftForeArm: Limb by Delegates.notNull()
    public var leftUpperArm: Limb by Delegates.notNull()

    public var rightForeArm: Limb by Delegates.notNull()
    public var rightUpperArm: Limb by Delegates.notNull()

    public var torso: Limb by Delegates.notNull()

    public var leftUpperLeg: Limb by Delegates.notNull()
    public var leftLowerLeg: Limb by Delegates.notNull()

    public var rightUpperLeg: Limb by Delegates.notNull()
    public var rightLowerLeg: Limb by Delegates.notNull()

    public var limbs: Array<Limb> by Delegates.notNull()

    public fun moveTo(x: Float, y: Float) {
        val deltaX = x - position.x
        val deltaY = y - position.y

        createLimbArray()

        for (limb in limbs) {
            limb.start.x += deltaX
            limb.start.y += deltaY
            limb.end.x += deltaX
            limb.end.y += deltaY
        }
    }

    public fun draw(surfaceHolder: SurfaceHolder) {
        val canvas = surfaceHolder.lockCanvas()

        val line = Paint(Paint.ANTI_ALIAS_FLAG)
        line.setColor(Color.BLACK)
        line.setStrokeWidth(4f)

        canvas.drawColor(Color.WHITE)
        for (limb in limbs) {
            canvas.drawLine(limb.start.x, limb.start.y, limb.end.x, limb.end.y, line)
        }

        val neck = torso.start
        val radius = 20f
        canvas.drawCircle(neck.x, neck.y - radius, radius, line)

        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    private fun createLimbArray() {
        val newLimbs = arrayOf(leftForeArm, leftUpperArm, leftUpperLeg, leftLowerLeg, torso, rightForeArm, rightUpperArm, rightUpperLeg, rightLowerLeg)

        limbs = newLimbs
    }

    companion object {
        public val HEAD_RADIUS: Float = 20f
        public val LENGTH_FORE_ARM: Float = 40f
        public val LENGTH_UPPER_ARM: Float = 50f
        public val LENGTH_UPPER_LEG: Float = 60f
        public val LENGTH_LOWER_LEG: Float = 50f
        public val LENGTH_TORSO: Float = 70f
    }
}
