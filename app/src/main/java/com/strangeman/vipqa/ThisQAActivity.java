package com.strangeman.vipqa;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pilot on 2017/5/8.
 */

public class ThisQAActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Answer> answerList ;
    private AnswerAdapter adapter;
    private ImageButton back;
    private ImageButton refresh;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thisqa);
        this_question=(TextView)findViewById(R.id.this_question);
        this_answer=(TextView)findViewById(R.id.this_answer);
        inputAnswer=(EditText)findViewById(R.id.inputAnswer);
        this_send=(Button)findViewById(R.id.this_send);
        this_send.setOnClickListener(this);
        answerList=new ArrayList<>();
        back = (ImageButton)findViewById(R.id.this_back);
        back.setOnClickListener(this);
        refresh = (ImageButton)findViewById(R.id.this_refresh);
        refresh.setOnClickListener(this);
        userId="158695";
        adapter = new AnswerAdapter(ThisQAActivity.this, R.layout.answer_item, answerList);
        ListView listView = (ListView) findViewById(R.id.AllA);
        listView.setAdapter(adapter);
        myRequest=new MyRequest(this);
        postInfo=new PostInfo(this);
        intent=getIntent();
        if(intent!=null)
        questionId=intent.getStringExtra("questionId");
        initQuestionInfo();
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
            case R.id.this_back:
                Intent intent = new Intent(ThisQAActivity.this,AllQAActivity.class);
                startActivity(intent);
                break;
            case R.id.this_refresh:
                break;
            case R.id.this_send:

                if(inputAnswer.getText()!=null&&!inputAnswer.getText().toString().isEmpty()&&questionId!=null&&userId!=null) {
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


}
