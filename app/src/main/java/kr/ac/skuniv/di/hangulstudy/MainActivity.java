package kr.ac.skuniv.di.hangulstudy;

import android.content.Intent;
import android.graphics.Path;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.util.LinkedList;

import kr.ac.skuniv.di.hangulstudy.sharedmemory.SharedMemory;

public class MainActivity extends FragmentActivity {
    Intent studylist;
    static boolean isStart;
    SharedMemory sharedMemory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sharedMemory = SharedMemory.getinstance();
        studylist = new Intent(this,StudyListActivity.class);

        isStart = false;
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isStart==false){
                    isStart=true;
                    startActivity(studylist);
                  finish();
                }
            }
        }, 3000);

    }
}
