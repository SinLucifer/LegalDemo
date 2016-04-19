package org.sin.legaldemo.LawyerUserUI;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.sin.legaldemo.CancelDialog;
import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerCheckAdapter;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.MyApplication;
import org.sin.legaldemo.Util.Utils;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import me.maxwin.view.XListView;

/**
 *
 */
public class CheckFragment extends Fragment implements XListView.IXListViewListener {
    private View mView;
    private XListView mListView;
    private BmobQuery<Task> mQuery;
    private BaseAdapter viewAdapter;
    private android.os.Handler mHandler;

    private Task task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_listview, container, false);
        mListView = (XListView) mView.findViewById(R.id.xListView);
        mListView.getOnItemClickListener();

        mQuery = new BmobQuery<Task>();

        initTask();

        mListView.setPullLoadEnable(true);    //xlist要设置的到mhandler
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this);
        mHandler = new android.os.Handler();

        return mView;
    }

    private void initTask() {
        mQuery.order("-createdAt");
        mQuery.include("task_publisher");
        mQuery.addWhereEqualTo("isBook", false);
        mQuery.findObjects(getContext(), new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> list) {

                viewAdapter = new LawyerCheckAdapter(getContext(), list, new MyOrderListener());
                mListView.setAdapter(viewAdapter);  //获取成功后才设置adapter
            }

            @Override
            public void onError(int i, String s) {

                Utils.mToast(s);

            }
        });
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("最近更新: " + new Date().toLocaleString());
    }

    @Override
    public void onRefresh() {   //下拉刷新
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initTask();
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {   //上拉刷新
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    private class MyOrderListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = mListView.getFirstVisiblePosition()
                 ; i <= mListView.getLastVisiblePosition(); i++) {
                task = (Task) mListView.getAdapter().getItem(i);

                if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition())
                        .findViewById(R.id.list_item_card_cancel)) {

                    if (task.isBook()) {
                        AlertDialog.Builder tooLate = new AlertDialog.Builder(MyApplication.getContext());
                        tooLate.setMessage("该单以被抢");
                        tooLate.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        tooLate.create().show();
                    } else {
                        final Context mContext = getActivity();
                        final Task temp = task;
                        temp.setBook(true);
                        String objectID = temp.getObjectId();
                        temp.setLawyer(BmobUser.getCurrentUser(getContext(), UserBean.class));
                        temp.update(mContext, objectID , new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                DetailDialog detailDialog = DetailDialog.newInstance(temp);
                                detailDialog.show(getActivity().getSupportFragmentManager(), "DetailDialog");
                                onRefresh();

                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Utils.mToast("抢单失败，请稍后重试" + s);
                                onRefresh();
                            }
                        });
                    }

                }
            }


        }
    }

}
