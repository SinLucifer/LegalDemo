package org.sin.legaldemo.LawyerUserUI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import junit.framework.Test;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.R;
import org.w3c.dom.Text;


/**
 *
 */
public class DetailDialog extends DialogFragment {

    private Task task;

    private TextView tvTitle;
    private TextView tvState;
    private TextView tvType;
    private TextView tvUsername;
    private TextView tvContent;
    private Button tvOk;

    public static DetailDialog newInstance(Task task) {
        Bundle args = new Bundle();
        args.putSerializable("task", task);
        DetailDialog fragment = new DetailDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (getArguments() != null){
            task = (Task) getArguments().getSerializable("task");
        }
        View view = inflater.inflate(R.layout.dialog_detail, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        tvState = (TextView) view.findViewById(R.id.dialog_state);
        tvType = (TextView) view.findViewById(R.id.dialog_type);
        tvUsername = (TextView) view.findViewById(R.id.dialog_username);
        tvContent = (TextView) view.findViewById(R.id.dialog_content);
        tvOk = (Button) view.findViewById(R.id.dialog_ok);

        tvTitle.setText(task.getTitle());
        tvState.setText("抢单成功");
        tvType.setText(task.getEvent_type());
        tvUsername.setText("发布人：" + task.getTask_publisher().getUsername());
        tvContent.setText(task.getShort_content());
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
