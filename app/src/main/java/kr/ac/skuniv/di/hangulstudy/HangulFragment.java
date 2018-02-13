package kr.ac.skuniv.di.hangulstudy;
//confirm

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;


import kr.ac.skuniv.di.hangulstudy.VO.PointVO;
import kr.ac.skuniv.di.hangulstudy.sharedmemory.SharedMemory;


/**
 * Created by namgiwon on 2018. 1. 30..
 */

public class HangulFragment extends Fragment {
    DrawLine drawLine;
    RelativeLayout parentLayout;
    Gson gson;
    int backgroundid =1000;
    int id = 1;
    int blackBlockSize = 100;
    int clearBlockSize = 200;

    int x1;
    int x2;
    int y1;
    int y2;
    ArrayList<View> GuideLine = new ArrayList<View>();
    ArrayList<Integer> CheckIDList = new ArrayList<Integer>();
    JsonArray jsonarr = new JsonArray();
    JsonArray jsonarr1 = new JsonArray();
    JsonArray jsonarr2 = new JsonArray();

    JsonObject jsonobj = new JsonObject();
    JsonObject jsonobj1 = new JsonObject();
    JsonObject jsonobj2 = new JsonObject();
    JsonObject jsono = new JsonObject();
    ScalableLayout sl;
    ScalableLayout lastSL;
    ScalableLayout sl1;
    ScalableLayout sl2;

    String answerCho = "2";
    String answerJung = "1,1";
    String answerJong = "2";
    int a[] = {2,1,1,2};
    int index = 0;
    int count = 0;

    SharedMemory sharedMemory;
    public HangulFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        sharedMemory = SharedMemory.getinstance();
        gson = new Gson();
        jsonobj.addProperty("x1","0");
        jsonobj.addProperty("y1","0");
        jsonobj.addProperty("x2","200");
        jsonobj.addProperty("y2","0");
        jsonarr.add(jsonobj);
        jsonobj = new JsonObject();
        jsonobj.addProperty("x1","200");
        jsonobj.addProperty("y1","100");
        jsonobj.addProperty("x2","200");
        jsonobj.addProperty("y2","300");
        jsonarr.add(jsonobj);
        jsonobj = new JsonObject();

        jsonobj1.addProperty("x1","400");
        jsonobj1.addProperty("y1","0");
        jsonobj1.addProperty("x2","400");
        jsonobj1.addProperty("y2","300");
        jsonarr.add(jsonobj1);
        jsonobj1 = new JsonObject();

        jsonobj1.addProperty("x1","400");
        jsonobj1.addProperty("y1","200");
        jsonobj1.addProperty("x2","500");
        jsonobj1.addProperty("y2","200");
        jsonarr.add(jsonobj1);
        jsonobj1 = new JsonObject();


        jsonobj2.addProperty("x1","100");
        jsonobj2.addProperty("y1","500");
        jsonobj2.addProperty("x2","100");
        jsonobj2.addProperty("y2","600");
        jsonarr.add(jsonobj2);
        jsonobj2 = new JsonObject();

        jsonobj2.addProperty("x1","200");
        jsonobj2.addProperty("y1","600");
        jsonobj2.addProperty("x2","500");
        jsonobj2.addProperty("y2","600");
        jsonarr.add(jsonobj2);
        jsonobj2 = new JsonObject();



//        jsono.add("cho", jsonarr);
//        jsono.add("jung", jsonarr1);
//        jsono.add("jong", jsonarr2);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {   View view = inflater.inflate(R.layout.fragment_hangul,null);

