package com.example.week11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int LINE = 1, CIRCLE = 2, RECT=3;
    static int curShape = LINE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)  {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.itemline:
                curShape = LINE; // 선
                return true;
            case R.id.itemcircle:
                curShape = CIRCLE; // 원
                return true;
            case R.id.itemrect:
                curShape = RECT;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static class MyGraphicView extends View {
        ArrayList<KeepShape> myShapeList = new ArrayList<>();
        KeepShape current;
        int startX = -1, startY = -1, stopX = -1, stopY = -1;

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("hwang", "move:x=" + event.getX() + "  y=" + event.getY()+ "  RawX="+event.getRawX()+" RawY="+event.getRawY());
                    break;
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    this.invalidate();
                    break;
            }
            return true;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);

            current = new KeepShape(curShape, startX, startY, stopX, stopY, paint);
            myShapeList.add(current);

            for (KeepShape cshape: myShapeList)
                draw_shape(cshape, canvas);

            if (current != null)
                draw_shape(current, canvas);

        }

        public void draw_shape(KeepShape keepShape, Canvas canvas) {

            switch (keepShape.shape_type) {
                case LINE:
                    canvas.drawLine(keepShape.startX, keepShape.startY, keepShape.stopX, keepShape.stopY, keepShape.paint);
                    break;
                case CIRCLE:
                    int radius = (int) Math.sqrt(Math.pow(keepShape.stopX - keepShape.startX, 2)
                            + Math.pow(keepShape.stopY - keepShape.startY, 2));
                    canvas.drawCircle(keepShape.startX, keepShape.startY, radius, keepShape.paint);
                    break;

                case RECT:
                    Rect rect = new Rect(keepShape.startX, keepShape.startY, keepShape.stopX, keepShape.stopY);
                    canvas.drawRect(rect,keepShape.paint);
                    break;
            }
        }

        private static class KeepShape{
            Paint paint;
            int shape_type, startX, startY, stopX, stopY;

            public KeepShape(int shape_type, int startX, int startY, int stopX, int stopY, Paint paint){
                this.shape_type = shape_type;
                this.startX = startX;
                this.startY = startY;
                this.stopX = stopX;
                this.stopY = stopY;
                this.paint = paint;
                paint.setColor(Color.RED);
            }
        }
    }
}