package com.strangeman.vipqa;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panzhi on 2017/5/7.
 */

public class MyRequest {

    Context context = null;

    RequestQueue mqueue;

    ArrayList<Question>questionsList=new ArrayList<>();


    public MyRequest(Context context) {
        this.context = context;
        this.mqueue = Volley.newRequestQueue(this.context);

    }

    public void getPreView( final VolleyCallback<QuestionPreview> callback,String productId) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productId",productId);
        GsonRequest<QuestionPreview> gsonRequest = new GsonRequest<QuestionPreview>(com.android.volley.Request.Method.POST,
                "http://222.20.30.194:8080/VipQA/PreviewServlet",QuestionPreview.class ,hashMap,
                new Response.Listener<QuestionPreview>() {
                    @Override
                    public void onResponse(QuestionPreview response) {

                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络出现问题", Toast.LENGTH_SHORT).show();

            }
        });

        this.mqueue.add(gsonRequest);
    }
    public void getAllQuestion( final VolleyCallback<QuestionMethod> callback,String productId) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productId",productId);
        GsonRequest<QuestionMethod> gsonRequest = new GsonRequest<QuestionMethod>(Request.Method.POST,
                "http://222.20.30.194:8080/VipQA/AllQuestionServlet",QuestionMethod.class ,hashMap,
                new Response.Listener<QuestionMethod>() {
                    @Override
                    public void onResponse(QuestionMethod response) {

                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "网络出现问题", Toast.LENGTH_SHORT).show();

            }
        });

        this.mqueue.add(gsonRequest);
    }
    public void getQuestionInfo( final VolleyCallback<Question> callback,String productId,String questionId) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("productId",productId);
        hashMap.put("questionId",questionId);
        GsonRequest<Question> gsonRequest = new GsonRequest<Question>(Request.Method.POST,
                "http://222.20.30.194:8080/VipQA/FristServlet",Question.class ,hashMap,
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

        this.mqueue.add(gsonRequest);
    }

}


