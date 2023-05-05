package com.cursoandroid.youtube.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import com.cursoandroid.youtube.R;

public class SingleViewTouchableMotionLayout extends MotionLayout {

    private volatile View viewToDetectTouch;
    private final Rect viewRect = new Rect();
    private boolean touchStarted = false;

    public View getViewToDetectTouch(){//lazy initialization
        View result = viewToDetectTouch;
        if(result == null){//Frist check - no locking
            synchronized (this){
                result = viewToDetectTouch;
                if (result == null) {//second check - with locking
                    viewToDetectTouch = findViewById(R.id.playerBackground);
                }
            }
        }
        return viewToDetectTouch;
    }

    private final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e){
            transitionToEnd();
            return false;
        }
    });

   public SingleViewTouchableMotionLayout(Context context, AttributeSet attributeSet){
       super(context,attributeSet);

       setTransitionListener(new TransitionListener() {
           @Override
           public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {}

           @Override
           public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {}

           @Override
           public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
               touchStarted = false;
           }

           @Override
           public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {}
       });
   }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       //gestureDetector.onTouchEvent(event);
       switch (event.getActionMasked()){
           case MotionEvent.ACTION_UP:
               case MotionEvent.ACTION_CANCEL:
                   touchStarted = false;
                   return super.onTouchEvent(event);
       }
       if (!touchStarted){
           if (getViewToDetectTouch() != null) {
               getViewToDetectTouch().getHitRect(viewRect);
               touchStarted = viewRect.contains((int) event.getX(),(int) event.getY());
           }
       }
        return touchStarted && super.onTouchEvent(event);
    }
}
