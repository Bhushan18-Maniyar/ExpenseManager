package com.example.dell.expensemanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    Spinner spinner;
    String item;
    final ArrayList<Entry> yValues = new ArrayList<>();
    final ArrayList<ILineDataSet> dataset = new ArrayList<>();
    LineDataSet set1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_graph);

        spinner =  findViewById(R.id.CategorySp);
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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                item = spinner.getSelectedItem().toString();
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();


                float j = 10;
                int length = Dashboard.data.size();

                for(int k = 0 ; k < length ; k++){
                    if(item.equals(Dashboard.data.get(k).getCategory()))
                    {
                        yValues.add(new Entry(j, Float.parseFloat(Dashboard.data.get(k).getAmount())));
                        j = j + 10;
                        Toast.makeText(getBaseContext(), Dashboard.data.get(k).getCategory(), Toast.LENGTH_SHORT).show();
                    }
                }
                clear();
                createGraph();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Toast.makeText(this, item, Toast.LENGTH_SHORT).show();

        /*mChart = (LineChart) findViewById(R.id.CategoryChart);
        // mChart.setOnChartGestureListener(CategoryWiseGraph.this);
        //mChart.setOnChartValueSelectedListener(CategoryWiseGraph.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);



        Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();
        ArrayList<Entry> yValues = new ArrayList<>();

        float j = 10;
        int length = Dashboard.data.size();

        for(int i = 0 ; i < length ; i++){
            if(item.equals(Dashboard.data.get(i).getCategory()))
            {
                yValues.add(new Entry(j, Float.parseFloat(Dashboard.data.get(i).getAmount())));
                j = j + 10;
                Toast.makeText(this, Dashboard.data.get(i).getCategory(), Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(this,"End",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this,"Final",Toast.LENGTH_SHORT).show();*/
    }
    private void clear()
    {
        mChart.clear();
        dataset.clear();
    }
    private void createGraph()
    {
        set1 = new LineDataSet(yValues,"Spends");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        dataset.add(set1);

        LineData data = new LineData(dataset);

        mChart.setData(data);
        Toast.makeText(getBaseContext(),"Final",Toast.LENGTH_SHORT).show();
    }

}
