package com.newcoolweather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.newcoolweather.R;
import com.newcoolweather.activity.MainActivity;
import com.newcoolweather.activity.WeatherActivity;
import com.newcoolweather.db.City;
import com.newcoolweather.db.County;
import com.newcoolweather.db.Province;
import com.newcoolweather.util.HttpUtil;
import com.newcoolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by XiaoYe on 2017/12/28.
 */

public class ChooseAreaFragment extends Fragment {
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    private ProgressDialog mDialog;
    private TextView mTitleText;
    private Button mBackBtn;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;

    private Province mProvince;
    private City mCity;
    private County mCounty;

    private int currentLevel;//当前选中的级别
    private boolean mResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        mTitleText = view.findViewById(R.id.title_text);
        mBackBtn = view.findViewById(R.id.back_btn);
        mListView = view.findViewById(R.id.list_view);

        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvinces();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    mProvince = mProvinceList.get(i);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    mCity = mCityList.get(i);
                    queryCounty();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = mCountyList.get(i).getWeatherId();
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                        queryProvinces();
                    }
                }
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                }
            }
        });

    }

    private void queryProvinces() {
        mTitleText.setText("中国");
        mBackBtn.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList.size() > 0) {
            mList.clear();
            for (Province province : mProvinceList) {
                mList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    private void queryCities() {
        mTitleText.setText(mProvince.getProvinceName());
        mBackBtn.setVisibility(View.VISIBLE);
        mCityList = DataSupport.where("provinceid =?", String.valueOf(mProvince.getId())).find(City.class);
        if (mCityList.size() > 0) {
            mList.clear();
            for (City city : mCityList) {
                mList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            String address = "http://guolin.tech/api/china/" + mProvince.getProvinceCode();
            queryFromServer(address, "city");
        }
    }

    private void queryCounty() {
        mTitleText.setText(mCity.getCityName());
        mBackBtn.setVisibility(View.VISIBLE);
        mCountyList = DataSupport.where("cityid =?", String.valueOf(mCity.getId())).find(County.class);
        if (mCountyList.size() > 0) {
            mList.clear();
            for (County county : mCountyList) {
                mList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            String address = "http://guolin.tech/api/china/" + mProvince.getProvinceCode() + "/" + mCity.getCityCode();
            queryFromServer(address, "county");
        }
    }


    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                mResult = false;
                if ("province".equals(type)) {
                    mResult = Utility.handlePronvinceResponse(data);
                } else if ("city".equals(type)) {
                    mResult = Utility.handleCityResponse(data, mProvince.getId());
                } else if ("county".equals(type)) {
                    mResult = Utility.handleCountyResponse(data, mCity.getId());
                }
                if (mResult) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void closeProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getContext());
            mDialog.setMessage("正在加载.....");
            mDialog.setCanceledOnTouchOutside(false);//在对话框的外面点击,让对话框消失
        }
        mDialog.show();
    }

}
