package com.apps.esampaio.tarefas.view.listeners;

import android.content.ContentValues;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by eduardo on 30/07/2016.
 */

public class GestureDetectorTouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener{
    private GestureDetector gestureDetector ;
    public GestureDetectorTouchListener(Context context){
        gestureDetector = new GestureDetector(context,this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }
}
