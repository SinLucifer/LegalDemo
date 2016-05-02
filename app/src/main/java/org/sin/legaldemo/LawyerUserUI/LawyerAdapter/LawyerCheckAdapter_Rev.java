package org.sin.legaldemo.LawyerUserUI.LawyerAdapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.LawyerUserUI.DetailDialog;
import org.sin.legaldemo.R;

import java.util.List;

public class LawyerCheckAdapter_Rev extends RecyclerView.Adapter<LawyerCheckAdapter_Rev.ViewHolder> {

    private Context mContext;
    private List<Task> mList;

    public LawyerCheckAdapter_Rev(Context mContext, List<Task> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public LawyerCheckAdapter_Rev.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rev_item,parent,false);
        ViewHolder myViewHolder = new ViewHolder(mView);

        return myViewHolder;
    }
    

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = mList.get(position);
        if (task.getTask_publisher() != null) {
            holder.itemTitle.setText(task.getTitle());
            holder.itemState.setText("订单");
            holder.itemType.setText(task.getEvent_type());
            holder.itemUsername.setText("发布人: " + task.getTask_publisher().getUsername());
            holder.itemContent.setText(task.getShort_content());
            holder.itemBnMore.setOnClickListener(new MyTurnListener(holder.itemContent
                    , holder.itemBnMore));
            holder.itemBnSubmit.setOnClickListener(new MyOrderListener(task,holder,position));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemTitle;
        public TextView itemState;
        public TextView itemType;
        public TextView itemUsername;
        public TextView itemContent;
        public Button itemBnMore;
        public Button itemBnSubmit;

        public ViewHolder(View view){
            super(view);
            itemTitle = (TextView) view.findViewById(R.id.list_item_title);
            itemState = (TextView) view.findViewById(R.id.list_item_state);
            itemType = (TextView) view.findViewById(R.id.list_item_type);
            itemUsername = (TextView) view.findViewById(R.id.list_item_username);
            itemContent = (TextView) view.findViewById(R.id.list_item_content);
            itemBnMore = (Button) view.findViewById(R.id.list_item_card_more);
            itemBnSubmit = (Button) view.findViewById(R.id.list_item_card_cancel);
            itemBnSubmit.setText("抢单");
        }
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

    private class MyOrderListener implements View.OnClickListener {

        private Task task;
        private ViewHolder holder;
        private int p;

        public MyOrderListener(Task task,ViewHolder holder,int p){
            this.task = task;
            this.holder = holder;
            this.p = p;
        }

        @Override
        public void onClick(View v) {
            DetailDialog detailDialog = DetailDialog.newInstance(task);
            detailDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "DetailDialog");

            mList.remove(p);   //notifyitemremoved需要的是显示位置，list需要的是位置-1
            notifyItemRemoved(holder.getAdapterPosition());
            notifyDataSetChanged();
        }
    }


}
