package com.example.administrator.monthtest1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import static com.example.administrator.monthtest1.R.id.butFrag1;

public class SecondActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * 好医生
     */
    private Button mButDoctor;
    /**
     * 清除缓存
     */
    private Button mButClear;
    private LinearLayout mL1;
    /**
     * 基因检测
     */
    private Button mButFrag1;
    /**
     * 药物
     */
    private Button mButFrag2;
    /**
     * 课题
     */
    private Button mButFrag3;
    private LinearLayout mL2;
    private FrameLayout mFrame;
    private RelativeLayout mActivitySecond;
    private Fragment01 fragment01;
    private Fragment02 fragment02;
    private Fragment03 fragment03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
        /**
         * 2)编写Activity和界面，界面中包含三个导航，每个导航对应的页面是Fragment，点击导航切换下方的Fragment（10分）
         */
    }

    private void initView() {
        mButDoctor = (Button) findViewById(R.id.butDoctor);
        mButDoctor.setOnClickListener(this);
        mButClear = (Button) findViewById(R.id.butClear);
        mButClear.setOnClickListener(this);
        mL1 = (LinearLayout) findViewById(R.id.l1);
        mButFrag1 = (Button) findViewById(butFrag1);
        mButFrag1.setOnClickListener(this);
        mButFrag2 = (Button) findViewById(R.id.butFrag2);
        mButFrag2.setOnClickListener(this);
        mButFrag3 = (Button) findViewById(R.id.butFrag3);
        mButFrag3.setOnClickListener(this);
        mL2 = (LinearLayout) findViewById(R.id.l2);
        mFrame = (FrameLayout) findViewById(R.id.frame);
        mActivitySecond = (RelativeLayout) findViewById(R.id.activity_second);
        fragment01 = new Fragment01();
        fragment02 = new Fragment02();
        fragment03 = new Fragment03();
        mButFrag1.setSelected(true);
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.butDoctor:
                boolean netState = getNetState();
                if(netState==false){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("是否设置网络");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                        }
                    });
                    builder.create().show();
                }else{
                    Toast.makeText(SecondActivity.this,"有网！！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.butClear:
                ImageLoader.getInstance().clearMemoryCache();//清除内存
                ImageLoader.getInstance().clearDiskCache();//清除sd卡
                Toast.makeText(SecondActivity.this,"清除OK",Toast.LENGTH_SHORT).show();
                break;
            case butFrag1:
                mButFrag1.setSelected(true);
                mButFrag2.setSelected(false);
                mButFrag3.setSelected(false);
                transaction.replace(R.id.frame,fragment01).commit();
                break;
            case R.id.butFrag2:
                mButFrag2.setSelected(true);
                mButFrag1.setSelected(false);
                mButFrag3.setSelected(false);
                transaction.replace(R.id.frame,fragment02).commit();
                break;
            case R.id.butFrag3:
                mButFrag3.setSelected(true);
                mButFrag1.setSelected(false);
                mButFrag2.setSelected(false);
                transaction.replace(R.id.frame,fragment03).commit();
                break;
        }
    }
    public boolean getNetState(){
        ConnectivityManager manager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null){
            return true;
        }
        return false;
    }
}
