/**
 * 弃用，用RecyclerView取代
 */

//package org.sin.legaldemo.LawyerUserUI;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import org.sin.legaldemo.JavaBean.Task;
//import org.sin.legaldemo.JavaBean.UserBean;
//import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerCheckAdapter;
//import org.sin.legaldemo.R;
//import org.sin.legaldemo.Util.MyApplication;
//import org.sin.legaldemo.Util.Utils;
//
//import java.util.Date;
//import java.util.List;
//
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.listener.FindListener;
//import me.maxwin.view.XListView;
//
///**
// *
// */
//public class CheckFragment extends Fragment implements XListView.IXListViewListener {
//    private XListView mListView;
//    private BmobQuery<Task> mQuery;
//    private BaseAdapter viewAdapter;
//    private android.os.Handler mHandler;
//
//    public static CheckFragment newInstance() {
//
//        Bundle args = new Bundle();
//
//        CheckFragment fragment = new CheckFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View mView = inflater.inflate(R.layout.fragment_listview, container, false);
//        mListView = (XListView) mView.findViewById(R.id.xListView);
//        mListView.getOnItemClickListener();
//
//        mQuery = new BmobQuery<Task>();
//
//        initTask();
//
//        mListView.setPullLoadEnable(true);    //xlist要设置的到mhandler
//        mListView.setPullRefreshEnable(true);
//        mListView.setXListViewListener(this);
//        mHandler = new android.os.Handler();
//
//        return mView;
//    }
//
//    private void initTask() {
//        mQuery.order("-createdAt");
//        mQuery.include("task_publisher");
//        mQuery.addWhereEqualTo("isBook", false);
//        mQuery.addWhereEqualTo("event_type", UserBean.getCurrentUser(getContext(),UserBean.class).getAbility());
//        mQuery.findObjects(getContext(), new FindListener<Task>() {
//            @Override
//            public void onSuccess(List<Task> list) {
//
//                viewAdapter = new LawyerCheckAdapter(getContext(), list, new MyOrderListener());
//                mListView.setAdapter(viewAdapter);  //获取成功后才设置adapter
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//                Utils.mToast(s);
//
//            }
//        });
//    }
//
//    private void onLoad() {
//        mListView.stopRefresh();
//        mListView.stopLoadMore();
//        mListView.setRefreshTime("最近更新: " + new Date().toLocaleString());
//    }
//
//    @Override
//    public void onRefresh() {   //下拉刷新
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initTask();
//                onLoad();
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onLoadMore() {   //上拉刷新
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                viewAdapter.notifyDataSetChanged();
//                onLoad();
//            }
//        }, 2000);
//    }
//
//    private class MyOrderListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            for (int i = mListView.getFirstVisiblePosition()
//                 ; i <= mListView.getLastVisiblePosition(); i++) {
//                Task task = (Task) mListView.getAdapter().getItem(i);
//
//                if (v == mListView.getChildAt(i - mListView.getFirstVisiblePosition())
//                        .findViewById(R.id.list_item_card_cancel)) {
//
//                    if (task.isBook()) {
//                        AlertDialog.Builder tooLate = new AlertDialog.Builder(MyApplication.getContext());
//                        tooLate.setMessage("该单以被抢");
//                        tooLate.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        tooLate.create().show();
//                    } else {
//                        DetailDialog detailDialog = DetailDialog.newInstance(task);
//                        detailDialog.show(getActivity().getSupportFragmentManager(), "DetailDialog");
//                        onRefresh();
//                    }
//                }
//            }
//        }
//    }
//
//}
