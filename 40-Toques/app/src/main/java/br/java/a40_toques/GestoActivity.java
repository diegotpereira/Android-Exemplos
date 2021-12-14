package br.java.a40_toques;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestoActivity extends AppCompatActivity {

    private static final String TAG = "DominandoAndroid";
    GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesto);

        mDetector = new GestureDetectorCompat(this, mGestureListener);
        mDetector.setOnDoubleTapListener(mDoubleTapListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    GestureDetector.OnDoubleTapListener mDoubleTapListener =
            new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent motionEvent) {

                    Log.d(TAG, "onSingleTapConfirmed");
                    return true;
                }

                @Override
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    Log.d(TAG, "onDoubleTap");
                    return true;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                    Log.d(TAG, "onDoubleTapEvent");
                    return true;
                }
            };

    GestureDetector.OnGestureListener mGestureListener =
            new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    Log.d(TAG, "onDown");
                    return true;
                }

                @Override
                public void onShowPress(MotionEvent motionEvent) {
                    Log.d(TAG, "onShowPress");
                }

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    Log.d(TAG, "onSingleTapUp");
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1,
                                        float v, float v1) {
                    Log.d(TAG, "onScroll");
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent motionEvent) {
                    Log.d(TAG, "onLongPress");
                }

                @Override
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1,
                                       float v, float v1) {
                    Log.d(TAG, "onFling");
                    return true;
                }
            };
}