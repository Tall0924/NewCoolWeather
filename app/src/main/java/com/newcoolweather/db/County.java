package com.newcoolweather.db;

import org.litepal.crud.DataSupport;

/**
 * "id": 1090,
 * "name": "同安",
 * "weather_id": "CN101230202"
 * Created by XiaoYe on 2017/12/28.
 */

public class County extends DataSupport {
    private int id;
    private String countyName;
    private String countyCode;
    private String weatherId;
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
