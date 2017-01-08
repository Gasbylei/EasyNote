package cn.ftl213.easynote;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.ftl213.easynote.Bean.MyUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private AutoCompleteTextView email;
    private EditText password, confirm_password;
    private Button btn_register, btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化Bomb
        Bmob.initialize(this, "98ccd110795105d09df28256fc4dff83");

        username = (EditText) findViewById(R.id.username);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.Confirm_password);

        btn_back = (Button) findViewById(R.id.back_btn);
        btn_back.setOnClickListener(new BackLoginListener());


    }

    // 返回登录界面
    class BackLoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            RegisterActivity.this.finish();
        }
    }

    public void sign_up(View view){

            // TODO Auto-generated method stub
            String userName;
            String passWord;
            String confirm;
            String Email;

            userName = username.getText().toString();
            passWord = password.getText().toString();
            confirm = confirm_password.getText().toString();
            Email = email.getText().toString();


            if (userName != null && "".equals(userName)) {
                username.setError(getString(R.string.error_field_required));
                username.setFocusable(true);
                username.requestFocus();
                return;
            }
            if (!TextUtils.isEmpty(passWord) && !isPasswordValid(passWord)) {
                password.setError(getString(R.string.error_invalid_password));
                password.setFocusable(true);
                password.requestFocus();
                return;
            }
            if (!TextUtils.isEmpty(confirm) && !isPasswordValid(confirm)) {
                confirm_password.setError(getString(R.string.error_invalid_password));
                confirm_password.setFocusable(true);
                confirm_password.requestFocus();
                return;
            }

            if (Email != null && "".equals(Email)) {
                email.setError(getString(R.string.error_field_required));
                email.setFocusable(true);
                email.requestFocus();
                return;
            } else if (!isEmailValid(Email)) {
                email.setError(getString(R.string.error_invalid_email));
                email.setFocusable(true);
                email.requestFocus();
            }

            if (!confirm.equals(passWord)) {
                password.setError(getString(R.string.reg_error_confirmError));
                password.setText("");
                confirm_password.setText("");
                password.setFocusable(true);
                password.requestFocus();
                return;
            }

            BmobUser bu = new BmobUser();
            bu.setUsername(userName);
            bu.setPassword(passWord);
            bu.setEmail(Email);
            //注意：不能用save方法进行注册
            bu.signUp(new SaveListener<BmobUser>() {

                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
//                                toast("注册成功");
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败！ ", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }


        private boolean isEmailValid(String Email) {
            //TODO: Replace this with your own logic
            return Email.contains("@");
        }

        private boolean isPasswordValid(String password) {
            //TODO: Replace this with your own logic
            return password.length() > 4;
        }



}
