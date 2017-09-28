package es.esy.mobilehost.android.savelife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DestActivity extends AppCompatActivity {

    private Context context;
    public static final String KEY = "DataSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest);
        context = this;
        ////帳號名稱設定
        TextView textView = (TextView) findViewById(R.id.dest_name);
        textView.setText("冒險家:" + getData("name"));

        findViewById(R.id.Bdest_lv1).setOnClickListener(new BdestLv1());
        findViewById(R.id.Bdest_lv2).setOnClickListener(new BdestLv2());
        findViewById(R.id.Bdest_lv3).setOnClickListener(new BdestLv3());
        findViewById(R.id.Bdest_time60).setOnClickListener(new Bdesttime60());
        findViewById(R.id.Bdest_time45).setOnClickListener(new Bdesttime45());
        findViewById(R.id.Bdest_time30).setOnClickListener(new Bdesttime30());
        findViewById(R.id.DCBtton).setOnClickListener(new DCBtton());
        findViewById(R.id.BHButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(DestActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    //難度設定確認事件
    private class DCBtton implements View.OnClickListener {
        TextView tsarySetView = (TextView) findViewById(R.id.dest_lvset);
        TextView timeSetView = (TextView) findViewById(R.id.dest_Time);
        @Override
        public void onClick(View view) {

            if(!tsarySetView.getText().equals("牌數")||!timeSetView.getText().equals("時間")){

                if (!tsarySetView.getText().equals("牌數")||timeSetView.getText().equals("時間")){

                    if(!timeSetView.getText().equals("時間")){
                        startActivity(new Intent().setClass(DestActivity.this, PlayActivity.class));
                        finish();
                    }else {
                        Toast.makeText(view.getContext(), "請選擇時間", Toast.LENGTH_LONG).show();
                        }
                }else{
                        Toast.makeText(view.getContext(), "請選擇難度", Toast.LENGTH_LONG).show();
                    }

            }else {
                Toast.makeText(view.getContext(), "請選擇難度和時間", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class BdestLv1 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setRC(5,4);
            TextView tsarySetView = (TextView) findViewById(R.id.dest_lvset);
            tsarySetView.setText("20"+"格");
        }
    }
    private class BdestLv2 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setRC(6,4);
            TextView tsarySetView = (TextView) findViewById(R.id.dest_lvset);
            tsarySetView.setText("24"+"格");        }
    }
    private class BdestLv3 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setRC(7,4);
            TextView tsarySetView = (TextView) findViewById(R.id.dest_lvset);
            tsarySetView.setText("28"+"格");        }
    }

    private class Bdesttime60 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setIntData("SaveTime", 60);
            TextView timeSetView = (TextView) findViewById(R.id.dest_Time);
            timeSetView.setText("60" + "秒");
        }
    }
    private class Bdesttime45 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setIntData("SaveTime", 45);
            TextView timeSetView = (TextView) findViewById(R.id.dest_Time);
            timeSetView.setText("45" + "秒");
        }
    }
    private class Bdesttime30 implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            setIntData("SaveTime", 30);
            TextView timeSetView = (TextView) findViewById(R.id.dest_Time);
            timeSetView.setText("30" + "秒");
        }
    }


    //設定卡牌數量
    public void setRC(int rowCount,int columeCount){
        setIntData("SaveLsRow", rowCount);
        setIntData("SaveLsColume", columeCount);
    }
    //設定檔儲存
    public void setIntData(String key, int value) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor PE = spref.edit();
        PE.putInt(key, value);
        PE.commit();
    }
    //設定檔讀取
    public String getData(String key) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String strValue = spref.getString(key, null);
        return strValue;
    }

}
