package cn.ftl213.easynote.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.ftl213.easynote.Adapter.SearchAdapter;
import cn.ftl213.easynote.Bean.Notes;
import cn.ftl213.easynote.DetailActivity;
import cn.ftl213.easynote.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private EditText search_note;
    private Button query;
    private ListView search_json;
    private String querycontent;
    private List<Notes> list = new ArrayList<Notes>();
    private SearchAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Bmob.initialize(getActivity(), "98ccd110795105d09df28256fc4dff83");

        search_note = (EditText) view.findViewById(R.id.search_note);
        query = (Button) view.findViewById(R.id.query);
        search_json = (ListView) view.findViewById(R.id.search_json);


        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
        adapter = new SearchAdapter(getContext(), list);

        return view;
    }

    private void query() {

        BmobUser user = BmobUser.getCurrentUser();
        BmobQuery<Notes> query = new BmobQuery<>();
        querycontent = search_note.getText().toString();
        query.addWhereEqualTo("writer", user.getUsername());
        query.addWhereEqualTo("title", querycontent);
        query.setLimit(50);
        //根据创建时间降序
        query.order("-createdAt");
        query.findObjects(new FindListener<Notes>() {
            @Override
            public void done(final List<Notes> list, BmobException e) {
                if (e == null) {
                    adapter = new SearchAdapter(getActivity(), list);
                    search_json.setAdapter(adapter);
                    search_json.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Notes note = list.get(position);
                            Intent intent = new Intent(search_json.getContext(), DetailActivity.class);
                            intent.putExtra("nid", note.getObjectId());
                            intent.putExtra("title", note.getTitle());
                            intent.putExtra("content", note.getContent());
                            intent.putExtra("createtime", note.getCreatedAt());
                            startActivity(intent);
                        }
                    });
                    Toast.makeText(getActivity(), "搜索成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "没有找到", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
