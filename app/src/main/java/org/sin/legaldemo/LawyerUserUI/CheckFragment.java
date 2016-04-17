package org.sin.legaldemo.LawyerUserUI;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.sin.legaldemo.CustomListView;
import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.LawyerUserUI.LawyerAdapter.LawyerTaskAdapter;
import org.sin.legaldemo.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dola321 on 2016/4/17.
 */
public class CheckFragment extends Fragment {

    private View mView;
    private CustomListView listView;
    private BmobQuery<Task> query;
    private LawyerTaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_lawyer_check, container, false);

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

                        init();

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
        init();

        return mView;
    }

    private void init(){
        query = new BmobQuery<Task>();
        query.order("-createdAt");
        query.findObjects(getContext(), new FindListener<Task>() {

            @Override
            public void onSuccess(List<Task> tasks) {
                adapter = new LawyerTaskAdapter(getContext(), R.layout.ltid_item, tasks);
                listView.setAdapter(adapter);
            }

            @Override
            public void onError(int code, String arg0) {
                Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
