
package tfsapps.loveprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//　広告
import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

//サブスク
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;

public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener {
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

//    private RadioButton rbtn_tax0_A;
    private RadioButton rbtn_tax8_A;
    private RadioButton rbtn_tax10_A;
//    private RadioButton rbtn_tax0_B;
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

    // 広告
    private AdView mAdview;
    public RewardedAd rewardedAd;

    private LinearLayout admobLayout;
    private boolean visibleAd = true;
    private boolean isKeyboardVisible = false;
    private View rootView;
    private int iniScreenHight = 0;
//test_make
    //本番 ID　バナー
    private String adUnitID = "ca-app-pub-4924620089567925/8148766886";
    //テスト ID　バナー
//    private String adUnitID = "ca-app-pub-3940256099942544/6300978111";
//test_make
    // 本番ID 動画
    private String AD_UNIT_ID = "ca-app-pub-4924620089567925/2621100342";
    //テストID バナー
//    private String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";

    //サブスク
    private BillingClient billingClient;
    private static final String TAG = "tag-ad-free-MainActivity";
    private static final String SUBSCRIPTION_ID = "ad_free_plan_xxxx"; //

    //自動計算
//test_make
    private int REWARD_AUTO_CAL = 63;
    private boolean auto_cal = false;
    private boolean invalid_cal = false;

    //評価ポップアップ
    private int ReviewCount = 1;
//test_make
//    private int REVIEW_POP = 3;
    private int REVIEW_POP = 15;

    //  国設定
    private Locale _local;
    private String _language;
    private String _country;

    //  DB関連
    private MyOpenHelper helper;    //DBアクセス
    private int db_isopen = 0;      //DB使用したか
    private int db_tax_type_a = 0;  //DB税金種類
    private int db_tax_type_b = 0;  //DB
    private int db_amount_a = 0;    //DB容量
    private int db_amount_b = 0;    //DB
    private int db_set_a = 0;       //DB数量
    private int db_set_b = 0;       //DB
    private int db_price_a = 0;     //DB金額
    private int db_price_b = 0;     //DB
    private int db_point_a = 0;     //DBポイント
    private int db_point_b = 0;     //DB
    private int db_discount_a = 0;  //DB値引き額
    private int db_discount_b = 0;  //DB
    private int db_dis_type_a = 0;  //DB値引き種類
    private int db_dis_type_b = 0;  //DB
    private int db_data1 = 0;       //DB 自動計算フラグ
    private int db_data2 = 0;       //DB 評価ポップアップ
    private int db_data3 = 0;       //DB
    private int db_data4 = 0;       //DB
    private int db_data5 = 0;       //DB
    private int db_data6 = 0;       //DB
    private int db_data7 = 0;       //DB
    private int db_data8 = 0;       //DB
    private int db_data9 = 0;       //DB
    private int db_data10 = 0;       //DB

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

//        rbtn_tax0_A = (RadioButton)findViewById((R.id.rbtn_tax0_A));
//        rbtn_tax0_A.setBackgroundTintList(null);
//        rbtn_tax0_A.setTextColor(Color.DKGRAY);
        rbtn_tax8_A = (RadioButton)findViewById((R.id.rbtn_tax8_A));
        rbtn_tax8_A.setBackgroundTintList(null);
        rbtn_tax8_A.setTextColor(Color.DKGRAY);
        rbtn_tax10_A = (RadioButton)findViewById((R.id.rbtn_tax10_A));
        rbtn_tax10_A.setBackgroundTintList(null);
        rbtn_tax10_A.setTextColor(Color.DKGRAY);

//        rbtn_tax0_B = (RadioButton)findViewById((R.id.rbtn_tax0_B));
//        rbtn_tax0_B.setBackgroundTintList(null);
//        rbtn_tax0_B.setTextColor(Color.DKGRAY);
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

        tax_flag_A = 0; //税抜き
        tax_flag_B = 0; //税抜き

        //  国設定
        _local = Locale.getDefault();
        _language = _local.getLanguage();
        _country = _local.getCountry();

        /* 初期表示 */
        CalResult();

        //広告
        MobileAds.initialize(this);
        mAdview = new AdView(this);
        mAdview.setAdUnitId(adUnitID);
        mAdview.setAdSize(AdSize.BANNER);
        admobLayout = findViewById(R.id.adView);
        admobLayout.addView(mAdview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        //動画リワード
        loadRewardedAd();

        /* 広告動的表示 */
        rootView = findViewById(android.R.id.content);
        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);

                int screenHeight = rootView.getHeight();
                int keypadHeight = screenHeight - r.bottom;

                if (iniScreenHight == 0){
                    iniScreenHight = screenHeight;
                }
                if (iniScreenHight > screenHeight) {
                    // ソフトキーボードが表示されている場合
                    AdViewActive(false);
                } else {
                    // ソフトキーボードが非表示の場合
                    AdViewActive(true);
                }
            }
        });
    }
    /*
        広告を動的表示・非表示
    * */
    public void AdViewActive(boolean flag){
        //if (!visibleAd) {
        if (flag != visibleAd){
            visibleAd = flag;
        }
        else{
            return;
        }
        if (visibleAd){
            // admob 表示
            admobLayout.addView(mAdview);
            admobLayout.setVisibility(LinearLayout.VISIBLE);
            mAdview.setVisibility(AdView.VISIBLE);
        } else {
            // admob 非表示
            mAdview.setVisibility(AdView.GONE);
            admobLayout.removeView(mAdview);
        }
    }

    /************************
     リワード広告処理
     *************************/
    private void loadRewardedAd() {
        RewardedAd.load(this,
                AD_UNIT_ID,
                new AdRequest.Builder().build(),
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedAd Ad) {
                        rewardedAd = Ad;
//                        Context context = getApplicationContext();
//                        Toast.makeText(context, "報酬動画準備OK !!", Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", "The rewarded ad loaded.");
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
//                        Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                    }
                });

    }
    public void RdShow(){
        if (rewardedAd != null) {
            Activity activityContext = MainActivity.this;
            rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    RdPresent();
                }
            });
        } else {
//            Log.d("TAG", "The rewarded ad wasn't ready yet.");
        }
    }
    public void RdPresent() {
        Context context = getApplicationContext();
        Toast.makeText(context, "自動計算[ON]", Toast.LENGTH_SHORT).show();
        db_data1 = REWARD_AUTO_CAL;
        CalBtnDisp();
        loadRewardedAd();
    }


    private void ShowRatingPopup() {

        String ttl = "";
        String mess = "";

        //アプリを起動して 20回目の時
        if (db_data2 != REVIEW_POP){
            return;
        }
        else {
            db_data2++;
        }
        ttl =   "★☆アプリ評価のお願い☆★";
        mess =  "\nいつもご利用ありがとうございます\n"+
                "\nたくさん利用して頂いている貴方にお願いです。アプリを評価してもらませんか？ 評価して頂けると励みになります。"+
                "\n\n(この通知は今回限りです)"+
                "\n\n\n";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(ttl);
        builder.setMessage(mess);
        builder.setPositiveButton("評価する", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RedirectToPlayStoreForRating();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("　後で　", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void ShowRatingPopupNG() {
        String ttl = "";
        String mess = "";

        ttl = "接続に失敗しました";
        mess = "\n評価サイトへのアクセスに失敗しました\n" +
                "\n" +
                "\n\n" +
                "\n\n\n";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(ttl);
        builder.setMessage(mess);
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void RedirectToPlayStoreForRating() {
        try {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            ShowRatingPopupNG();
        }
    }


    /* **************************************************
       各種OS上の動作定義
    ****************************************************/
    @Override
    public void onStart() {
        super.onStart();
        //DBのロード
        /* データベース */
        helper = new MyOpenHelper(this);
        AppDBInitRoad();
        CalBtnDisp();

        //評価ポップアップ処理
        if (db_data2 <= REVIEW_POP){
            if (ReviewCount != 0){
                db_data2++;
                ReviewCount = 0;
            }
        }
        ShowRatingPopup();
    }
    @Override
    public void onResume() {
        super.onResume();

        //サブスク
        //BillingClientを初期化
        /* test_make
        billingClient = BillingClient.newBuilder(this)
                .setListener(this)
                .enablePendingPurchases()
                .build();

        // Google Playへの接続
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "@@@@@@ Billing Client connected ");
                    checkSubscriptionStatus();
                } else {
                    Log.e(TAG, "@@@@@@ Billing connection failed: " + billingResult.getDebugMessage());
//                    Log.e(TAG, "Billing Client connection failed");
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.e(TAG, "@@@@@@ Billing Service disconnected");
            }
        });
        */
    }
    @Override
    public void onPause(){
        super.onPause();
        AppDBUpdated(false); //DB保存
    }
    @Override
    public void onStop(){
        super.onStop();
        AppDBUpdated(false); //DB保存
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        AppDBUpdated(false); //DB保存
        /*  test_make
        if (billingClient != null){
            billingClient.endConnection();
        }
         */
    }

    /* **************************************************
        DB初期ロードおよび設定
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
        sql.append(" ,data1,data2");
        sql.append(" ,data3,data4");
        sql.append(" ,data5,data6");
        sql.append(" ,data7,data8");
        sql.append(" ,data9,data10");
        sql.append(" FROM appinfo;");
        try {
            Cursor cursor = db.rawQuery(sql.toString(), null);
            //TextViewに表示
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
                db_data1 = cursor.getInt(15);
                db_data2 = cursor.getInt(16);
                db_data3 = cursor.getInt(17);
                db_data4 = cursor.getInt(18);
                db_data5 = cursor.getInt(19);
                db_data6 = cursor.getInt(20);
                db_data7 = cursor.getInt(21);
                db_data8 = cursor.getInt(22);
                db_data9 = cursor.getInt(23);
                db_data10 = cursor.getInt(24);
            }
        } finally {
            db.close();
        }

        db = helper.getWritableDatabase();
        if (db_isopen == 0) {
            long ret;
            /* 新規レコード追加 */
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
            insertValues.put("data1", 0);
            insertValues.put("data2", 0);
            insertValues.put("data3", 0);
            insertValues.put("data4", 0);
            insertValues.put("data5", 0);
            insertValues.put("data6", 0);
            insertValues.put("data7", 0);
            insertValues.put("data8", 0);
            insertValues.put("data9", 0);
            insertValues.put("data10", 0);
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
        DB更新
    ****************************************************/
    public void AppDBUpdated(boolean isdisp) {
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
        insertValues.put("data1", db_data1);
        insertValues.put("data2", db_data2);
        insertValues.put("data3", db_data3);
        insertValues.put("data4", db_data4);
        insertValues.put("data5", db_data5);
        insertValues.put("data6", db_data6);
        insertValues.put("data7", db_data7);
        insertValues.put("data8", db_data8);
        insertValues.put("data9", db_data9);
        insertValues.put("data10", db_data10);
        int ret;
        try {
            ret = db.update("appinfo", insertValues, null, null);
        } finally {
            db.close();
        }
        if (ret != -1){
            if (isdisp) {
                Context context = getApplicationContext();
                Toast.makeText(context, "セーブ中...", Toast.LENGTH_SHORT).show();
//               Toast.makeText(context, "セーブ中...("+db_data1+")...", Toast.LENGTH_SHORT).show();
            }
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
        各ボタン処理
    ****************************************************/
    //  DBデータに設定
    public void set_DataToDb(){
        //税率
        db_tax_type_a = tax_flag_A;
        db_tax_type_b = tax_flag_B;

        //容量
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

        // 数量
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

        // 金額
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

        //ポイント
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

        // 割引
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

        //  割引種類
        db_dis_type_a = chk_type_A;
        db_dis_type_b = chk_type_B;
    }

    //  DBデータを取得
    public void get_DataToDb(){

        //税金リセット
        switch (db_tax_type_a){
//            case 1:     rbtn_tax0_A.setChecked(true);   break;
            case 2:     rbtn_tax8_A.setChecked(true);   break;
            case 3:     rbtn_tax10_A.setChecked(true);  break;

            default:
//                        rbtn_tax0_A.setChecked(false);
                        rbtn_tax8_A.setChecked(false);
                        rbtn_tax10_A.setChecked(false); break;

        }
        switch (db_tax_type_b){
//            case 1:     rbtn_tax0_B.setChecked(true);   break;
            case 2:     rbtn_tax8_B.setChecked(true);   break;
            case 3:     rbtn_tax10_B.setChecked(true);  break;

            default:
//                        rbtn_tax0_B.setChecked(false);
                        rbtn_tax8_B.setChecked(false);
                        rbtn_tax10_B.setChecked(false); break;
        }
        tax_flag_A = db_tax_type_a;
        tax_flag_B = db_tax_type_b;

        /* 入力値リセット */
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

        //割引
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

    /* 記憶ボタン */
    public void onSave(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("〜 短縮入力 機能 〜");
        builder.setMessage("\n\n短縮入力とは「税率、容量、数量、価格、ポイント、割引」を保存&読込する事が可能です。日常的に使用する条件を保存すると便利です。\n\n操作に応じてボタンを選択して下さい。\n\n\n\n [戻る] 短縮入力画面を閉じる\n [読込] 保存した値を読み込む\n [保存] 入力した値を保存する\n\n\n");

        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //ダイアログ処理
                set_DataToDb();
                AppDBUpdated(true);
                calcurate_done = false;
                CalResult();
            }
        });

        builder.setNegativeButton("読込", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //バックアップ
//                boolean bk_flag = auto_cal;
//                int bk_data1 = db_data1;
                invalid_cal = true;

                get_DataToDb();
                calcurate_done = false;
                CalResult();

                //バックアップのロード
//                auto_cal = bk_flag;
//                db_data1 = bk_data1;
                invalid_cal = false;
                CalBtnDisp();
            }
        });

        builder.setNeutralButton("戻る", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                /*
                *   処理なし（戻るだけ）
                * */
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        /* ソフトキーボードを隠す */
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* 計算結果詳細 */
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
                temp_ttl = "「計算」未実行エラー";
                temp_st = "条件を入力して「計算」を押して下さい\n\n\n\n\n";
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
                    temp_ttl = "商品Ａがお得！！";
                } else if (data_b < data_a) {
                    temp_ttl = "商品Ｂがお得！！";
                } else {
                    temp_ttl = "どちらも同じ";
                }

                temp_st = "<<商品Ａ>>\n 最終単位価格　　▶" + pdm.format(data_a) + "円\n 単価（割引含む）▶" + pdm.format(unit_A) + "円\n ポイント還元　　▶" + String.valueOf(point_pri_A) + "pt\n\n" +
                        "<<商品Ｂ>>\n 最終単位価格　　▶" + pdm.format(data_b) + "円\n 単価（割引含む）▶" + pdm.format(unit_B) + "円\n ポイント還元　　▶" + String.valueOf(point_pri_B) + "pt";
            }
            else {
                if (data_a < data_b) {
                    temp_ttl = "ITEM A is a bargain";
                } else if (data_b < data_a) {
                    temp_ttl = "ITEM B is a bargain";
                } else {
                    temp_ttl = "BOTH are the same";
                }
                temp_st = "<<ITEM A>>\n Final unit price\n  ▶" + pdm.format(data_a) + "\n Unit price\n  ▶" + pdm.format(unit_A) + "\n Point reduction\n  ▶" + String.valueOf(point_pri_A) + "pt\n\n" +
                        "<<ITEM B>>\n Final unit price\n  ▶" + pdm.format(data_b) + "\n Unit price\n  ▶" + pdm.format(unit_B) + "\n Point reduction\n  ▶" + String.valueOf(point_pri_B) + "pt";

            }
        }

        temp_ad.setTitle(temp_ttl);
        temp_ad.setMessage(temp_st);
        if (_language.equals("ja")) {
            temp_ad.setPositiveButton("戻る", null);
        }
        else
        {
            temp_ad.setPositiveButton("CANCEL", null);
        }
        temp_ad.show();

        /* ソフトキーボードを隠す */
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* 便利ボタン */
    public void onAddFunc(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("〜 便利　自動計算[ON] 〜");
        builder.setMessage("\n\n広告動画を視聴して「自動計算」を有効にしますか？" +
                "\n\n「自動計算」とは価格、容量、数量などを入力するだけで自動的に金額計算を行います。「計算」ボタンの操作が不要となります。" +
                "\n\nしばらく使用すると「自動計算」は無効になります、続けて「自動計算」を利用する場合は再視聴下さい。" +
                "\n\n [戻る] 画面を閉じる" +
                "\n [視聴] 広告動画を視聴する" +
                "\n");

        builder.setPositiveButton("視聴", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //ダイアログ処理
                //test_make
                RdShow();
            }
        });

        builder.setNeutralButton("戻る", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                /*
                 *   処理なし（戻るだけ）
                 * */
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        /* ソフトキーボードを隠す */
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* 自動計算　督促表示 */
    public void RewardRecommend(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("〜 便利　自動計算[OFF] 〜");
        builder.setMessage("" +
                "\n\n「自動計算」が無効となりました。" +
                "\n\n広告動画を視聴して「自動計算」を有効にしますか？" +
                "\n\n" +
                "\n\n" +
                "\n\n [戻る] 今は視聴しない" +
                "\n [視聴] 広告動画を視聴する" +
                "\n");

        builder.setPositiveButton("視聴", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //ダイアログ処理
                //test_make
                RdShow();
            }
        });

        builder.setNeutralButton("戻る", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                /*
                 *   処理なし（戻るだけ）
                 * */
                CalBtnDisp();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /********************************************
     *  自動計算処理
     ********************************************/
    public void Auto_Cal()
    {
        if (inp_pri_A.getText().toString().isEmpty() ) return;
        if (inp_pri_B.getText().toString().isEmpty() ) return;

        int pri_a = Integer.parseInt(inp_pri_A.getText().toString());
        int pri_b = Integer.parseInt(inp_pri_B.getText().toString());

        //自動計算無効の場合はスキップする
        if (invalid_cal){
            return;
        }

        //自動計算可否判断
        if (db_data1 > 0){
            auto_cal = true;
        }
        else{
            auto_cal = false;
        }

        //自動計算処理
        if (auto_cal){
            if (pri_a > 0 && pri_b > 0){
                PriceCalcurate();
                db_data1--; //計算をしたのでデクリメント
                if (db_data1 <= 0){
                    db_data1 = 0;
                    RewardRecommend();
                }
                calcurate_done = false;
            }
        }
        else {
            CalBtnDisp();
        }

//test_make
//        Context context = getApplicationContext();
//        Toast.makeText(context, "" + auto_cal + db_data1, Toast.LENGTH_SHORT).show();

    }

    public void tax_disp()
    {
    }

    /* 税設定Ａ 0%  */
    public void onRbtn_tax0_A(View view)
    {
        tax_flag_A = 1;
        tax_disp();
    }
    /* 税設定Ａ 8%  */
    public void onRbtn_tax8_A(View view)
    {
        tax_flag_A = 2;
        tax_disp();
    }
    /* 税設定Ａ 10% */
    public void onRbtn_tax10_A(View view)
    {
        tax_flag_A = 3;
        tax_disp();
    }
    /* 税設定Ｂ 0%  */
    public void onRbtn_tax0_B(View view)
    {
        tax_flag_B = 1;
        tax_disp();
    }
    /* 税設定Ｂ 0%  */
    public void onRbtn_tax8_B(View view)
    {
        tax_flag_B = 2;
        tax_disp();
    }
    /* 税設定Ｂ 0%  */
    public void onRbtn_tax10_B(View view)
    {
        tax_flag_B = 3;
        tax_disp();
    }

    public void CalBtnDisp(){
        Button btn_cal = findViewById(R.id.btn_cal);

        //自動計算可否判断
        if (db_data1 > 0){
            auto_cal = true;
        }
        else{
            auto_cal = false;
        }

        btn_cal.setBackgroundTintList(null);
        if (auto_cal){
            btn_cal.setBackgroundResource(R.drawable.bak_btn_4);
            btn_cal.setText("自動");
        }
        else{
            btn_cal.setBackgroundResource(R.drawable.bak_btn_2);
            btn_cal.setText("計算");
        }
    }

    /* 結果 */
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

        CalBtnDisp();

        DecimalFormat pdm = new DecimalFormat("#0.00");

        if (calcurate_done == false)
        {
            if (_language.equals("ja")) {
                temp_st1 = "条件入力後に「計算」して下さい";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "輸入條件後請「計算」";
            }
            else if (_language.equals("ko")) {
                temp_st1 = "조건 입력 후「계산」해주세요";
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
                temp_st1 = "商品Ａがお得！！";
                temp_st2 = "単位価格　A ▶" + pdm.format(data_a) + "円" + "　B ▶" + pdm.format(data_b) + "円";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "產品A是很大的！！";
                temp_st2 = "單價　A ▶" + pdm.format(data_a)  + "　B ▶" + pdm.format(data_b);
            }
            else if (_language.equals("ko")) {
                temp_st1 = "제품 A가 거래！！";
                temp_st2 = "단위 가격　A ▶" + pdm.format(data_a)  + "　B ▶" + pdm.format(data_b);
            }
            else
            {
                temp_st1 = "ITEM A is a bargain";
                temp_st2 = "Unit price　A ▶" + pdm.format(data_a) + "　B ▶" + pdm.format(data_b);
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
                temp_st1 = "商品Ｂがお得！！";
                temp_st2 = "単位価格　A ▶" + pdm.format(data_a) + "円" + "　B ▶" + pdm.format(data_b) + "円";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "產品B是很大的！！";
                temp_st2 = "單價　A ▶" + pdm.format(data_a)  + "　B ▶" + pdm.format(data_b);
            }
            else if (_language.equals("ko")) {
                temp_st1 = "제품 B가 거래！！";
                temp_st2 = "단위 가격　A ▶" + pdm.format(data_a)  + "　B ▶" + pdm.format(data_b);
            }
            else {
                temp_st1 = "ITEM B is a bargain";
                temp_st2 = "Unit price　A ▶" + pdm.format(data_a) + "　B ▶" + pdm.format(data_b);
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
                temp_st1 = "どっちも同じ";
                temp_st2 = "単位価格　A ▶" + pdm.format(data_a) + "円" + "　B ▶" + pdm.format(data_b) + "円";
            }
            else if (_language.equals("zh")) {
                temp_st1 = "兩者都是一樣的！！";
                temp_st2 = "單價　A ▶" + pdm.format(data_a)  + "　B ▶" + pdm.format(data_b);
            }
            else if (_language.equals("ko")) {
                temp_st1 = "모두 같은！！";
                temp_st2 = "단위 가격　A ▶" + pdm.format(data_a)  + "　B ▶" + pdm.format(data_b);
            }
            else
            {
                temp_st1 = "BOTH are the same";
                temp_st2 = "Unit price　A ▶" + pdm.format(data_a) + "　B ▶" + pdm.format(data_b);
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

    /* 計算 */
    public int CalcurateExec()
    {
//        CheckBox chk_temp;
        // 容量
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

        // 数量
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

        // 金額
        if (inp_pri_A.getText().toString().isEmpty() ) return -1;
        int pri_a = Integer.parseInt(inp_pri_A.getText().toString());
        if (inp_pri_B.getText().toString().isEmpty() ) return -1;
        int pri_b = Integer.parseInt(inp_pri_B.getText().toString());

        // 税金補正処理追加
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

        // ポイント単価
        int point_a = 0;
        if (inp_point_A.getText().toString().isEmpty() == false) {
            point_a = Integer.parseInt(inp_point_A.getText().toString());
        }
        int point_b = 0;
        if (inp_point_B.getText().toString().isEmpty() == false) {
            point_b = Integer.parseInt(inp_point_B.getText().toString());
        }
        // 割引
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
        int point_pri_a = 0;    //還元
        int point_pri_b = 0;    //還元

        //必須項目の入力チェック
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

        //割引後の金額
        if (chk_type_A > 0 && dis_a > 0) {
            //割引率
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
            //割引率
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

        //ポイント単価をやめる
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


        //１個当たりの金額
        if (temp_a <= 0 || temp_b <= 0) {
            return -2;
        }
        if (set_a > 0) {
            temp_a = (temp_a / set_a);
        }
        if (set_b > 0) {
            temp_b = (temp_b / set_b);
        }
        //１容量当たりの金額
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
    //test_make
    public void onCalcurate(View view)
    {
        PriceCalcurate();

        /* ソフトキーボードを隠す */
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void PriceCalcurate()
//    public void onCalcurate(View view)
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
                ad.setTitle("金額計算エラー");
                ad.setMessage("\n\n\n入力した値が適切ではありません\n\n\n");
                ad.setPositiveButton("ＯＫ", null);
            }
            else if (_language.equals("zh")) {
                ad.setTitle("資金計算錯誤");
                ad.setMessage("\n\n\n您輸入的值不合適\n\n\n");
                ad.setPositiveButton("ＯＫ", null);
            }
            else if (_language.equals("ko")) {
                ad.setTitle("금액 계산 오류");
                ad.setMessage("\n\n\n입력 한 값이 올바르지 않습니다\n\n\n");
                ad.setPositiveButton("ＯＫ", null);
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
            //結果表示
            CalResult();
        }

//        AdViewActive();

//test_make
        /* ソフトキーボードを隠す */
//        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* リセット */
    public void onReset(View view)
    {
        //バックアップ
//        boolean bk_flag = auto_cal;
//        int bk_data1 = db_data1;
        invalid_cal = true;

        unit_A = 0;
        unit_B = 0;
        point_pri_A = 0;
        point_pri_B = 0;
        calcurate_done = false;

        /* 入力値リセット */
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

        //税金リセット
        RadioGroup rtax_a= (RadioGroup)findViewById(R.id.RadioGroupTax_A);
        RadioGroup rtax_b= (RadioGroup)findViewById(R.id.RadioGroupTax_B);
        rtax_a.clearCheck();
        rtax_b.clearCheck();
        tax_flag_A = 0;
        tax_flag_B = 0;

        //割引
        RadioGroup rdis_a= (RadioGroup)findViewById(R.id.RadioGroupDiscount_A);
        RadioGroup rdis_b= (RadioGroup)findViewById(R.id.RadioGroupDiscount_B);
        rdis_a.clearCheck();
        rdis_b.clearCheck();
        chk_type_A = 0;
        chk_type_B = 0;

        /* 初期表示 */
        CalResult();

        //バックアップのロード
//        auto_cal = bk_flag;
//        db_data1 = bk_data1;
        invalid_cal = false;
        CalBtnDisp();


        /* ソフトキーボードを隠す */
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* 割引きＡ　％ */
    public void onRbtn_discount_percent_A(View view)
    {
        chk_type_A = 1;
    }
    /* 割引きＡ　－円 */
    public void onRbtn_discount_price_A(View view)
    {
        chk_type_A = 2;
    }
    /* 割引きＢ　％ */
    public void onRbtn_discount_percent_B(View view)
    {
        chk_type_B = 1;
    }
    /* 割引きＢ　－円 */
    public void onRbtn_discount_price_B(View view)
    {
        chk_type_B = 2;
    }

    /* 入力範囲チェック */
    class TextWatcherExtend implements TextWatcher {
        private int max_keta;
        private int select_id;

        // コンストラクタ
        public TextWatcherExtend(int editTextId, int max) {
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

            if (inputStr.length() > max_keta) {
                switch (max_keta) {
                    case 1:
                        warnStr = "(1-9)";
                        break;
                    case 2:
                        warnStr = "(1-99)";
                        break;
                    case 3:
                        warnStr = "(1-999)";
                        break;
                    case 4:
                        warnStr = "(1-9999)";
                        break;
                    case 5:
                        warnStr = "(1-99999)";
                        break;
                    case 6:
                        warnStr = "(1-999999)";
                        break;
                }
                inp_chk_temp = (EditText) findViewById(select_id);
                inp_chk_temp.setText("");
                if (_language.equals("ja")) {
                    ad.setTitle("桁数オーバー");
                    ad.setMessage("\n\n\nもう一度入力して下さい\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("ＯＫ", null);
                } else if (_language.equals("zh")) {
                    ad.setTitle("超過位數");
                    ad.setMessage("\n\n\n請重新輸入\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("ＯＫ", null);
                } else if (_language.equals("ko")) {
                    ad.setTitle("자릿수 오버");
                    ad.setMessage("\n\n\n다시 입력 해주세요\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("ＯＫ", null);
                } else {
                    ad.setTitle("Number of digits exceeded");
                    ad.setMessage("\n\n\nPlease re-enter\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("OK", null);
                }
                ad.show();
            }

            Auto_Cal();
        }
    }

    /*-----------------------------------------------------------------
        サブスク処理
     -----------------------------------------------------------------*/
    /*test_make*/
    private void checkSubscriptionStatus(){
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),
                (billingResult, purchasesList) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchasesList != null){
                        boolean isSubscribed = false;
                        for (Purchase purchase : purchasesList) {
                            if (purchase.getProducts().contains(SUBSCRIPTION_ID)) {
                                isSubscribed = true;
                                break;
                            }
                        }
                        if (isSubscribed){
                            Log.e(TAG, "@@@@@@ サブスク有効");
                            AdViewActive(false);
                        }
                        else{
                            Log.e(TAG, "@@@@@@ サブスク無効");
                            AdViewActive(true);
                        }
                    }
                    else{
                        Log.e(TAG, "@@@@@@ サブスク有効／無効情報の取得エラー");
                    }
                }
        );
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase>purchases){

    }

}