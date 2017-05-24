package com.strangeman.vipqa.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.strangeman.vipqa.R;
import com.strangeman.vipqa.entity.Question;

import java.util.List;

/**
 * Created by pilot on 2017/5/8.
 */

public class QuetionAdapter extends ArrayAdapter<Question> {
    private int resourceId;
    private Context context;
    public QuetionAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question thisQuestion = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.firstLine = (ImageView) view.findViewById(R.id.firstLine);
            viewHolder.ask = (ImageView) view.findViewById(R.id.ask);
            viewHolder.quetion = (TextView) view.findViewById(R.id.question);
            TextPaint textPaint = viewHolder.quetion.getPaint();
            textPaint.setFakeBoldText(true);
            viewHolder.reply = (ImageView) view.findViewById(R.id.reply);
            viewHolder.answer = (TextView) view.findViewById(R.id.answer);
            viewHolder.showAllA = (TextView) view.findViewById(R.id.showAllA);
            viewHolder.askDate = (TextView) view.findViewById(R.id.askDate);
            viewHolder.lastLine = (ImageView) view.findViewById(R.id.lastLine);
            viewHolder.accept= (TextView) view.findViewById(R.id.accept);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.quetion.setText(thisQuestion.getContent());
        if((thisQuestion.getAnswerList()!=null)&&(thisQuestion.getAnswerList().size()!=0)){
            if(thisQuestion.getBestAnswer()!=null&&thisQuestion.getBestAnswer()!=""){
                viewHolder.accept.setText("已采纳");
            }
            viewHolder.answer.setText(thisQuestion.getAnswerList().get(0).getContent());
            viewHolder.showAllA.setText(context.getResources().getString(R.string.query)+thisQuestion.getAnswerList().size()+context.getResources().getString(R.string.description));
        }
        else {
            viewHolder.answer.setText("");
            viewHolder.accept.setText("");
            viewHolder.showAllA.setText(context.getResources().getString(R.string.answerfirst));
        }

        viewHolder.askDate.setText(thisQuestion.getAskDate());
        return view;
    }

    class ViewHolder{
        ImageView firstLine;
        ImageView ask;
        TextView quetion;
        ImageView reply;
        TextView answer;
        TextView showAllA;
        TextView askDate;
        ImageView lastLine;
        TextView accept;
    }
}
