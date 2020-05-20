package com.example.appproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class WriteMessageFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if(activity != null){
            ((NavigationActivity)activity).setActionBarTitle("메시지 작성");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.writemessage_layout, container, false);


        final TextView sId = rootView.findViewById(R.id.receivepeople);
        final TextView sTitle = rootView.findViewById(R.id.title);
        final TextView sContents = rootView.findViewById(R.id.contents);
        final Button sendButton = rootView.findViewById(R.id.sendButton);
        final Button cancelButton = rootView.findViewById(R.id.cancelButton);



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((NavigationActivity)getActivity()).sid = sId.getText().toString();
                ((NavigationActivity)getActivity()).stitle = sTitle.getText().toString();
                ((NavigationActivity)getActivity()).scontents = sContents.getText().toString();
                ((NavigationActivity)getActivity()).onClickSend();

                int checkSend = 0;
                if(getArguments() != null){
                    checkSend = getArguments().getInt("checkSend");
                }
                if(checkSend == 1){
                    sId.setText(null);
                    sTitle.setText(null);
                    sContents.setText(null);
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity)getActivity()).onClickCancel();
                sId.setText(null);
                sTitle.setText(null);
                sContents.setText(null);
            }
        });





        return rootView;
    }
}