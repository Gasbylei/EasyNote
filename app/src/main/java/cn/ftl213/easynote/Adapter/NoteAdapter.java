package cn.ftl213.easynote.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.ftl213.easynote.Bean.Notes;
import cn.ftl213.easynote.Fragment.NoteFragment;

public class NoteAdapter extends RecyclerView.Adapter<NoteFragment.ViewHolder>{

    private LayoutInflater inflater;
    private List<Notes> notesList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public NoteAdapter(Context context, List<Notes> notesList) {
        this.inflater = LayoutInflater.from(context);
        this.notesList = notesList;
        this.context=context;
    }

    @Override
    public NoteFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(final NoteFragment.ViewHolder holder, int position) {

        Notes note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        holder.createtime.setText(note.getCreatedAt());
//        holder.avator.setImageDrawable(note.g);
        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
