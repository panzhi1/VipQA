package com.strangeman.vipqa.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.strangeman.vipqa.R;
import com.strangeman.vipqa.entity.CheckLoginState;
import com.strangeman.vipqa.entity.User;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by panzhi on 2017/5/22.
 */

public class BaseActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private View headview;
    private NavigationView navView;
    private Button button;
    private TextView textView;
    private CircleImageView circleImageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        headview = navView.inflateHeaderView(R.layout.nav_header);
        button = (Button) headview.findViewById(R.id.login);
        textView = (TextView) headview.findViewById(R.id.identity);
        circleImageView = (CircleImageView) headview.findViewById(R.id.icon_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity.this, "123", Toast.LENGTH_SHORT).show();
                if (CheckLoginState.getUser() == null) {
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    CheckLoginState.setUser(null);
                    // headview = navView.inflateHeaderView(R.layout.nav_header);
                    //button = (Button) headview.findViewById(R.id.login);
                    //textView = (TextView) headview.findViewById(R.id.identity);

                    button.setText(getString(R.string.login));
                    textView.setText(getString(R.string.visitorIdentity));
                    circleImageView.setImageResource(R.drawable.vipuser);
                }
            }
        });
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()){
                case R.id.user:
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    break;
            }
            return true;
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.toolbar, menu);
            return true;
        }
        @Override
        protected void onResume() {
            super.onResume();
            User user = CheckLoginState.getUser();
            if(user!=null){
                button.setText(getString(R.string.logout));
                textView.setText(user.getUserName());
                Glide.with(this).load(user.getUserPhoto()).into(circleImageView);


            }
        }
    }

