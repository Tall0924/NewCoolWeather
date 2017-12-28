package com.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class Now {
    //    "now": {
//        "cond": {
//            "code": "100",
//            "txt": "晴"
//        },
//        "fl": "13",
//                "hum": "50",
//                "pcpn": "0.0",
//                "pres": "1022",
//                "tmp": "19",
//                "vis": "7",
//                "wind": {
//            "deg": "61",
//                    "dir": "东北风",
//                    "sc": "3-4",
//                    "spd": "10"
//        }
//    },
    @SerializedName("tmp")
    public String temperature;//气温

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;//天气
    }
}
