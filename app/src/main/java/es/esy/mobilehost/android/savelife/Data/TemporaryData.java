package es.esy.mobilehost.android.savelife.Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/9/15.
 */
public class TemporaryData extends Activity {
    private static final String KEY = "DataSet";
    private String tkey;
    private int tvalue;

    public TemporaryData(String key,int value){
        this.tkey = key;
        this.tvalue = value;
    }

    //設定檔儲存
    public void setData()
    {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor PE = spref.edit();
        PE.putInt(tkey, tvalue);
        PE.commit();

    }
    //設定檔讀取
    public String getData(String key)
    {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String strValue = spref.getString(key, null);
        return strValue;
    }
}
