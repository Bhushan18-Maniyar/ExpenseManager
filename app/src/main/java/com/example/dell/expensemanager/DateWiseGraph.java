package com.example.dell.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateWiseGraph extends AppCompatActivity {

    TextView textView4,textView5;
    DatePickerDialog datepicker;
    EditText etStart,etEnd;
    Button btnShow;
    final ArrayList<Entry> yValues = new ArrayList<>();
    ArrayList<ILineDataSet> dataset = new ArrayList<>();
    LineDataSet set1;

    private static final String TAG = "DateWiseGraph";
    private LineChart mChart;

    private int start_date, start_month, start_year;
    private int end_date, end_month, end_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_vise_graph);

        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        btnShow = findViewById(R.id.btnShow);

        etStart=(EditText) findViewById(R.id.etStart);
        etEnd=(EditText) findViewById(R.id.etEnd);
        etStart.setInputType(InputType.TYPE_NULL);
        etEnd.setInputType(InputType.TYPE_NULL);

        mChart = (LineChart) findViewById(R.id.DateChart);
        // mChart.setOnChartGestureListener(CategoryWiseGraph.this);
        //mChart.setOnChartValueSelectedListener(CategoryWiseGraph.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        etStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(DateWiseGraph.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                initialize_Start(dayOfMonth, monthOfYear, year);
                                etStart.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        etEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(DateWiseGraph.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                initialize_End(dayOfMonth, monthOfYear, year);
                                etEnd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGraph();

            }
        });

    }

    private void initialize_Start(int dayOfMonth,int monthOfYear,int year){
        start_date = dayOfMonth;
        start_month = monthOfYear;
        start_year = year;
    }

    private void initialize_End(int dayOfMonth,int monthOfYear,int year){
        end_date = dayOfMonth;
        end_month = monthOfYear;
        end_year = year;
    }
    private void getGraph()
    {
        float j = 10;

        for(int i = 0 ; i < Dashboard.data.size() ; i++){
            String date = Dashboard.data.get(i).getDate() + "/" +
                    Dashboard.data.get(i).getMonth() + "/" +Dashboard.data.get(i).getYear();

            String start = start_date + "/" + start_month + "/" + start_year;
            String end = end_date + "/" + end_month + "/" + end_year;
            try {
                if(isInRange(new SimpleDateFormat("dd/MM/yyyy").parse(date),
                        new SimpleDateFormat("dd/MM/yyyy").parse(start),
                        new SimpleDateFormat("dd/MM/yyyy").parse(end))){
                    yValues.add(new Entry(j, Float.parseFloat(Dashboard.data.get(i).getAmount())));
                    j = j + 10;

                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this,"hv",Toast.LENGTH_SHORT).show();
            }
        }


        set1 = new LineDataSet(yValues,"Spends");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        dataset = new ArrayList<>();
        dataset.add(set1);

        LineData data = new LineData(dataset);

        mChart.setData(data);

    }
    boolean isInRange(Date date , Date startDate , Date endDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int startDayOfYear = calendar.get(Calendar.DAY_OF_YEAR); // first day is 1, last day is 365
        int startYear = calendar.get(Calendar.YEAR);

        calendar.setTime(endDate);
        int endDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int endYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        return (year > startYear && year < endYear) // year is within the range
                || (year == startYear && dayOfYear >= startDayOfYear) // year is same as start year, check day as well
                || (year == endYear && dayOfYear < endDayOfYear); // year is same as end year, check day as well

    }

    /*private void prepareGraph()
    {
        int length = Dashboard.data.size();
        float j = 10;
        for(int k = 0 ; k < length ; k++)
        {
            if(Dashboard.data.get(k).getYear()>=start_year && end_year<=Dashboard.data.get(k).getYear())
            {
                if(Dashboard.data.get(k).getMonth()>=start_month && end_month<=Dashboard.data.get(k).getMonth())
                {
                    if(Dashboard.data.get(k).getDate()>=start_date && end_date<=Dashboard.data.get(k).getDate())
                    {

                        yValues.add(new Entry(j, Float.parseFloat(Dashboard.data.get(k).getAmount())));
                        j = j + 10;
                        Toast.makeText(this,Dashboard.data.get(k).getDate(),Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
        Toast.makeText(this,"prepared",Toast.LENGTH_SHORT).show();
        set1 = new LineDataSet(yValues,"Spends");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        dataset = new ArrayList<>();
        dataset.add(set1);

        LineData data = new LineData(dataset);

        mChart.setData(data);
        Toast.makeText(this,"Final",Toast.LENGTH_SHORT).show();
    }
    private void showGraph()
    {

        set1 = new LineDataSet(yValues,"Spends");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        dataset = new ArrayList<>();
        dataset.add(set1);

        LineData data = new LineData(dataset);

        mChart.setData(data);
        Toast.makeText(this,"Final",Toast.LENGTH_SHORT).show();

    }*/
}


