package com.example.macbookair.laprichat.Fragments;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macbookair.laprichat.R;

/**
 * Created by macbookair on 13/07/2017.
 */

public class RequestsFragment extends Fragment {
    private static final String TAG = "RequestsFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_fragment,container,false);
        TextView txtView = (TextView) view.findViewById(R.id.page_fragment_textView);
        return view;

    }
}
