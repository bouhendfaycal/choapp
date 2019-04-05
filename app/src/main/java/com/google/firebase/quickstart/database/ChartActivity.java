package com.google.firebase.quickstart.database;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.quickstart.database.models.Entry;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        LineChartView chart = findViewById(R.id.chart);
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        chart.setHorizontalFadingEdgeEnabled(true);

        List<Entry> entries = DataSupport.order("mYear").order("mMonth").order("mDate").order("mHour").order("mMinute").find(Entry.class);
        int size = entries.size();




        List<PointValue> values = new ArrayList<PointValue>();

        List xAxisValues = new ArrayList<>();
        List yAxisValues = new ArrayList<>();
        for(int i=0; i<size;i++)
        {
            AxisValue axisValueX = new AxisValue(i);
            axisValueX.setValue(entries.get(i).getTimeUnix());
            axisValueX.setLabel(entries.get(i).getTime());
            xAxisValues.add(axisValueX);

            AxisValue axisValueY = new AxisValue(i);
            axisValueY.setValue(entries.get(i).getTauxGlycemie());
            axisValueY.setLabel(entries.get(i).getTauxGlycemie()+"");
            yAxisValues.add(axisValueY);
            values.add(new PointValue(entries.get(i).getTimeUnix(),entries.get(i).getTauxGlycemie()));
        }
        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axisX = new Axis();
        axisX.setName(getString(R.string.entry_date));
        axisX.setValues(xAxisValues);
        axisX.setTextColor(Color.BLACK);
        data.setAxisXBottom(axisX);
        Axis axisY = new Axis();
        axisY.setName(getString(R.string.entry_glycemie));
        axisY.setValues(yAxisValues);
        axisY.setTextColor(Color.BLACK);
        axisX.setHasSeparationLine(true);
        axisX.setHasTiltedLabels(true);
        data.setAxisYLeft(axisY);
        chart.setLineChartData(data);
    }
}
