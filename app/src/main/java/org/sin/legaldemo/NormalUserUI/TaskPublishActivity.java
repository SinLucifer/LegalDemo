package org.sin.legaldemo.NormalUserUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import org.sin.legaldemo.JavaBean.Task;
import org.sin.legaldemo.JavaBean.UserBean;
import org.sin.legaldemo.R;

import cn.bmob.v3.BmobACL;

public class TaskPublishActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mContent;
    private TextView mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_publish);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        mTitle = (EditText) findViewById(R.id.et_title);
        mContent = (EditText) findViewById(R.id.et_content);
        mType = (TextView) findViewById(R.id.tv_type);

        mType.setText(intent.getStringExtra("Task_Type"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_toolbar_send) {

            UserBean user = UserBean.getCurrentUser(this, UserBean.class);

            final Task task = new Task();
            BmobACL acl = new BmobACL();

            acl.setPublicWriteAccess(true);
            acl.setPublicReadAccess(true);

            task.setTitle(mTitle.getText().toString().trim());
            task.setShort_content(mContent.getText().toString().trim());
            task.setEvent_type(mType.getText().toString().trim());
            task.setBook(false);
            task.setTask_publisher(user);
            task.setACL(acl);

            TaskConfirmDialog taskConfirmDialog = TaskConfirmDialog.newInstance(task);
            taskConfirmDialog.show(getSupportFragmentManager(),"TaskConfirmDialog");
        }

        return super.onOptionsItemSelected(item);
    }


}
