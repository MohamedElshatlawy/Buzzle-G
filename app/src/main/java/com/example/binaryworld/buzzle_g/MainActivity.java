package com.example.binaryworld.buzzle_g;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener {

    final ArrayList clicks = new ArrayList();
    final ArrayList imgsKey = new ArrayList();
    int rows = 4;
    int columns = 4;
    ImageView[][] imgs;
    TableLayout tableLayout;
    TableRow tableRow;
    int[] imgsData1;
    Button bt;
    TextView scoreText;
    int score = 0;
    int o;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgs = new ImageView[4][4];
        imgsData1 = new int[]{R.drawable.m_1_tom, R.drawable.m_2_jerry, R.drawable.m_3_mickey, R.drawable.m_4_namour,
                R.drawable.m_5_whinny,
                R.drawable.m_6_simba, R.drawable.m_7_dog, R.drawable.m_8_rabb};

        tableLayout = (TableLayout) findViewById(R.id.table_layout);
        scoreText = (TextView) findViewById(R.id.score);

        bt = (Button) findViewById(R.id.startBtn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onDestroy();
                onCreate(savedInstanceState);
                initImages();
            }
        });


    }


    public void initImages() {
        int k = 0;
        for (int i = 0; i < rows; i++) {
            tableRow = new TableRow(this);

            tableLayout.addView(tableRow);
            for (int j = 0; j < columns; j++) {
                imgs[i][j] = new ImageView(this);
                imgs[i][j].setImageResource(android.R.drawable.btn_star);
                imgs[i][j].setMinimumWidth(120);
                imgs[i][j].setMinimumHeight(120);

                tableRow.addView(imgs[i][j]);
            }
        }
        for (int i = 0; i < rows; i++) {
            List l = randomImages();
            if (i == 2)
                k = 0;

            for (int m = 0; m < columns; m++) {
                //imgs[i][((int) l.get(m))].setImageResource(imgsData1[k]);
                imgs[i][((int) l.get(m))].setTag(imgsData1[k]);

                imgs[i][((int) l.get(m))].setOnClickListener(this);
                k++;

            }
        }
    }

    public ArrayList randomImages() {

        ArrayList arr = new ArrayList<>();
        ArrayList keys = new ArrayList<>();
        ArrayList noFoundVaals = new ArrayList<>();


        for (int i = 0; i < 4; i++)
            arr.add((int) (Math.random() * 4));
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i) == arr.get(j)) {
                    keys.add(j);
                }
            }
        }

        for (int i = 0; i < arr.size(); i++) {
            if (arr.contains(i) != true)
                noFoundVaals.add(i);
        }

        for (int i = 0; i < noFoundVaals.size(); i++) {
            arr.set((int) keys.get(i), noFoundVaals.get(i));
        }

        return arr;


    }

    @Override
    public void onClick(View v) {
        final ImageView imageView = (ImageView) v;
        imageView.setImageResource((int) imageView.getTag());

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        //Toast.makeText(MainActivity.this, bitmap.toString(), Toast.LENGTH_LONG).show();

        clicks.add(bitmap);
        imgsKey.add(imageView);
        if (clicks.size() == 2) {
            if (clicks.get(0) == (clicks.get(1))) {
                Toast.makeText(MainActivity.this, "good", Toast.LENGTH_LONG).show();

                for (int i = 0; i < imgsKey.size(); i++) {

                    final ImageView bb = ((ImageView) imgsKey.get(i));
                    ((ImageView) imgsKey.get(i)).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bb.setVisibility(View.INVISIBLE);
                        }
                    }, 1000);
                }
                score++;

                scoreText.setText(score + "");
            } else {
                Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_LONG).show();


                for (int i = 0; i < imgsKey.size(); i++) {

                    final ImageView bb = ((ImageView) imgsKey.get(i));
                    ((ImageView) imgsKey.get(i)).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bb.setImageResource(android.R.drawable.btn_star);
                        }
                    }, 1000);
                }
                score--;
                scoreText.setText(score + "");

            }
            clicks.clear();
            imgsKey.clear();
        } else if (clicks.size() > 2) {
            clicks.clear();
            imgsKey.clear();
        }


    }
}
