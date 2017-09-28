package es.esy.mobilehost.android.savelife;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import es.esy.mobilehost.android.savelife.Data.UserData;
import es.esy.mobilehost.android.savelife.Data.UserDataDAO;
import es.esy.mobilehost.android.savelife.PlayGame.AnimalCard;
import es.esy.mobilehost.android.savelife.PlayGame.GameTime;


public class PlayActivity extends AppCompatActivity {
    private static int			items;
    private Context context;
    private Drawable backImage;
    private int[][]				cards;
    private List<Drawable> images;
    private AnimalCard firstCard;
    private AnimalCard				seconedCard;
    private ButtonListener		buttonListener;
    private static Object lock		= new Object();
    int							pairCount;
    private TableLayout mainTable;
    private UpdateCardsHandler	handler;
    private TextView timeView;
    private GameTime timeset;
    public static final String KEY = "DataSet";
    private UserDataDAO userDataDAO;
    private UserData userData;
    private String A = "";
    ArrayList<Integer> list = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.activity_play);

        backImage = getResources().getDrawable(R.drawable.empty);
        buttonListener = new ButtonListener();
        mainTable = (TableLayout) findViewById(R.id.GameLayout);
        context = mainTable.getContext();

        findViewById(R.id.SetUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeset.cancel();
                winDialog();
            }
        });

        // 取得資料庫物件
        userDataDAO = new UserDataDAO(this);

        // 取得指定編號的物件
        userData = userDataDAO.get(getDate("id"));

        //帳號名稱設定
        TextView textView = (TextView) findViewById(R.id.play_name);
        textView.setText("冒險家:"+ getStringData("name"));

        timeView = (TextView) findViewById(R.id.time);
        GameTime(getDate("SaveTime"));
        initilizeGame(getDate("SaveLsRow"),getDate("SaveLsColume"));
    }

    private void initilizeGame(int rowCount,int columeCount) {
        cards = new int[columeCount][rowCount];
        items = (rowCount * columeCount) / 2; // 記錄可配對個數

        mainTable.removeAllViews();

        for (int y = 0; y < rowCount; y++) {
            mainTable.addView(createRow(y));
        }

        firstCard = null;
        loadCards(rowCount,columeCount); // 產生卡片
        pairCount = 0;

    }

    private void loadImages() {
        images = new ArrayList<Drawable>();
        images.add(getResources().getDrawable(R.drawable.item01));
        images.add(getResources().getDrawable(R.drawable.item02));
        images.add(getResources().getDrawable(R.drawable.item03));
        images.add(getResources().getDrawable(R.drawable.item04));
        images.add(getResources().getDrawable(R.drawable.item05));
        images.add(getResources().getDrawable(R.drawable.item06));
        images.add(getResources().getDrawable(R.drawable.item07));
        images.add(getResources().getDrawable(R.drawable.item08));
        images.add(getResources().getDrawable(R.drawable.item09));
        images.add(getResources().getDrawable(R.drawable.item10));
        images.add(getResources().getDrawable(R.drawable.item11));
        images.add(getResources().getDrawable(R.drawable.item12));
        images.add(getResources().getDrawable(R.drawable.item13));
        images.add(getResources().getDrawable(R.drawable.item14));
    }

    private void loadCards(int rowCount,int columeCount) {
        try {
            //卡排生成數量 例: 6*4
            int size = rowCount * columeCount;

            for (int i = 0; i < size; i++) {
                list.add(new Integer(i)); // 加入所有卡片編號
            }

            Random r = new Random();

            for (int i = size - 1; i >= 0; i--) {
                int t = 0;

                if (i > 0) {
                    t = r.nextInt(i); // 隨機取得編號
                }

                t = list.remove(t).intValue(); // 從 list 中取出編號
                cards[i % columeCount][i / columeCount] = t % (size / 2); // 將編號放入指定位置
            }

            // 再次洗牌
            for (int i = 0; i < rowCount; i++)
                for (int j = 0; j < columeCount; j++) {
                    int rc = r.nextInt(rowCount);
                    int cc = r.nextInt(columeCount);
                    int temp;

                    temp = cards[i][j];
                    cards[i][j] = cards[rc][cc];
                    cards[rc][cc] = temp;
                }
        }
        catch (Exception e) {
        }
    }

    private TableRow createRow(int y) {
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);
        for (int x = 0; x < getDate("SaveLsColume"); x++) {
            row.addView(createImageButton(x, y));
        }
        return row;
    }

    private View createImageButton(int x, int y) {
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100 * x + y);
//        TableLayout tableLayout = (TableLayout)findViewById(R.id.GameLayout);
//        button.setHeight(l/4);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            synchronized (lock) {
                if (firstCard != null && seconedCard != null) {
                    return;
                }
                int id = v.getId();
                int x = id / 100;
                int y = id % 100;
                turnCard((Button) v, x, y);
            }
        }

        //點選時 卡背轉卡面 是否選到同張卡片
        private void turnCard(Button button, int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if (firstCard == null) {
                firstCard = new AnimalCard(button, x, y);
            }
            else {
                if (firstCard.x == x && firstCard.y == y) {
                    return; // 選到相同的卡片則不動作
                }

                seconedCard = new AnimalCard(button, x, y);

                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        catch (Exception e) {
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 500); // 停頓0.5秒
            }
        }
    }

    class UpdateCardsHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]) {
                firstCard.button.setEnabled(false);
                seconedCard.button.setEnabled(false);
                Toast.makeText(getApplicationContext(), "配對成功！", Toast.LENGTH_SHORT).show();
                pairCount++;
                if (pairCount >= items) {
                    timeset.cancel();
                    //勝利後結算
                    winDialog();
                    A="";
                }
            }
            else {
                seconedCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
                Toast.makeText(getApplicationContext(), "配對錯誤...", Toast.LENGTH_SHORT).show();
            }
            firstCard = null;
            seconedCard = null;
        }
    }

    private static final int	MenuGroupID = 0;
    private static final int	MenuItemID1 = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(MenuGroupID, MenuItemID1, Menu.NONE, "重新遊戲");
        return true;
    }

    //右上角選擇設定
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MenuItemID1: // 重新遊戲
                this.finish();
                return true;
        }
        return false;
    }

    //遊戲時間計算
    private void GameTime(int time) {
        //設定時間
        timeset = new GameTime(time*1000,1000) {
            public void onTick(long millisUntilFinished, int percent) {
                long minute;
                minute = (millisUntilFinished / 60000);
                millisUntilFinished = millisUntilFinished - (minute * 60000);
                // 傳回參數指定秒數的「時：分」格式字串

                timeView.setText(
                        "時間：" +
                                String.format("%02d", minute) +
                                ":" +
                                String.format("%02d", millisUntilFinished / 1000)
                );
            }
            public void onFinish() {
                timeView.setText(
                        "時間：" +
                                String.format("%02d", 0) +
                                ":" +
                                String.format("%02d", 0 / 1000)
                );
                //時間到 任務失敗訊息
                loseDialog();
            }
        }.start();
    }

    //防止玩家按返回鍵時回上頁的Layout, 讓此Layout的返回鍵變成跟home鍵功能一樣
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            Intent intentHome = new Intent(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //離開遊戲畫面時呼叫timeset.pause()暫停時間
    @Override
    protected void onPause() {
        super.onPause();
        timeset.pause();
    }

    //回到遊戲畫面時呼叫timeset.pause()暫停時間
    @Override
    protected void onRestart() {
        super.onRestart();
        timeset.resume();
    }

    //勝利結算訊息
    public void winDialog() {
        animalsave();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("開啟以下圖鑑:");
        builder1.setMessage(A);
        builder1.setIcon(android.R.drawable.btn_star_big_on);

        String option2[] = {"繼續遊戲", "回首頁", "前往圖鑑"};
        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        builder2.setTitle("勝利");
        builder2.setIcon(android.R.drawable.btn_star_big_on);
        // 列表選項（注意：不可以與builder.setMessage()同時調用）
        builder2.setItems(option2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(new Intent().setClass(PlayActivity.this, DestActivity.class));
                        finish();
                        break;
                    case 1:
                        startActivity(new Intent().setClass(PlayActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent().setClass(PlayActivity.this, GalleryActivity.class));
                        finish();
                        break;
                }
            }
        });
        builder2.create().show();
        builder1.create().show();
    }

    //任務失敗訊息（時間到）
    public void loseDialog() {
        String option[] = {"繼續遊戲", "回首頁", "前往圖鑑"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("任務失敗!");
        builder.setIcon(android.R.drawable.btn_star_big_off);
        // 列表選項（注意：不可以與builder.setMessage()同時調用）
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(new Intent().setClass(PlayActivity.this, DestActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent().setClass(PlayActivity.this, HomeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent().setClass(PlayActivity.this, GalleryActivity.class));
                        break;
                }
            }
        });
        builder.create().show();
    }

    //判斷開啟幾個動物欄位
    public void animalsave(){
        int setTtime = getDate("SaveTime");
        int setLsary = getDate("SaveLsRow") * getDate("SaveLsColume");
        Random r = new Random();

        switch (setTtime){
            case 60:
                switch (setLsary){
                    case 20:
                        animalcardget(r.nextInt(20)+1);
                        break;
                    case 24:
                        for (int i = 1; i <= 4; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                    case 28:
                        for (int i = 1; i <= 7; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                }
                break;
            case 45:
                switch (setLsary){
                    case 20:
                        for (int i = 1; i <= 2; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                    case 24:
                        for (int i = 1; i <= 8; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                    case 28:
                        for (int i = 1; i <= 6; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                }
                break;
            case 30:
                switch (setLsary){
                    case 20:
                        for (int i = 1; i <= 3; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                    case 24:
                        for (int i = 1; i <= 6; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                    case 28:
                        for (int i = 1; i <= 9; i++){
                            animalcardget(r.nextInt(20)+1);
                        }
                        break;
                }
                break;
        }
    }

    //判斷開啟那個動物欄位
    public void animalcardget(int i){

        if (i!=0) {
            switch (i) {
                case 1:
                    userData.setAnimalCard_1("1");
                    setIntData("a01",1);
                    A = "石虎\n";
                    break;
                case 2:
                    userData.setAnimalCard_2("2");
                    setIntData("a02",2);
                    A = A + "歐亞水獺\n";
                    break;
                case 3:
                    userData.setAnimalCard_3("3");
                    setIntData("a03",3);
                    A = A + "台灣長鬃山羊\n";
                    break;
                case 4:
                    userData.setAnimalCard_4("4");
                    setIntData("a04",4);
                    A = A + "臺灣水鹿\n";
                    break;
                case 5:
                    userData.setAnimalCard_5("5");
                    setIntData("a05",5);
                    A = A + "食蟹獴\n ";
                    break;
                case 6:
                    userData.setAnimalCard_6("6");
                    setIntData("a06",6);
                    A = A + "麝貓\n";
                    break;
                case 7:
                    userData.setAnimalCard_7("7");
                    setIntData("a07",7);
                    A = A + "台灣穿山甲\n";
                    break;
                case 8:
                    userData.setAnimalCard_8("8");
                    setIntData("a08",8);
                    A = A + "山羌\n";
                    break;
                case 9:
                    userData.setAnimalCard_9("9");
                    setIntData("a09",9);
                    A = A + "台灣小黃鼠狼\n";
                    break;
                case 10:
                    userData.setAnimalCard_10("10");
                    setIntData("a10",10);
                    A = A + "果子狸\n";
                    break;
                case 11:
                    userData.setAnimalCard_11("11");
                    setIntData("a11",11);
                    A = A + "臺灣獼猴\n";
                    break;
                case 12:
                    userData.setAnimalCard_12("12");
                    setIntData("a12",12);
                    A = A + "抹香鯨\n";
                    break;
                case 13:
                    userData.setAnimalCard_13("13");
                    setIntData("a13",13);
                    A = A + "黑面琵鷺\n ";
                    break;
                case 14:
                    userData.setAnimalCard_14("14");
                    setIntData("a14",14);
                    A = A + "短尾信天翁\n";
                    break;
                case 15:
                    userData.setAnimalCard_15("15");
                    setIntData("a15",15);
                    A = A + "藍腹鷳\n";
                    break;
                case 16:
                    userData.setAnimalCard_16("16");
                    setIntData("a16",16);
                    A = A + "環頸雉\n";
                    break;
                case 17:
                    userData.setAnimalCard_17("17");
                    setIntData("a17",17);
                    A = A + "蘭嶼角鴞\n";
                    break;
                case 18:
                    userData.setAnimalCard_18("18");
                    setIntData("a18",18);
                    A = A + "帝雉\n";
                    break;
                case 19:
                    userData.setAnimalCard_19("19");
                    setIntData("a19",19);
                    A = A + "台灣雲豹\n";
                    break;
                case 20:
                    userData.setAnimalCard_20("20");
                    setIntData("a20",20);
                    A = A + "臺灣黑熊\n";
                    break;
            }
            userDataDAO.update(userData);
            setUserData(getDate("Pid"));
        }else{}
    }

    //儲存選擇的帳號個別資料
    public void setUserData(int i) {
        //DB資料的內容讀取 :
        UserDataDAO userDataDAO = new UserDataDAO(this);
        Cursor mCursor = userDataDAO.getAllCursor();

        try {
            if (!mCursor.moveToPosition(i)) {
            } else {
                setStringData("name",mCursor.getString(mCursor.getColumnIndex("name")));
                setStringData("a01",mCursor.getString(mCursor.getColumnIndex("a01")));
                setStringData("a02",mCursor.getString(mCursor.getColumnIndex("a02")));
                setStringData("a03",mCursor.getString(mCursor.getColumnIndex("a03")));
                setStringData("a04",mCursor.getString(mCursor.getColumnIndex("a04")));
                setStringData("a05",mCursor.getString(mCursor.getColumnIndex("a05")));
                setStringData("a06",mCursor.getString(mCursor.getColumnIndex("a06")));
                setStringData("a07",mCursor.getString(mCursor.getColumnIndex("a07")));
                setStringData("a08",mCursor.getString(mCursor.getColumnIndex("a08")));
                setStringData("a09",mCursor.getString(mCursor.getColumnIndex("a09")));
                setStringData("a10",mCursor.getString(mCursor.getColumnIndex("a10")));
                setStringData("a11",mCursor.getString(mCursor.getColumnIndex("a11")));
                setStringData("a12",mCursor.getString(mCursor.getColumnIndex("a12")));
                setStringData("a13",mCursor.getString(mCursor.getColumnIndex("a13")));
                setStringData("a14",mCursor.getString(mCursor.getColumnIndex("a14")));
                setStringData("a15",mCursor.getString(mCursor.getColumnIndex("a15")));
                setStringData("a16",mCursor.getString(mCursor.getColumnIndex("a16")));
                setStringData("a17",mCursor.getString(mCursor.getColumnIndex("a17")));
                setStringData("a18",mCursor.getString(mCursor.getColumnIndex("a18")));
                setStringData("a19",mCursor.getString(mCursor.getColumnIndex("a19")));
                setStringData("a20",mCursor.getString(mCursor.getColumnIndex("a20")));
                setIntData("SaveTime", 0);
                setIntData("SaveLsColume", 0);
                setIntData("SaveLsRow", 0);
            }
        } catch (Exception e) {
            Toast.makeText(this, "錯誤",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setIntData(String key, int value) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor PE = spref.edit();
        PE.putInt(key, value);
        PE.commit();
    }
    public int getDate(String key) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        int strValue = spref.getInt(key, 0);
        return strValue;
    }
    public void setStringData(String key, String value) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor PE = spref.edit();
        PE.putString(key, value);
        PE.commit();
    }
    public String getStringData(String key) {
        SharedPreferences spref = getApplication().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        String strValue = spref.getString(key, null);
        return strValue;
    }
}




