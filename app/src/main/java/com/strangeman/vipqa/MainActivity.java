package com.strangeman.vipqa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    TextView textView;
    Button button;
    MyRequest myRequest;
    Question question;
    PostInfo postInfo;
    private int Id;
    private String answer;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.text) ;
        button =(Button)findViewById(R.id.button) ;
        button.setOnClickListener(this);
        textView.setText("1");
        myRequest =new MyRequest(this);
        postInfo =new PostInfo(this);
        //question=new Question(003,"这个好用吗",null,158);
        Id=004;
        answer="非常好用";
        userId=159;
    }

    @Override
    public void onClick(View v) {
       // myRequest.getPreView();

      // postInfo.sendAnswer(Id,answer,userId);
        //postInfo.postInfo(question);
    }
}
