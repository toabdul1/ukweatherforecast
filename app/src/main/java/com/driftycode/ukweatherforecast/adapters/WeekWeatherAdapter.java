package com.driftycode.ukweatherforecast.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.driftycode.ukweatherforecast.R;
import com.driftycode.ukweatherforecast.service.models.WeatherModel;
import com.driftycode.ukweatherforecast.utils.Common;

import java.text.ParseException;

/**
 * Created by nagendra on 18/11/17.
 */

public class WeekWeatherAdapter extends RecyclerView.Adapter<WeekWeatherAdapter.ViewHolder> {

    private static final String TAG = "WeekWeatherAdapter";
    private static WeatherModel[] weekWeatherArray;
    private Common common;

    public WeekWeatherAdapter(Context context, WeatherModel[] weekWeatherArray) {
        WeekWeatherAdapter.weekWeatherArray = weekWeatherArray;
        common = new Common();
    }

    @Override
    public WeekWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            String dayOfWeek = common.getWeekFromDateString(weekWeatherArray[position].getDt_txt());
            String timeOfDay = common.getTimeFromDateString(weekWeatherArray[position].getDt_txt());
            if (dayOfWeek != null)
                holder._weatherDay.setText(dayOfWeek);

            if (timeOfDay != null)
                holder._weatherTime.setText(timeOfDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder._weatherDesc.setText(weekWeatherArray[position].getWeather()[0].getDescription());
        float temp = Common.getKelToCels(weekWeatherArray[position].getMain().getTemp());
        holder._weatherTemp.setText(temp + " \u2103");


    }

    @Override
    public int getItemCount() {
        return weekWeatherArray.length;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView _weatherDay, _weatherDesc, _weatherTemp, _weatherTime;

        ViewHolder(View v) {
            super(v);
            _weatherDay = v.findViewById(R.id.tv_weather_day);
            _weatherDesc = v.findViewById(R.id.tv_weather_desc);
            _weatherTemp = v.findViewById(R.id.tv_weather_temp);
            _weatherTime = v.findViewById(R.id.tv_weather_time);

        }

    }
}
