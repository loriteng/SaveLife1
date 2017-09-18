package es.esy.mobilehost.android.savelife.Data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/9/7.
 *///
//玩家資料
public class UserDataDAO extends Activity {
    private AnimalData animalData;

    // 表格名稱
    public static final String TABLE_NAME = "pcd";

    // 編號表格欄位名稱，固定不變
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "name";

    // 所有欄位名稱陣列，把所有表格欄位名稱變數湊起來建立一個字串陣列
    public static final String[] COLUMNS =
            { USER_ID, USER_NAME };

    // 顯示用欄位名稱陣列，
    // 在資料查詢畫面上希望顯示位置表格的編號、日期時間和說明，
    // 所以額外使用三個表格欄位名稱變數建立這個欄位名稱陣列
    public static final String[] SHOW_COLUMNS =
            { USER_ID, USER_NAME };

    public void NewUser(){
        animalData.getAnimalData();
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " REAL NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public UserDataDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    // 新增參數指定的物件
    public UserData insert(UserData userdata) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(USER_NAME, userdata.getusername());

        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        long id = db.insert(TABLE_NAME, null, cv);

        // 設定編號
        userdata.setId(id);
        // 回傳結果
        return userdata;
    }

    // 修改參數指定的物件
    public boolean update(UserData userdata) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(USER_NAME, userdata.getusername());

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = USER_ID  + "=" + userdata.getId();

        // 執行修改資料並回傳修改的資料數量是否成功
        return  db.update(TABLE_NAME, cv, where ,null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean delete(long id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = USER_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where, null) > 0;
    }

     //取得所有資料的Cursor物件
    public Cursor getAllCursor() {
        Cursor result = db.query(TABLE_NAME, SHOW_COLUMNS, null, null ,null ,null ,null);
        return result;
    }

    // 取得指定編號的資料物件
    public UserData get(long id) {
        // 準備回傳結果用的物件
        UserData userData = null;
        // 使用編號為查詢條件
        String where = USER_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, COLUMNS, where ,null ,null ,null, null ,null);
        // 如果有查詢結果
        if (result.moveToFirst()){
            // 讀取包裝一筆資料的物件
            userData = getRecord(result);
        }
        // 關閉Cursor物件
        result.close();;
        // 回傳結果
        return userData;
    }

    // 把Cursor目前的資料包裝為物件
    public UserData getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        UserData result = new UserData();

        result.setId(cursor.getLong(0));
        result.setusername(cursor.getString(1));

        // 回傳結果
        return result;
    }


    public void rgetnameData(RadioButton radioButton, int i) {

        //DB資料的內容讀取 :
        UserDataDAO mGDB = new UserDataDAO(this);
        //取得資料庫的指標
        Cursor mCursor = mGDB.getAllCursor();
        //將指標滑動到第一筆，取第一筆資料
        //mCursor.moveToPosition(0);
        //第一筆資料的姓名、年齡、性別、電話、地址資訊
        try {
                    if (!mCursor.moveToPosition(i)){}
                    else {
                        String Name = mCursor.getString(mCursor.getColumnIndex("name"));
                        radioButton.setText(Name);
                    }
        }catch (Exception e){
            Toast.makeText(this, "錯誤",
                    Toast.LENGTH_SHORT).show();
        }

//        如果要一次取多筆資料的話可以使用迴圈方式讀取:
//        for(int i = 0 ; i < mCursor.getCount() ; i++ )
//        {
//            //利用for迴圈切換指標位置
//            mCursor.moveToPosition(i);
//            //每筆姓名、年齡、性別、電話、地址資訊
//            String Name = mCursor.getString(mCursor.getColumnIndex("name"));
//            String Age = mCursor.getString(mCursor.getColumnIndex("age"));
//            String Sex = mCursor.getString(mCursor.getColumnIndex("sex"));
//            String Phone = mCursor.getString(mCursor.getColumnIndex("phone"));
//            String Address = mCursor.getString(mCursor.getColumnIndex("address"));
//        }
    }

}
