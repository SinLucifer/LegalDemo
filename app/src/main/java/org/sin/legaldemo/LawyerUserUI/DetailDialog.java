package org.sin.legaldemo.LawyerUserUI;

import android.content.Intent;
import android.net.Uri;
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
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Utils;
import org.w3c.dom.Text;

import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (getArguments() != null){
            task = (Task) getArguments().getSerializable("task");

            UserBean lawyer = BmobUser.getCurrentUser(getContext(),UserBean.class);

            BmobACL acl = new BmobACL();

            acl.setWriteAccess(lawyer,true);
            acl.setReadAccess(lawyer,true);
            acl.setWriteAccess(task.getTask_publisher(),true);
            acl.setReadAccess(task.getTask_publisher(),true);

            task.setACL(acl);
            task.setBook(true);
            task.setLawyer(BmobUser.getCurrentUser(getContext(), UserBean.class));
            task.update(getContext() , new UpdateListener() {
                @Override
                public void onSuccess() {

                    Utils.mToast("抢单成功~！");
                }

                @Override
                public void onFailure(int i, String s) {
                    Utils.mToast("抢单失败，请稍后重试，该订单可能已被其他律师抢到了！");
                }
            });


        }
        View view = inflater.inflate(R.layout.dialog_detail, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        tvUsername = (TextView) view.findViewById(R.id.dialog_username);
        tvEmail = (TextView) view.findViewById(R.id.dialog_email);

        btPhone=(Button) view.findViewById(R.id.dialog_phone);
        btOk = (Button) view.findViewById(R.id.dialog_ok);



        tvTitle.setText(task.getTitle());
        if(task.getTask_publisher().getSex() == true) {
            tvUsername.setText("发布人：" + task.getTask_publisher().getFirstName() + "先生");
        }else{
            tvUsername.setText("发布人：" + task.getTask_publisher().getFirstName() + "女士");
        }
        tvEmail.setText("邮箱：" + task.getTask_publisher().getEmail());

        btPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + task.getTask_publisher().getMobilePhoneNumber()));
                startActivity(intent);
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
