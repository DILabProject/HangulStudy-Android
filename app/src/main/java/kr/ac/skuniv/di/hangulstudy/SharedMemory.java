package kr.ac.skuniv.di.hangulstudy;

import android.graphics.Path;

import java.util.LinkedList;

/**
 * Created by namgiwon on 2017. 11. 17..
 */

public class SharedMemory {
    private static SharedMemory sharedMemory = null;
    private static LinkedList<Path> stack = null;

    public static synchronized SharedMemory getinstance(){
        if(sharedMemory == null){
            sharedMemory = new SharedMemory();
        }
        if(stack == null){
            stack = new LinkedList<Path>();
        }
     return sharedMemory;
    }

    DrawLine drawLine;


    public static LinkedList<Path> getStack() {
        return stack;
    }

    public static void setStack(LinkedList<Path> stack) {
        SharedMemory.stack = stack;
    }

    public DrawLine getDrawLine() {
        return drawLine;
    }

    public void setDrawLine(DrawLine drawLine) {
        this.drawLine = drawLine;
    }
}
