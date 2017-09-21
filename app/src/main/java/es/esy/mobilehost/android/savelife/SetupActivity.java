package es.esy.mobilehost.android.savelife;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;


public class SetupActivity extends Activity {

    private Context context;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private SeekBar bgControl, seVolControl;
    private SoundPool soundPool;
    private int sound01,sound02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        context = this;

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(context, R.raw.nothing_on_you);

        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
        sound01 = soundPool.load(SetupActivity.this, R.raw.sound01, 0);
    }

    // Button click 事件回呼方法
    public void onClick(View view) {
        switch (view.getId()) {

//            case R.id.button2: // 2.多鍵式對話框
//                showDialog_2();
//                break;

            case R.id.button6: // 版權說明
                copyRightView();
                break;

            case R.id.button7: // 遊戲設定
                try {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                    }
                    mediaPlayer.prepare();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            mediaPlayer.start();
                          }
                    });
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
                gameSetUp();
                break;
        }
    }

    // 1.雙鍵式對話框
    private void reset() {
        Builder builder = new Builder(context);
        builder.setTitle(R.string.button_text1);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(R.string.clean_record);
        builder.setPositiveButton(R.string.okay, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameSetUp();
            }
        });
        builder.create().show();
    }



    // 遊戲設定 view
    private void gameSetUp() {
        Builder builder = new Builder(context);
        builder.setTitle(R.string.button_text7);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        //列表選項(注意：不可以與builder.setMessage()同時調用)
        builder.setItems(R.array.gs, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        soundVolume();
                        break;
                    case 1:
                        reset();
                }
            }
        });

//        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        builder.create().show();
    }

    // 音量設定 View
    public void soundVolume() {

        // 自定Layout
        LayoutInflater inflater = getLayoutInflater();
        // 將 xml layout 轉換成視圖 View 物件
        View layout = inflater.inflate(R.layout.dialog,
                (ViewGroup) findViewById(R.id.menu_volume));
        // 自定View

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxBgVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curBgVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        int maxSeVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int curSeVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

        // 控制聲音大小
        bgControl = (SeekBar) layout.findViewById(R.id.seekBar);
        bgControl.setMax(maxBgVolume);
        bgControl.setProgress(curBgVolume);
        bgControl.setOnSeekBarChangeListener(new MyVolControlOnSeekBarChangeListener());

        //控制音效大小
        seVolControl = (SeekBar) layout.findViewById(R.id.seekBar2);
        seVolControl.setMax(maxSeVolume);
        seVolControl.setProgress(curSeVolume);
        seVolControl.setOnSeekBarChangeListener(new MyVolControlOnSeekBarChangeListener());

//        SeekBar S1 = (SeekBar) findViewById(R.id.seekBar);
//        SeekBar S2 = (SeekBar) findViewById(R.id.seekBar2);

        Builder builder = new Builder(context);
        builder.setTitle(R.string.button_text7);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(layout);
        builder.setPositiveButton(R.string.okay, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameSetUp();
            }
        });
        builder.create().show();
    }

    // 版權說明 view
    private void copyRightView() {
        Builder builder = new Builder(context);
        builder.setTitle(R.string.button_text6);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        //列表選項(注意：不可以與builder.setMessage()同時調用)
        builder.setItems(R.array.crl, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        dataInfoView();
                        break;
                    case 1:
                        teamWorkView();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    // 資料出處 view
    private void dataInfoView() {
        LayoutInflater inflater = getLayoutInflater();
        // 將 xml layout 轉換成視圖 View 物件
        View layout = inflater.inflate(R.layout.datainfo,
                (ViewGroup) findViewById(R.id.menu_copyright));
        Builder builder = new Builder(context);
        builder.setTitle("資料出處");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(layout);
        builder.setPositiveButton("返回遊戲首頁", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish();
            }
        });
        builder.setNegativeButton("返回上頁", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                copyRightView();
            }
        });
        builder.create().show();
    }

    // 製作團隊 view
    private void teamWorkView() {
        LayoutInflater inflater = getLayoutInflater();
        // 將 xml layout 轉換成視圖 View 物件
        View layout = inflater.inflate(R.layout.teamwork,
                (ViewGroup) findViewById(R.id.menu_copyright));
        Builder builder = new Builder(context);
        builder.setTitle("製作團隊");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(layout);
        builder.setPositiveButton("返回遊戲首頁", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish();
            }
        });
        builder.setNegativeButton("返回上頁", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                copyRightView();
            }
        });
        builder.create().show();
    }

    // 調整聲音大小
    private class MyVolControlOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.seekBar :
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    break;
                // 改變右聲道音量
                case R.id.seekBar2 :
                    audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, 0);
                    float vol = progress/1F;
                    soundPool.setVolume(sound01, vol, vol);
                    soundPool.play(sound01, vol, vol, 0, 0, 1);
                    break;
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }



}
