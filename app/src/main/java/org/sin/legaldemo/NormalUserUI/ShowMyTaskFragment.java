package org.sin.legaldemo.NormalUserUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.NormalUserUI.UserAdapter.TaskViewAdapter;
import org.sin.legaldemo.R;

import java.util.List;
import java.util.logging.Handler;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.maxwin.view.XListView;


public class ShowMyTaskFragment extends Fragment implements XListView.IXListViewListener {
    private View mView;
    private XListView mListView;
    private BmobQuery<Task> mQuery;
    private TaskViewAdapter taskViewAdapter;
    private android.os.Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_show_my_task,container,false);
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
        mQuery.addWhereEqualTo("task_publisher",UserBean.getCurrentUser(getContext(),UserBean.class));
        mQuery.order("-updatedAt");
        mQuery.include("task_publisher");
        mQuery.findObjects(getContext(), new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> list) {
                taskViewAdapter = new TaskViewAdapter(getContext(),list);
                mListView.setAdapter(taskViewAdapter);  //获取成功后才设置adapter
            }

            @Override
            public void onError(int i, String s) {
                Log.d("Sin",s);
            }
        });
    }

    private void onLoad() {    //TODO   想办法弄成具体时间
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
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
                taskViewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
}
