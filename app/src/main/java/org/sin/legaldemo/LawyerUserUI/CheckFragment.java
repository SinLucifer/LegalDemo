package org.sin.legaldemo.LawyerUserUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerCheckAdapter;
import org.sin.legaldemo.R;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
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
        mQuery.order("-createdAt");
        mQuery.include("task_publisher");
        mQuery.addWhereEqualTo("isBook", false);
        mQuery.findObjects(getContext(), new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> list) {

                viewAdapter = new LawyerCheckAdapter(getContext(), list);
                mListView.setAdapter(viewAdapter);  //获取成功后才设置adapter
                onRefresh();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT).show();
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
                viewAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }

}
