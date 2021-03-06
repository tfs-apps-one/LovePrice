
package tfsapps.loveprice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

//γεΊε
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

public class MainActivity extends AppCompatActivity {
    private TextView text_item_A;
    private TextView text_item_B;
    private TextView ttl_amount_A;
    private TextView ttl_amount_B;
    private TextView ttl_set_A;
    private TextView ttl_set_B;
    private TextView ttl_pri_A;
    private TextView ttl_pri_B;
    private TextView ttl_point_A;
    private TextView ttl_point_B;

    private EditText inp_amount_A;
    private EditText inp_set_A;
    private EditText inp_pri_A;
    private EditText inp_discount_A;
    private EditText inp_point_A;
    private EditText inp_amount_B;
    private EditText inp_set_B;
    private EditText inp_pri_B;
    private EditText inp_discount_B;
    private EditText inp_point_B;
    private EditText inp_chk_temp;

    private RadioButton rbtn_tax0_A;
    private RadioButton rbtn_tax8_A;
    private RadioButton rbtn_tax10_A;
    private RadioButton rbtn_tax0_B;
    private RadioButton rbtn_tax8_B;
    private RadioButton rbtn_tax10_B;

    private RadioButton rbtn_dis_per_A;
    private RadioButton rbtn_dis_pri_A;
    private RadioButton rbtn_dis_per_B;
    private RadioButton rbtn_dis_pri_B;

    private double unit_A = 0;
    private double unit_B = 0;
    private int point_pri_A = 0;
    private int point_pri_B = 0;
    private int tax_flag_A = 0;
    private int tax_flag_B = 0;

    private int chk_type_A = 0;
    private int chk_type_B = 0;

    AlertDialog.Builder ad;
    private boolean calcurate_done = false;

    // εΊε
    private AdView mAdview;

    //  ε½θ¨­ε?
    private Locale _local;
    private String _language;
    private String _country;


    //  DBι’ι£
    private MyOpenHelper helper;    //DBγ’γ―γ»γΉ
    private int db_isopen = 0;      //DBδ½Ώη¨γγγ
    private int db_tax_type_a = 0;  //DBη¨ιη¨?ι‘
    private int db_tax_type_b = 0;  //DB
    private int db_amount_a = 0;    //DBε?Ήι
    private int db_amount_b = 0;    //DB
    private int db_set_a = 0;       //DBζ°ι
    private int db_set_b = 0;       //DB
    private int db_price_a = 0;     //DBιι‘
    private int db_price_b = 0;     //DB
    private int db_point_a = 0;     //DBγγ€γ³γ
    private int db_point_b = 0;     //DB
    private int db_discount_a = 0;  //DBε€εΌγι‘
    private int db_discount_b = 0;  //DB
    private int db_dis_type_a = 0;  //DBε€εΌγη¨?ι‘
    private int db_dis_type_b = 0;  //DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ad = new AlertDialog.Builder(this);

        inp_amount_A = (EditText) findViewById(R.id.inp_amount_A);
        inp_amount_A.addTextChangedListener(new TextWatcherExtend(R.id.inp_amount_A, 5));
        inp_amount_A.setBackgroundTintList(null);
        inp_amount_A.setTextColor(Color.BLACK);

        inp_set_A = (EditText) findViewById(R.id.inp_set_A);
        inp_set_A.addTextChangedListener(new TextWatcherExtend(R.id.inp_set_A, 4));
        inp_set_A.setBackgroundTintList(null);
        inp_set_A.setTextColor(Color.BLACK);

        inp_pri_A = (EditText) findViewById(R.id.inp_pri_A);
        inp_pri_A.addTextChangedListener(new TextWatcherExtend(R.id.inp_pri_A, 6));
        inp_pri_A.setBackgroundTintList(null);
        inp_pri_A.setTextColor(Color.BLACK);

        inp_point_A = (EditText) findViewById(R.id.inp_point_A);
        inp_point_A.addTextChangedListener(new TextWatcherExtend(R.id.inp_point_A, 4));
        inp_point_A.setBackgroundTintList(null);
        inp_point_A.setTextColor(Color.BLACK);

        inp_discount_A = (EditText) findViewById(R.id.inp_discount_A);
        inp_discount_A.addTextChangedListener(new TextWatcherExtend(R.id.inp_discount_A, 6));
        inp_discount_A.setBackgroundTintList(null);
        inp_discount_A.setTextColor(Color.BLACK);

        inp_amount_B = (EditText) findViewById(R.id.inp_amount_B);
        inp_amount_B.addTextChangedListener(new TextWatcherExtend(R.id.inp_amount_B, 5));
        inp_amount_B.setBackgroundTintList(null);
        inp_amount_B.setTextColor(Color.BLACK);

        inp_set_B = (EditText) findViewById(R.id.inp_set_B);
        inp_set_B.addTextChangedListener(new TextWatcherExtend(R.id.inp_set_B, 4));
        inp_set_B.setBackgroundTintList(null);
        inp_set_B.setTextColor(Color.BLACK);

        inp_pri_B = (EditText) findViewById(R.id.inp_pri_B);
        inp_pri_B.addTextChangedListener(new TextWatcherExtend(R.id.inp_pri_B, 6));
        inp_pri_B.setBackgroundTintList(null);
        inp_pri_B.setTextColor(Color.BLACK);

        inp_point_B = (EditText) findViewById(R.id.inp_point_B);
        inp_point_B.addTextChangedListener(new TextWatcherExtend(R.id.inp_point_B, 4));
        inp_point_B.setBackgroundTintList(null);
        inp_point_B.setTextColor(Color.BLACK);

        inp_discount_B = (EditText) findViewById(R.id.inp_discount_B);
        inp_discount_B.addTextChangedListener(new TextWatcherExtend(R.id.inp_discount_B, 6));
        inp_discount_B.setBackgroundTintList(null);
        inp_discount_B.setTextColor(Color.BLACK);

