package com.example.all_xutils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 给listView设置适配器
 */
public class BeanAdapter extends BaseAdapter {

    private Context context;
    private List<Bean> beans;

    public BeanAdapter(Context context, List<Bean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view= LayoutInflater.from(context).inflate(R.layout.items,viewGroup,false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder holder = (ViewHolder) view.getTag();

        Bean bean = beans.get(i);
        //设置title
        holder.title.setText(bean.getTitle());
        //下载设置图片
        BitmapHelper.getUtils().display(holder.image,bean.getImage());

        return view;
    }

    public void addAll(List<Bean> bean){
        beans.addAll(bean);
        notifyDataSetChanged();

    }

    public void clear(){
        beans.clear();
        notifyDataSetChanged();
    }




    private static class ViewHolder{
        @ViewInject(R.id.title)
        private TextView title;
        @ViewInject(R.id.image)
        private ImageView image;

        public ViewHolder(View itemView){
            ViewUtils.inject(this,itemView);
        }
    }


}
