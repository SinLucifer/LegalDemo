package org.sin.legaldemo.LawyerUserUI;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerTaskAdapter;
import org.sin.legaldemo.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class CheckFragment extends Fragment {

    private View mView;
    private CustomListView listView;
    private BmobQuery<Task> query;
    private LawyerTaskAdapter adapter;
    private List<Task> taskList;

    private Task temp;
    private String title;
    private String content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_lawyer_check, container, false);

        query = new BmobQuery<Task>();

        listView = (CustomListView) mView.findViewById(R.id.clv);
        listView.setonRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        init();             //刷新 待优化

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }
                }.execute(null, null, null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                temp = taskList.get(position - 1);
                title = temp.getTitle();
                content = temp.getShort_content();
                dialog();
            }
        });
        init();

        return mView;
    }

    private void init(){
        query.order("-createdAt");
        query.findObjects(getContext(), new FindListener<Task>() {

            @Override
            public void onSuccess(List<Task> tasks) {
                taskList = tasks;
                adapter = new LawyerTaskAdapter(getContext(), R.layout.ltid_item, tasks);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(int code, String arg0) {
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(content);

        builder.setTitle(title);

        builder.setPositiveButton("抢单", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                if (temp.isBook()){
                    AlertDialog.Builder tooLate = new AlertDialog.Builder(getContext());
                    tooLate.setMessage("该单以被抢");
                    tooLate.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    tooLate.create().show();
                }else {
                    temp.setBook(true);
                    String objectID = temp.getObjectId();
                    temp.setLawyer(BmobUser.getCurrentUser(getContext(), UserBean.class));
                    temp.update(getContext(), objectID , new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getContext(), "抢单成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(getContext(), "抢单失败，请稍后重试" + s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

        });

        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });

        builder.create().show();

    }
}
