package org.sin.legaldemo.LawyerUserUI.LawyerAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Utils;

import java.util.List;

/**
 * Created by dola321 on 2016/4/17.
 */
public class LawyerTaskAdapter extends ArrayAdapter<Task> {
    private int lId;
    public LawyerTaskAdapter(Context context, int LId, List<Task> objects){
        super(context, LId, objects);
        lId = LId;
    }

    @Override
    public View getView(int position, View conventView, ViewGroup parent){
        Task temp = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (conventView == null) {
            view = LayoutInflater.from(getContext()).inflate(lId,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.tv_task_title);
            viewHolder.type = (TextView) view.findViewById(R.id.tv_task_type);
            viewHolder.content = (TextView) view.findViewById(R.id.tv_task_content);
            viewHolder.created = (TextView) view.findViewById(R.id.tv_task_created);
            view.setTag(viewHolder);
        }
        else {
            view = conventView;
            viewHolder =(ViewHolder) view.getTag();
        }
        viewHolder.title.setText(temp.getTitle());
        viewHolder.type.setText(temp.getEvent_type());
        viewHolder.content.setText(temp.getShort_content());
        viewHolder.created.setText(temp.getCreatedAt() + "发布");
        return view;
    }
}

class ViewHolder{
    TextView title;
    TextView type;
    TextView content;
    TextView created;

}
