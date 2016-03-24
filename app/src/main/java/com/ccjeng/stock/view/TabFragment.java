package com.ccjeng.stock.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.ccjeng.stock.R;

/**
 * Created by andycheng on 2016/3/24.
 */
public class TabFragment extends Fragment {
    private static final String TAG = "TabFragment";
    private static final String ARG_POSITION = "position";

    private int position;

    public static TabFragment newInstance(int position) {
        TabFragment f = new TabFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_info, container, false);
       // TextView textView = (TextView) view;
       // textView.setText("Fragment #" + position);
        //v.setBackgroundResource(R.color.windowBackground);
        Log.d(TAG, "Tab " + (position + 1));

        return view;
    }
}