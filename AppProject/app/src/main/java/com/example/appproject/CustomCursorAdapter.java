package com.example.appproject;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;
    public CustomCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView t_mailTitle = view.findViewById(R.id.mailID);
        t_mailTitle.setText(cursor.getString( cursor.getColumnIndex( "id" ) ));
        TextView t_mailContains = view.findViewById(R.id.mailTitle);
        t_mailContains.setText(cursor.getString( cursor.getColumnIndex( "title" ) ));
        TextView t_date = view.findViewById(R.id.date);
        t_date.setText(cursor.getString( cursor.getColumnIndex( "date" ) ));
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_message, parent, false);
    }

}
