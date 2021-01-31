package com.app.targauto;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class PieChartView extends View {

    private float[] valuesDegree;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int[] colors = {Color.rgb(109, 34, 34), Color.rgb(71, 71, 71)};
    private RectF rectF = new RectF(10, 10, 700, 700);
    private int temp = 0;

    public PieChartView(Context context, float[] values) {
        super(context);
        this.valuesDegree = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            this.valuesDegree[i] = values[i];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < valuesDegree.length; i++) {
            paint.setColor(colors[i]);
            if (i == 0) {
                canvas.drawArc(rectF, 0, valuesDegree[i], true, paint);
            } else {
                temp += (int) valuesDegree[i - 1];
                canvas.drawArc(rectF, temp, valuesDegree[i], true, paint);
            }
        }
    }
}