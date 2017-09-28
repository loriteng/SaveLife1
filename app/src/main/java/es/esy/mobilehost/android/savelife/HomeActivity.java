package es.esy.mobilehost.android.savelife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends MenuActivity {

    private MediaPlayer mediaPlayer;
    public static final String KEY = "DataSet";

    private SoundPool soundPool;
    private int sound01;

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //設定按按鈕就出現音效聲
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        sound01 = soundPool.load(this, R.raw.sound01, 1);

        //電話狀態的Listener
        MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener();
        //取得TelephonyManager
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //將電話狀態的Listener加到取得TelephonyManager
        telephonyManager.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        //帳號名稱設定
        TextView textView = (TextView) findViewById(R.id.showName);
        textView.setText("冒險家:" + getData("name"));

        //背景音樂播放
        mediaPlayer = MediaPlayer.create(this, R.raw.sugar);
        mediaPlayer.setLooping(true);
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
                soundPool.play(sound01,1 ,1, 0, 0, 1); //點擊按鈕出現音效
                startActivity(new Intent().setClass(HomeActivity.this, DestActivity.class));
            }
        });

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

    public class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state) {
                //電話狀態是閒置的
                case TelephonyManager.CALL_STATE_IDLE:
                    mediaPlayer.start();
                    break;
                //電話狀態是接起的
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(HomeActivity.this, "正接起電話…", Toast.LENGTH_LONG).show();
                    break;
                //電話狀態是響起的
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(HomeActivity.this, phoneNumber + "正打電話來…", Toast.LENGTH_LONG).show();
                    mediaPlayer.pause();
                    break;
                default:
                    break;
            }
        }
    }

}