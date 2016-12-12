package com.equityinfo.pipealarm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.model.bean.WarnBean;
import com.equityinfo.pipealarm.util.imageLoad.LoadImageforURL;

import java.util.List;


/**
 * Created by user on 2016/6/13.
 */
public class MainAdapter extends BaseAdapter{
    Context context;
    List<WarnBean> mList;
    ListView lv;
    String mCookie;
    private int RESULT=2;
    /**
     * 构造函数
     * @param context 上下文
     * @param list 包含了所有要显示的图片的ImageEntry对象的列表
     */
    public MainAdapter(Context context, List<WarnBean> list, ListView lv){
        this.context = context;
        this.mList = list;
        this.lv=lv;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        WarnBean warnBean=mList.get(position);
        if(null==convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_taskitem, null, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.IV_task_item);
            holder.conterDes = (TextView) convertView.findViewById(R.id.TV_describe);
            holder.taskTM = (TextView) convertView.findViewById(R.id.tv_time);
            holder.address=(TextView) convertView.findViewById(R.id.tv_address);
            holder.tx_tre_num=(TextView) convertView.findViewById(R.id.tx_tre_num);
//            holder.signBT = (Button) convertView.findViewById(R.id.BT_sign);
            holder.layout=(RelativeLayout) convertView.findViewById(R.id.lay_home_main);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
       final View view=convertView;

        holder.conterDes.setText(warnBean.content);
        holder.taskTM.setText(warnBean.tm_);
        holder.tx_tre_num.setText(warnBean.code);
        holder.address.setText(warnBean.description);
      String imageUrl="http://10.100.0.1/patrol/data"+mList.get(position).path;
        setimage(imageUrl,holder.img);
        return convertView;
    }



    class ViewHolder{
        ImageView img;
        TextView conterDes;
        TextView taskTM;
        TextView address;
//        Button signBT;
        TextView tx_tre_num;
        RelativeLayout layout;
    }

    void setimage(String imgurl,final ImageView imageView){
        LoadImageforURL loader=LoadImageforURL.getInstance();
        loader.load(context,imgurl,imageView);

    }
}

