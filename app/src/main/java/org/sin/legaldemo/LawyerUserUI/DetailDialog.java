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
    private TextView tvUsername;
    private TextView tvEmail;
    private Button btPhone;
    private Button btWait;
    private Button btOk;

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
        tvUsername = (TextView) view.findViewById(R.id.dialog_username);
        tvEmail = (TextView) view.findViewById(R.id.dialog_email);

        btPhone=(Button) view.findViewById(R.id.dialog_phone);
        btOk = (Button) view.findViewById(R.id.dialog_ok);

        tvTitle.setText(task.getTitle());
        tvState.setText("抢单成功");
        tvUsername.setText("发布人：" + task.getTask_publisher().getFirstName() + task.getTask_publisher().getLastName());
        tvEmail.setText("邮箱：" + task.getTask_publisher().getEmail());

        btPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
