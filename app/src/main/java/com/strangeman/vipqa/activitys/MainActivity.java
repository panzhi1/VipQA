package com.strangeman.vipqa.activitys;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.strangeman.vipqa.R;
import com.strangeman.vipqa.entity.QuestionPreview;
import com.strangeman.vipqa.network.MyRequest;
import com.strangeman.vipqa.utils.VolleyCallback;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private TextView mainQAView_1;
    private TextView mainQAView_2;
    private TextView mainQAView_1Num;
    private TextView mainQAView_2Num;
    private TextView productName;
    private TextView productPrice;
    private ImageView productImage;
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
        productName=(TextView)findViewById(R.id.goodsNote);
        productPrice=(TextView)findViewById(R.id.goodsPrice);
        productImage=(ImageView)findViewById(R.id.goods) ;
        myRequest=new MyRequest(this);
        Intent intent=getIntent();
        productId=intent.getStringExtra("productId");
        productName.setText(intent.getStringExtra("productName"));
        productPrice.setText(String.valueOf(intent.getFloatExtra("productPrice",0)));
        Glide.with(this).load(intent.getStringExtra("imagePath")).into(productImage);
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
        intent.putExtra("productName",productName.getText());
        intent.putExtra("productId",productId);
        startActivity(intent);
    }
}
