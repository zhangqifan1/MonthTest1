package com.example.administrator.monthtest1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017/7/21.
 */

public class Fragment01 extends Fragment {
    private String path = "http://huixinguiyu.cn/Assets/js/data.js";
    private List<Bean.ApkBean> apk=new ArrayList<>();
    private View view;
    private ListView mLv1;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag1, container, false);
        db = new MyHelper(getContext()).getWritableDatabase();
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(path).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(6000);
                    connection.setReadTimeout(6000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String textFromStream = Tools.getTextFromStream(inputStream);
                        Gson gson = new Gson();
                        Bean bean = gson.fromJson(textFromStream, Bean.class);
                        apk = bean.getApk();
                        for (int i = 0; i <apk.size() ; i++) {
                            Bean.ApkBean apkBean = apk.get(i);
                            //name,iconUrl
                            ContentValues values=new ContentValues();
                            values.put("name",apkBean.getName());
                            values.put("iconUrl",apkBean.getIconUrl());
                            long tb = db.insert("tb", null, values);
                            if(tb!=-1){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        Toast.makeText(getActivity(),"插入成功",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLv1.setAdapter(new MyAdapter(apk,getContext()));
                                System.out.println("000000000000000000000"+apk.size());
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        initView(view);

        return view;
    }

    private void initView(View view) {
        mLv1 = (ListView) view.findViewById(R.id.lv1);
    }
}
