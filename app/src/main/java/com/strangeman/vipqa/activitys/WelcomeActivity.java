package com.strangeman.vipqa.activitys;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.ImageView;

import com.strangeman.vipqa.R;

public class WelcomeActivity extends Activity {
    private ImageView welcome_img;
    private ShineTextView welcome_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.welcome);

        welcome_img = (ImageView)findViewById(R.id.welcome_img);
        welcome_txt = (ShineTextView)findViewById(R.id.welcome_txt);
        TextPaint tp = welcome_txt.getPaint();
        tp.setFakeBoldText(true);
        welcome_txt.setText(getString(R.string.keyword));
        welcome_img.setImageResource(R.drawable.vip);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(welcome_img, "scaleX", 0.5f, 1f),
                ObjectAnimator.ofFloat(welcome_img, "scaleY", 0.5f, 1f),
                ObjectAnimator.ofFloat(welcome_img, "alpha", 1f, 0.25f,1f),
                ObjectAnimator.ofFloat(welcome_txt, "scaleX", 0.5f, 1f),
                ObjectAnimator.ofFloat(welcome_txt, "scaleY", 0.5f, 1f)

        );


        set.setDuration(3 * 1000).start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            Intent intent = new Intent(WelcomeActivity.this, AllProductActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