        parentLayout = view.findViewById(R.id.parent);
        parentLayout.setOnTouchListener(tListener);
        parentLayout.setOnDragListener(dListener);
        parentLayout.setClickable(false);



//        sl = new ScalableLayout(getActivity(),400,450);
//        sl1 = new ScalableLayout(getActivity(),300,450);
//        sl2 = new ScalableLayout(getActivity(),700,250);
        sl = new ScalableLayout(getActivity(),700,700);
        lastSL = new ScalableLayout(getActivity(),700,700);
//        lastSL.setId(1000);
        PaintBackground(sl);
        PaintWord(jsonarr,sl);
        PaintGuideLine(jsonarr,sl);
//        PaintWord(jsonarr1,sl1);
//        PaintWord(jsonarr2,sl2);
        lastSL.addView(sl,0,0,700,700);
//        lastSL.addView(sl1,400,0,300,450);
//        lastSL.addView(sl2,0,450,700,250);
        parentLayout.addView(lastSL);
        return view;
    }

    View.OnTouchListener tListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int[] location = new int[2];

            view.getLocationOnScreen(location);

            Rect l = new Rect();
            parentLayout.getGlobalVisibleRect(l);

            if ((int) view.getId() < 2000) {         //10 이란 숫자는 그려진 최대 id값
                x = x + location[0]-l.left;                // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 왼쪽마진값을 더한다
                y = y +location[1] -l.top;          // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 위쪽마진 값을 더한다
            }
            switch (motionEvent.getAction()& MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN: {
                    sharedMemory.getDrawLine().oldX = x;
                    sharedMemory.getDrawLine().oldY = y;
                    sharedMemory.getDrawLine().path.reset();
                    sharedMemory.getDrawLine().path.moveTo(x, y);

                    ClipData.Item item = new ClipData.Item(
                            (CharSequence) view.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    ClipData data = new ClipData("a", mimeTypes, item);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            null);
                    view.startDrag(data, // data to be dragged
                            shadowBuilder, // drag shadow
                            view, // 드래그 드랍할  Vew
                            0 // 필요없은 플래그
                    );
                    return false;
                }
                case MotionEvent.ACTION_MOVE: {
                    return true;
                }
                case MotionEvent.ACTION_UP :{
                    return true;
                }
            }
            return true;
        }
    };

    View.OnDragListener dListener = new View.OnDragListener(){
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            float x = dragEvent.getX();
            float y = dragEvent.getY();
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Rect l = new Rect();
            parentLayout.getGlobalVisibleRect(l);

            if ((int) view.getId() < 2000) {         //10 이란 숫자는 그려진 최대 id값
                x = x + location[0]-l.left;                // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 왼쪽마진값을 더한다
                y = y + location[1] - l.top;           // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 위쪽마진 값을 더한다
            }

            switch (dragEvent.getAction()) {
                // 이미지를 드래그 시작될때

                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                // 드래그한 이미지를 옮길려는 지역으로 들어왔을때

                case DragEvent.ACTION_DRAG_ENTERED:
                    CheckIDList.add(view.getId());
                    return false;

                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("aa",String.valueOf(dragEvent.getX()));
                    Log.d("ab",String.valueOf(x));
                    Log.d("ac",String.valueOf(l.left));
                    //포인트가 이동될때 마다 두 좌표(이전에눌렀던 좌표와 현재 이동한 좌료)간의 간격을 구한다.
                    float dx = Math.abs(x - sharedMemory.getDrawLine().oldX);
                    float dy = Math.abs(y - sharedMemory.getDrawLine().oldY);
                    //두 좌표간의 간격이 4px이상이면 (가로든, 세로든) 그리기 bitmap에 선을 그린다.
                    if (dx >= 4 || dy >= 4) {
                        //path에 좌표의 이동 상황을 넣는다. 이전 좌표에서 신규 좌표로..
                        //lineTo를 쓸수 있지만.. 좀더 부드럽게 보이기 위해서 quadTo를 사용함.
                        sharedMemory.getDrawLine().path.quadTo(sharedMemory.getDrawLine().oldX, sharedMemory.getDrawLine().oldY, x, y);
                        //포인터의 마지막 위치값을 기억한다.
                        sharedMemory.getDrawLine().oldX = x;
                        sharedMemory.getDrawLine().oldY = y;
                        //그리기 bitmap에 path를 따라서 선을 그린다.
                        sharedMemory.getDrawLine().canvas.drawPath(sharedMemory.getDrawLine().path, sharedMemory.getDrawLine().paint);
                    }
                    //화면을 갱신시킴... 이 함수가 호출 되면 onDraw 함수가 실행됨.
                    sharedMemory.getDrawLine().invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    sharedMemory.getStack().push(sharedMemory.getDrawLine().path);
                    sharedMemory.getDrawLine().path = new Path();
                    boolean isCollect = true; // 획 그리기 성공 체크
                    for(int i =0 ; i<CheckIDList.size();i++){
                        if(CheckIDList.get(i) > 100 && CheckIDList.get(i) < 2000){
                            isCollect = false;
                        }
                    }
                    if(!isCollect){Fail();}
                    else if(isCollect){Collect();}

                    CheckIDList.clear();
                    return true;
            }
            return true;
        }
    };

    public void Collect(){
        Log.d("index",String.valueOf(index));
        PaintGuideLine(jsonarr,sl);
    }
    public void Fail(){
        Toast.makeText(getContext(),"다시그려보세요",Toast.LENGTH_LONG).show();
        sharedMemory.getDrawLine().canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        if(sharedMemory.getStack().size() > 0 ) sharedMemory.getStack().pop();
        if(sharedMemory.getStack().size() > 0){
            for(int i = 0 ; i < sharedMemory.getStack().size(); i++){
                sharedMemory.getDrawLine().canvas.drawPath(sharedMemory.getStack().get(i),sharedMemory.getDrawLine().paint);
            }
        }
        sharedMemory.getDrawLine().invalidate();
    }


    public void PaintGuideLine(JsonArray jsonarr,ScalableLayout sl){  //정답 가이드라인 나오는곳
        if(GuideLine.size()!=0) {
            Log.d("ff","remove guideline");
            removeGuidLine();}

        for (int i = count; i <count+a[index] ; i++) {
            JsonObject jsonobj = jsonarr.get(i).getAsJsonObject();
            PointVO point = gson.fromJson(jsonobj,PointVO.class);
            String direct = PaintDirect(point);  // 그려야할 방향이 가로인지 세로인지 리턴값 = vertical or horizontal or round
            for(int j = 0;;j++){
                ImageView iv1 = new ImageView(getActivity());
                int ivTOP=0;
                int ivLEFT=0;
                iv1.setId(id);
                iv1.setClickable(true);
                id++;
                iv1.setOnTouchListener(tListener);
                iv1.setOnDragListener(dListener);
                iv1.setBackgroundResource(R.drawable.b1); // 이미지뷰 이미지지정 : 투명블럭(글자의 정답체크를 위한 투명 이미지)

                GuideLine.add(iv1); // arraylist 에 추가
                //글자블럭 param 설정
                if(direct.equals("horizontal") ) {
                    ivLEFT =point.getX1()+j*blackBlockSize;
                    ivTOP = point.getY1();
                }else if(direct.equals("vertical")){
                    ivLEFT =point.getX1();
                    ivTOP = point.getY1()+j*blackBlockSize;
                }
//                sl.addView(iv,ivLEFT,ivTOP,blackBlockSize,blackBlockSize);
                sl.addView(iv1,ivLEFT-(clearBlockSize-blackBlockSize)/2,ivTOP-(clearBlockSize-blackBlockSize)/2,clearBlockSize,clearBlockSize);
                if(direct.equals("horizontal") ) {
                    if(j == (point.getX2()-point.getX1())/blackBlockSize) break;
                }else if(direct.equals("vertical")){
                    if(j == ((point.getY2()-point.getY1())/blackBlockSize)) break;
                }
            }
        }
        count += a[index];
        index++;
        id = 0;
        Log.d("count",String.valueOf(count));
        Log.d("index",String.valueOf(index));
    }

    public void removeGuidLine(){
        for(int i = 0; i<GuideLine.size(); i++){
            sl.removeView(GuideLine.get(i));
        }
        GuideLine.clear();
    }

    public void PaintBackground(ScalableLayout sl){
        for(int i = 0; i < 7;i++){
            for(int j =0; j<7;j++){
                ImageView iv2 = new ImageView(getActivity());
                int ivTOP=0;
                int ivLEFT=0;
                iv2.setId(backgroundid);
                backgroundid++;
//                iv.setOnTouchListener(tListener);
                iv2.setOnDragListener(dListener);
                iv2.setBackgroundResource(R.drawable.a1); //이미지뷰 이미지지정 :  글자블럭

                //글자블럭 param 설정
                ivLEFT =i*blackBlockSize;
                ivTOP = j*blackBlockSize;
                sl.addView(iv2,ivLEFT,ivTOP,blackBlockSize,blackBlockSize);
            }
        }
    }
    public void PaintWord(JsonArray jsonarr , ScalableLayout sl){
        for (int i = 0; i < jsonarr.size(); i++) {
            JsonObject jsonobj = jsonarr.get(i).getAsJsonObject();
            PointVO point = gson.fromJson(jsonobj,PointVO.class);
            String direct = PaintDirect(point);  // 그려야할 방향이 가로인지 세로인지 리턴값 = vertical or horizontal or round
            for(int j = 0;;j++){
                Log.d("gg","generate view");
                ImageView iv = new ImageView(getActivity());
                int ivTOP=0;
                int ivLEFT=0;
//                iv.setOnTouchListener(tListener);
//                iv.setOnDragListener(dListener);
                iv.setBackgroundResource(R.drawable.b); //이미지뷰 이미지지정 :  글자블럭
                //글자블럭 param 설정
                if(direct.equals("horizontal") ) {
                    ivLEFT =point.getX1()+j*blackBlockSize;
                    ivTOP = point.getY1();
                }else if(direct.equals("vertical")){
                    ivLEFT =point.getX1();
                    ivTOP = point.getY1()+j*blackBlockSize;
                }
                sl.addView(iv,ivLEFT,ivTOP,blackBlockSize,blackBlockSize);
//                sl.addView(iv1,ivLEFT-(clearBlockSize-blackBlockSize)/2,ivTOP-(clearBlockSize-blackBlockSize)/2,clearBlockSize,clearBlockSize);
                if(direct.equals("horizontal") ) {
                    if(j == (point.getX2()-point.getX1())/blackBlockSize) break;
                }else if(direct.equals("vertical")){
                    if(j == ((point.getY2()-point.getY1())/blackBlockSize)) break;
                }
            }
        }
    }

    public String PaintDirect(PointVO point){
        if(point.getX1() < point.getX2() && point.getY1() == point.getY2())
            return "horizontal";
        else if(point.getX1() == point.getX2() && point.getY1() < point.getY2())
            return "vertical";
        else return "round";
    }

