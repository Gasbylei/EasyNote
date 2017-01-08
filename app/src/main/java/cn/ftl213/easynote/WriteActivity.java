package cn.ftl213.easynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.ftl213.easynote.Bean.Notes;

import static android.R.string.cancel;

public class WriteActivity extends AppCompatActivity {

    private EditText mtitile,mcontent;
    public static String TAG = "bmob";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Bmob.initialize(this, "98ccd110795105d09df28256fc4dff83");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //set a link to MainActivity in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mtitile = (EditText) findViewById(R.id.title);
        mcontent = (EditText) findViewById(R.id.content);
    }

    public  void submit(View view){
        String vtitle = mtitile.getText().toString();
        String vcontent = mcontent.getText().toString();

        if (TextUtils.isEmpty(vtitle)) {
            mtitile.setError(getString(R.string.error_field_required));
            mtitile.setFocusable(true);
            mtitile.requestFocus();
            return;
        }else if (!isTitleValid(vtitle)){
            mtitile.setError(getString(R.string.error_invalid_title));
            mtitile.setFocusable(true);
            mtitile.requestFocus();
        }

        if (TextUtils.isEmpty(vcontent)){
            mcontent.setError(getString(R.string.error_field_required));
            mcontent.setFocusable(true);
            mcontent.requestFocus();
            return;
        }
        //获取当前登录用户信息
        BmobUser user = BmobUser.getCurrentUser();

        Notes note = new Notes();
        //注意：不能调用gameScore.setObjectId("")方法
        note.setTitle(vtitle);
        note.setContent(vcontent);
        note.setWriter(user.getUsername());

        note.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    toast("发表成功");
                    Intent  intent = new Intent(WriteActivity.this,MainActivity.class);
                    startActivity(intent);
//                    Toast.makeText(WriteActivity.this, "发表成功："+ objectId, Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }

    private boolean isTitleValid(String vtitile) {

        return vtitile.length() > 4;
    }
    public static void loge(Throwable e) {
        Log.i(TAG,"===============================================================================");
        if(e instanceof BmobException){
            Log.e(TAG, "错误码："+((BmobException)e).getErrorCode()+",错误描述："+((BmobException)e).getMessage());
        }else{
            Log.e(TAG, "错误描述："+e.getMessage());
        }
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
