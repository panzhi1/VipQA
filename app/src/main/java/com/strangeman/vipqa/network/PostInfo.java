package com.strangeman.vipqa.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strangeman.vipqa.entity.Answer;
import com.strangeman.vipqa.entity.Question;
import com.strangeman.vipqa.entity.User;
import com.strangeman.vipqa.utils.GsonRequest;
import com.strangeman.vipqa.utils.QuestionMethod;
import com.strangeman.vipqa.utils.VolleyCallback;


import java.util.HashMap;
import java.util.Map;

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
    public void sendQuestion(final VolleyCallback<QuestionMethod> callback, Question question){
        String  questionJson=gson.toJson(question, Question.class);
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("question",questionJson);


        GsonRequest<QuestionMethod> gsonRequest = new GsonRequest<QuestionMethod>(Request.Method.POST,
                "http://123.206.115.152/pz/AskQuestionServlet",QuestionMethod.class ,hashMap,
                new Response.Listener<QuestionMethod>() {
                    @Override
                    public void onResponse(QuestionMethod response) {
                      callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "456", Toast.LENGTH_SHORT).show();
            }
        });

        mqueue.add(gsonRequest);

    }
    public void sendAnswer(final VolleyCallback<Question> callback,Answer answer){

        String  answerJson=gson.toJson(answer, Answer.class);
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("answer",answerJson);

        GsonRequest<Question> gsonRequest = new GsonRequest<Question>(Request.Method.POST,
                "http://123.206.115.152/pz/AnswerServlet",Question.class ,hashMap,
                new Response.Listener<Question>() {
                    @Override
                    public void onResponse(Question response) {
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
    public void LoginIn(final VolleyCallback<User> callback, String userId, String passwd){
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("userId",userId);
        hashMap.put("passwd",passwd);

        GsonRequest<User> gsonRequest = new GsonRequest<User>(Request.Method.POST,
                "http://123.206.115.152/pz/LoginInServlet",User.class ,hashMap,
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
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
    public void Adoption(final String questionId, final String answerId){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://123.206.115.152/pz/AdoptionServlet" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("questionId", questionId);
                map.put("answerId", answerId);
                return map;
            }
        };

        mqueue.add(stringRequest);
    }
}
