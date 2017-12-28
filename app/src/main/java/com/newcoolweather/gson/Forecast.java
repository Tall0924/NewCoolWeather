package com.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class Forecast {
    //  "daily_forecast": [
//          {
//              "date": "2017-12-28",
//              "cond": {
//                  "txt_d": "晴",
//               },
//              "tmp": {
//                    "max": "21",
//                    "min": "14"
//                  },
//          ],
    public String date;

    @SerializedName("cond")
    public More more;

    @SerializedName("tmp")
    public Temperature temperature;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}