        rbtn_tax0_A = (RadioButton)findViewById((R.id.rbtn_tax0_A));
        rbtn_tax0_A.setBackgroundTintList(null);
        rbtn_tax0_A.setTextColor(Color.DKGRAY);
        rbtn_tax8_A = (RadioButton)findViewById((R.id.rbtn_tax8_A));
        rbtn_tax8_A.setBackgroundTintList(null);
        rbtn_tax8_A.setTextColor(Color.DKGRAY);
        rbtn_tax10_A = (RadioButton)findViewById((R.id.rbtn_tax10_A));
        rbtn_tax10_A.setBackgroundTintList(null);
        rbtn_tax10_A.setTextColor(Color.DKGRAY);

        rbtn_tax0_B = (RadioButton)findViewById((R.id.rbtn_tax0_B));
        rbtn_tax0_B.setBackgroundTintList(null);
        rbtn_tax0_B.setTextColor(Color.DKGRAY);
        rbtn_tax8_B = (RadioButton)findViewById((R.id.rbtn_tax8_B));
        rbtn_tax8_B.setBackgroundTintList(null);
        rbtn_tax8_B.setTextColor(Color.DKGRAY);
        rbtn_tax10_B = (RadioButton)findViewById((R.id.rbtn_tax10_B));
        rbtn_tax10_B.setBackgroundTintList(null);
        rbtn_tax10_B.setTextColor(Color.DKGRAY);

        rbtn_dis_per_A = (RadioButton)findViewById((R.id.rbtn_dis_per_A));
        rbtn_dis_per_A.setBackgroundTintList(null);
        rbtn_dis_per_A.setTextColor(Color.DKGRAY);

        rbtn_dis_pri_A = (RadioButton)findViewById((R.id.rbtn_dis_pri_A));
        rbtn_dis_pri_A.setBackgroundTintList(null);
        rbtn_dis_pri_A.setTextColor(Color.DKGRAY);

        rbtn_dis_per_B = (RadioButton)findViewById((R.id.rbtn_dis_per_B));
        rbtn_dis_per_B.setBackgroundTintList(null);
        rbtn_dis_per_B.setTextColor(Color.DKGRAY);

        rbtn_dis_pri_B = (RadioButton)findViewById((R.id.rbtn_dis_pri_B));
        rbtn_dis_pri_B.setBackgroundTintList(null);
        rbtn_dis_pri_B.setTextColor(Color.DKGRAY);

        text_item_A = (TextView) findViewById(R.id.item_A);
        text_item_B = (TextView) findViewById(R.id.item_B);
        ttl_amount_A = (TextView) findViewById(R.id.ttl_amout_A);
        ttl_amount_B = (TextView) findViewById(R.id.ttl_amout_B);
        ttl_set_A = (TextView) findViewById(R.id.ttl_set_A);
        ttl_set_B = (TextView) findViewById(R.id.ttl_set_B);
        ttl_pri_A = (TextView) findViewById(R.id.ttl_pri_A);
        ttl_pri_B = (TextView) findViewById(R.id.ttl_pri_B);
        ttl_point_A = (TextView) findViewById(R.id.ttl_point_A);
        ttl_point_B = (TextView) findViewById(R.id.ttl_point_B);

        tax_flag_A = 0; //η¨ζγ
        tax_flag_B = 0; //η¨ζγ

        //  ε½θ¨­ε?
        _local = Locale.getDefault();
        _language = _local.getLanguage();
        _country = _local.getCountry();

        /* εζθ‘¨η€Ί */
        CalResult();

