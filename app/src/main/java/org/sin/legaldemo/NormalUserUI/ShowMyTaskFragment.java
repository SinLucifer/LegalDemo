package org.sin.legaldemo.NormalUserUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.NormalUserUI.UserAdapter.TaskViewAdapter;
import org.sin.legaldemo.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.maxwin.view.XListView;


public class ShowMyTaskFragment extends Fragment implements XListView.IXListViewListener {
    private View mView;
    private XListView mListView;
    private ListView listview;
    private BmobQuery<Task> mQuery;
    private TaskViewAdapter taskViewAdapter;
    private List<Task> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_show_my_task,container,false);
        listview = (ListView) mView.findViewById(R.id.xListView);
        mQuery = new BmobQuery<Task>();
        mQuery.addWhereEqualTo("task_publisher",UserBean.getCurrentUser(getContext(),UserBean.class));
        mQuery.order("-updatedAt");
        mQuery.include("task_publisher");
        mQuery.findObjects(getContext(), new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> list) {
                taskViewAdapter = new TaskViewAdapter(getContext(),list);
                listview.setAdapter(taskViewAdapter);
                mList = list;
            }

            @Override
            public void onError(int i, String s) {
                Log.d("Sin",s);
            }
        });


//        mListView = (XListView)mView.findViewById(R.id.xListView);
//        mListView.setPullLoadEnable(true);
//        mListView.setAdapter(taskViewAdapter);
//        mListView.setXListViewListener(this);

        return mView;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
