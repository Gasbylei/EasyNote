package cn.ftl213.easynote;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.ftl213.easynote.Bean.Notes;

import static cn.bmob.newim.core.BmobIMClient.getContext;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";
    private List<Notes> notesList;
    private AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page

        Intent intent=getIntent();
        collapsingToolbar.setTitle(intent.getStringExtra("title"));

        TextView content = (TextView) findViewById(R.id.content_detail);
        content.setText(intent.getStringExtra("content"));

        TextView createtime = (TextView) findViewById(R.id.createtime_detail);
        createtime.setText(intent.getStringExtra("createtime"));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(getResources().getDrawable(R.drawable.c));


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent edit = new Intent(DetailActivity.this,EditActivity.class);
            edit.putExtra("eid", getIntent().getStringExtra("nid"));
            startActivity(edit);
        }else if (id == R.id.action_delete){
            builder=new AlertDialog.Builder(this);
            builder.setTitle("删除");
            builder.setMessage("确定删除？");

            //监听下方button点击事件
            builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Notes update = new Notes();
                    update.setObjectId(getIntent().getStringExtra("nid"));
                    update.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(DetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                Intent update = new Intent(DetailActivity.this,MainActivity.class);
                                startActivity(update);
                            }else{
                                Log.i("bmob","删除失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(DetailActivity.this, "取消", Toast.LENGTH_SHORT).show();
                }
            });

            //设置对话框是可取消的
            builder.setCancelable(true);
            AlertDialog dialog=builder.create();
            dialog.show();
        }



        return super.onOptionsItemSelected(item);
    }

}
