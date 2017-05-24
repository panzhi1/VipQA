package com.strangeman.vipqa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.strangeman.vipqa.R;
import com.strangeman.vipqa.entity.Answer;

import java.util.List;

/**
 * Created by pilot on 2017/5/9.
 */

public class AnswerAdapter extends ArrayAdapter<Answer> {
    private int resourceId;
    private String bestAnswer;

    public AnswerAdapter(Context context, int resource, List<Answer> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    public AnswerAdapter(Context context,int resource,
                         List<Answer> objects,String bestAnswer){
        super(context,resource,objects);
        resourceId=resource;
        this.bestAnswer=bestAnswer;
    }

    public void setBestAnswer(String bestAnswer){
        this.bestAnswer=bestAnswer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Answer thisAnswer = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.firstLine = (ImageView) view.findViewById(R.id.answer_firstLine);
            viewHolder.userId= (TextView) view.findViewById(R.id.userId);
            viewHolder.answerDate = (TextView) view.findViewById(R.id.answerDate);
            viewHolder.content= (TextView) view.findViewById(R.id.content);
            viewHolder.lastLine = (ImageView) view.findViewById(R.id.answer_lastLine);
            viewHolder.bestAnswer= (TextView) view.findViewById(R.id.bestAnswer);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(thisAnswer!=null){
            if(thisAnswer.getAnswerId().equals(bestAnswer)){
                viewHolder.bestAnswer.setText("最佳答案");
            }
            viewHolder.userId.setText(thisAnswer.getUserId());
            viewHolder.answerDate.setText(thisAnswer.getAnswerDate());
            viewHolder.content.setText(thisAnswer.getContent());
        }
        return view;
    }

    class ViewHolder{
        ImageView firstLine;
        TextView userId;
        TextView answerDate;
        TextView content;
        ImageView lastLine;
        TextView bestAnswer;
    }
}
