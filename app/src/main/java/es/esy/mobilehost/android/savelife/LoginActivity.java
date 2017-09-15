package es.esy.mobilehost.android.savelife;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import es.esy.mobilehost.android.savelife.Data.UserDataDAO;

public class LoginActivity extends Activity {

    private UserDataDAO userDataDAO;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 建立資料存取物件
        userDataDAO = new UserDataDAO(this);

        //實做OnClickListener界面
        LayoutInflater inflater =  getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.activity_home, null);//找出第一個視窗
        final View view2 = inflater.inflate(R.layout.activity_newuser, null);//找出第一個視窗
        //setContentView(view1); //顯示第一個視

        findViewById(R.id.newUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(view2);
                startActivity(new Intent().setClass(LoginActivity.this, NewUserActivity.class));
                finish();
            }
        });

        findViewById(R.id.Benter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(view1);
                RadioGroup Iwant = (RadioGroup) findViewById(R.id.radioGroup);
                if (Iwant.getCheckedRadioButtonId() == R.id.rBUser01||
                        Iwant.getCheckedRadioButtonId() == R.id.rBUser02||
                        Iwant.getCheckedRadioButtonId() == R.id.rBUser03){
                    //判斷選擇的項目資源 ID是不是所選擇的該項ID
                    if(Iwant.getCheckedRadioButtonId() == R.id.rBUser01) {
                        userDataDAO.getnameData((RadioButton)findViewById(R.id.rBUser01), 0);
                        startActivity(new Intent().setClass(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                    else if(Iwant.getCheckedRadioButtonId() == R.id.rBUser02){
                        userDataDAO.getnameData((RadioButton)findViewById(R.id.rBUser02), 1);
                        startActivity(new Intent().setClass(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                    else if(Iwant.getCheckedRadioButtonId() == R.id.rBUser03){
                        userDataDAO.getnameData((RadioButton)findViewById(R.id.rBUser03), 2);
                        startActivity(new Intent().setClass(LoginActivity.this, HomeActivity.class));
                        finish();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"請選擇遊戲帳號",Toast.LENGTH_SHORT).show();
                }
            }
        });
        userDataDAO.getnameData((RadioButton)findViewById(R.id.rBUser01), 0);
        userDataDAO.getnameData((RadioButton)findViewById(R.id.rBUser02), 1);
        userDataDAO.getnameData((RadioButton)findViewById(R.id.rBUser03), 2);
    }
}
