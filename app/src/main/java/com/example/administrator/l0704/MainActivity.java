package com.example.administrator.l0704;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView lvmemo;
    private dbadapter dbadapter;
    private SimpleCursorAdapter scAdapter;
    private Intent i;
//產生menu----------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInf = getMenuInflater();
        menuInf.inflate(R.menu.listmenu,menu);
        return true;
    }
//menu新增的動作----------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addMemo:
                Intent intent = new Intent();
                intent.putExtra("type","add");
                intent.setClass(MainActivity.this, editActivity.class );
                startActivity(intent);
                //finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

//----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//連線database----------------------------------------------------------------------------------------------
        lvmemo = findViewById(R.id.memolist);
        dbadapter = new dbadapter(this);
        if(dbadapter.listContacts().getCount()==0){
            //lvmemo.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(),"沒有資料",Toast.LENGTH_SHORT).show();
        }
//讀取database資料秀到清單上----------------------------------------------------------------------------------------------
        Cursor cur = dbadapter.listContacts();
        String[] col = new String[]{dbadapter.KEY_TITLE,dbadapter.KEY_CONTENT,dbadapter.KEY_DATE};
        int[] to = new int[]{R.id.memotitle,R.id.memocontent,R.id.memoDate};
        scAdapter = new SimpleCursorAdapter(this,R.layout.memolistlayout,cur,col,to,0);
        lvmemo.setAdapter(scAdapter);
//選擇要變更的資料----------------------------------------------------------------------------------------------
        lvmemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor itemC = (Cursor) lvmemo.getItemAtPosition(position);
                int itemI = itemC.getInt(itemC.getColumnIndexOrThrow("_id"));
                Toast.makeText(getBaseContext(), "id:" + String.valueOf(itemI), Toast.LENGTH_SHORT).show();
                i = new Intent();
                i.putExtra("type","edit");
                i.putExtra("_id", itemI);
                i.setClass(MainActivity.this, editActivity.class);
                startActivity(i);
            }
        });
//----------------------------------------------------------------------------------------------

    }
}
