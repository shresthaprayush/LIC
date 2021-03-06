package com.example.lic.Main.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lic.Main.Datamodel.Insightsdatamodel;
import com.example.lic.Main.Datamodel.User;
import com.example.lic.Main.Utilities.RetrofitClient;
import com.example.lic.Main.Utilities.SharedPreferenceManager;
import com.example.lic.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class sixmonthsreportfragment extends Fragment {
    private String date,pan;

    String[] datesalessixmonths,dateprofitsixmonths,lablebar;
    private int i;
    LineChart lineChartsalessixmonths,lineChartprofitsixmonths;
    BarChart barChartsixmonths;
    ArrayList<Entry> yValuesprofitsixmonths = new ArrayList<>();
    ArrayList<Entry> yValues2salessixmonths = new ArrayList<>();
    ArrayList<Entry> yValuesinventorysixmonths = new ArrayList<>();




    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sixmonthsreportfragment, container, false);


        lineChartsalessixmonths = v.findViewById(R.id.mylinegraph1sixmonths);
        lineChartprofitsixmonths = v.findViewById(R.id.mylinegraph2sixmonths);
//        barChartsixmonths = v.findViewById(R.id.chartinventory);


        User user = SharedPreferenceManager.getmInstance(getContext()).getUser();
        pan = String.valueOf(user.getUserid());

//
        Call< List<Insightsdatamodel>> call = RetrofitClient.getmInstance().getApi().getinsightsdatalong(pan);
        call.enqueue(new Callback<List<Insightsdatamodel>>() {
                         @Override
                         public void onResponse(Call<List<Insightsdatamodel>> call, Response<List<Insightsdatamodel>> response) {


                             if (response.body() != null) {


                                 List<Insightsdatamodel> list = response.body();

                                 String[] datacomingfrommoedl = new String[list.size()];
                                 datesalessixmonths = new String[list.size()];
                                 String[] salessp = new String[list.size()];
                                 dateprofitsixmonths = new String[list.size()];
                                 String[] profitsp = new String[list.size()];
                                 lablebar = new String[list.size()];
                                 String[] spbar = new String[list.size()];


                                 for (int i = 0; i < list.size(); i++) {
                                     datacomingfrommoedl[i] = list.get(i).getName();

                                     if (datacomingfrommoedl[i].equals("sales")) {

                                         datesalessixmonths[i] = list.get(i).getDate();
                                         salessp[i] = String.valueOf(list.get(i).getSp());

                                         yValues2salessixmonths.add(new Entry(i, Float.parseFloat(salessp[i])));


                                     } else if (datacomingfrommoedl[i].equals("profit")) {

                                         dateprofitsixmonths[i] = list.get(i).getDate();
                                         profitsp[i] = String.valueOf(list.get(i).getSp());
                                         yValuesprofitsixmonths.add(new Entry(i, Float.parseFloat(profitsp[i])));

                                     } else if (datacomingfrommoedl[i].equals("bar")) {

                                         lablebar[i] = list.get(i).getLabel();
                                         spbar[i] = String.valueOf(list.get(i).getSp());
                                         yValuesinventorysixmonths.add(new Entry(i, Float.parseFloat(spbar[i])));


                                     }


                                 }

                                 LineDataSet set1 = new LineDataSet(yValues2salessixmonths, "Sales Data");
                                 LineDataSet set2 = new LineDataSet(yValuesprofitsixmonths, "Profit Data");


                                 set1.setFillAlpha(500);
                                 set1.setColor(Color.parseColor("#1ea1e5"));
                                 set1.setLineWidth(3);
                                 set1.setValueTextSize(10);

                                 set2.setFillAlpha(500);
                                 set2.setColor(Color.parseColor("#2a86ab"));
                                 set2.setLineWidth(3);
                                 set2.setValueTextSize(10);


                                 ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                 dataSets.add(set1);


                                 ArrayList<ILineDataSet> dataset2 = new ArrayList<>();
                                 dataset2.add(set2);


                                 LineData data = new LineData(dataSets);
                                 lineChartsalessixmonths.setData(data);
                                 lineChartsalessixmonths.animateXY(3000, 3000);
                                 lineChartsalessixmonths.invalidate();
                                 lineChartsalessixmonths.setNoDataText("No Data Found");
                                 lineChartsalessixmonths.getAxisRight().setEnabled(false);
                                 XAxis xAxis = lineChartsalessixmonths.getXAxis();
                                 //xAxis.setValueFormatter(new Myaxisvalueformatter(datesalessixmonths));
                                 xAxis.setGranularity(1);
                                 xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                                 LineData data2 = new LineData(dataset2);
                                 lineChartprofitsixmonths.setData(data2);
                                 lineChartprofitsixmonths.invalidate();
                                 lineChartsalessixmonths.animateXY(3000, 3000);
                                 lineChartprofitsixmonths.setNoDataText("No Profit Data");
                                 lineChartprofitsixmonths.getAxisRight().setEnabled(false);
                                 XAxis xAxis1 = lineChartprofitsixmonths.getXAxis();
                                 //xAxis1.setValueFormatter(new Myaxisvalueformatter(dateprofitsixmonths));
                                 xAxis1.setGranularity(1);
                                 xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);

                             }

                             else {
                                 Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                             }
                         }

                         class Myaxisvalueformatter implements IAxisValueFormatter {

                             private String[] mvalues;
                             public Myaxisvalueformatter(String[] values){
                                 this.mvalues = values;
                             }

                             @Override
                             public String getFormattedValue(float value, AxisBase axis) {
                                 return mvalues[(int)value];
                             }

                      }

                         @Override
                         public void onFailure(Call<List<Insightsdatamodel>> call, Throwable t) {

                             Toast.makeText(getContext(),"Please Connect To Internet And Try Again",Toast.LENGTH_LONG).show();


                         }
                     }
        );

        return v;
    }


}





