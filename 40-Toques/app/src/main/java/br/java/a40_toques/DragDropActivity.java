package br.java.a40_toques;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class DragDropActivity extends AppCompatActivity
         implements View.OnTouchListener, View.OnDragListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);

        findViewById(R.id.myimage1).setOnTouchListener(this);
        findViewById(R.id.myimage2).setOnTouchListener(this);
        findViewById(R.id.myimage3).setOnTouchListener(this);
        findViewById(R.id.myimage4).setOnTouchListener(this);
        findViewById(R.id.topleft).setOnDragListener(this);
        findViewById(R.id.topright).setOnDragListener(this);
        findViewById(R.id.bottomleft).setOnDragListener(this);
        findViewById(R.id.bottomright).setOnDragListener(this);
    }

    public boolean onTouch(View view, MotionEvent me) {
        int action = me.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.VISIBLE);

            return true;
        }

        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent dragEvent) {

        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundResource(R.drawable.bg_hover);
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundResource(R.drawable.bg);
                break;

            case DragEvent.ACTION_DROP:
                View view = (View) dragEvent.getLocalState();
                ViewGroup ower = (ViewGroup) view.getParent();
                ower.removeView(view);
                LinearLayout container = (LinearLayout) v;
                container.addView(view);
                view.setVisibility(View.VISIBLE);


            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundResource(R.drawable.bg);
                View view2 = (View) dragEvent.getLocalState();
                view2.setVisibility(View.VISIBLE);
            default:
                break;

        }
        return true;
    }
}