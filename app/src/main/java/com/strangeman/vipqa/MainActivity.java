package com.strangeman.vipqa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mainQAView_1;
    private TextView mainQAView_2;
    private TextView mainQAView_1Num;
    private TextView mainQAView_2Num;
    private Button showAllQustions;
    private MyRequest myRequest;
    private String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainQAView_1 = (TextView)findViewById(R.id.mainQA_1);
        mainQAView_2 = (TextView)findViewById(R.id.mainQA_2);
        mainQAView_1Num=(TextView)findViewById(R.id.mainQA_1_num);
        mainQAView_2Num=(TextView) findViewById(R.id.mainQA_2_num);
        myRequest=new MyRequest(this);
        productId="456";
        showAllQustions = (Button)findViewById(R.id.showAllQ);
        showAllQustions.setOnClickListener(this);
        initMainQA();
    }
    public void initMainQA(){
         myRequest.getPreView(new VolleyCallback<QuestionPreview>() {
             @Override
             public void onSuccess(QuestionPreview result) {
                 if(result!=null) {
                     mainQAView_1.setText(result.getQuestionOne());
                     mainQAView_2.setText(result.getQuestionTwo());
                     mainQAView_1Num.setText(String.valueOf(result.getAnswerOneNum()) + "" + MainActivity.this.getString(R.string.description));
                     mainQAView_2Num.setText(String.valueOf(result.getAnswerTwoNum()) + "" + MainActivity.this.getString(R.string.description));
                     showAllQustions.setText(MainActivity.this.getString(R.string.showAll) + " " + String.valueOf(result.getQuestionNum()));
                 }
                 else showAllQustions.setText(MainActivity.this.getString(R.string.askfirst));
             }

         },productId);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,AllQAActivity.class);
        startActivity(intent);
    }
}
