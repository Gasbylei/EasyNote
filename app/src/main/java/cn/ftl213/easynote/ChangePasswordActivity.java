package cn.ftl213.easynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPassword,newPassword,newPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //set a link to MainActivity in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        oldPassword = (EditText)findViewById(R.id.old_password);
        newPassword = (EditText)findViewById(R.id.new_password);
        newPasswordAgain = (EditText) findViewById(R.id.new_password_again);
    }

    public void changePassword(View view){
        String opassword = oldPassword.getText().toString();
        String npassword = newPassword.getText().toString();
        String npassword1 = newPasswordAgain.getText().toString();

        if (TextUtils.isEmpty(opassword)) {
            oldPassword.setError(getString(R.string.error_field_required));
            oldPassword.setFocusable(true);
            oldPassword.requestFocus();
            return;
        }else if(!isPasswordValid(npassword)){
            oldPassword.setError(getString(R.string.error_invalid_password));
            oldPassword.setFocusable(true);
            oldPassword.requestFocus();
        }

        if (TextUtils.isEmpty(npassword)) {
            newPassword.setError(getString(R.string.error_field_required));
            newPassword.setFocusable(true);
            newPassword.requestFocus();
            return;
        }else if(!isPasswordValid(npassword)){
            newPassword.setError(getString(R.string.error_invalid_password));
            newPassword.setFocusable(true);
            newPassword.requestFocus();
        }

        if (TextUtils.isEmpty(npassword1)) {
            newPasswordAgain.setError(getString(R.string.error_field_required));
            newPasswordAgain.setFocusable(true);
            newPasswordAgain.requestFocus();
            return;
        }else if(!isPasswordValid(npassword1)){
            newPasswordAgain.setError(getString(R.string.error_invalid_password));
            newPasswordAgain.setFocusable(true);
            newPasswordAgain.requestFocus();
        }

        if (!npassword1.equals(npassword)) {
            newPassword.setError(getString(R.string.reg_error_confirmError));
            newPassword.setText("");
            newPasswordAgain.setText("");
            newPassword.setFocusable(true);
            newPassword.requestFocus();
            return;
        }

        BmobUser.updateCurrentUserPassword(opassword, npassword, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ChangePasswordActivity.this, "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private boolean isPasswordValid(String npassword) {
        return npassword.length() > 4;
    }
}
