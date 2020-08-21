package com.example.dell.expensemanager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class CategoryWiseGraph extends AppCompatActivity {

    private static final String TAG = "CategoryWiseGraph";
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_graph);

        Spinner spinner = findViewById(R.id.CategorySp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.expense_array_spinner, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mChart = (LineChart) findViewById(R.id.CategoryChart);
        // mChart.setOnChartGestureListener(CategoryWiseGraph.this);
        //mChart.setOnChartValueSelectedListener(CategoryWiseGraph.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setClickable(true);

//        Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();
        ArrayList<Entry> yValues = new ArrayList<>();
        float j = 3;
        int length = Dashboard.data.size();
        for(int i = 0 ; i < length ; i++){
            yValues.add(new Entry(j, Float.parseFloat(Dashboard.data.get(i).getAmount())));
            j = j + 3;
//            Toast.makeText(this,Dashboard.data.get(i).getAmount(),Toast.LENGTH_SHORT).show();
        }
//        Toast.makeText(this,"End",Toast.LENGTH_SHORT).show();
//        yValues.add(new CategoryWiseGraph_DataClass(1,50f));
//        yValues.add(new CategoryWiseGraph_DataClass(2,25f));
//        yValues.add(new CategoryWiseGraph_DataClass(3,36f));
//        yValues.add(new CategoryWiseGraph_DataClass(4,90f));
//        yValues.add(new CategoryWiseGraph_DataClass(5,80f));
//        yValues.add(new CategoryWiseGraph_DataClass(6,60f));

        LineDataSet set1 = new LineDataSet(yValues,"Spends");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        ArrayList<ILineDataSet> dataset = new ArrayList<>();
        dataset.add(set1);

        LineData data = new LineData(dataset);

        mChart.setData(data);
        Toast.makeText(this,"Final",Toast.LENGTH_SHORT).show();
    }
}
