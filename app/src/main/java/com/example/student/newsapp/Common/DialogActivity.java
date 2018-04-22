package com.example.student.newsapp.Common;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.student.newsapp.R;
import com.github.ybq.android.spinkit.style.Circle;
import com.tuyenmonkey.mkloader.MKLoader;

import static com.example.student.newsapp.Common.Colors.colors;
import static com.example.student.newsapp.DetailsActivity.mCircleDrawable;


public class DialogActivity extends Activity implements View.OnClickListener {
    public static Activity activity;
    MKLoader mkloader2;
    RelativeLayout rl_click;
     private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);

        init();
        listener();

    }

    private void init() {
        rl_click = (RelativeLayout) findViewById(R.id.rl_click);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(mCircleDrawable);
        progressBar.setProgress(0);

        activity = DialogActivity.this;
        this.setFinishOnTouchOutside(false);
    }

    private void listener() {
        rl_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_click:
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
/*

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.student.newsapp.R;
import com.github.ybq.android.spinkit.style.Circle;

import static com.example.student.newsapp.Common.Colors.colors;
import static com.example.student.newsapp.DetailsActivity.mCircleDrawable;


public class DialogActivity extends Activity implements View.OnClickListener {
    public static Activity activity;
    RelativeLayout rl_click;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);


        init();
        listener();

    }

    private void init() {
        rl_click = (RelativeLayout) findViewById(R.id.rl_click);

        progressBar = (ProgressBar)findViewById(R.id.progress);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(colors[7]);
        progressBar.setIndeterminateDrawable(mCircleDrawable);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);

        activity = DialogActivity.this;
        this.setFinishOnTouchOutside(false);
    }

    private void listener() {
        rl_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_click:
                break;

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
*/
