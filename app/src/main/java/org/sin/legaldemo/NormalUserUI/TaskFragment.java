package org.sin.legaldemo.NormalUserUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.sin.legaldemo.Util.Content;
import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.R;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Sin on 2016/4/15.
 */
public class TaskFragment extends Fragment {
    private View mView;
    private EditText mTitle;
    private EditText mContent;
    private TextView mType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);   //告知Activity的toolbar添加fragment新增的方法
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();


        mView = inflater.inflate(R.layout.fragment_task_publish,container,false);

        mTitle = (EditText)mView.findViewById(R.id.et_title);
        mContent = (EditText)mView.findViewById(R.id.et_content);
        mType = (TextView)mView.findViewById(R.id.tv_type);

        mType.setText(bundle.get("Task_Type").toString());

        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Content.isTask = false;
        getActivity().invalidateOptionsMenu();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_toolbar_settings) {
            return true;
        }else if (id == R.id.main_toolbar_send){

            UserBean user = UserBean.getCurrentUser(getContext(),UserBean.class);

            Task task = new Task();

            task.setTitle(mTitle.getText().toString().trim());
            task.setShort_content(mContent.getText().toString().trim());
            task.setEvent_type(mType.getText().toString().trim());
            task.setTask_publisher(user);

            task.save(getContext(), new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(),"任务发布成功~请等待律师抢单~",Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_fragment_container,new SelectFragment());
                    transaction.commit();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(getContext(),"发布失败！请稍后重试！",Toast.LENGTH_SHORT).show();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
