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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.strangeman.vipqa.R;
import com.strangeman.vipqa.adapter.AnswerAdapter;
import com.strangeman.vipqa.entity.Answer;
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
 * Created by pilot on 2017/5/8.
 */

public class ThisQAActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Answer> answerList ;
    private AnswerAdapter adapter;
    private ImageButton back;
    private MyRequest myRequest;
    private PostInfo postInfo;
    private String questionId;
    private Intent intent;
    private TextView this_question;
    private TextView this_answer;
    private EditText inputAnswer;
    private Button this_send;
    private String userId;
    private Answer answer;
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;
    private View headview;
    private NavigationView navView;
    private Button button;
    private TextView textView;
    private CircleImageView circleImageView;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thisqa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout2);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        this_question=(TextView)findViewById(R.id.this_question);
        this_answer=(TextView)findViewById(R.id.this_answer);
        inputAnswer=(EditText)findViewById(R.id.inputAnswer);
        this_send=(Button)findViewById(R.id.this_send);
        this_send.setOnClickListener(this);
        answerList=new ArrayList<>();
        adapter = new AnswerAdapter(ThisQAActivity.this, R.layout.answer_item, answerList);
        ListView listView = (ListView) findViewById(R.id.AllA);
        listView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }


        });
        navView = (NavigationView) findViewById(R.id.nav_view);
        headview = navView.inflateHeaderView(R.layout.nav_header);
        button = (Button) headview.findViewById(R.id.login);
        textView = (TextView) headview.findViewById(R.id.identity);
        circleImageView = (CircleImageView) headview.findViewById(R.id.icon_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckLoginState.getUser() == null) {
                    Intent intent = new Intent(ThisQAActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    CheckLoginState.setUser(null);
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
        myRequest=new MyRequest(this);
        postInfo=new PostInfo(this);
        intent=getIntent();
        if(intent!=null)
        questionId=intent.getStringExtra("questionId");
        initQuestionInfo();
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
                            myRequest.getQuestionInfo(new VolleyCallback<Question>() {
                                @Override
                                public void onSuccess(Question result) {
                                    if (result != null) {
                                        if (result.getAnswerList() != null) {
                                            answerList.clear();
                                            for (int i = 0; i < result.getAnswerList().size(); i++) {
                                                answerList.add(result.getAnswerList().get(i));
                                                adapter.notifyDataSetChanged();
                                            }
                                            this_answer.setText(result.getAnswerList().size() + ThisQAActivity.this.getString(R.string.description));
                                        } else
                                            this_answer.setText("0" + ThisQAActivity.this.getString(R.string.description));
                                        this_question.setText(result.getContent());
                                    }
                                }
                            }, questionId);
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                }
            }).start();
    }



    private void initQuestionInfo() {
        myRequest.getQuestionInfo(new VolleyCallback<Question>() {
            @Override
            public void onSuccess(Question result) {
                if(result!=null){
                    if(result.getAnswerList()!=null) {
                        this_answer.setText(result.getAnswerList().size() + ThisQAActivity.this.getString(R.string.description));
                        for (int i = 0; i < result.getAnswerList().size(); i++) {
                            answerList.add(result.getAnswerList().get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else this_answer.setText("0"+ThisQAActivity.this.getString(R.string.description));
                    this_question.setText(result.getContent());
                }
            }
        },questionId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.this_send:
                if(CheckLoginState.getUser()==null) {
                    Toast.makeText(ThisQAActivity.this, "请先登陆", Toast.LENGTH_LONG).show();
                    return;
                }
                userId=CheckLoginState.getUser().getUserId();
                if(inputAnswer.getText()!=null&&inputAnswer.getText().toString()!=null&&questionId!=null&&userId!=null) {
                    answer = new Answer(questionId, null, userId, inputAnswer.getText().toString(),null);
                    postInfo.sendAnswer(new VolleyCallback<Question>() {
                        @Override
                        public void onSuccess(Question result) {
                            if(result!=null){
                                if(result.getAnswerList()!=null) {
                                    answerList.clear();
                                    for (int i = 0; i < result.getAnswerList().size(); i++) {
                                        answerList.add(result.getAnswerList().get(i));

                                    }
                                    adapter.notifyDataSetChanged();
                                    inputAnswer.setText("");
                                    inputAnswer.setCursorVisible(false);
                                }
                            }


                        }
                    }, answer);
                }
                else Toast.makeText(this,ThisQAActivity.this.getString(R.string.noneanswer),Toast.LENGTH_LONG).show();
                break;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.user:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case android.R.id.home:
                Intent intent = new Intent(ThisQAActivity.this,AllQAActivity.class);
                startActivity(intent);
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
