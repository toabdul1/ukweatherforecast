package com.driftycode.ukweatherforecast;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.driftycode.ukweatherforecast.adapters.WeekWeatherAdapter;
import com.driftycode.ukweatherforecast.base.BaseActivity;
import com.driftycode.ukweatherforecast.service.models.CitiesWeatherModel;
import com.driftycode.ukweatherforecast.service.models.WeatherModel;
import com.driftycode.ukweatherforecast.utils.Common;
import com.driftycode.ukweatherforecast.utils.Constants;
import com.driftycode.ukweatherforecast.viewmodels.ProjectViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    ProjectViewModel _viewModel;

    @BindView(R.id.tv_weather_condition)
    TextView _weatherConditionTxt;
    @BindView(R.id.tv_temp_max)
    TextView _tempMaxTxt;
    @BindView(R.id.tv_weather_humidity)
    TextView _weatherHumidityTxt;
    @BindView(R.id.tv_weather_pressure)
    TextView _weatherPressureTxt;

    @BindView(R.id.rv_week_weather)
    RecyclerView _weekWeatherRecycleView;

    @BindView(R.id.main_content_layout)
    View _contentView;

    @BindView(R.id.no_network_available)
    TextView _noNetworkTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        _weekWeatherRecycleView.setLayoutManager(layoutManager);
        _weekWeatherRecycleView.setMinimumWidth(200);

        _viewModel =
                ViewModelProviders.of(this).get(ProjectViewModel.class);

        getWeatherForecastData();
    }


    private void getWeatherForecastData() {
        if (isNetworkAvailable()) {
            _viewModel.getWeatherByCountry(Constants.CITY_LONDON_UK).observe(this, new Observer<CitiesWeatherModel>() {
                @Override
                public void onChanged(@Nullable CitiesWeatherModel citiesWeatherModel) {
                    if (citiesWeatherModel != null && citiesWeatherModel.getCod() == 200) {
                        Log.d(TAG, "Weather Data " + citiesWeatherModel.getList().length);
                        if (citiesWeatherModel.getList().length > 0) {
                            _noNetworkTxt.setVisibility(View.GONE);
                            _contentView.setVisibility(View.VISIBLE);
                            generateTodayWeatherDetail(citiesWeatherModel.getList()[0]);
                            RecyclerView.Adapter mAdapter = new WeekWeatherAdapter(MainActivity.this, citiesWeatherModel.getList());
                            _weekWeatherRecycleView.setAdapter(mAdapter);
                        } else {
                            _noNetworkTxt.setVisibility(View.VISIBLE);
                            _contentView.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d(TAG, "Data is not available for your country");
                        Toast.makeText(MainActivity.this, "Data is not available for your country", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, Constants.NO_INTERNET_MSG, Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void generateTodayWeatherDetail(WeatherModel weatherModel) {
        //getDayFromDate();
        if (weatherModel != null) {
            _weatherConditionTxt.setText(weatherModel.getWeather()[0].getDescription());
            String maxTemp = Common.getKelToCels(weatherModel.getMain().getTemp()) + " \u2103";
            _tempMaxTxt.setText(maxTemp);

            double pressurePSI = weatherModel.getMain().getPressure() / 68.9476;
            _weatherHumidityTxt.setText(weatherModel.getMain().getHumidity() + "%");
            _weatherPressureTxt.setText(Math.round(pressurePSI) + " psi");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
