package com.example.administrator.monthtest1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyAdapter extends BaseAdapter {
    private List<Bean.ApkBean> list;
    private Context context;

    public MyAdapter(List<Bean.ApkBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

   @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           ViewHolder holder = null;
           if(convertView==null){
               holder=new ViewHolder();
               convertView=convertView.inflate(context,R.layout.item,null);
               holder.tv1= (TextView) convertView.findViewById(R.id.tv1);
               holder.tv2= (TextView) convertView.findViewById(R.id.tv2);
               holder.imageView= (ImageView) convertView.findViewById(R.id.iv1);
               convertView.setTag(holder);
           }else{
               holder= (ViewHolder) convertView.getTag();
           }
               ImageLoaderUtils.setImageView(list.get(position).getIconUrl(),context,holder.imageView);

           holder.tv1.setText(list.get(position).getName());
           holder.tv2.setText(list.get(position).getCategoryName());
           return convertView;
       }
       static class ViewHolder{
           TextView tv1,tv2;
           ImageView imageView;
       }
}
