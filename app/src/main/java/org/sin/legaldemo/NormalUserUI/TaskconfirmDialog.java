package org.sin.legaldemo.NormalUserUI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Content;
import org.sin.legaldemo.Util.Utils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Sin on 2016/4/20.
 */
public class TaskConfirmDialog extends DialogFragment {

    public static TaskConfirmDialog newInstance(Task task) {

        Bundle args = new Bundle();

        args.putSerializable("Task",task);

        TaskConfirmDialog fragment = new TaskConfirmDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Task task = (Task)getArguments().getSerializable("Task");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("请确认")
                .setMessage("您确定要发布订单么？")
                .setNegativeButton(R.string.no,null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.save(getContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Utils.mToast("任务发布成功~请等待律师抢单~");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Utils.mToast("发布失败！请稍后重试！"+s);
                            }
                        });
                    }
                });

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
