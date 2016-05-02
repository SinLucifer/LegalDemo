package org.sin.legaldemo.NormalUserUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.sin.legaldemo.Util.Content;
import org.sin.legaldemo.NormalUserUI.UserAdapter.GridViewAdapter;
import org.sin.legaldemo.R;

public class SelectFragment extends Fragment {

    public static SelectFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SelectFragment fragment = new SelectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_normal_select, container, false);

        GridView mGridView = (GridView) mView.findViewById(R.id.gv_select_task);
//        Log.d("Sin", "In SelectFragment");
        mGridView.setAdapter(new GridViewAdapter(getContext()));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),TaskPublishActivity.class);

                intent.putExtra("Task_Type", Content.img_text[position]);
                startActivity(intent);
                Content.isTask = true;
            }
        });


        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Sin","I just Attach");
    }
}
