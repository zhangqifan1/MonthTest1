package com.example.administrator.monthtest1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //进来就是一个判断第几次进入
        getData();
        /**
         * 1)第一次进入系统时，播放小机器人的从左到右的2秒动画，播放完成后进入图一主界面（5分），
         * 第二次进入系统不再播放动画，直接进入图一界面（5分）。（10分）
         */
        //先做补间动画：位移
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation.start();
        mImage.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            handler.sendEmptyMessage(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void getData() {
        SharedPreferences sp = getSharedPreferences("content", MODE_PRIVATE);
        boolean flag = sp.getBoolean("flag", false);
        if(flag==false){
            sp.edit().putBoolean("flag",true).commit();
        }
        if(flag==true){
            handler.sendEmptyMessage(0);
        }
    }

    private void initView() {
        mImage = (ImageView) findViewById(R.id.image);
    }
}
