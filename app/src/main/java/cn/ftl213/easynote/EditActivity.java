package cn.ftl213.easynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.ftl213.easynote.Bean.Notes;

public class EditActivity extends AppCompatActivity {

    private EditText etitile,econtent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bmob.initialize(this, "98ccd110795105d09df28256fc4dff83");
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //set a link to MainActivity in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etitile = (EditText) findViewById(R.id.title_edit);
        econtent = (EditText) findViewById(R.id.content_edit);
        Intent intent=getIntent();
        String eid = intent.getStringExtra("eid");

        BmobQuery<Notes> query = new BmobQuery<Notes>();
        query.getObject(eid, new QueryListener<Notes>() {

            @Override
            public void done(Notes object, BmobException e) {
                if(e==null){
                    //获取数据并将其绑定到TextView
                    etitile.setText(object.getTitle());
                    econtent.setText(object.getContent());
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    public void edit(View view){

        String vtitle = etitile.getText().toString();
        String vcontent = econtent.getText().toString();

        if (TextUtils.isEmpty(vtitle)) {
            etitile.setError(getString(R.string.error_field_required));
            etitile.setFocusable(true);
            etitile.requestFocus();
            return;
        }else if (!isTitleValid(vtitle)){
            etitile.setError(getString(R.string.error_invalid_title));
            etitile.setFocusable(true);
            etitile.requestFocus();
        }

        if (TextUtils.isEmpty(vcontent)){
            econtent.setError(getString(R.string.error_field_required));
            econtent.setFocusable(true);
            econtent.requestFocus();
            return;
        }
        //获取当前登录用户信息
        BmobUser user = BmobUser.getCurrentUser();
        Intent intent=getIntent();
        String eid = intent.getStringExtra("eid");
        Notes note = new Notes();

        note.setTitle(vtitle);
        note.setContent(vcontent);
        note.setWriter(user.getUsername());

        note.update(eid,new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("修改成功");
                    Intent  intent = new Intent(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });

    }
    private boolean isTitleValid(String vtitile) {

        return vtitile.length() > 4;
    }


    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
