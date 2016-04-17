package org.sin.legaldemo.NormalUserUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.sin.legaldemo.Util.Content;
import org.sin.legaldemo.NormalUserUI.UserAdapter.GridViewAdapter;
import org.sin.legaldemo.R;

/**
 * Created by Sin on 2016/4/15.
 */
public class SelectFragment extends Fragment {
    private View mView;
    private GridView mGridView;
    private TaskFragment taskFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_normal_select,container,false);

        mGridView = (GridView)mView.findViewById(R.id.gv_select_task);
        Log.d("Sin","In SelectFragment");
        mGridView.setAdapter(new GridViewAdapter(getContext()));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("Task_Type",Content.img_text[position]);
                taskFragment = new TaskFragment();
                taskFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container,taskFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Content.isTask = true;


            }
        });

        return mView;
    }
}
