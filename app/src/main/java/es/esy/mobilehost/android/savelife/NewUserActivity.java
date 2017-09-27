package es.esy.mobilehost.android.savelife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.esy.mobilehost.android.savelife.Data.UserData;
import es.esy.mobilehost.android.savelife.Data.UserDataDAO;


public class NewUserActivity extends Activity {

    private EditText newUersname_edit;

    // 資料庫物件
    private UserDataDAO userDataDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        processViews();
        // 取得資料庫物件
        userDataDAO = new UserDataDAO(this);
    }

    private void processViews() {
        newUersname_edit = (EditText) findViewById(R.id.username__edit);
    }

    public void clickOk(View view) {

        NewUersData();
        // 顯示修改成功
        Toast.makeText(this, "新增帳號成功!", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        // 設定回傳結果
        setResult(Activity.RESULT_OK, intent);
        // 結束
        startActivity(new Intent().setClass(NewUserActivity.this, LoginActivity.class));
        finish();
    }

    public void clickCancel(View view) {
        // 結束
        startActivity(new Intent().setClass(NewUserActivity.this, LoginActivity.class));
        finish();
    }

    public void NewUersData(){
        String newUersname = newUersname_edit.getText().toString();
        UserData userData = new UserData();
        // 把讀取的資料設定給物件
        userData.setUsername(newUersname);
        userData.setAnimalCard_1("0");
        userData.setAnimalCard_2("0");
        userData.setAnimalCard_3("0");
        userData.setAnimalCard_4("0");
        userData.setAnimalCard_5("0");
        userData.setAnimalCard_6("0");
        userData.setAnimalCard_7("0");
        userData.setAnimalCard_8("0");
        userData.setAnimalCard_9("0");
        userData.setAnimalCard_10("0");
        userData.setAnimalCard_11("0");
        userData.setAnimalCard_12("0");
        userData.setAnimalCard_13("0");
        userData.setAnimalCard_14("0");
        userData.setAnimalCard_15("0");
        userData.setAnimalCard_16("0");
        userData.setAnimalCard_17("0");
        userData.setAnimalCard_18("0");
        userData.setAnimalCard_19("0");
        userData.setAnimalCard_20("0");
        // 新增
        userDataDAO.insert(userData);
    }


}