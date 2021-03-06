package com.bearcub.recyclerviewlibrary;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Home on 6/10/2015.
 */
public abstract class ExtRecyclerView extends Fragment{
    public OnTouchItemSelectedListener mCallBack;

    public ExtRecyclerView(){}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (OnTouchItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTouchItemSelectedListener");
        }
    }

    public class ExtTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private ExtClickListener extClickListener;


        public ExtTouchListener(Context context, final RecyclerView recyclerView, final ExtClickListener extClickListener){
            this.extClickListener = extClickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View view = recyclerView.findChildViewUnder(e.getX(),e.getY());

                    if(view!=null && extClickListener!=null){
                        extClickListener.onLongClick(view, recyclerView.getChildAdapterPosition(view));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View view = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

            if(view!=null && extClickListener!=null && gestureDetector.onTouchEvent(motionEvent)){
                extClickListener.onClick(view, recyclerView.getChildAdapterPosition(view));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }
    }

    public static interface ExtClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    // Container Activity must implement this interface
    public interface OnTouchItemSelectedListener {
        public void onTouchItemSelected(View view, int position);
    }
}
