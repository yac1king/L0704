package com.example.administrator.l0704;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class spinneradapter  extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private ArrayList<colorSelectList> colors;

    public spinneradapter(Context c,ArrayList<colorSelectList> colors) {
        layoutInflater = (LayoutInflater)c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return colors.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        colorSelectList colorsel = (colorSelectList)getItem(position);

        View v = layoutInflater.inflate(R.layout.spinner_view,null);
        ImageView colorimg = v.findViewById(R.id.spinnerImg);
        colorimg.setBackgroundColor(Color.parseColor(colorsel.colorCode));
        TextView colortext = v.findViewById(R.id.spinnerText);
        colortext.setText(colorsel.colorName);
        return null;
    }
}
