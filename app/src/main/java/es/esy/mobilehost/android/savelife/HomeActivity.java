package es.esy.mobilehost.android.savelife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends MenuActivity {

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private int sound01;
    public static final String KEY = "DataSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //帳號名稱設定
        TextView textView = (TextView) findViewById(R.id.showName);
        textView.setText(getData("name"));

        //背景音樂播放
        mediaPlayer = MediaPlayer.create(this, R.raw.nothing_on_you);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
        });



        //進入遊戲
        Button hometoplaygame = (Button) findViewById(R.id.BStart);
        hometoplaygame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(HomeActivity.this, DestActivity.class));
            }
        });

//        //設定
//        Button home2setup = (Button) findViewById(R.id.BSetup);
//        home2setup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent().setClass(HomeActivity.this, SetupActivity.class));
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //設定檔讀取
    public String getData(String key)
    {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String strValue = spref.getString(key, null);
        return strValue;
    }
}