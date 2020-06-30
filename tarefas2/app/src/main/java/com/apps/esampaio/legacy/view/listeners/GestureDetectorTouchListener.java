package com.apps.esampaio.legacy.view.listeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;



public class GestureDetectorTouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener{
    private GestureDetector gestureDetector ;
    public GestureDetectorTouchListener(Context context){
        gestureDetector = new GestureDetector(context,this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

}
