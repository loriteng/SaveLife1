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
    private UserData userdata;

    // 資料庫物件
    private UserDataDAO userDataDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        // 取得資料庫物件
        userDataDAO = new UserDataDAO(this);
        Intent intent = getIntent();
        // 讀取修改資料的編號
        long id = intent.getLongExtra("id", -1);
        // 取得指定編號的物件
        userdata = userDataDAO.get(id);
        processViews();

    }

    private void processViews() {

        newUersname_edit = (EditText) findViewById(R.id.username__edit);

    }

    public void clickOk(View view) {
        String newUersname = newUersname_edit.getText().toString();
        UserData userData = new UserData();
        // 把讀取的資料設定給物件
        userData.setusername(newUersname);

        // 新增
        userData = userDataDAO.insert(userData);

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

}