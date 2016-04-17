package org.sin.legaldemo.NormalUserUI.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.sin.legaldemo.R;
import org.sin.legaldemo.Util.Content;

/**
 * Created by Sin on 2016/4/15.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;


    public GridViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return Content.img_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private final class MyViewHolder{

        TextView tv_item;
        ImageView iv_item;

        public TextView getTv_item() {
            return tv_item;
        }

        public void setTv_item(TextView tv_item) {
            this.tv_item = tv_item;
        }

        public ImageView getIv_item() {
            return iv_item;
        }

        public void setIv_item(ImageView iv_item) {
            this.iv_item = iv_item;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null){
            myViewHolder = new MyViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item,parent,false);
            myViewHolder.tv_item = (TextView)convertView.findViewById(R.id.tv_item);
            myViewHolder.iv_item = (ImageView)convertView.findViewById(R.id.iv_item);

            convertView.setTag(myViewHolder);
        }else {
            myViewHolder =  (MyViewHolder) convertView.getTag();
        }

        myViewHolder.tv_item.setText(Content.img_text[position]);
        myViewHolder.iv_item.setImageResource(R.drawable.ic_menu_camera);
        return convertView;
    }
}
