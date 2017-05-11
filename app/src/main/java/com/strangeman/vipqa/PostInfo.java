package com.strangeman.vipqa;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Created by panzhi on 2017/5/10.
 */

public class PostInfo {
    Context context=null;
    private RequestQueue mqueue;
    private Gson gson;
    private GsonBuilder builder;


    public PostInfo(Context context){
        this.context=context;
        mqueue = Volley.newRequestQueue(this.context);
        builder=new GsonBuilder();
        gson=builder.create();
    }
    public void sendQuestion(final VolleyCallback<String> callback,Question question){
        String  questionJson=gson.toJson(question, Question.class);
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("question",questionJson);


        GsonRequest<String> gsonRequest = new GsonRequest<String>(Request.Method.POST,
                "http://222.20.30.194:8080/VipQA/SecondServlet",String.class ,hashMap,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络出现问题", Toast.LENGTH_SHORT).show();
            }
        });

        mqueue.add(gsonRequest);

    }
    public void sendAnswer(final VolleyCallback<String> callback,Answer answer){

        String  answerJson=gson.toJson(answer, Answer.class);
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("answer",answerJson);

        GsonRequest<String> gsonRequest = new GsonRequest<String>(Request.Method.POST,
                "http://222.20.30.194:8080/VipQA/AnswerServlet",String.class ,hashMap,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    callback.onSuccess(response);

              }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络出现问题", Toast.LENGTH_SHORT).show();
            }
        });

        mqueue.add(gsonRequest);


    }
}
