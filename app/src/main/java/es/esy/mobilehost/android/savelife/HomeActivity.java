package es.esy.mobilehost.android.savelife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        //取的intent中的bundle物件
//        Bundle bundle =this.getIntent().getExtras();
//
//        String name = bundle.getString("name");
//        TextView textView = (TextView)findViewById(R.id.showName);
//        textView.setText("冒險家 "+name+",");

        Button hometoplaygame = (Button) findViewById(R.id.BStart);
        hometoplaygame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(HomeActivity.this, DestActivity.class));
            }
        });

        findViewById(R.id.BGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(HomeActivity.this, GalleryActivity.class));
            }
        });


        findViewById(R.id.BSetup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(HomeActivity.this, SetupActivity.class));
            }
        });
    }
}
