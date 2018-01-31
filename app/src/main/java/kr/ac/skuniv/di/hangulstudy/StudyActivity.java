package kr.ac.skuniv.di.hangulstudy;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * Created by namgiwon on 2018. 1. 31..
 */

public class StudyActivity extends FragmentActivity{

    RelativeLayout hangulja;
    hangul hangul;
    private DrawLine drawLine = null; // 선그리기 뷰 객체
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_startstudy);


        hangulja = (RelativeLayout) findViewById(R.id.main_hangulja);
//        hangulja.setOnTouchListener(tListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        hangul = new hangul();

        fragmentTransaction.replace(R.id.main_hangulja, hangul);
        fragmentTransaction.commitNow();

    }




//    View.OnTouchListener tListener = new View.OnTouchListener(){
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            float x = motionEvent.getX();
//            float y = motionEvent.getY();
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//
//            Rect l = new Rect();
//            hangulja.getGlobalVisibleRect(l);
//
//            if ((int) view.getId() < 100) {         //10 이란 숫자는 그려진 최대 id값
//                x = x + location[0];                // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 왼쪽마진값을 더한다
//                y = y +location[1] -l.top;          // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 위쪽마진 값을 더한다
//            }
//            switch (motionEvent.getAction()& MotionEvent.ACTION_MASK){
//                case MotionEvent.ACTION_DOWN: {
//                    return false;
//                }
//                case MotionEvent.ACTION_MOVE: {
//                        Log.d("asdfasdf","asdfasdf");
//                    return true;
//                }
//                case MotionEvent.ACTION_UP :{
//                    return true;
//                }
//            }
//            return true;
//        }
//    };


//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus)
//    {
//        //hasFocus : 앱이 화면에 보여졌을때 true로 설정되어 호출됨.
//        //만약 그리기 뷰 전역변수에 값이 없을경우 전역변수를 초기화 시킴.
//        if(hasFocus && drawLine == null)
//        {
//            //그리기 뷰가 보여질(나타날) 레이아웃 찾기..
//            if(hangulja != null) //그리기 뷰가 보여질 레이아웃이 있으면...
//            {
//                //그리기 뷰 레이아웃의 넓이와 높이를 찾아서 Rect 변수 생성.
//                Rect rect = new Rect(0, 0,
//                        hangulja.getMeasuredWidth(), hangulja.getMeasuredHeight());
//
//                //그리기 뷰 초기화..
//                drawLine = new DrawLine(  this, rect);
//
//                //그리기 뷰를 그리기 뷰 레이아웃에 넣기 -- 이렇게 하면 그리기 뷰가 화면에 보여지게 됨.
//                hangulja.addView(drawLine);
//            }
//            if(drawLine != null) drawLine.setLineColor(Color.BLACK);
//        }
//        super.onWindowFocusChanged(hasFocus);
//    }


}
