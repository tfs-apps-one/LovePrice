package tfsapps.loveprice;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper
{
    private static final String TABLE = "appinfo";
    public MyOpenHelper(Context context) {
        super(context, "AppDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE + "("
                + "isopen integer,"             //DBオープン
                + "tax_type_a iterger,"         //税金フラグ
                + "tax_type_b iterger,"         //
                + "amount_a iterger,"           //容量
                + "amount_b iterger,"           //
                + "set_a iterger,"              //数量
                + "set_b iterger,"              //
                + "price_a iterger,"            //金額
                + "price_b iterger,"            //
                + "point_a iterger,"            //ポイント
                + "point_b iterger,"            //
                + "discount_a iterger,"         //割引データ
                + "discount_b iterger,"         //
                + "dis_type_a iterger,"         //割引フラグ
                + "dis_type_b iterger,"         //
                + "data1 integer,"              //予備１～１０
                + "data2 integer,"
                + "data3 integer,"
                + "data4 integer,"
                + "data5 integer,"
                + "data6 integer,"
                + "data7 integer,"
                + "data8 integer,"
                + "data9 integer,"
                + "data10 integer);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}