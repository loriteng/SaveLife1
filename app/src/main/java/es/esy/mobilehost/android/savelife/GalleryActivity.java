package es.esy.mobilehost.android.savelife;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import es.esy.mobilehost.android.savelife.Data.UserDataDAO;

public class GalleryActivity extends MenuActivity {

    private MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private int sound01;

    static GalleryActivity galleryActivity;
    private GridView simpleGrid;
    public static final String KEY = "DataSet";
    //DB資料的內容讀取 :
    UserDataDAO mGDB = new UserDataDAO(this);
    //取得資料庫的指標
    Cursor mCursor = mGDB.getAllCursor();

    int info[] = {
            R.string.logo1, R.string.logo2, R.string.logo3, R.string.logo4,
            R.string.logo5, R.string.logo6, R.string.logo7, R.string.logo8,
            R.string.logo9, R.string.logo10, R.string.logo11, R.string.logo12,
            R.string.logo13, R.string.logo14,R.string.logo15, R.string.logo16,
            R.string.logo17, R.string.logo18, R.string.logo19,R.string.logo20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        galleryActivity = this;

        //設定按按鈕就出現音效聲
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        sound01 = soundPool.load(this, R.raw.sound01, 1);

        mCursor.moveToPosition(getDate("id"));
        simpleGrid = (GridView) findViewById(R.id.simpleGridView);
        GalleryAdapter customAdapter = new GalleryAdapter(getApplicationContext(), photoList());
        simpleGrid.setAdapter(customAdapter);

        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                soundPool.play(sound01,1 ,1, 0, 0, 1); //點擊按鈕出現音效
                // set an Intent to Another Activity
                if(photoList()[position] != R.drawable.galleryback) {
                    Intent intent = new Intent(GalleryActivity.this, GalleryDetail.class);
                    intent.putExtra("image", photoList()[position]); // put image data in Intent
                    intent.putExtra("text", info[position]); // put image info data in Intent
                    startActivity(intent); // start Intent
                }else{

                }
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

    public int getDate(String key) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        int strValue = spref.getInt(key, 0);
        return strValue;
    }
    public String getStringData(String key) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String strValue = spref.getString(key, null);
        return strValue;
        }

    public int[] photoList(){

        int photoList[] ={
                getStringData("a01").equals("0") ? R.drawable.galleryback : R.drawable.p0,
                getStringData("a02").equals("0") ? R.drawable.galleryback : R.drawable.p1,
                getStringData("a03").equals("0") ? R.drawable.galleryback : R.drawable.p2,
                getStringData("a04").equals("0") ? R.drawable.galleryback : R.drawable.p3,
                getStringData("a05").equals("0")? R.drawable.galleryback : R.drawable.p4,
                getStringData("a06").equals("0") ? R.drawable.galleryback : R.drawable.p5,
                getStringData("a07").equals("0") ? R.drawable.galleryback : R.drawable.p6,
                getStringData("a08").equals("0") ? R.drawable.galleryback : R.drawable.p7,
                getStringData("a09").equals("0") ? R.drawable.galleryback : R.drawable.p8,
                getStringData("a10").equals("0") ? R.drawable.galleryback : R.drawable.p9,
                getStringData("a11").equals("0") ? R.drawable.galleryback : R.drawable.p10,
                getStringData("a12").equals("0")? R.drawable.galleryback : R.drawable.p11,
                getStringData("a13").equals("0") ? R.drawable.galleryback : R.drawable.p12 ,
                getStringData("a14").equals("0") ? R.drawable.galleryback : R.drawable.p13,
                getStringData("a15").equals("0") ? R.drawable.galleryback : R.drawable.p14 ,
                getStringData("a16").equals("0") ? R.drawable.galleryback : R.drawable.p15,
                getStringData("a17").equals("0") ? R.drawable.galleryback : R.drawable.p16,
                getStringData("a18").equals("0") ? R.drawable.galleryback : R.drawable.p17,
                getStringData("a19").equals("0") ? R.drawable.galleryback : R.drawable.p18,
                getStringData("a20").equals("0") ? R.drawable.galleryback : R.drawable.p19,
        };
        return photoList;
    }

    //防止玩家按返回鍵時回上頁的Layout, 讓此Layout的返回鍵變成跟home鍵功能一樣
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);

    }


}



