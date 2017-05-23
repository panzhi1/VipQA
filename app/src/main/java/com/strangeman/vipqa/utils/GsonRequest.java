package com.strangeman.vipqa.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panzhi on 2017/5/8.
 */

public class GsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;

    private Gson mGson;

    private Class<T> mClass;

    private Map<String,String > mparams;

    public GsonRequest(int method, String url,Class<T> clazz, Map<String,String>params, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url,errorListener);
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
        mparams=params;
    }

    public GsonRequest(String url, Class<T> clazz,Map<String,String>params, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url,clazz,params, listener, errorListener);
    }
    @Override
    protected Map<String, String> getParams()
            throws AuthFailureError {
        return mparams;
    }
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }

}