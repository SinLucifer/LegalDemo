package org.sin.legaldemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerTaskViewAdapter;
import org.sin.legaldemo.NormalUserUI.UserAdapter.TaskViewAdapter;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.MyApplication;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import me.maxwin.view.XListView;


public class ShowMyTaskFragment extends Fragment implements XListView.IXListViewListener {
    private View mView;
    private XListView mListView;
    private BmobQuery<Task> mQuery;
    private BaseAdapter viewAdapter;
    private android.os.Handler mHandler;

    private UserBean user;
    private Task task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_listview,container,false);
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

    private void initTask(){
        user = UserBean.getCurrentUser(MyApplication.getContext(),UserBean.class);
        if (!user.isLayer()){
            mQuery.addWhereEqualTo("task_publisher", user);
            mQuery.include("lawyer");
        }else {
            mQuery.addWhereEqualTo("lawyer", user);
            mQuery.addWhereEqualTo("isBook", true);
            mQuery.include("task_publisher");
        }
        mQuery.order("-updatedAt");

        mQuery.findObjects(getContext(), new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> list) {
                if (!user.isLayer()) {
                    viewAdapter = new TaskViewAdapter(getContext(), list,new ListItemButtonClickListener());
                }else{
                    viewAdapter = new LawyerTaskViewAdapter(getContext(), list,new ListItemButtonClickListener());
                }
                mListView.setAdapter(viewAdapter);  //获取成功后才设置adapter
            }

            @Override
            public void onError(int i, String s) {
                Log.d("Sin",s);
            }
        });
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("最近更新:" + new Date().toLocaleString());
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
                initTask();
                viewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            for (int i = mListView.getFirstVisiblePosition()
                 ; i <= mListView.getLastVisiblePosition(); i++) {

                task = (Task) mListView.getAdapter().getItem(i);
                if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition())
                        .findViewById(R.id.list_item_card_cancel)) {
                    CancelDialog cancelDialog = CancelDialog.newInstance(task.getObjectId());
                    cancelDialog.show(getActivity().getSupportFragmentManager(),"CancelDialog");
                    onRefresh();
                }
            }
        }
    }
}
