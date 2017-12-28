package com.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class Basic {
//     "basic": {
//             "city": "厦门",
//             "cnty": "中国",
//             "id": "CN101230201",
//             "lat": "24.4904747",
//             "lon": "118.11022186",
//             "update": {
//             "loc": "2017-12-27 13:52",
//             "utc": "2017-12-27 05:52"
//             }
    @SerializedName("city")
    public String cityName;//让JSON字段与Java字段之间建立映射关系

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;//更新时间
    }
}
