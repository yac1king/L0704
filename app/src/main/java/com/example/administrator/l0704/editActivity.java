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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
/*/輸入colorlist資料----------------------------------------------------------------------------------------------
        colorList = new ArrayList<colorSelectList>();
        colorList.add(new colorSelectList("藍色", "#5F70B6"));
        colorList.add(new colorSelectList("紅色", "#F67181"));
        colorList.add(new colorSelectList("綠色", "#7DDB65"));
        colorList.add(new colorSelectList("黃色", "#FFD675"));
        SpAda = new spinneradapter(this,colorList);

        spinner.setAdapter(SpAda);
*/
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

            String setCol = cursor.getString(4);
            Toast.makeText(getBaseContext(),setCol,Toast.LENGTH_SHORT).show();
            switch (setCol){
                case "blue":
                    conLo.setBackgroundColor(Color.rgb(50,100,255));
                    break;
            }
        }

    }
//----------------------------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        String st = title.getText().toString();
        String sc = content.getText().toString();
        String sd = memodate.getText().toString();
        String scol = "blue";
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
                DPD.show();
                break;
//----------------------------------------------------------------------------------------------

            case R.id.editSave:
                if(bundle.getString("type").equals("edit")) {
                    try{
                        dbadapter.updateMemo(index,st, sc,sd,scol);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    }
                }else {

                    try {
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
                i = new Intent(editActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

        }
    }
}
