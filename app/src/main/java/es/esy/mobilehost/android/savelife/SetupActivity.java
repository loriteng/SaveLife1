package es.esy.mobilehost.android.savelife;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

public class SetupActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        context = this;
    }

    // Button click 事件回呼方法
    public void onClick(View view) {
        switch (view.getId()) {

//            case R.id.button2: // 2.多鍵式對話框
//                showDialog_2();
//                break;

            case R.id.button6: // 遊戲設定
                copyRightView();
                break;

            case R.id.button7: // 版權說明
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

// 2.多鍵式對話框
//    private void showDialog_2() {
//        AlertDialog.Builder builder = new Builder(context);
//        builder.setTitle(R.string.button_text2);
//        builder.setIcon(android.R.drawable.ic_dialog_info);
//        builder.setMessage(R.string.like_android);
//        builder.setPositiveButton(R.string.like, new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton(R.string.not, new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.setNegativeButton(R.string.no_idea, new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.create().show();
//    }

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

        builder.setNegativeButton(R.string.cancel, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    // 音量設定 View
    private void soundVolume() {
        // 自定Layout
        LayoutInflater inflater = getLayoutInflater();
        // 將 xml layout 轉換成視圖 View 物件
        View layout = inflater.inflate(R.layout.dialog,
                (ViewGroup) findViewById(R.id.volume));
        // 自定View
        SeekBar S1 = (SeekBar) findViewById(R.id.seekBar);
        SeekBar S2 = (SeekBar) findViewById(R.id.seekBar2);

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
                (ViewGroup) findViewById(R.id.dataresource));
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
                (ViewGroup) findViewById(R.id.dataresource));
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
}