        //εΊε
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
    }

    /* **************************************************
       εη¨?OSδΈγ?εδ½ε?ηΎ©
    ****************************************************/
    @Override
    public void onStart() {
        super.onStart();
        //DBγ?γ­γΌγ
        /* γγΌγΏγγΌγΉ */
        helper = new MyOpenHelper(this);
        AppDBInitRoad();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    /* **************************************************
        DBεζγ­γΌγγγγ³θ¨­ε?
    ****************************************************/
    public void AppDBInitRoad() {
        SQLiteDatabase db = helper.getReadableDatabase();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" isopen");
        sql.append(" ,tax_type_a,tax_type_b");
        sql.append(" ,amount_a,amount_b");
        sql.append(" ,set_a,set_b");
        sql.append(" ,price_a,price_b");
        sql.append(" ,point_a,point_b");
        sql.append(" ,discount_a,discount_b");
        sql.append(" ,dis_type_a,dis_type_b");
        sql.append(" FROM appinfo;");
        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);
            //TextViewγ«θ‘¨η€Ί
            StringBuilder text = new StringBuilder();
            if (cursor.moveToNext()) {
                db_isopen = cursor.getInt(0);
                db_tax_type_a = cursor.getInt(1);
                db_tax_type_b = cursor.getInt(2);
                db_amount_a = cursor.getInt(3);
                db_amount_b = cursor.getInt(4);
                db_set_a = cursor.getInt(5);
                db_set_b = cursor.getInt(6);
                db_price_a = cursor.getInt(7);
                db_price_b = cursor.getInt(8);
                db_point_a = cursor.getInt(9);
                db_point_b = cursor.getInt(10);
                db_discount_a = cursor.getInt(11);
                db_discount_b = cursor.getInt(12);
                db_dis_type_a = cursor.getInt(13);
                db_dis_type_b = cursor.getInt(14);
            }
        } finally {
            db.close();
        }

        db = helper.getWritableDatabase();
        if (db_isopen == 0) {
            long ret;
            /* ζ°θ¦γ¬γ³γΌγθΏ½ε  */
            ContentValues insertValues = new ContentValues();
            insertValues.put("isopen", 1);
            insertValues.put("tax_type_a", 0);
            insertValues.put("tax_type_b", 0);
            insertValues.put("amount_a", 0);
            insertValues.put("amount_b", 0);
            insertValues.put("set_a", 0);
            insertValues.put("set_b", 0);
            insertValues.put("price_a", 0);
            insertValues.put("price_b", 0);
            insertValues.put("point_a", 0);
            insertValues.put("point_b", 0);
            insertValues.put("discount_a", 0);
            insertValues.put("discount_b", 0);
            insertValues.put("dis_type_a", 0);
            insertValues.put("dis_type_b", 0);
            try {
                ret = db.insert("appinfo", null, insertValues);
            } finally {
                db.close();
            }
            /*
            if (ret == -1) {
                Toast.makeText(this, "DataBase Create.... ERROR", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "DataBase Create.... OK", Toast.LENGTH_SHORT).show();
            }
            */
       } else {
            /*
            Toast.makeText(this, "Data Loading...  tax_a:" + db_tax_type_a, Toast.LENGTH_SHORT).show();
             */
        }
    }
    /* **************************************************
        DBζ΄ζ°
    ****************************************************/
    public void AppDBUpdated() {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("isopen", db_isopen);
        insertValues.put("tax_type_a", db_tax_type_a);
        insertValues.put("tax_type_b", db_tax_type_b);
        insertValues.put("amount_a", db_amount_a);
        insertValues.put("amount_b", db_amount_b);
        insertValues.put("set_a", db_set_a);
        insertValues.put("set_b", db_set_b);
        insertValues.put("price_a", db_price_a);
        insertValues.put("price_b", db_price_b);
        insertValues.put("point_a", db_point_a);
        insertValues.put("point_b", db_point_b);
        insertValues.put("discount_a", db_discount_a);
        insertValues.put("discount_b", db_discount_b);
        insertValues.put("dis_type_a", db_dis_type_a);
        insertValues.put("dis_type_b", db_dis_type_b);
        int ret;
        try {
            ret = db.update("appinfo", insertValues, null, null);
        } finally {
            db.close();
        }
        /*
        if (ret == -1) {
            Toast.makeText(this, "Saving.... ERROR ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saving.... OK ", Toast.LENGTH_SHORT).show();
        }
         */
    }
    /* **************************************************
        εγγΏγ³ε¦η
    ****************************************************/
    //  DBγγΌγΏγ«θ¨­ε?
    public void set_DataToDb(){
        //η¨η
        db_tax_type_a = tax_flag_A;
        db_tax_type_b = tax_flag_B;

        //ε?Ήι
        if (inp_amount_A.getText().toString().isEmpty() == false) {
            db_amount_a = Integer.parseInt(inp_amount_A.getText().toString());
        }else{
            db_amount_a = 0;
        }

        if (inp_amount_B.getText().toString().isEmpty() == false) {
            db_amount_b = Integer.parseInt(inp_amount_B.getText().toString());
        }
        else{
            db_amount_b = 0;
        }

        // ζ°ι
        if (inp_set_A.getText().toString().isEmpty() == false) {
            db_set_a = Integer.parseInt(inp_set_A.getText().toString());
        }
        else{
            db_set_a = 0;
        }

        if (inp_set_B.getText().toString().isEmpty() == false) {
            db_set_b = Integer.parseInt(inp_set_B.getText().toString());
        }
        else{
            db_set_b = 0;
        }

        // ιι‘
        if (inp_pri_A.getText().toString().isEmpty() == false) {
            db_price_a = Integer.parseInt(inp_pri_A.getText().toString());
        }
        else{
            db_price_a = 0;
        }

        if (inp_pri_B.getText().toString().isEmpty() == false) {
            db_price_b = Integer.parseInt(inp_pri_B.getText().toString());
        }
        else{
            db_price_b = 0;
        }

        //γγ€γ³γ
        if (inp_point_A.getText().toString().isEmpty() == false) {
            db_point_a = Integer.parseInt(inp_point_A.getText().toString());
        }
        else{
            db_point_a = 0;
        }

        if (inp_point_B.getText().toString().isEmpty() == false) {
            db_point_b = Integer.parseInt(inp_point_B.getText().toString());
        }
        else{
            db_point_b = 0;
        }

        // ε²εΌ
        if (inp_discount_A.getText().toString().isEmpty() == false) {
            db_discount_a = Integer.parseInt(inp_discount_A.getText().toString());
        }
        else{
            db_discount_a = 0;
        }

        if (inp_discount_B.getText().toString().isEmpty() == false) {
            db_discount_b = Integer.parseInt(inp_discount_B.getText().toString());
        }
        else{
            db_discount_b = 0;
        }

        //  ε²εΌη¨?ι‘
        db_dis_type_a = chk_type_A;
        db_dis_type_b = chk_type_B;
    }

    //  DBγγΌγΏγεεΎ
    public void get_DataToDb(){

        //η¨ιγͺγ»γγ
        switch (db_tax_type_a){
            case 1:     rbtn_tax0_A.setChecked(true);   break;
            case 2:     rbtn_tax8_A.setChecked(true);   break;
            case 3:     rbtn_tax10_A.setChecked(true);  break;

            default:    rbtn_tax0_A.setChecked(false);
                        rbtn_tax8_A.setChecked(false);
                        rbtn_tax10_A.setChecked(false); break;

        }
        switch (db_tax_type_b){
            case 1:     rbtn_tax0_B.setChecked(true);   break;
            case 2:     rbtn_tax8_B.setChecked(true);   break;
            case 3:     rbtn_tax10_B.setChecked(true);  break;

            default:    rbtn_tax0_B.setChecked(false);
                        rbtn_tax8_B.setChecked(false);
                        rbtn_tax10_B.setChecked(false); break;
        }
        tax_flag_A = db_tax_type_a;
        tax_flag_B = db_tax_type_b;

        /* ε₯εε€γͺγ»γγ */
        if (db_amount_a > 0)    inp_amount_A.setText(""+db_amount_a);
        else                    inp_amount_A.setText("");

        if (db_set_a > 0)       inp_set_A.setText(""+db_set_a);
        else                    inp_set_A.setText("");

        if (db_price_a > 0)     inp_pri_A.setText(""+db_price_a);
        else                    inp_pri_A.setText("");

        if (db_point_a > 0)     inp_point_A.setText(""+db_point_a);
        else                    inp_point_A.setText("");

        if (db_discount_a > 0)  inp_discount_A.setText(""+db_discount_a);
        else                    inp_discount_A.setText("");

        if (db_amount_b > 0)    inp_amount_B.setText(""+db_amount_b);
        else                    inp_amount_B.setText("");

        if (db_set_b > 0)       inp_set_B.setText(""+db_set_b);
        else                    inp_set_B.setText("");

        if (db_price_b > 0)     inp_pri_B.setText(""+db_price_b);
        else                    inp_pri_B.setText("");

        if (db_point_b > 0)     inp_point_B.setText(""+db_point_b);
        else                    inp_point_B.setText("");

        if (db_discount_b > 0)  inp_discount_B.setText(""+db_discount_b);
        else                    inp_discount_B.setText("");

        //ε²εΌ
        switch (db_dis_type_a){
            case 1:     rbtn_dis_per_A.setChecked(true);    break;
            case 2:     rbtn_dis_pri_A.setChecked(true);    break;

            default:    rbtn_dis_per_A.setChecked(false);
                        rbtn_dis_pri_A.setChecked(false);   break;
        }
        switch (db_dis_type_b){
            case 1:     rbtn_dis_per_B.setChecked(true);    break;
            case 2:     rbtn_dis_pri_B.setChecked(true);    break;

            default:    rbtn_dis_per_B.setChecked(false);
                        rbtn_dis_pri_B.setChecked(false);   break;
        }
        chk_type_A = db_dis_type_a;
        chk_type_B = db_dis_type_b;
    }

    /* θ¨ζΆγγΏγ³ */
    public void onSave(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("γ η­ηΈ?ε₯ε ζ©θ½ γ");
        builder.setMessage("\n\nη­ηΈ?ε₯εγ¨γ―γη¨ηγε?Ήιγζ°ιγδΎ‘ζ Όγγγ€γ³γγε²εΌγγδΏε­&θͺ­θΎΌγγδΊγε―θ½γ§γγζ₯εΈΈηγ«δ½Ώη¨γγζ‘δ»ΆγδΏε­γγγ¨δΎΏε©γ§γγ\n\nζδ½γ«εΏγγ¦γγΏγ³γιΈζγγ¦δΈγγγ\n\n\n\n [ζ»γ] η­ηΈ?ε₯εη»ι’γιγγ\n [θͺ­θΎΌ] δΏε­γγε€γθͺ­γΏθΎΌγ\n [δΏε­] ε₯εγγε€γδΏε­γγ\n\n\n");

        builder.setPositiveButton("δΏε­", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //γγ€γ’γ­γ°ε¦η
                set_DataToDb();
                AppDBUpdated();
                calcurate_done = false;
                CalResult();
            }
        });

        builder.setNegativeButton("θͺ­θΎΌ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                get_DataToDb();
                calcurate_done = false;
                CalResult();
            }
        });

        builder.setNeutralButton("ζ»γ", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                /*
                *   ε¦ηγͺγοΌζ»γγ γοΌ
                * */
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /* θ¨η?η΅ζθ©³η΄° */
    public void onDetail(View view)
    {
        double data_a;
        double data_b;
        AlertDialog.Builder temp_ad = new AlertDialog.Builder(this);;
        DecimalFormat pdm = new DecimalFormat("#0.00");
        String temp_ttl = "";
        String temp_st = "";

        if (calcurate_done == false)
        {
            if (_language.equals("ja")) {
                temp_ttl = "γθ¨η?γζͺε?θ‘γ¨γ©γΌ";
                temp_st = "ζ‘δ»Άγε₯εγγ¦γθ¨η?γγζΌγγ¦δΈγγ\n\n\n\n\n";
            }
            else
            {
                temp_ttl = "[CAL.] not executed error";
                temp_st = "Please enter conditions and press [CAL.]\n\n\n\n\n";
            }
        }
        else {
            data_a = unit_A - point_pri_A;
            data_b = unit_B - point_pri_B;

            if (_language.equals("ja")) {

                if (data_a < data_b) {
                    temp_ttl = "εεοΌ‘γγεΎοΌοΌ";
                } else if (data_b < data_a) {
                    temp_ttl = "εεοΌ’γγεΎοΌοΌ";
                } else {
                    temp_ttl = "γ©γ‘γγεγ";
                }

                temp_st = "<<εεοΌ‘>>\n ζη΅εδ½δΎ‘ζ ΌγγβΆ" + pdm.format(data_a) + "ε\n εδΎ‘οΌε²εΌε«γοΌβΆ" + pdm.format(unit_A) + "ε\n γγ€γ³γιεγγβΆ" + String.valueOf(point_pri_A) + "pt\n\n" +
                        "<<εεοΌ’>>\n ζη΅εδ½δΎ‘ζ ΌγγβΆ" + pdm.format(data_b) + "ε\n εδΎ‘οΌε²εΌε«γοΌβΆ" + pdm.format(unit_B) + "ε\n γγ€γ³γιεγγβΆ" + String.valueOf(point_pri_B) + "pt";
            }
            else {
                if (data_a < data_b) {
                    temp_ttl = "ITEM A is a bargain";
                } else if (data_b < data_a) {
                    temp_ttl = "ITEM B is a bargain";
                } else {
                    temp_ttl = "BOTH are the same";
                }
                temp_st = "<<ITEM A>>\n Final unit price\n  βΆ" + pdm.format(data_a) + "\n Unit price\n  βΆ" + pdm.format(unit_A) + "\n Point reduction\n  βΆ" + String.valueOf(point_pri_A) + "pt\n\n" +
                        "<<ITEM B>>\n Final unit price\n  βΆ" + pdm.format(data_b) + "\n Unit price\n  βΆ" + pdm.format(unit_B) + "\n Point reduction\n  βΆ" + String.valueOf(point_pri_B) + "pt";

            }
        }

        temp_ad.setTitle(temp_ttl);
        temp_ad.setMessage(temp_st);
        if (_language.equals("ja")) {
            temp_ad.setPositiveButton("ζ»γ", null);
        }
        else
        {
            temp_ad.setPositiveButton("CANCEL", null);
        }
        temp_ad.show();
    }

    public void tax_disp()
    {
    }

    /* η¨θ¨­ε?οΌ‘ 0%  */
    public void onRbtn_tax0_A(View view)
    {
        tax_flag_A = 1;
        tax_disp();
    }
    /* η¨θ¨­ε?οΌ‘ 8%  */
    public void onRbtn_tax8_A(View view)
    {
        tax_flag_A = 2;
        tax_disp();
    }
    /* η¨θ¨­ε?οΌ‘ 10% */
    public void onRbtn_tax10_A(View view)
    {
        tax_flag_A = 3;
        tax_disp();
    }
    /* η¨θ¨­ε?οΌ’ 0%  */
    public void onRbtn_tax0_B(View view)
    {
        tax_flag_B = 1;
        tax_disp();
    }
    /* η¨θ¨­ε?οΌ’ 0%  */
    public void onRbtn_tax8_B(View view)
    {
        tax_flag_B = 2;
        tax_disp();
    }
    /* η¨θ¨­ε?οΌ’ 0%  */
    public void onRbtn_tax10_B(View view)
    {
        tax_flag_B = 3;
        tax_disp();
    }

    /* η΅ζ */
    public void CalResult()
    {
        double data_a;
        double data_b;
        String temp_st1 = "";
        String temp_st2 = "";
        TextView v1 = (TextView) findViewById(R.id.status);
        TextView v2 = (TextView) findViewById(R.id.status2);

        LinearLayout temp_v0 = (LinearLayout)findViewById(R.id.linearLayout0);
        LinearLayout temp_v1 = (LinearLayout)findViewById(R.id.linearLayout1);
        LinearLayout temp_v2 = (LinearLayout)findViewById(R.id.linearLayout2);
        LinearLayout temp_v4 = (LinearLayout)findViewById(R.id.linearLayout4);

        tax_disp();

        DecimalFormat pdm = new DecimalFormat("#0.00");

        if (calcurate_done == false)
        {
            if (_language.equals("ja")) {
                temp_st1 = "ζ‘δ»Άε₯εεΎγ«γθ¨η?γγγ¦δΈγγ";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "θΌΈε₯ζ’δ»ΆεΎθ«γθ¨η?γ";
            }
            else if (_language.equals("ko")) {
                temp_st1 = "μ‘°κ±΄ μλ ₯ νγκ³μ°γν΄μ£ΌμΈμ";
            }
            else
            {
                temp_st1 = "Please enter conditions and press [CAL.]";
            }
            v1.setText(temp_st1);
            v1.setTextSize(16);
            v1.setTextColor(Color.DKGRAY);
//            v1.setTypeface(Typeface.DEFAULT);
            v1.setTypeface(Typeface.DEFAULT_BOLD);
            temp_st2 = "";
            v2.setText(temp_st2);
            v2.setTextColor(Color.BLACK);
            v2.setTypeface(Typeface.DEFAULT);
            temp_v4.setBackgroundResource(R.drawable.bak_status);
//            temp_v0.setBackgroundResource(R.drawable.bak_noselect);
            temp_v1.setBackgroundResource(R.drawable.bak_noselect);
            temp_v2.setBackgroundResource(R.drawable.bak_noselect);

            text_item_A.setTextColor(Color.DKGRAY);
            text_item_A.setTypeface(Typeface.DEFAULT);
            ttl_amount_A.setTextColor(Color.DKGRAY);
            ttl_set_A.setTextColor(Color.DKGRAY);
            ttl_pri_A.setTextColor(Color.DKGRAY);
            ttl_point_A.setTextColor(Color.DKGRAY);

            text_item_B.setTextColor(Color.DKGRAY);
            text_item_B.setTypeface(Typeface.DEFAULT);
            ttl_amount_B.setTextColor(Color.DKGRAY);
            ttl_set_B.setTextColor(Color.DKGRAY);
            ttl_pri_B.setTextColor(Color.DKGRAY);
            ttl_point_B.setTextColor(Color.DKGRAY);
            return;
        }

        data_a = unit_A - point_pri_A;
        data_b = unit_B - point_pri_B;

        if (data_a < data_b)
        {

            if (_language.equals("ja")) {
                temp_st1 = "εεοΌ‘γγεΎοΌοΌ";
                temp_st2 = "εδ½δΎ‘ζ ΌγA βΆ" + pdm.format(data_a) + "ε" + "γB βΆ" + pdm.format(data_b) + "ε";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "η’εAζ―εΎε€§ηοΌοΌ";
                temp_st2 = "ε?εΉγA βΆ" + pdm.format(data_a)  + "γB βΆ" + pdm.format(data_b);
            }
            else if (_language.equals("ko")) {
                temp_st1 = "μ ν Aκ° κ±°λοΌοΌ";
                temp_st2 = "λ¨μ κ°κ²©γA βΆ" + pdm.format(data_a)  + "γB βΆ" + pdm.format(data_b);
            }
            else
            {
                temp_st1 = "ITEM A is a bargain";
                temp_st2 = "Unit priceγA βΆ" + pdm.format(data_a) + "γB βΆ" + pdm.format(data_b);
            }
            temp_v4.setBackgroundResource(R.drawable.bak_select_cal);
//            temp_v0.setBackgroundResource(R.drawable.bak_select);
            temp_v1.setBackgroundResource(R.drawable.bak_select_box);
            temp_v2.setBackgroundResource(R.drawable.bak_noselect);
            text_item_A.setTextColor(Color.rgb(255,14,93));
            text_item_A.setTypeface(Typeface.DEFAULT_BOLD);
            ttl_amount_A.setTextColor(Color.rgb(255,14,93));
            ttl_set_A.setTextColor(Color.rgb(255,14,93));
            ttl_pri_A.setTextColor(Color.rgb(255,14,93));
            ttl_point_A.setTextColor(Color.rgb(255,14,93));

            text_item_B.setTextColor(Color.DKGRAY);
            text_item_B.setTypeface(Typeface.DEFAULT);
            ttl_amount_B.setTextColor(Color.DKGRAY);
            ttl_set_B.setTextColor(Color.DKGRAY);
            ttl_pri_B.setTextColor(Color.DKGRAY);
            ttl_point_B.setTextColor(Color.DKGRAY);
        }
        else if (data_b < data_a)
        {
            if (_language.equals("ja")) {
                temp_st1 = "εεοΌ’γγεΎοΌοΌ";
                temp_st2 = "εδ½δΎ‘ζ ΌγA βΆ" + pdm.format(data_a) + "ε" + "γB βΆ" + pdm.format(data_b) + "ε";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "η’εBζ―εΎε€§ηοΌοΌ";
                temp_st2 = "ε?εΉγA βΆ" + pdm.format(data_a)  + "γB βΆ" + pdm.format(data_b);
            }
            else if (_language.equals("ko")) {
                temp_st1 = "μ ν Bκ° κ±°λοΌοΌ";
                temp_st2 = "λ¨μ κ°κ²©γA βΆ" + pdm.format(data_a)  + "γB βΆ" + pdm.format(data_b);
            }
            else {
                temp_st1 = "ITEM B is a bargain";
                temp_st2 = "Unit priceγA βΆ" + pdm.format(data_a) + "γB βΆ" + pdm.format(data_b);
            }
            temp_v4.setBackgroundResource(R.drawable.bak_select_cal);
//            temp_v0.setBackgroundResource(R.drawable.bak_noselect);
            temp_v1.setBackgroundResource(R.drawable.bak_noselect);
            temp_v2.setBackgroundResource(R.drawable.bak_select_box);
            text_item_A.setTextColor(Color.DKGRAY);
            text_item_A.setTypeface(Typeface.DEFAULT);
            ttl_amount_A.setTextColor(Color.DKGRAY);
            ttl_set_A.setTextColor(Color.DKGRAY);
            ttl_pri_A.setTextColor(Color.DKGRAY);
            ttl_point_A.setTextColor(Color.DKGRAY);

            text_item_B.setTextColor(Color.rgb(255,14,93));
            text_item_B.setTypeface(Typeface.DEFAULT_BOLD);
            ttl_amount_B.setTextColor(Color.rgb(255,14,93));
            ttl_set_B.setTextColor(Color.rgb(255,14,93));
            ttl_pri_B.setTextColor(Color.rgb(255,14,93));
            ttl_point_B.setTextColor(Color.rgb(255,14,93));
        }
        else
        {
            if (_language.equals("ja")) {
                temp_st1 = "γ©γ£γ‘γεγ";
                temp_st2 = "εδ½δΎ‘ζ ΌγA βΆ" + pdm.format(data_a) + "ε" + "γB βΆ" + pdm.format(data_b) + "ε";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "ε©θι½ζ―δΈζ¨£ηοΌοΌ";
                temp_st2 = "ε?εΉγA βΆ" + pdm.format(data_a)  + "γB βΆ" + pdm.format(data_b);
            }
            else if (_language.equals("ko")) {
                temp_st1 = "λͺ¨λ κ°μοΌοΌ";
                temp_st2 = "λ¨μ κ°κ²©γA βΆ" + pdm.format(data_a)  + "γB βΆ" + pdm.format(data_b);
            }
            else
            {
                temp_st1 = "BOTH are the same";
                temp_st2 = "Unit priceγA βΆ" + pdm.format(data_a) + "γB βΆ" + pdm.format(data_b);
            }
            temp_v4.setBackgroundResource(R.drawable.bak_select_cal);
//            temp_v0.setBackgroundResource(R.drawable.bak_noselect);
            temp_v1.setBackgroundResource(R.drawable.bak_noselect);
            temp_v2.setBackgroundResource(R.drawable.bak_noselect);

            text_item_A.setTextColor(Color.DKGRAY);
            text_item_A.setTypeface(Typeface.DEFAULT);
            ttl_amount_A.setTextColor(Color.DKGRAY);
            ttl_set_A.setTextColor(Color.DKGRAY);
            ttl_pri_A.setTextColor(Color.DKGRAY);
            ttl_point_A.setTextColor(Color.DKGRAY);

            text_item_B.setTextColor(Color.DKGRAY);
            text_item_B.setTypeface(Typeface.DEFAULT);
            ttl_amount_B.setTextColor(Color.DKGRAY);
            ttl_set_B.setTextColor(Color.DKGRAY);
            ttl_pri_B.setTextColor(Color.DKGRAY);
            ttl_point_B.setTextColor(Color.DKGRAY);
        }
        v1.setText(temp_st1);
        v1.setTextSize(21);
        v1.setTextColor(Color.BLUE);
//        v1.setTextColor(Color.rgb(255,14,93));
        v1.setTypeface(Typeface.DEFAULT_BOLD);
        v2.setText(temp_st2);
        v2.setTextColor(Color.rgb(255,14,93));
        v2.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /* θ¨η? */
    public int CalcurateExec()
    {
//        CheckBox chk_temp;
        // ε?Ήι
        int amount_a = 0;
        if (inp_amount_A.getText().toString().isEmpty() == false) {
            amount_a = Integer.parseInt(inp_amount_A.getText().toString());
        }
        if (amount_a == 0)  amount_a = 1;

        int amount_b = 0;
        if (inp_amount_B.getText().toString().isEmpty() == false) {
            amount_b = Integer.parseInt(inp_amount_B.getText().toString());
        }
        if (amount_b == 0)  amount_b = 1;

        // ζ°ι
        int set_a = 0;
        if (inp_set_A.getText().toString().isEmpty() == false) {
            set_a = Integer.parseInt(inp_set_A.getText().toString());
        }
        if (set_a == 0)  set_a = 1;

        int set_b = 0;
        if (inp_set_B.getText().toString().isEmpty() == false) {
            set_b = Integer.parseInt(inp_set_B.getText().toString());
        }
        if (set_b == 0)  set_b = 1;

        // ιι‘
        if (inp_pri_A.getText().toString().isEmpty() ) return -1;
        int pri_a = Integer.parseInt(inp_pri_A.getText().toString());
        if (inp_pri_B.getText().toString().isEmpty() ) return -1;
        int pri_b = Integer.parseInt(inp_pri_B.getText().toString());

        // η¨ιθ£ζ­£ε¦ηθΏ½ε 
        if (tax_flag_A == 2) {  //8%
            pri_a = (pri_a * 108) / 100;
        }
        else if (tax_flag_A == 3) {  //10%
            pri_a = (pri_a * 110) / 100;
        }
        if (tax_flag_B == 2) {  //8%
            pri_b = (pri_b * 108) / 100;
        }
        else if (tax_flag_B == 3) {  //10%
            pri_b = (pri_b * 110) / 100;
        }

        // γγ€γ³γεδΎ‘
        int point_a = 0;
        if (inp_point_A.getText().toString().isEmpty() == false) {
            point_a = Integer.parseInt(inp_point_A.getText().toString());
        }
        int point_b = 0;
        if (inp_point_B.getText().toString().isEmpty() == false) {
            point_b = Integer.parseInt(inp_point_B.getText().toString());
        }
        // ε²εΌ
        int dis_a = 0;
        if (inp_discount_A.getText().toString().isEmpty() == false) {
            dis_a = Integer.parseInt(inp_discount_A.getText().toString());
        }
        int dis_b = 0;
        if (inp_discount_B.getText().toString().isEmpty() == false) {
            dis_b = Integer.parseInt(inp_discount_B.getText().toString());
        }

        double data = 0;
        double temp_a = 0;
        double temp_b = 0;
//        int chk_type_a = 0;
//        int chk_type_b = 0;
        int point_pri_a = 0;    //ιε
        int point_pri_b = 0;    //ιε

        //εΏι ι η?γ?ε₯εγγ§γγ―
        if (pri_a <= 0 || (set_a <= 0 && amount_a <=0) || pri_b <= 0 || (set_b <= 0 && amount_b <= 0)) {
            return -1;
        }
/*
        if (chk_per_A.isChecked())   chk_type_a = 1;
        if (chk_pri_A.isChecked())   chk_type_a = 2;
        if (chk_per_B.isChecked())   chk_type_b = 1;
        if (chk_pri_B.isChecked())   chk_type_b = 2;
*/

/*        chk_temp = (CheckBox)findViewById(R.id.chk_percent_A);
        if (chk_temp.isChecked())   chk_type_a = 1;
        chk_temp = (CheckBox)findViewById(R.id.chk_price_A);
        if (chk_temp.isChecked())   chk_type_a = 2;

        chk_temp = (CheckBox)findViewById(R.id.chk_percent_B);
        if (chk_temp.isChecked())   chk_type_b = 1;
        chk_temp = (CheckBox)findViewById(R.id.chk_price_B);
        if (chk_temp.isChecked())   chk_type_b = 2;*/

        temp_a = pri_a;
        temp_b = pri_b;

        //ε²εΌεΎγ?ιι‘
        if (chk_type_A > 0 && dis_a > 0) {
            //ε²εΌη
            if (chk_type_A == 1) {
                data = pri_a * dis_a;
                data = data / 100;
                temp_a = pri_a - data;
            }
            else if (chk_type_A == 2) {
                temp_a = pri_a - dis_a;
            }
        }
        if (chk_type_B > 0 && dis_b > 0) {
            //ε²εΌη
            if (chk_type_B == 1) {
                data = pri_b * dis_b;
                data = data / 100;
                temp_b = pri_b - data;
            }
            else if (chk_type_B == 2) {
                temp_b = pri_b - dis_b;
            }
        }
        if (temp_a <= 0 || temp_b <= 0) {
            return -2;
        }

        //γγ€γ³γεδΎ‘γγγγ
        if (point_a > 0)
        {
//            data = temp_a / point_a;
            data = point_a;
            if (data > 0)   point_pri_A = (int)data;
            else            point_pri_A = 0;
        }
        if (point_b > 0)
        {
//            data = temp_b / point_b;
            data = point_b;
            if (data > 0)   point_pri_B = (int)data;
            else            point_pri_B = 0;
        }


        //οΌεε½γγγ?ιι‘
        if (temp_a <= 0 || temp_b <= 0) {
            return -2;
        }
        if (set_a > 0) {
            temp_a = (temp_a / set_a);
        }
        if (set_b > 0) {
            temp_b = (temp_b / set_b);
        }
        //οΌε?Ήιε½γγγ?ιι‘
        if (temp_a <= 0 || temp_b <= 0) {
            return -2;
        }
        if (amount_a > 0) {
            temp_a = (temp_a / amount_a);
        }
        if (amount_b > 0) {
            temp_b = (temp_b / amount_b);
        }

        unit_A = temp_a;
        unit_B = temp_b;
        return 1;
    }
    public void onCalcurate(View view)
    {
        int ret;

        unit_A = 0;
        unit_B = 0;
        point_pri_A = 0;
        point_pri_B = 0;

        ret = CalcurateExec();
        if (ret < 0)
        {
            if (_language.equals("ja")) {
                ad.setTitle("ιι‘θ¨η?γ¨γ©γΌ");
                ad.setMessage("\n\n\nε₯εγγε€γι©εγ§γ―γγγΎγγ\n\n\n");
                ad.setPositiveButton("οΌ―οΌ«", null);
            }
            else if (_language.equals("zh")) {
                ad.setTitle("θ³ιθ¨η?ι―θͺ€");
                ad.setMessage("\n\n\nζ¨θΌΈε₯ηεΌδΈει©\n\n\n");
                ad.setPositiveButton("οΌ―οΌ«", null);
            }
            else if (_language.equals("ko")) {
                ad.setTitle("κΈμ‘ κ³μ° μ€λ₯");
                ad.setMessage("\n\n\nμλ ₯ ν κ°μ΄ μ¬λ°λ₯΄μ§ μμ΅λλ€\n\n\n");
                ad.setPositiveButton("οΌ―οΌ«", null);
            }
            else
            {
                ad.setTitle("Money calculation error");
                ad.setMessage("\n\n\nThe value you entered is not appropriate\n\n\n");
                ad.setPositiveButton("OK", null);
            }
            ad.show();
        }
        else
        {
            calcurate_done = true;
            //η΅ζθ‘¨η€Ί
            CalResult();
        }
    }

    /* γͺγ»γγ */
    public void onReset(View view)
    {
        unit_A = 0;
        unit_B = 0;
        point_pri_A = 0;
        point_pri_B = 0;
        calcurate_done = false;

        /* ε₯εε€γͺγ»γγ */
        inp_amount_A.setText("");
        inp_set_A.setText("");
        inp_pri_A.setText("");
        inp_point_A.setText("");
        inp_discount_A.setText("");
        inp_amount_B.setText("");
        inp_set_B.setText("");
        inp_pri_B.setText("");
        inp_point_B.setText("");
        inp_discount_B.setText("");

        //η¨ιγͺγ»γγ
        RadioGroup rtax_a= (RadioGroup)findViewById(R.id.RadioGroupTax_A);
        RadioGroup rtax_b= (RadioGroup)findViewById(R.id.RadioGroupTax_B);
        rtax_a.clearCheck();
        rtax_b.clearCheck();
        tax_flag_A = 0;
        tax_flag_B = 0;

        //ε²εΌ
        RadioGroup rdis_a= (RadioGroup)findViewById(R.id.RadioGroupDiscount_A);
        RadioGroup rdis_b= (RadioGroup)findViewById(R.id.RadioGroupDiscount_B);
        rdis_a.clearCheck();
        rdis_b.clearCheck();
        chk_type_A = 0;
        chk_type_B = 0;

        /* εζθ‘¨η€Ί */
        CalResult();
    }

    /* ε²εΌγοΌ‘γοΌ */
    public void onRbtn_discount_percent_A(View view)
    {
        chk_type_A = 1;
    }
    /* ε²εΌγοΌ‘γοΌε */
    public void onRbtn_discount_price_A(View view)
    {
        chk_type_A = 2;
    }
    /* ε²εΌγοΌ’γοΌ */
    public void onRbtn_discount_percent_B(View view)
    {
        chk_type_B = 1;
    }
    /* ε²εΌγοΌ’γοΌε */
    public void onRbtn_discount_price_B(View view)
    {
        chk_type_B = 2;
    }

    /* ε₯εη―ε²γγ§γγ― */
    class TextWatcherExtend implements TextWatcher {
        private int max_keta;
        private int select_id;
        // γ³γ³γΉγγ©γ―γΏ
        public TextWatcherExtend(int editTextId,int max) {
            select_id = editTextId;
            max_keta = max;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String inputStr = s.toString();
            String warnStr = "";

            if (inputStr.length() > max_keta)
            {
                switch (max_keta)
                {
                    case 1: warnStr = "(1-9)";          break;
                    case 2: warnStr = "(1-99)";         break;
                    case 3: warnStr = "(1-999)";        break;
                    case 4: warnStr = "(1-9999)";       break;
                    case 5: warnStr = "(1-99999)";      break;
                    case 6: warnStr = "(1-999999)";     break;
                }
                inp_chk_temp = (EditText)findViewById(select_id);
                inp_chk_temp.setText("");
                if (_language.equals("ja")) {
                    ad.setTitle("ζ‘ζ°γͺγΌγγΌ");
                    ad.setMessage("\n\n\nγγδΈεΊ¦ε₯εγγ¦δΈγγ\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("οΌ―οΌ«", null);
                }
                else if (_language.equals("zh")) {
                    ad.setTitle("θΆιδ½ζΈ");
                    ad.setMessage("\n\n\nθ«ιζ°θΌΈε₯\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("οΌ―οΌ«", null);
                }
                else if (_language.equals("ko")) {
                    ad.setTitle("μλ¦Ώμ μ€λ²");
                    ad.setMessage("\n\n\nλ€μ μλ ₯ ν΄μ£ΌμΈμ\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("οΌ―οΌ«", null);
                }
                else
                {
                    ad.setTitle("Number of digits exceeded");
                    ad.setMessage("\n\n\nPlease re-enter\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("OK", null);
                }
                ad.show();
            }
        }
    }
}