package com.example.dell.expensemanager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class CategoryWiseGraph extends AppCompatActivity {

    private static final String TAG = "CategoryWiseGraph";
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_vise_graph);

        Spinner spinner = (Spinner) findViewById(R.id.CategorySp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_array_spinner, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mChart = (LineChart) findViewById(R.id.CategoryChart);
        // mChart.setOnChartGestureListener(CategoryWiseGraph.this);
        //mChart.setOnChartValueSelectedListener(CategoryWiseGraph.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);


        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 60f));
        yValues.add(new Entry(1,50f));
        yValues.add(new Entry(2,25f));
        yValues.add(new Entry(3,36f));
        yValues.add(new Entry(4,90f));
        yValues.add(new Entry(5,80f));
        yValues.add(new Entry(6,60f));

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(set1);

        LineData data = new LineData(dataset);

        mChart.setData(data);
    }
}
