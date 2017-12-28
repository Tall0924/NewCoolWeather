package com.newcoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class Suggestion {
    //    "suggestion": {
//        "comf": {
//            "brf": "舒适",
//                    "txt": "白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"
//        },
//        "cw": {
//            "brf": "较适宜",
//                    "txt": "较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"
//        },
//        "sport": {
//            "brf": "较适宜",
//                    "txt": "天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。"
//        },
//    }
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carwash;

    public Sport sport;

    public class Comfort {
        @SerializedName("txt")
        public String info;
    }

    public class CarWash {//洗车
        @SerializedName("txt")
        public String info;
    }

    public class Sport {
        @SerializedName("txt")
        public String info;
    }
}
