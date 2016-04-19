package org.sin.legaldemo.NormalUserUI.UserAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.R;

import java.util.List;

import cn.bmob.v3.listener.DeleteListener;


public class TaskViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Task> mList;
    private final OnClickListener itemButtonClickListener;

    public TaskViewAdapter(Context mContext, List<Task> mList,OnClickListener itemButtonClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.itemButtonClickListener = itemButtonClickListener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private final class MyViewHolder {     //item拥有的所有部件
        private TextView itemTitle;
        private TextView itemState;
        private TextView itemType;
        private TextView itemUsername;
        private TextView itemContent;
        private Button itemBnMore;
        private Button itemBnCancel;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = (Task) getItem(position);


        final MyViewHolder myViewHolder;
        if (convertView == null){
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            myViewHolder.itemTitle = (TextView)convertView.findViewById(R.id.list_item_title);
            myViewHolder.itemState = (TextView)convertView.findViewById(R.id.list_item_state);
            myViewHolder.itemType = (TextView)convertView.findViewById(R.id.list_item_type);
            myViewHolder.itemUsername = (TextView)convertView.findViewById(R.id.list_item_username);
            myViewHolder.itemContent = (TextView)convertView.findViewById(R.id.list_item_content);
            myViewHolder.itemBnMore = (Button)convertView.findViewById(R.id.list_item_card_more) ;
            myViewHolder.itemBnCancel = (Button)convertView.findViewById(R.id.list_item_card_cancel) ;
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.itemTitle.setText(task.getTitle());
        if (task.isBook()){
            myViewHolder.itemState.setText("已被抢单");
            myViewHolder.itemUsername.setText("抢单律师: " + task.getLawyer().getUsername());
        }else{
            myViewHolder.itemState.setText("等待抢单");
            myViewHolder.itemUsername.setText("无");
        }
        myViewHolder.itemType.setText(task.getEvent_type());
        myViewHolder.itemContent.setText(task.getShort_content());

        myViewHolder.itemBnMore.setOnClickListener(new MyTurnListener(myViewHolder.itemContent
                    ,myViewHolder.itemBnMore));
        if (itemButtonClickListener != null) {
            myViewHolder.itemBnCancel.setOnClickListener(itemButtonClickListener);
        }
        return convertView;
    }

    private class MyTurnListener implements View.OnClickListener{   //TODO 考虑下小于三行的不能点击的问题，还有动画第一次会卡顿

        public MyTurnListener(TextView textView,Button button) {
            this.textView = textView;
            this.button = button;
        }

        private Button button;
        private TextView textView;
        boolean isExpand;

        @Override
        public void onClick(View v) {
            textView.clearAnimation();
            isExpand=!isExpand;
            final int tempHight;
            final int startHight= textView.getHeight();
            int durationMillis = 200;
            if (isExpand){
                tempHight = textView.getLineHeight() * textView.getLineCount() - startHight;
                button.setText(R.string.bn_shrink);
            }else {
                tempHight = textView.getLineHeight() * 3 - startHight;//为负值，即短文减去长文的高度差
                button.setText(R.string.bn_expand);
            }

            Animation animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    textView.setHeight((int) (startHight + tempHight * interpolatedTime));
                }
            };

            animation.setDuration(durationMillis);
            textView.startAnimation(animation);
        }
    }
}
