package com.newcoolweather.db;

import org.litepal.crud.DataSupport;

/**
 * "id": 19, "name": "江西"
 * Created by XiaoYe on 2017/12/28.
 */


public class Province extends DataSupport {
    private int id;//自增长ID
    private String provinceName;
    private int provinceCode;//省的代号 ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
