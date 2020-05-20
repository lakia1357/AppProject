package com.example.appproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class ReadMessageFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if(activity != null){
            ((NavigationActivity)activity).setActionBarTitle("메시지 읽기");
        }
    }
   private String send, receive, title, getTime, contain;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.readmessage_layout, container, false);

        final TextView sendpeopleView = rootView.findViewById(R.id.sendpeopleView);
        final TextView receivepeopleView = rootView.findViewById(R.id.receivepeopleView);
        final TextView titleView = rootView.findViewById(R.id.titleView);
        final TextView containView = rootView.findViewById(R.id.containView);
        final TextView getTimeView = rootView.findViewById(R.id.getTimeView);


        if (getArguments() != null) {
            send = getArguments().getString("sanded");
            receive = getArguments().getString("received");
            title = getArguments().getString("titled");
            contain = getArguments().getString("contained");
            getTime = getArguments().getString("date");
        }

        sendpeopleView.setText(send);
        receivepeopleView.setText(receive);
        titleView.setText(title);
        containView.setText(contain);
        getTimeView.setText(getTime);

        return rootView;
    }
}