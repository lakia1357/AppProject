package com.example.appproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class SendMessageFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if(activity != null){
            ((NavigationActivity)activity).setActionBarTitle("받은 메시지 함");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_navigation, container, false);

        return rootView;
    }
}