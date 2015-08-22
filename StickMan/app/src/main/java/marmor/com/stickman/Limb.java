package marmor.com.stickman;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Limb
{
    private final static double fullCircle = -2 * Math.PI;
    public PointF start;
    public PointF end;
    public float length;
    public double angle;


    public Limb(PointF start, float length, float angle) {
        this.start = new PointF(start.x, start.y);
        this.length = length;

        this.angle = degreeToRadial(angle);

        calculateEndPoint();
    }

    public void update(@Nullable PointF start, @Nullable Float length, @Nullable Float angle)
    {
        if(start != null)
        {
            this.start = start;
        }
        if(length != null)
        {
            this.length = length;
        }
        if(angle != null)
        {
            this.angle = angle;
        }
        calculateEndPoint();
    }

    public void setStart(@NonNull PointF start)
    {
        this.start = start;
        calculateEndPoint();
    }

    public void setAngle(float angle)
    {
        this.angle = degreeToRadial(angle);
        calculateEndPoint();
    }

    public void setLength(float length)
    {
        this.length = length;
        calculateEndPoint();
    }

    private double degreeToRadial(float angle)
    {
        return (angle / 360) * fullCircle + Math.PI;
    }


    private void calculateEndPoint()
    {
        float diffX = (float) Math.sin(this.angle) * length;
        float diffY = (float) Math.cos(this.angle) * length;

        this.end = new PointF(start.x + diffX, start.y + diffY);
    }
}
