package com.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class Weather {
    //            "status": "ok",
    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
