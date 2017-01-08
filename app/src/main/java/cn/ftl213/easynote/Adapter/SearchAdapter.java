package cn.ftl213.easynote.Adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import java.util.List;

import cn.ftl213.easynote.Bean.Notes;
import cn.ftl213.easynote.R;

public class SearchAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<Notes> list;


    public SearchAdapter(Context context, List<Notes> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context=context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setList(List<Notes> l) {
        list = l;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, null);
            viewHolder = new SearchAdapter.ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.list_content);
            viewHolder.createtime = (TextView) convertView.findViewById(R.id.createtime);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (SearchAdapter.ViewHolder) convertView.getTag();

        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.content.setText(list.get(position).getContent());
        viewHolder.createtime.setText(list.get(position).getCreatedAt());

        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView content;
        public TextView createtime;
    }
}
