package org.sin.legaldemo.LawyerUserUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerCheckAdapter_Rev;
import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class CheckFragment_Rev extends Fragment {

    private XRecyclerView mRecView;
    private LawyerCheckAdapter_Rev mAdapter;
    private BmobQuery<Task> mQuery;


    public static CheckFragment_Rev newInstance() {

        Bundle args = new Bundle();

        CheckFragment_Rev fragment = new CheckFragment_Rev();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.rev_layout, container, false);
        mQuery = new BmobQuery<Task>();

        mRecView = (XRecyclerView) mView.findViewById(R.id.revCheckList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecView.setLayoutManager(mLayoutManager);
        mRecView.setItemAnimator(new DefaultItemAnimator());
        mRecView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecView.setLoadingMoreEnabled(false);
        mRecView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initTask();
            }

            @Override
            public void onLoadMore() {

            }
        });

        initTask();

        return mView;
    }

    private void initTask() {
        mQuery.order("-createdAt");
        mQuery.include("task_publisher");
        mQuery.addWhereEqualTo("isBook", false);
        mQuery.addWhereEqualTo("event_type", UserBean.getCurrentUser(getContext(),UserBean.class).getAbility());
        mQuery.findObjects(getContext(), new FindListener<Task>() {
            @Override
            public void onSuccess(List<Task> list) {
                mAdapter = new LawyerCheckAdapter_Rev(getContext(), list);
                mRecView.setAdapter(mAdapter);  //获取成功后才设置adapter
                mRecView.refreshComplete();
            }

            @Override
            public void onError(int i, String s) {
                Utils.mToast(s);
                mRecView.refreshComplete();
            }
        });
    }

}
