package es.esy.mobilehost.android.savelife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by cooljack on 2017/9/3.
 */
public class PhotoInfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoinfo);

        Intent intent=this.getIntent();
        String showtest=intent.getStringExtra("test");
        Toast.makeText(PhotoInfoActivity.this,showtest.toString(), Toast.LENGTH_SHORT).show();
    }

}
