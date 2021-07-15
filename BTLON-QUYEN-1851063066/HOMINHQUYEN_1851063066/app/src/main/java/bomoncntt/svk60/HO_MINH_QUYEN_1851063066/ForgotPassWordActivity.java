package bomoncntt.svk60.HO_MINH_QUYEN_1851063066;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.UserControl;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.Account;

public class ForgotPassWordActivity extends AppCompatActivity {
    EditText edt_getUser;
    Button btn_getPassWord;
    String thongbao = "Bạn cần nhập usernam cần tìm mk. Sau đó ấn vào nút Lấy Mật Khẩu. Nếu user chính xác thì Pass sẽ hiện ra ở dòng Pass Word. Sau khi có được Mật Khẩu và user bạn có thể ấn vào nút Về Trang Đăng Nhập để Đăng nhập vào hệ thống";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Quên Mật Khẩu");
        setContentView(R.layout.activity_forgot_pass_word);

        edt_getUser = findViewById(R.id.edt_getUser);
        btn_getPassWord = findViewById(R.id.btn_getPassWord);
        //txt_textView7 = findViewById(R.id.textView7);

        UserControl helper = new UserControl(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        btn_getPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=edt_getUser.getText().toString();
                if(helper.serchUser(user)){
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Mật Khẩu ứng với tài khoản "+ user + " là : "+helper.serchPass(user));
                    builder.setPositiveButton("tôi biết rồi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else {
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Vui lòng điền đúng User Name");
                    builder.setPositiveButton("tôi biết rồi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId=item.getItemId();
        if(itemId==R.id.info){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hướng Dẫn");
            builder.setMessage(thongbao);
            builder.setPositiveButton("tôi biết rồi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}