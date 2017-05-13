package com.strangeman.vipqa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pilot on 2017/5/7.
 */

public class AllQAActivity extends AppCompatActivity implements View.OnClickListener{
    private List<Question> questionList ;
    private ImageButton back;
    private ImageButton refresh;
    private EditText inputQuestion;
    private Button send;
    private MyRequest myRequest;
    private String productId;
    private String userId;
    private PostInfo postInfo;
    private QuetionAdapter adapter;
    private Question question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allqa);

        back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(this);
        refresh = (ImageButton)findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        send=(Button)findViewById(R.id.send);
        send.setOnClickListener(this);

        inputQuestion=(EditText)findViewById(R.id.inputQuestion);
        productId="456";
        myRequest=new MyRequest(this);
        userId="158695";
        postInfo=new PostInfo(this);
        questionList = new ArrayList<Question>();
        adapter = new QuetionAdapter(AllQAActivity.this, R.layout.question_item, questionList);
        initQuestion();
        ListView listView = (ListView) findViewById(R.id.allQA);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = questionList.get(position);
                Intent intent = new Intent(AllQAActivity.this, ThisQAActivity.class);
                intent.putExtra("questionId",question.getQuestionId());
                startActivity(intent);
            }
        });
    }

    public void initQuestion(){
        myRequest.getAllQuestion(new VolleyCallback<QuestionMethod>() {
            @Override
            public void onSuccess(QuestionMethod result) {
                if(result.getQuestions()!=null) {
                    if (result.getQuestions() != null) {
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
            case R.id.back:
                Intent intent = new Intent(AllQAActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.refresh:
                onCreate(null);
                break;
            case R.id.send:

                if(inputQuestion.getText()!=null&&!inputQuestion.getText().toString().isEmpty()&&productId!=null&&userId!=null) {
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


}
