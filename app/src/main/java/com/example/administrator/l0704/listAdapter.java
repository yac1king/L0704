package com.example.administrator.l0704;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class listAdapter extends CursorAdapter{
    LayoutInflater inflater;
    public listAdapter(Context context, Cursor c) {
        super(context, c, 0);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.memolistlayout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout memoListLayout = view.findViewById(R.id.memoListLayout);
        TextView date = view.findViewById(R.id.memoDate);
        TextView content = view.findViewById(R.id.memocontent);
        TextView title = view.findViewById(R.id.memotitle);
        date.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        content.setText(cursor.getString(cursor.getColumnIndexOrThrow("content")));
        title.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        memoListLayout.setBackgroundColor(Color.parseColor(cursor.getString(4)));
    }
}
