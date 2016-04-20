package org.sin.legaldemo.LawyerUserUI.LawyerAdapter;

import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.LawyerUserUI.DetailDialog;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Utils;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 *
 */
public class LawyerCheckAdapter extends BaseAdapter {

    private Context mContext;
    private List<Task> mList;
    private final View.OnClickListener itemButtonClickListener;

    public LawyerCheckAdapter(Context mContext, List<Task> mList, View.OnClickListener itemButtonClickListener) {
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
        private Button itemBnCancel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = (Task) getItem(position);


        final MyViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            myViewHolder.itemTitle = (TextView) convertView.findViewById(R.id.list_item_title);
            myViewHolder.itemState = (TextView) convertView.findViewById(R.id.list_item_state);
            myViewHolder.itemType = (TextView) convertView.findViewById(R.id.list_item_type);
            myViewHolder.itemUsername = (TextView) convertView.findViewById(R.id.list_item_username);
            myViewHolder.itemContent = (TextView) convertView.findViewById(R.id.list_item_content);
            myViewHolder.itemBnMore = (Button) convertView.findViewById(R.id.list_item_card_more);
            myViewHolder.itemBnCancel = (Button) convertView.findViewById(R.id.list_item_card_cancel);
            myViewHolder.itemBnCancel.setText("抢单");
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        if (task.getTask_publisher() != null) {

            myViewHolder.itemTitle.setText(task.getTitle());
            myViewHolder.itemState.setText("订单");
            myViewHolder.itemType.setText(task.getEvent_type());
            myViewHolder.itemUsername.setText("发布人: " + task.getTask_publisher().getUsername());
            myViewHolder.itemContent.setText(task.getShort_content());

        }

        myViewHolder.itemBnMore.setOnClickListener(new MyTurnListener(myViewHolder.itemContent
                , myViewHolder.itemBnMore));
        if (itemButtonClickListener != null) {
            myViewHolder.itemBnCancel.setOnClickListener(itemButtonClickListener);
        }
        return convertView;
    }

    private class MyTurnListener implements View.OnClickListener {   //TODO 考虑下小于三行的不能点击的问题，还有动画第一次会卡顿

        public MyTurnListener(TextView textView, Button button) {
            this.textView = textView;
            this.button = button;
        }

        private Button button;
        private TextView textView;
        boolean isExpand;

        @Override
        public void onClick(View v) {
            textView.clearAnimation();
            isExpand = !isExpand;
            final int tempHight;
            final int startHight = textView.getHeight();
            int durationMillis = 200;
            if (isExpand) {
                tempHight = textView.getLineHeight() * textView.getLineCount() - startHight;
                button.setText(R.string.bn_shrink);
            } else {
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
