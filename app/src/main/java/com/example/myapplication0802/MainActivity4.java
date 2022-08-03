package com.example.myapplication0802;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Integer> num = new ArrayList<>();
    ArrayList<Button> btn_list = new ArrayList<>();
    static Integer cur_num = 1;

    ImageView imgView;
    TextView textView_success;

    Handler handler;
    Long StartTime;
    AtomicBoolean isGaming;
    TextView textView_time;
    Long MillisecondTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // For button Control
        btn_list.add(findViewById(R.id.btn_1));
        btn_list.add(findViewById(R.id.btn_2));
        btn_list.add(findViewById(R.id.btn_3));
        btn_list.add(findViewById(R.id.btn_4));
        btn_list.add(findViewById(R.id.btn_5));
        btn_list.add(findViewById(R.id.btn_6));
        btn_list.add(findViewById(R.id.btn_7));
        btn_list.add(findViewById(R.id.btn_8));
        btn_list.add(findViewById(R.id.btn_9));
        btn_list.add(findViewById(R.id.btn_10));
        btn_list.add(findViewById(R.id.btn_11));
        btn_list.add(findViewById(R.id.btn_12));
        for (Button ele: btn_list) {
            ele.setOnClickListener(this);
        }
        // For Handler Control.
        handler = new Handler();
        isGaming = new AtomicBoolean(false);
        textView_time = findViewById(R.id.textView);

        // Success Image & Text Control
        imgView = findViewById(R.id.imageView4);
        Glide.with(this).load(R.drawable.pv_success).into(imgView);
        imgView.setVisibility(View.GONE);
        textView_success = findViewById(R.id.textView_success);
        textView_success.setVisibility(View.GONE);

        resetButton();
    }

    public void onClick_btn_rest(View view)
    {
        Log.d("내꺼", "onClick_btn_rest: ??");
        resetButton();
    }

    public void resetButton()
    {
        cur_num = 1;
        for(int i=1; i<13; i++) num.add(i);
        for (Button ele: btn_list) {
            ele.setText(getRandomNum().toString());
            ele.setBackgroundColor(getRandomColor());
            ele.setVisibility(View.VISIBLE);
        }
        imgView.setVisibility(View.GONE);
        textView_success.setVisibility(View.GONE);
        isGaming.set(false);
        textView_time.setText("내가 좋아하는 게임!");
        MillisecondTime = 0L;
    }

    public Integer getRandomNum()
    {
        Random rand = new Random();
        int selected_idx = rand.nextInt(num.size());
        return num.remove(selected_idx);
    }

    public Integer getRandomColor()
    {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        return Color.argb(255, tlr.nextInt(100,256), tlr.nextInt(100,256), tlr.nextInt(100,256));
    }

    @Override
    public void onClick(View v) {
        TextView ele = findViewById(v.getId());
        if(isGaming.get() == false)
        {
            isGaming.set(true);
            handler.post(runable);
            StartTime = SystemClock.uptimeMillis();
        }
        if( ele.getText().toString() == cur_num.toString())
        {
            ele.setVisibility(View.INVISIBLE);
            if(cur_num == 12)
            {
                imgView.setVisibility(View.VISIBLE);
                textView_success.setVisibility(View.VISIBLE);
                isGaming.set(false);
            }
            cur_num++;
        }else
        {
            Toast.makeText(getApplicationContext(), "잘못누름!  " + cur_num + "을 누르세요!", Toast.LENGTH_SHORT).show();
        }
    }

    public Runnable runable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            textView_time.setText(MillisecondTime/1000 + "." + MillisecondTime%1000 + "sec");

            if(isGaming.get() == true)
            {
                handler.post(this);
            }
        }
    };
}
