package cn.ftl213.easynote.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.ValueEventListener;
import cn.ftl213.easynote.Adapter.NoteAdapter;
import cn.ftl213.easynote.Bean.Notes;
import cn.ftl213.easynote.DetailActivity;
import cn.ftl213.easynote.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    public NoteAdapter nAdapter;
    private List<Notes> notesList = new ArrayList<Notes>();
    private RecyclerView recyclerView;

    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        Bmob.initialize(getActivity(), "98ccd110795105d09df28256fc4dff83");
        prepareNotesData();

        nAdapter = new NoteAdapter(recyclerView.getContext(), notesList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        refresh();
        return recyclerView;
    }


    private void prepareNotesData() {

        BmobUser user = BmobUser.getCurrentUser();
        BmobQuery<Notes> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("writer", user.getUsername());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //根据创建时间降序
        query.order("-createdAt");
        query.findObjects(new FindListener<Notes>() {
            @Override
            public void done(final List<Notes> notesList, BmobException e) {
                if (e == null) {
                    // Toast.makeText(getActivity(),notesList, Toast.LENGTH_SHORT).show();
                    nAdapter = new NoteAdapter(getActivity(), notesList);
                    recyclerView.setAdapter(nAdapter);
                    nAdapter.notifyDataSetChanged();
                    nAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Notes note = notesList.get(position);
                            Intent intent = new Intent(recyclerView.getContext(), DetailActivity.class);
                            intent.putExtra("nid",note.getObjectId());
                            intent.putExtra("title",note.getTitle());
                            intent.putExtra("content",note.getContent());
                            intent.putExtra("createtime",note.getCreatedAt());
                            startActivity(intent);
                        }
                    });
                } else {

                }
            }
        });
    }

//    public  void refresh(){
//        loadPreData();
//    }
//
//    private  void loadPreData() {
//
//        final BmobRealTimeData rtd = new BmobRealTimeData();
//        rtd.start(new ValueEventListener() {
//            @Override
//            public void onDataChange(JSONObject data) {
//                Log.d("bmob", "("+data.optString("action")+")"+"数据："+data);
//
//                if(BmobRealTimeData.ACTION_UPDATETABLE.equals(data.optString("action"))){
//                    JSONObject data1 = data.optJSONObject("data");
//                    notesList.add(new Note(data1.optString("name"), data1.optString("content")));
//                    nAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onConnectCompleted(Exception ex) {
//                if(rtd.isConnected()){
//                    // 监听表更新
//                    rtd.subTableDelete("Notes");
//                }
//            }
//        });
//
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView avator;
        public TextView title;
        public TextView content;
        public TextView createtime;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
//            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            title = (TextView) itemView.findViewById(R.id.list_title);
            content = (TextView) itemView.findViewById(R.id.list_content);
            createtime = (TextView) itemView.findViewById(R.id.createtime);
        }
    }

}
