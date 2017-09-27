package es.esy.mobilehost.android.savelife;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class DialogActivity extends Activity {

    private Context context;
    public static final String KEY = "DataSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        context = this;
        announcedTheResultsDialog();
    }

    //宣佈成績
    private void announcedTheResultsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("宣佈成績");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("難度：幼幼班\n剩餘時間：17秒\n分數：9477");
        builder.setPositiveButton("確定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                winDialog();
            }
        });
        builder.create().show();
    }

    //勝利結算訊息
    public void winDialog() {
        try {
            animalsave();
        }catch (Exception e){Toast.makeText(this,"讀不到animalsave",Toast.LENGTH_SHORT).show();}
        String option[] = {"繼續遊戲", "回首頁", "前往圖鑑"};
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle("勝利");
        builder.setIcon(android.R.drawable.btn_star_big_on);
        // 列表選項（注意：不可以與builder.setMessage()同時調用）
        builder.setItems(option, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        announcedTheResultsDialog();
                        break;
                    case 1:
                        announcedTheResultsDialog();
                        break;
                    case 2:
                        announcedTheResultsDialog();
                        break;
                }
            }
        });
        builder.create().show();
    }

    //任務失敗訊息（時間到）
    public void loseDialog() {
        String option[] = {"繼續遊戲", "回首頁", "前往圖鑑"};
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle("失敗");
        builder.setIcon(android.R.drawable.btn_star_big_off);
        // 列表選項（注意：不可以與builder.setMessage()同時調用）
        builder.setItems(option, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        announcedTheResultsDialog();
                        break;
                    case 1:
                        announcedTheResultsDialog();
                        break;
                    case 2:
                        announcedTheResultsDialog();
                        break;
                }
            }
        });
        builder.create().show();
    }

    public void animalsave(){
        int setTtime = getDate("SaveTime");
        int setLsary = getDate("SaveLsRow") * getDate("SaveLsColume");
        Random r = new Random();
        switch (setTtime){
            case 90:
                switch (setLsary){
                    case 20:
                        Toast.makeText(this,"90秒 20格",Toast.LENGTH_SHORT).show();
                        break;
                    case 24:
                        Toast.makeText(this,"90秒 24格",Toast.LENGTH_SHORT).show();
                        break;
                    case 28:
                        Toast.makeText(this,"90秒 28格",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case 60:
                switch (setLsary){
                    case 20:
                        Toast.makeText(this,"60秒 20格",Toast.LENGTH_SHORT).show();
                        break;
                    case 24:
                        Toast.makeText(this,"60秒 24格",Toast.LENGTH_SHORT).show();
                        break;
                    case 28:
                        Toast.makeText(this,"60秒 28格",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case 30:
                switch (setLsary){
                    case 20:
                        Toast.makeText(this,"30秒 20格",Toast.LENGTH_SHORT).show();
                        break;
                    case 24:
                        Toast.makeText(this,"30秒 24格",Toast.LENGTH_SHORT).show();
                        break;
                    case 28:
                        Toast.makeText(this,"30秒 28格",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }

    //設定檔讀取
    //取得DateSet.xml暫存資料檔"SaveLsRow"和"SaveLsColume"
    public int getDate(String key) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        int strValue = spref.getInt(key, 0);
        return strValue;
    }
}
