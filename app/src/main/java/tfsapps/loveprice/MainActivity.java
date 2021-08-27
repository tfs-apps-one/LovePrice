
package tfsapps.loveprice;

import androidx.appcompat.app.AppCompatActivity;
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

import java.text.DecimalFormat;
import java.util.Locale;

//　広告
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

    // 広告
    private AdView mAdview;

    //  国設定
    private Locale _local;
    private String _language;
    private String _country;

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

        tax_flag_A = 0; //税抜き
        tax_flag_B = 0; //税抜き

        //  国設定
        _local = Locale.getDefault();
        _language = _local.getLanguage();
        _country = _local.getCountry();

        /* 初期表示 */
        CalResult();

        //広告
        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
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
            else if (_language.equals("zh")) {
                temp_ttl = "「計算」未執行錯誤";
                temp_st = "請輸入條件並按「計算」\n\n\n\n\n";
            }
            else if (_language.equals("ko")) {
                temp_ttl = "「계산」미 실행 오류";
                temp_st = "조건을 입력하고 「계산」을 눌러주세요\n\n\n\n\n";
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
            else if (_language.equals("zh")) {

                if (data_a < data_b) {
                    temp_ttl = "產品A是很大的！！";
                } else if (data_b < data_a) {
                    temp_ttl = "產品B是很大的！！";
                } else {
                    temp_ttl = "兩者都是一樣的";
                }

                temp_st = "<<產品A>>\n 最終單價　　　▶" + pdm.format(data_a) + "\n 單價（含折扣）▶" + pdm.format(unit_A) + "\n 減少點數　　　▶" + String.valueOf(point_pri_A) + "pt\n\n" +
                        "<<產品B>>\n 最終單價　　　▶" + pdm.format(data_b) + "\n 單價（含折扣）▶" + pdm.format(unit_B) + "\n 減少點數　　　▶" + String.valueOf(point_pri_B) + "pt";
            }
            else if (_language.equals("ko")) {

                if (data_a < data_b) {
                    temp_ttl = "제품 A가 거래！！";
                } else if (data_b < data_a) {
                    temp_ttl = "제품 B가 거래！！";
                } else {
                    temp_ttl = "모두 같은";
                }

                temp_st = "<<상품 A>>\n 마지막 단위 가격　▶" + pdm.format(data_a) + "\n 단가 （할인 포함）▶" + pdm.format(unit_A) + "\n 포인트 환원 　　　▶" + String.valueOf(point_pri_A) + "pt\n\n" +
                        "<<상품 B>>\n 마지막 단위 가격　▶" + pdm.format(data_b) + "\n 단가 （할인 포함）▶" + pdm.format(unit_B) + "\n 포인트 환원 　　　▶" + String.valueOf(point_pri_B) + "pt";
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
    }

    public void tax_disp()
    {
    }

    /* 税設定Ａ 0%  */
    public void onRbtn_tax0_A(View view)
    {
        tax_flag_A = 0;
        tax_disp();
    }
    /* 税設定Ａ 8%  */
    public void onRbtn_tax8_A(View view)
    {
        tax_flag_A = 1;
        tax_disp();
    }
    /* 税設定Ａ 10% */
    public void onRbtn_tax10_A(View view)
    {
        tax_flag_A = 2;
        tax_disp();
    }
    /* 税設定Ｂ 0%  */
    public void onRbtn_tax0_B(View view)
    {
        tax_flag_B = 0;
        tax_disp();
    }
    /* 税設定Ｂ 0%  */
    public void onRbtn_tax8_B(View view)
    {
        tax_flag_B = 1;
        tax_disp();
    }
    /* 税設定Ｂ 0%  */
    public void onRbtn_tax10_B(View view)
    {
        tax_flag_B = 2;
        tax_disp();
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
        if (tax_flag_A == 1) {  //8%
            pri_a = (pri_a * 108) / 100;
        }
        else if (tax_flag_A == 2) {  //10%
            pri_a = (pri_a * 110) / 100;
        }
        if (tax_flag_B == 1) {  //8%
            pri_b = (pri_b * 108) / 100;
        }
        else if (tax_flag_B == 2) {  //10%
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
    }

    /* リセット */
    public void onReset(View view)
    {
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
                    ad.setTitle("桁数オーバー");
                    ad.setMessage("\n\n\nもう一度入力して下さい\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("ＯＫ", null);
                }
                else if (_language.equals("zh")) {
                    ad.setTitle("超過位數");
                    ad.setMessage("\n\n\n請重新輸入\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("ＯＫ", null);
                }
                else if (_language.equals("ko")) {
                    ad.setTitle("자릿수 오버");
                    ad.setMessage("\n\n\n다시 입력 해주세요\n" + warnStr + "\n\n\n");
                    ad.setPositiveButton("ＯＫ", null);
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