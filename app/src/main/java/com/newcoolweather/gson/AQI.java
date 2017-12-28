package com.newcoolweather.gson;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class AQI {
    //            "aqi": {
//        "city": {
//            "aqi": "57",
//                    "qlty": "良",
//                    "pm25": "22",
//                    "pm10": "64",
//                    "no2": "17",
//                    "so2": "6",
//                    "co": "0",
//                    "o3": "77"
//        }
    public AQICity city;

    public class AQICity {
        public String aqi;//空气污染指数
        public String pm25;
    }
}