//    public void PaintWord(JsonArray jsonarr , RelativeLayout rl){
//        for (int i = 0; i < jsonarr.size(); i++) {
//            JsonObject jsonobj = jsonarr.get(i).getAsJsonObject();
//            PointVO point = gson.fromJson(jsonobj,PointVO.class);
//            String direct = PaintDirect(point);  // 그려야할 방향이 가로인지 세로인지 리턴값 = vertical or horizontal or round
//            for(int j = 0;;j++){
//                Log.d("gg","generate view");
//                ImageView iv1 = new ImageView(getActivity());
//                ImageView iv = new ImageView(getActivity());
//                int ivTOP=0;
//                int ivLEFT=0;
//                iv1.setId(id);
//                iv1.setClickable(true);
//                id++;
//                iv1.setOnTouchListener(tListener);
//                iv1.setOnDragListener(dListener);
//                iv1.setBackgroundResource(R.drawable.b1); // 이미지뷰 이미지지정 : 투명블럭(글자의 정답체크를 위한 투명 이미지)
//                iv.setBackgroundResource(R.drawable.b); //이미지뷰 이미지지정 :  글자블럭
//
//                //글자블럭 param 설정
//                if(direct.equals("horizontal") ) {
//                    ivLEFT =point.getX1()+j*blackBlockSize;
//                    ivTOP = point.getY1();
//                }else if(direct.equals("vertical")){
//                    ivLEFT =point.getX1();
//                    ivTOP = point.getY1()+j*blackBlockSize;
//                }
//
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp.width = blackBlockSize;
//                lp.height = blackBlockSize;
//                lp.setMargins(ivLEFT,ivTOP,0,0);
//                iv.setLayoutParams(lp);
//                rl.addView(iv);
//
//                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp1.width = clearBlockSize;
//                lp1.height = clearBlockSize;
//                lp1.setMargins(ivLEFT-(clearBlockSize-blackBlockSize)/2,ivTOP-(clearBlockSize-blackBlockSize)/2,0,0);
//                iv1.setLayoutParams(lp1);
//                rl.addView(iv1);
////                rl.addView(iv1,ivLEFT-(clearBlockSize-blackBlockSize)/2,ivTOP-(clearBlockSize-blackBlockSize)/2);
//
//                if(direct.equals("horizontal") ) {
//                    if(j == (point.getX2()-point.getX1())/blackBlockSize) break;
//                }else if(direct.equals("vertical")){
//                    if(j == ((point.getY2()-point.getY1())/blackBlockSize)) break;
//                }
//            }
//        }
//    }


}
