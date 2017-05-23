package com.strangeman.vipqa.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.strangeman.vipqa.R;
import com.strangeman.vipqa.adapter.QuetionAdapter;
import com.strangeman.vipqa.entity.CheckLoginState;
import com.strangeman.vipqa.entity.Question;
import com.strangeman.vipqa.entity.User;
import com.strangeman.vipqa.network.MyRequest;
import com.strangeman.vipqa.network.PostInfo;
import com.strangeman.vipqa.utils.QuestionMethod;
import com.strangeman.vipqa.utils.VolleyCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pilot on 2017/5/7.
 */

public class AllQAActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Question> questionList ;
    private ImageButton back;
    private EditText inputQuestion;
    private Button send;
    private MyRequest myRequest;
    private String productId;
    private String userId;
    private PostInfo postInfo;
    private QuetionAdapter adapter;
    private Question question;
    private SwipeRefreshLayout swipeRefresh;
    private DrawerLayout mDrawerLayout;
    private Intent intent;
    private View headview;
    private NavigationView navView;
    private Button button;
    private TextView textView;
    private CircleImageView circleImageView;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allqa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout1);
        navView = (NavigationView) findViewById(R.id.nav_view_1);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        intent = getIntent();
        productId = intent.getStringExtra("productId");
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        inputQuestion = (EditText) findViewById(R.id.inputQuestion);
        myRequest = new MyRequest(this);
        postInfo = new PostInfo(this);
        questionList = new ArrayList<Question>();
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

        });
        adapter = new QuetionAdapter(AllQAActivity.this, R.layout.question_item, questionList);
        initQuestion();
        ListView listView = (ListView) findViewById(R.id.allQA);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = questionList.get(position);
                Intent intent = new Intent(AllQAActivity.this, ThisQAActivity.class);
                intent.putExtra("questionId", question.getQuestionId());
                startActivity(intent);
            }
        });
        headview = navView.inflateHeaderView(R.layout.nav_header);
        button = (Button) headview.findViewById(R.id.login);
        textView = (TextView) headview.findViewById(R.id.identity);
        circleImageView = (CircleImageView) headview.findViewById(R.id.icon_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckLoginState.getUser() == null) {
                    Intent intent = new Intent(AllQAActivity.this, LoginActivity.class);
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
        user = CheckLoginState.getUser();
        if (user != null) {
            button.setText(getString(R.string.logout));
            textView.setText(user.getUserName());
            Glide.with(this).load(user.getUserPhoto()).into(circleImageView);
        }
    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myRequest.getAllQuestion(new VolleyCallback<QuestionMethod>() {
                            @Override
                            public void onSuccess(QuestionMethod result) {
                                if(result.getQuestions()!=null) {
                                    questionList.clear();
                                    if (result.getQuestions() != null) {
                                        for (int i = 0; i < result.getQuestions().size(); i++) {
                                            questionList.add(result.getQuestions().get(i));
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            }
                        },productId);
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    public void initQuestion(){
        myRequest.getAllQuestion(new VolleyCallback<QuestionMethod>() {
            @Override
            public void onSuccess(QuestionMethod result) {
                if(result!=null) {
                    if (result.getQuestions() != null) {
                        questionList.clear();
                        for (int i = 0; i < result.getQuestions().size(); i++) {
                            questionList.add(result.getQuestions().get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        },productId);
//
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
            if(CheckLoginState.getUser()==null){
                Toast.makeText(AllQAActivity.this,"请先登陆",Toast.LENGTH_LONG).show();
                return;
            }
                userId= CheckLoginState.getUser().getUserId();
                if(inputQuestion.getText()!=null&&(inputQuestion.getText().toString().length()>1)&&productId!=null&&userId!=null) {
                  question = new Question(null, inputQuestion.getText().toString(), productId, userId,null);
                    postInfo.sendQuestion(new VolleyCallback<QuestionMethod>() {
                        @Override
                        public void onSuccess(QuestionMethod result) {
                            Toast.makeText(AllQAActivity.this,result.getQuestions().size()+"",Toast.LENGTH_LONG).show();
                            if(result.getQuestions()!=null){
                                questionList.clear();
                                for(int i=0;i<result.getQuestions().size();i++) {
                                    questionList.add(result.getQuestions().get(i));

                                }
                                adapter.notifyDataSetChanged();
                                inputQuestion.setText("");
                                inputQuestion.setCursorVisible(false);

                            }


                        }
                    }, question);
                }
                else Toast.makeText(this,AllQAActivity.this.getString(R.string.newQuestion),Toast.LENGTH_LONG).show();
                break;


        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(AllQAActivity.this, com.strangeman.vipqa.activitys.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.user:
                mDrawerLayout.openDrawer(GravityCompat.START);

                break;
        }
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
         user = CheckLoginState.getUser();
        if(user!=null){
            button.setText(getString(R.string.logout));
            textView.setText(user.getUserName());
            Glide.with(this).load(user.getUserPhoto()).into(circleImageView);


        }
    }
    }


