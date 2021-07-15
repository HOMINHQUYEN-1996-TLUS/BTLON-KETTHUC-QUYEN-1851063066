package bomoncntt.svk60.HO_MINH_QUYEN_1851063066;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.UserControl;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.Account;

public class SingUpActivity extends AppCompatActivity {
    EditText edt_userSingUp, edt_passSingUp,edt_passSingUp_again;
    Button btn_singUp;
    UserControl myDbSingUp=null;
    String thongbao = "Bạn cần điền tên đăng nhập vào trường Diền tên đăng nhập và pass vào trường Nhập MK. Sau đó ấn nút Đăng ký. Đây là Accout dùng để đăng nhập vào app nên hãy cố gắng nhớ nó. Trường hợp quên MK thì cũng không sao. Bên ngoài có mục quên Mk để tìm lại MK cho các bạn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        setTitle("Đăng Ký");
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        anhxa();
        myDbSingUp = new UserControl(this);


        btn_singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_userSingUp.getText().toString().equals("")){
                    edt_userSingUp.requestFocus();
                    edt_userSingUp.setError("Vui Lòng không bỏ trống user name");
                }
                else if(edt_passSingUp.getText().toString().equals("")){
                    edt_passSingUp.requestFocus();
                    edt_passSingUp.setError("Vui lòng không bỏ trống mật khẩu");
                }
                else if(edt_passSingUp_again.getText().toString().equals("")){
                    edt_passSingUp_again.requestFocus();
                    edt_passSingUp_again.setError("Vui lòng không bỏ trống ô này");
                }
                else
                {
                    if(edt_passSingUp_again.getText().toString().equals(edt_passSingUp.getText().toString())){
                        Account account1 = new Account();
                        account1.setUsername(edt_userSingUp.getText().toString());
                        account1.setPassword(edt_passSingUp.getText().toString());

                        boolean inserted = myDbSingUp.insertDataUser(account1);

                        if(inserted){
                            Log.d("tag_dk",""+account1.getUsername()+" "+account1.getPassword());
                            Toast.makeText(SingUpActivity.this,"Đăng Ký thành công",Toast.LENGTH_LONG).show();
                            finish();//đóng cửa sổ(activity) hiện hành
                            Intent st = new Intent(SingUpActivity.this, LoginActivity.class);
                            st.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(st);
                        }
                        else {
                            builder.setTitle("Thông Báo");
                            builder.setMessage("Username đã tồn tại");
                            builder.setPositiveButton("Tôi biết rồi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                    }
                    else {
                        edt_passSingUp_again.setError("MK không trùng khớp");
                        edt_passSingUp_again.setText("");
                    }
                }
            }
        });

    }

    private void anhxa() {
        btn_singUp = findViewById(R.id.btn_singUp);
        edt_userSingUp = findViewById(R.id.edt_userSingUp);
        edt_passSingUp = findViewById(R.id.edt_passSingUp);
        edt_passSingUp_again = findViewById(R.id.edt_passSingUP_again);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId=item.getItemId();//lấy tất cả các item mà đã đạt trong item_actionbar
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