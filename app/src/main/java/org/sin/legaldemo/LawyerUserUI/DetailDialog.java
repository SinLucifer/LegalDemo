package org.sin.legaldemo.LawyerUserUI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.sin.legaldemo.R;


/**
 *
 */
public class DetailDialog extends DialogFragment {

    private String title;
    private String content;

    private TextView tvTitle;
    private TextView tvContent;
    private Button tvOk;

    public static DetailDialog newInstance(String title, String content) {
        Bundle args = new Bundle();
        args.putString("title",title);
        args.putString("short_content", content);
        DetailDialog fragment = new DetailDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (getArguments() != null){
            title = getArguments().getString("title");
            content = getArguments().getString("short_content");
        }
        View view = inflater.inflate(R.layout.dialog_detail, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        tvContent = (TextView) view.findViewById(R.id.dialog_content);
        tvOk = (Button) view.findViewById(R.id.dialog_ok);

        tvTitle.setText(title);
        tvContent.setText(content);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
}
