package es.esy.mobilehost.android.savelife.Data;

/**
 * Created by Administrator on 2017/9/7.
 */
//動物卡圖資料庫
public class AnimalData {
    int AnimalCard_01 = 0,AnimalCard_02 = 0,AnimalCard_03 = 0,AnimalCard_04 = 0;
    int AnimalCard_05 = 0,AnimalCard_06 = 0,AnimalCard_07 = 0,AnimalCard_08 = 0;
    int AnimalCard_09 = 0,AnimalCard_10 = 0,AnimalCard_11 = 0,AnimalCard_12 = 0;
    int AnimalCard_13 = 0,AnimalCard_14 = 0,AnimalCard_15 = 0,AnimalCard_16 = 0;
    int AnimalCard_17 = 0,AnimalCard_18 = 0,AnimalCard_19 = 0,AnimalCard_20 = 0;

    int Animas[]  = {
            AnimalCard_01, AnimalCard_02, AnimalCard_03, AnimalCard_04,
            AnimalCard_05, AnimalCard_06, AnimalCard_07, AnimalCard_08,
            AnimalCard_09, AnimalCard_10, AnimalCard_11, AnimalCard_12,
            AnimalCard_13, AnimalCard_14, AnimalCard_15, AnimalCard_16,
            AnimalCard_17, AnimalCard_18, AnimalCard_19, AnimalCard_20,
                };

    public int aa(int x){
        return Animas[x];
    }

}
