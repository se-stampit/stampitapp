package com.jku.stampit.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jku.stampit.R;

/**
 * Created by user on 25/05/16.
 */
public class StampView extends View {

    private int circleCol = Color.BLACK;
    private int circleCount = 10;
    private int filledCircleCount = 0;
    private int circleBorderCol = Color.BLACK;
    private Paint circlePaint;
    private Paint filledCirclePaint;
    private int defaultCircleDiameter = 80;
    private final int MAX_CIRCLE_ROW_COUNT = 5;
    public int getCircleBorderCol(){
        return circleBorderCol;
    }

    public void setCircleBorderColCol(int newColor){
        //update the instance variable
        circleBorderCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public int getCircleCol(){
        return circleCol;
    }

    public void setCircleCol(int newColor){
        //update the instance variable
        circleCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public int getFilledCircleCount(){
        return filledCircleCount;
    }

    public void setFilledCircleCount(int newCount){
        //update the instance variable
        filledCircleCount=newCount;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public int getCircleCount(){
        return circleCount;
    }

    public void setCircleCount(int newCount){
        //update the instance variable
        circleCount=newCount;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public StampView(Context context) {
        this(context, null);
    }

    public StampView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // real work here
        //paint object for drawing in onDraw
        circlePaint = new Paint();
        filledCirclePaint = new Paint();
        if(attrs != null) {
            //get the attributes specified in attrs.xml using the name we included
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.StampView, 0, 0);
            try {
                //get the text and colors specified using the names in attrs.xml
                circleCount = a.getInteger(R.styleable.StampView_circleCount,10); //0 is default
                filledCircleCount = a.getInteger(R.styleable.StampView_filledCircleCount,0); //0 is default
                circleCol = a.getInteger(R.styleable.StampView_circleColor, Color.BLUE);//blue is default
                circleBorderCol = a.getInteger(R.styleable.StampView_circleBorderColor, Color.BLACK);//Black is default
            } finally {
                a.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = calculateDesiredWith();
        int desiredHeight = calculateDesiredHeight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    private int calculateDesiredWith() {
        //calculate desired space with gap between the circles
        return defaultCircleDiameter * MAX_CIRCLE_ROW_COUNT * 2 ;
    }

    private int calculateDesiredHeight() {
        Double height = 0.0;
        //calculate desired space with gab between the circles
        int rowCount = getCircleCount() / MAX_CIRCLE_ROW_COUNT;
        if(mod(getCircleCount(),MAX_CIRCLE_ROW_COUNT) > 0){
            rowCount += 1;
        }
        height = 0.5 * defaultCircleDiameter + (defaultCircleDiameter * rowCount - 1) + defaultCircleDiameter * rowCount;
        return height.intValue() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setStrokeWidth(10);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleBorderCol);

        filledCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        filledCirclePaint.setAntiAlias(true);
        filledCirclePaint.setStrokeWidth(10);
        filledCirclePaint.setColor(circleCol);
        Paint paint = circlePaint;
        int circleDiameter = 0;
        int circlePaddingX = 0;
        int circlePaddingY = 0;

        int paddingY = 0;
        int paddingX = 0;

        int rowCount = getCircleCount() / MAX_CIRCLE_ROW_COUNT;
        if(mod(getCircleCount(),MAX_CIRCLE_ROW_COUNT) > 0){
            rowCount += 1;
        }
        circlePaddingY = this.getMeasuredHeight() / ( 3 * rowCount);

        int tmp = (this.getMeasuredWidth() / ( 3 * MAX_CIRCLE_ROW_COUNT));
        circleDiameter = 2*tmp;
        circlePaddingX = tmp;

        int currCircleCount = 0;
       for(int row = 0; row < rowCount; row++) {
            //carlculate space from top, which is constant factor + rows diameter + space between rows
            paddingY = circlePaddingY / 2 + row * circleDiameter + row * circlePaddingY/2;
            for(int i =0; i< MAX_CIRCLE_ROW_COUNT;i++ ){
                paddingX = circlePaddingX / 2 + i * circleDiameter + i * circlePaddingX;
                //calculate current drawn circles, rows * max CircleCount + circles of current line
                currCircleCount = (i + 1 ) + (row * MAX_CIRCLE_ROW_COUNT);
                if(currCircleCount > getCircleCount()) {
                    break;
                }
                if(currCircleCount <= filledCircleCount) {
                    paint = filledCirclePaint;
                } else {
                    paint = circlePaint;
                }

                canvas.drawCircle(paddingX + circleDiameter / 2, paddingY + circleDiameter / 2, circleDiameter/2, paint);
            }
        }



        //set the text color using the color specified
        //circlePaint.setColor(labelCol);

    }
    private int mod(int x, int y)
    {
        int result = x % y;
        return result < 0? result + y : result;
    }

}
