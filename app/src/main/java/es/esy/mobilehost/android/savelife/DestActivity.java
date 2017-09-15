package es.esy.mobilehost.android.savelife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import es.esy.mobilehost.android.savelife.Data.TemporaryData;
import es.esy.mobilehost.android.savelife.Data.UserDataDAO;


public class DestActivity extends AppCompatActivity {

    private Spinner LsarySpinner;//喧告Spinner物件
    private Spinner TsarySpinner;

    private String[] lsary = { "簡單", "普通", "困難" };//喧告難度陣列
    private ArrayAdapter<String> lsarylistAdapter; //喧告listAdapter物件

    private String[] tsary = {"30秒", "60秒" ,"90秒"};//喧告時間陣列
    private ArrayAdapter<String> tsarylistAdapter; //喧告listAdapter物件

    private Context context;
    public static final String KEY = "DataSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest);
        context = this;

        //帳號名稱設定
        TextView textView = (TextView) findViewById(R.id.dest_name);
        textView.setText(getData("name"));

        // 建立TsarySpinner的傾聽者物件
        TsarySpinner = (Spinner) findViewById(R.id.TimeSet);
        tsarylistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tsary);
        TsarySpinner.setAdapter(tsarylistAdapter);
        TsarySet();

        // 建立LsarySpinner的傾聽者物件
        LsarySpinner = (Spinner) findViewById(R.id.LvSet);
        lsarylistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsary);
        LsarySpinner.setAdapter(lsarylistAdapter);
        LsarySet();
        findViewById(R.id.DCBtton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(DestActivity.this, PlayActivity.class));
            }
        });

        findViewById(R.id.BHButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(DestActivity.this, HomeActivity.class));
            }
        });
    }

    //時間設定
    private void TsarySet(){
        TsarySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView,
                                       View view, int position, long id){
                if(TsarySpinner.getSelectedItem().toString().equals("30秒")){
                    try {
                        setData("SaveTime", 30);
                        //Toast.makeText(dest.this, SET+"" , Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(context, R.string.erro,
                                Toast.LENGTH_SHORT).show();
                    }
                }else if(TsarySpinner.getSelectedItem().toString().equals("60秒")){
                    try {
                        setData("SaveTime", 60);
                        //Toast.makeText(dest.this, SET+"" , Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(context, R.string.erro,
                                Toast.LENGTH_SHORT).show();
                    }
                }else if(TsarySpinner.getSelectedItem().toString().equals("90秒")){
                    try {
                        setData("SaveTime", 90);
                        //Toast.makeText(dest.this, SET+"" , Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(context, R.string.erro,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
    }

    //難度設定(卡牌數量)
    private void LsarySet(){
        LsarySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView,
                                       View view, int position, long id){
                if(LsarySpinner.getSelectedItem().toString().equals("簡單")){
                    try {
                        setRC(5,4);
                        //Toast.makeText(dest.this,rowCount+" X "+columeCount ,Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(context, R.string.erro,
                                Toast.LENGTH_SHORT).show();
                    }
                }else if(LsarySpinner.getSelectedItem().toString().equals("普通")){
                    try {
                        setRC(6,4);
                        //Toast.makeText(dest.this, rowCount+" X "+columeCount , Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(context, R.string.erro,
                                Toast.LENGTH_SHORT).show();
                    }
                }else if(LsarySpinner.getSelectedItem().toString().equals("困難")){
                    try {
                        setRC(7,4);
                        //Toast.makeText(dest.this, rowCount+" X "+columeCount , Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(context, R.string.erro,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            public void onNothingSelected(AdapterView arg0) {
            }
        });
    }

    //設定卡牌數量
    public void setRC(int rowCount,int columeCount){
        setData("SaveLsRow", rowCount);
        setData("SaveLsColume", columeCount);
    }

    //設定檔儲存
    public void setData(String key, int value)
    {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor PE = spref.edit();
        PE.putInt(key, value);
        PE.commit();
    }
    //設定檔讀取
    public String getData(String key)
    {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String strValue = spref.getString(key, null);
        return strValue;
    }

}
