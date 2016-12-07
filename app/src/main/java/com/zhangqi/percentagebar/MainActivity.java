package com.zhangqi.percentagebar;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private PercentageBar mBarGraph;
    private ArrayList<String> respName;

    float f=-500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        respName = new ArrayList<String>();

        TextView  textView= (TextView) findViewById(R.id.tv);
        textView.setShadowLayer(25, 0, 0, getResources().getColor(R.color.bar_shadow_color));
        respName.add("周三");
        respName.add("周四");
        respName.add("周五");
        respName.add("周六");
        respName.add("周日");
        respName.add("周一");
        respName.add("周二");
        respName.add("周三");
        respName.add("周四");
        respName.add("周五");
        respName.add("周六");
        respName.add("周日");
        respName.add("周一");
        respName.add("周二");
        respName.add("周三");
        respName.add("周四");
        respName.add("周五");
        respName.add("周六");
        respName.add("周日");
        respName.add("周一");
        respName.add("周二");

        ArrayList<BarChart> charts=new ArrayList<>();
        float max=0;
        for (int i = 0; i < 21; i++) {
            f= (float) (Math.random()*20000);
            if (i%3==0){
                f=-f;
            }
            BarChart chart=new BarChart();
            chart.setData_id(i);
            chart.setNum(f<0?-f:f);
            chart.setWeek(respName.get(i));
            chart.setPNColor(f<0?R.color.bar_negative_color:R.color.bar_color);
            charts.add(chart);
            max=f<0?-f>max?-f:max:f>max?f:max;
        }


        mBarGraph = (PercentageBar) findViewById(R.id.bargraph);

        mBarGraph.setMax((float) (1.2*max));
        mBarGraph.setBarWidth(0.03);
        mBarGraph.setStartX(mBarGraph.getBarWidth()/2);
        mBarGraph.setBarChart(charts);

        mBarGraph.setVerticalLineNum(4);
        mBarGraph.setOnItemSelectListener(new PercentageBar.OnItemSelectListener() {
            @Override
            public void onItemSelect(int index, BarChart chart) {
                Log.i("whx",index+":"+chart.toString());
            }
        });
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BarChart> charts=new ArrayList<>();
                float max=0;
                for (int i = 0; i < 21; i++) {
                    f= (float) (Math.random()*20000);
                    if (i%3==0){
                        f=-f;
                    }
                    BarChart chart=new BarChart();
                    chart.setData_id(i);
                    chart.setNum(f<0?-f:f);
                    chart.setWeek(respName.get(i));
                    chart.setPNColor(f<0?R.color.bar_negative_color:R.color.bar_color);
                    charts.add(chart);
                    max=f<0?-f>max?-f:max:f>max?f:max;
                }
                mBarGraph.setMax((float) (1.2*max));
                mBarGraph.setBarChart(charts);
                mBarGraph.invalidate();
            }
        });
    }


}
