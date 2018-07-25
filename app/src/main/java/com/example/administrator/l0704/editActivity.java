package com.example.administrator.l0704;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class editActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText content,title,memodate;
    private Button btnsave,btncancel;
    private Intent i;
    private Bundle bundle;
    private dbadapter dbadapter;
    private int index;
    private int mY,mM,mD;
    private ConstraintLayout conLo;
    private String BGcolor;

    private Spinner spinner;
    private ArrayList<colorSelectList> colorList;
    private spinneradapter SpAda;


//----------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInf = getMenuInflater();
        menuInf.inflate(R.menu.editmenu,menu);
        return true;
    }
//menu上的刪除按鈕----------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delmemo:
                Boolean isDeleted = dbadapter.deleteMemo(index);
                if(isDeleted) Toast.makeText(editActivity.this, "已刪除!", Toast.LENGTH_LONG).show();
                i = new Intent(editActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//OnCreate----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//----------------------------------------------------------------------------------------------
        title = findViewById(R.id.editTitle);
        content = findViewById(R.id.editContent);
        memodate = findViewById(R.id.editDate);
        btnsave = findViewById(R.id.editSave);
        btncancel = findViewById(R.id.editCancel);
        conLo = findViewById(R.id.editLayout);

        dbadapter = new dbadapter(this);

        spinner = findViewById(R.id.spinner);

        BGcolor = "#5F70B6";
//輸入colorlist資料----------------------------------------------------------------------------------------------
        colorList = new ArrayList<colorSelectList>();
        colorList.add(new colorSelectList("藍色", "#5F70B6"));
        colorList.add(new colorSelectList("紅色", "#F67181"));
        colorList.add(new colorSelectList("綠色", "#7DDB65"));
        colorList.add(new colorSelectList("黃色", "#FFD675"));

//        Log.i("(っ・ω・）っ≡≡≡≡≡≡☆",colorList.get(0).getColorName());
//        Log.i("(っ・ω・）っ≡≡≡≡≡≡=☆",colorList.get(0).getColorCode());
        SpAda = new spinneradapter(this,colorList);
        spinner.setAdapter(SpAda);


//----------------------------------------------------------------------------------------------
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BGcolor = colorList.get(position).getColorCode();
                Log.i("(っ・ω・）っ≡≡≡≡≡≡☆",BGcolor);
                conLo.setBackgroundColor(Color.parseColor(BGcolor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//----------------------------------------------------------------------------------------------
        btncancel.setOnClickListener(this);
        btnsave.setOnClickListener(this);

//傳來的type是edit參數時----------------------------------------------------------------------------------------------
        bundle = this.getIntent().getExtras();
        index = bundle.getInt("_id");
        if(bundle.getString("type").equals("edit")) {
            Cursor cursor = dbadapter.queryById(bundle.getInt("_id"));

            //index = cursor.getInt(0);
            title.setText(cursor.getString(1));
            content.setText(cursor.getString(2));
            memodate.setText(cursor.getString(3));
            BGcolor = cursor.getString(4);
            Log.i("(∩｀-´)⊃━✿✿✿✿✿✿",BGcolor);
            conLo.setBackgroundColor(Color.parseColor(BGcolor));
            spinner.setAdapter(SpAda);
            switch (BGcolor){
                case "#5F70B6":
                    spinner.setSelection(0);
                    break;
                case "#F67181":
                    spinner.setSelection(1);
                    break;
                case "#7DDB65":
                    spinner.setSelection(2);
                    break;
                case "#FFD675":
                    spinner.setSelection(3);
                    break;
            }
        }else {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sd = sDateFormat.format(new java.util.Date());
            memodate.setText(sd);
        }
    }


//----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        String st = title.getText().toString();
        String sc = content.getText().toString();
        String sd = memodate.getText().toString();
        String scol = BGcolor;
//日期選擇----------------------------------------------------------------------------------------------
        switch (v.getId()){
            case R.id.editDate:
                final Calendar c = Calendar.getInstance();
                mY = c.get(Calendar.YEAR);
                mM = c.get(Calendar.MONTH);
                mD = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog DPD = new DatePickerDialog(editActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        memodate.setText(year + "-" + (month+1) + "-" +dayOfMonth);
                    }
                },mY,mM,mD);
                //DatePickerDialog DPD = new DatePickerDialog(editActivity.this);
                DPD.show();
                break;
//確定按鈕----------------------------------------------------------------------------------------------
//確定按鈕(編輯狀態)----------------------------------------------------------------------------------------------
            case R.id.editSave:
                if(bundle.getString("type").equals("edit")) {
                    try{
                        dbadapter.updateMemo(index,st, sc,sd,scol);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
//確定按鈕(新增狀態)----------------------------------------------------------------------------------------------
                }else {
                    try {

                        //Log.i("(∩｀-´)⊃━炎炎炎炎炎",date);
                        dbadapter.createMemo(st,sc,sd,scol);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                break;
//按下確定按鈕按下取消按鈕----------------------------------------------------------------------------------------------
            case R.id.editCancel:
//                i = new Intent(editActivity.this,MainActivity.class);
//                startActivity(i);
                finish();
                break;

        }
    }
}
