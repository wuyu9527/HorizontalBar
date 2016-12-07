package com.zhangqi.percentagebar;

/**
 * Created by whx on 16/12/1.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class PercentageBar extends View {
    /**
     * 画线的画笔
     */
    private Paint mLinePaint;
    /**
     * 画柱状图的画笔
     */
    private Paint mBarPaint;
    /**
     * 名字的画笔
     */
    private Paint mTextPaint;
    /**
     * 数字的笔画
     */
    private Paint mNumPaint;
    /**
     * 开始X坐标 设置开始X坐标为0
     */
    private int startX = 0;
    /**
     * 开始Y坐标 设置开始Y坐标为50
     */
    private int startY = 50;
    /**
     * 结束X坐标
     */
    private int stopX;
    /**
     * 结束Y坐标
     */
    private int stopY;
    /**
     * 测量值 宽度
     */
    private int measuredWidth;
    /**
     * 测量值 高度
     */
    private int measuredHeight;
    /**
     * 每条柱状图的宽度
     */
    private int barWidth;
    /**
     * 设置最大值，用于计算比例
     */
    private float max;
    /**
     * 设置最小值,用于计算比例
     */
    private float min;
    /**
     * 设置每条柱状图的目标值，除以max即为比例
     */
    private ArrayList<Float> respTarget;
    /**
     * 设置一共有几条柱状图
     */
    private int totalBarNum;
    /**
     * 设置每条柱状图的当前比例
     */
    private Float[] currentBarProgress;
    /**
     * 透明度
     */
    private int textAlpha;
    /**
     * 每条竖线的当前比例
     */
    private int currentVerticalLineProgress;
    /**
     * 最上面一条横线的比例
     */
    private int currentHorizentalLineProgress;
    /**
     * 每条柱状图的名字
     */
    private ArrayList<String> respName;
    /**
     * 每条竖线之间的间距
     */
    private int deltaX;
    /**
     * 每条柱状图之间的间距
     */
    private int deltaY;
    /**
     * 一共有几条竖线
     */
    private int verticalLineNum;
    /**
     * 单位
     */
    private String unit;
    /**
     * 每条竖线之间相差的值
     */
    private float numPerUnit;
    /**
     * 设置开始X坐标
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }
    /**
     * 设置开始Y坐标
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Paint getmLinePaint() {
        return mLinePaint;
    }

    public Paint getmBarPaint() {
        return mBarPaint;
    }

    public Paint getmTextPaint() {
        return mTextPaint;
    }


    public int getStartX() {
        return startX;
    }


    public int getStartY() {
        return startY;
    }

    public int getStopX() {
        return stopX;
    }

    public int getStopY() {
        return stopY;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public float getMax() {
        return max;
    }

    public ArrayList<Float> getRespTarget() {
        return respTarget;
    }

    public int getTotalBarNum() {
        return totalBarNum;
    }

    public Float[] getCurrentBarProgress() {
        return currentBarProgress;
    }

    public int getCurrentVerticalLineProgress() {
        return currentVerticalLineProgress;
    }

    public int getCurrentHorizentalLineProgress() {
        return currentHorizentalLineProgress;
    }

    public ArrayList<String> getRespName() {
        return respName;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public int getVerticalLineNum() {
        return verticalLineNum;
    }

    public String getUnit() {
        return unit;
    }

    public float getNumPerUnit() {
        return numPerUnit;
    }

    public Float getTextSize() {
        return textSize;
    }

    public ArrayList<Integer> getPNColor() {
        return PNColor;
    }

    private Context context;

    public PercentageBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initScreenW_H();
        init(context);

    }

    public PercentageBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initScreenW_H();
        init(context);
    }

    public PercentageBar(Context context) {
        super(context);
        this.context = context;
        initScreenW_H();
        init(context);
    }


    /**
     * 初始化获取屏幕宽高 设置屏幕起始X和Y轴
     */
    protected void initScreenW_H() {
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        startY = (int) (screenHeight * 0.039);
        min = (float) (screenWidth * 0.023);
    }

    /**
     * 初始化
     * */
    private void init(Context context) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        //初始化柱状图画笔
        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setColor(getResources().getColor(R.color.bar_color));
        mBarPaint.setStyle(Style.FILL);
        //设置透明度
        textAlpha = 0;
        mBarPaint.setShadowLayer(10, 5, 0, getResources().getColor(R.color.bar_shadow_color));
        //初始化线的画笔
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Style.FILL);
        mLinePaint.setColor(getResources().getColor(R.color.line_color));
        mLinePaint.setStrokeWidth(1);


    }

    /**
     * 测量方法，主要考虑宽和高设置为wrap_content的时候，我们的view的宽高设置为多少
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果宽和高都为wrap_content的时候，我们将宽设置为我们输入的max值，也就是柱状图的最大值
        //高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) max, totalBarNum * (barWidth + deltaY) + barWidth / 2);
            //如果宽度为wrap_content  高度为match_parent或者精确数值的时候
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            //宽度设置为max，高度为父容器高度
            setMeasuredDimension((int) max, heightSpecSize);
            //如果宽度为match_parent或者精确数值的时候，高度为wrap_content
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            //宽度设置为父容器的宽度，高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
            setMeasuredDimension(widthSpecSize, totalBarNum * (barWidth + deltaY) + barWidth / 2);
        }
    }

    /**
     * 获得文字宽度
     */
    public float getTextWidth(String text, float size) { //第一个参数是要计算的字符串，第二个参数是字提大小
        TextPaint FontPaint = new TextPaint();
        FontPaint.setTextSize(size);
        if (textHeight == 0) {
            getTextHeight();
        }
        return FontPaint.measureText(text);
    }

    /**
     * 获取字体一半高度
     */
    public void getTextHeight() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        textHeight = (fontMetrics.ascent + fontMetrics.descent) / 2;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private int mWidth;
    private int mHeight;

    protected int screenWidth;
    protected int screenHeight;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获得测量后的宽度
        measuredWidth = getMeasuredWidth();
        //获得测量后的高度
        measuredHeight = getMeasuredHeight();
        //计算结束X的值
        stopX = measuredWidth - (int) getTextWidth(String.valueOf(respName.get(0)), textSize);
        //计算结束Y的值
        stopY = measuredHeight;
        //计算每条竖线之间的间距
        deltaX = (stopX - (startX + 7 * barWidth / 5)) / verticalLineNum;
        //计算出每条竖线所代表的数值
        numPerUnit = max / verticalLineNum;
        //初始化最上面横线的初始进度
        currentHorizentalLineProgress = startX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画竖线
         */
        for (int i = 0; i < verticalLineNum; i++) {
            canvas.drawLine(startX + i * deltaX, deltaY, startX + i * deltaX, deltaY + totalBarNum * (deltaY + barWidth), mLinePaint);
        }
        /**
         * 画最下面的横线
         */
        canvas.drawLine(startX, deltaY + totalBarNum * (deltaY + barWidth), stopX - getTextWidth(respName.get(0), textSize) / 2, deltaY + totalBarNum * (deltaY + barWidth), mLinePaint);

        /**
         * 画柱状图
         */
        for (int i = 0; i < totalBarNum; i++) {
            if (charts.get(i).getCoordinate() == null) {
                BarChart.Coordinate coordinate = new BarChart.Coordinate();
                coordinate.setLeftX(startX);
                coordinate.setTopY(startY + i * (deltaY + barWidth) - barWidth);
                coordinate.setRightX((float) ((respTarget.get(i) / max) * stopX+min*1.25));
                coordinate.setBottomY(startY + i * (deltaY + barWidth));
                charts.get(i).setCoordinate(coordinate);
            }
            if (currentBarProgress[i] < (respTarget.get(i) / max) * stopX) {
                currentBarProgress[i] += 10;//每次增加10像素
                postInvalidateDelayed(10);//每10毫秒重绘一次
            }
            mBarPaint.setColor(getResources().getColor(PNColor.get(i)));
            canvas.drawRect(startX,
                    startY + i * (deltaY + barWidth) - barWidth,
                    currentBarProgress[i] + min,
                    startY + i * (deltaY + barWidth),
                    mBarPaint);
            canvas.drawText(respName.get(i), stopX - barWidth / 2, startY + i * (deltaY + barWidth) + 3 * barWidth / 4 - barWidth + 2, mTextPaint);
            if (currentBarProgress[i] >= (respTarget.get(i) / max) * stopX) {
                if (textAlpha < 255) {
                    textAlpha += 1;
                    mNumPaint.setAlpha(textAlpha);
                    postInvalidateDelayed(100);//每100毫秒重绘一次
                }
                float textWidth = getTextWidth(PNColor.get(i) == R.color.bar_color ? String.valueOf(respTarget.get(i)) : "-" + String.valueOf(respTarget.get(i)), mNumPaint.getTextSize());
                canvas.drawText(PNColor.get(i) == R.color.bar_color ? String.valueOf(respTarget.get(i)) : "-" + String.valueOf(respTarget.get(i)),
                        textWidth < charts.get(i).getCoordinate().getRightX() - charts.get(i).getCoordinate().getLeftX() ?  (currentBarProgress[i] + min-startX)/2-textWidth/2+min : (float) (currentBarProgress[i] + min * 1.25),
                        charts.get(i).getCoordinate().getTopY() + (charts.get(i).getCoordinate().getBottomY() - charts.get(i).getCoordinate().getTopY()) / 2 - textHeight,
                        mNumPaint);
            }

        }

    }

    private Float textSize;
    private double proportion = 0.032;//比例 0.027

    /**
     * 设置每个柱状图的宽度
     * 屏幕Y轴百分比 建议0.027
     *
     * @param proportion
     */
    public void setBarWidth(double proportion) {
        this.proportion = proportion;
        this.barWidth = (int) (proportion * screenHeight);
        this.textSize = (float) proportion * screenHeight - 5;
        //计算每条柱状图之间的间距
        deltaY = barWidth / 5;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(3 * barWidth / 5);
        mNumPaint.setTextSize(3 * barWidth / 5);
        mTextPaint.setStrokeWidth(1);
        mNumPaint.setStrokeWidth(1);
        mTextPaint.setColor(Color.WHITE);
        mNumPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(textSize);
        mNumPaint.setTextSize(textSize);
    }

    /**
     * 设置最大值
     *
     * @param max
     */
    public void setMax(float max) {
        this.max = max;
    }
    /**
     * 设置最小值
     *
     * @param min
     */
    public void setMin(float min) {
        this.min = min;
    }

    /**
     * 设置一共有几个柱状图
     *
     * @param totalNum
     */
    public void setTotalBarNum(int totalNum) {
        this.totalBarNum = totalNum;
        currentBarProgress = new Float[totalNum];
        for (int i = 0; i < totalNum; i++) {
            currentBarProgress[i] = 0.0f;
        }
    }

    /**
     * 分别设置每个柱状图的目标值
     *
     * @param respTarget
     */
    public void setRespectTargetNum(ArrayList<Float> respTarget) {
        this.respTarget = respTarget;

    }

    ArrayList<Integer> PNColor;
    private ArrayList<BarChart> charts;

    /**
     * 添加图表数据  Coordinate
     *
     * @param charts
     */
    public void setBarChart(ArrayList<BarChart> charts) {
        this.charts = charts;
        stopX = screenWidth - (int) getTextWidth(String.valueOf(charts.get(0).getWeek()), textSize);
        respTarget = new ArrayList<>();
        respName = new ArrayList<>();
        PNColor = new ArrayList<>();
        for (int i = 0; i < charts.size(); i++) {
            respName.add(charts.get(i).getWeek());
            respTarget.add(charts.get(i).getNum());
            PNColor.add(charts.get(i).getPNColor());
        }
        setTotalBarNum(charts.size());
    }

    /**
     * 分别设置每个柱状图的名字
     *
     * @param respName
     */
    public void setRespectName(ArrayList<String> respName) {
        this.respName = respName;
    }

    /**
     * 设置单位
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 设置有几条竖线
     *
     * @param num
     */
    public void setVerticalLineNum(int num) {
        this.verticalLineNum = num;
    }


    private int width;
    private int height;

    private float textWidth;
    private float textHeight = 0;
    private int index = -1;

    /**
     * 定义一个接口对象listerner
     */
    private OnItemSelectListener listener;

    /**
     * 获得接口监听的方法。
     * @param listener
     */
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    /**
     * 定义一个接口
     */
    public interface OnItemSelectListener {
        void onItemSelect(int index, BarChart chart);
    }

    private float y;
    private float x;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                float x1 = event.getX();
                float y1 = event.getY();
                if (x - 10 < x1 && x + 10 > x1 && y - 10 < y1 && y + 10 > y1) {
                    for (int i = 0; i < charts.size(); i++) {
                        if (charts.get(i).getCoordinate().getTopY() < y1 + deltaY / 2 && charts.get(i).getCoordinate().getBottomY() > y1 - deltaY / 2) {
                            if (listener != null) {
                                listener.onItemSelect(i, charts.get(i));
                            }
                        }
                    }
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
