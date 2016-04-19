package org.sin.legaldemo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.Util.Utils;

import cn.bmob.v3.listener.DeleteListener;

public class CancelDialog extends DialogFragment {

    private String objectID;

    public static CancelDialog newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("ObjectID",id);
        CancelDialog fragment = new CancelDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null){
            objectID = getArguments().getString("ObjectID");
        }
        return new AlertDialog.Builder(getContext())
                .setTitle("请确认")
                .setMessage("您确定要取消订单么？")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //注意，context要设置成final,因为删除实现是异步刷新的，在获取到信息的时候，
                        // DialogFragment已经被销毁，故无法再次getActivity导致空指针异常
                        final Context mContext = getActivity();
                        final Task del = new Task();
                        del.setObjectId(objectID);
                        del.delete(getContext(), new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                Utils.mToast(mContext,"删除成功！");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Utils.mToast(mContext,"删除失败！"+s);
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.no,null)
                .create();
    }

}
