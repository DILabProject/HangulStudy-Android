package kr.ac.skuniv.di.hangulstudy;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class MainActivity extends FragmentActivity {
    Intent startstudy;
    boolean isStart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        startstudy = new Intent(this,StudyActivity.class);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isStart==false){
                    isStart=true;
                    startActivity(startstudy);
                  finish();
                }
            }
        }, 3000);

    }
}
