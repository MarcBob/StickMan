package marmor.com.stickman;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.SurfaceHolder;

public class StickMan
{
    public static final float HEAD_RADIUS = 20;
    public static final float LENGTH_FORE_ARM = 40;
    public static final float LENGTH_UPPER_ARM = 50;
    public static final float LENGTH_UPPER_LEG = 60;
    public static final float LENGTH_LOWER_LEG = 50;
    public static final float LENGTH_TORSO = 70;

    private PointF position = new PointF(0, 0);

    public float scaleFactor = 1;

    public Limb leftForeArm;
    public Limb leftUpperArm;

    public Limb rightForeArm;
    public Limb rightUpperArm;

    public Limb torso;

    public Limb leftUpperLeg;
    public Limb leftLowerLeg;

    public Limb rightUpperLeg;
    public Limb rightLowerLeg;

    public Limb[] limbs;

    public void moveTo(float x, float y)
    {
        float deltaX = x - position.x;
        float deltaY = y - position.y;

        createLimbArray();

        for(Limb limb : limbs)
        {
            limb.start.x += deltaX;
            limb.start.y += deltaY;
            limb.end.x += deltaX;
            limb.end.y += deltaY;
        }
    }

    public void draw(SurfaceHolder surfaceHolder)
    {
        Canvas canvas = surfaceHolder.lockCanvas();

        Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
        line.setColor(Color.BLACK);
        line.setStrokeWidth(4);

        canvas.drawColor(Color.WHITE);
        for (Limb limb : limbs)
        {
            canvas.drawLine(limb.start.x, limb.start.y, limb.end.x, limb.end.y, line);
        }

        PointF neck = torso.start;
        float radius = 20;
        canvas.drawCircle(neck.x, neck.y - radius, radius, line);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    private void createLimbArray()
    {
        Limb[] newLimbs = {leftForeArm, leftUpperArm, leftUpperLeg, leftLowerLeg,
            torso, rightForeArm, rightUpperArm, rightUpperLeg, rightLowerLeg};

        limbs = newLimbs;
    }
}
