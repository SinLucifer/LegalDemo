package org.sin.legaldemo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.sin.legaldemo.Content;
import org.sin.legaldemo.R;

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
            Log.d("Sin","send has been click in TaskFragment");
        }

        return super.onOptionsItemSelected(item);
    }
}
