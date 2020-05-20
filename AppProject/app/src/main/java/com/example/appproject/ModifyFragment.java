package com.example.appproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class ModifyFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if(activity != null){
            ((NavigationActivity)activity).setActionBarTitle("개인 정보 설정");
        }
    }
    private String mid;
    private String mpw;
    private String mname;
    private String id, pw, name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.modify_layout, container, false);

        final TextView mID = (TextView) rootView.findViewById(R.id.modify_id);
        final TextView mPw = (TextView)rootView.findViewById(R.id.modify_pw);
        final TextView mName = (TextView)rootView.findViewById(R.id.modify_name);
        final Button modifyButton = rootView.findViewById(R.id.modify_Button);

        if (getArguments() != null) {
            mid = getArguments().getString("mid");
            mpw = getArguments().getString("mpw");
            mname = getArguments().getString("mname");
        }

        mID.setText(mid);
        mPw.setText(mpw);
        mName.setText(mname);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = mID.getText().toString();
                pw = mPw.getText().toString();
                name = mName.getText().toString();

                ((NavigationActivity)getActivity()).mid = id;
                ((NavigationActivity)getActivity()).mpw = pw;
                ((NavigationActivity)getActivity()).mname = name;
                ((NavigationActivity)getActivity()).onClickModify();
            }
        });

        return rootView;
    }
}