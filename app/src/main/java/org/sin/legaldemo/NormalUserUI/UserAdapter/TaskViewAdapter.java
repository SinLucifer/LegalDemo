package org.sin.legaldemo.NormalUserUI.UserAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.R;

import java.util.List;


public class TaskViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Task> mList;

    public TaskViewAdapter(Context mContext, List<Task> mList) {
        this.mContext = mContext;
        this.mList = mList;
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

    private final class MyViewHolder {TextView tv_item;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = (Task) getItem(position);
        if (task.getTask_publisher() != null){
            Log.d("Sin",task.getTask_publisher().getNick());
        }
        MyViewHolder myViewHolder;
        if (convertView == null){
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            myViewHolder.tv_item = (TextView)convertView.findViewById(R.id.list_item_textview);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.tv_item.setText(task.getTitle());
        return convertView;
    }
}
