package bomoncntt.svk60.HO_MINH_QUYEN_1851063066;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.AccountAdapter;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.UserControl;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.Account;

public class LoginActivity extends AppCompatActivity {
    CheckBox check_save;
    EditText txt_username,txt_password;
    Button btn_login, btn_dangky;
    TextView txt_quenMK;

    public static final String MyPREFERENCES="MYPREFS";
    SharedPreferences pref;// khai báo
    SharedPreferences.Editor editor;//chỉnh sửa dữ liệu

    String thongbao = "Nếu bạn chưa có tài khoản. Vui lòng ấn vào nút Đăng Ký." +
            "Nếu đã có TK nhưng quên MK hãy ấn vào nút Quên Mật Khẩu ?" +
            "Phía dưới có list Account hiện có. Bạn có thể xóa 1 account bằng cách nhấn giữ vào nó" +
            "Nếu bạn không muốn đăng nhập lại mỗi khi mở app hãy check vào ô Nhớ Đăng Nhập";
    int vitri = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng Nhập");
        anhxa();
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        UserControl helper = new UserControl(this);
        pref=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //Đọc thông tin từ share ra
        String username=pref.getString("USERNAME","");
        String password=pref.getString("PASSWORD","");
        Log.v("username",username);
        if(!username.equals("")&&!password.equals("")){

            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
            finish();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=txt_username.getText().toString();
                String pass=txt_password.getText().toString();
                //Kiểm tra u và p có tồn tại trong user csdl ?
                if(user.length()==0){
                    txt_username.requestFocus();
                    txt_username.setError("VUI LÒNG KHÔNG BỎ TRỐNG USERNAME");
                }
                else if(pass.length()==0){
                    txt_password.requestFocus();
                    txt_password.setError("VUI LÒNG KHÔNG BỎ TRỐNG PASS");
                }
                else {
                    if(helper.serchUser(user)){
                        if(pass.equals(helper.serchPass(user))) {
                            writeMessage(user,pass);//ghi lại lịch sử đăng nhập
                            if (check_save.isChecked()) {
                                //lưu thông tin xuống sharepreferences
                                editor = pref.edit(); //chỉnh sửa file  MYPREFS.xml
                                editor.putString("USERNAME", user); //ghi thông tin vào fields USERNAME='admin'
                                editor.putString("PASSWORD", pass);
                                editor.commit();

                            } else {

                                //xóa preferences
                                editor = pref.edit();
                                editor.clear();
                                editor.commit();
                            }
                            Account ac = new Account(user,pass);
                            //showAccount();
                            Intent in = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(in);
                            finish();
                        }
                        else {
                            String mes = "MẬT KHẨU KHÔNG CHÍNH XÁC";
                            ShowBuilder(mes);
                        }
                    }
                    else {
                        if(user.equals("1851063066")){
                            String mes = "Bạn đang đăng nhập acc mặc định của Thầy Nhã à. Không được đâu. Hãy đăng ký 1 acc mới ngay nào :))))))";

                            ShowBuilder(mes);
                        }
                        else {
                            String mes = "Bạn chưa có TK ? Hãy ấn nút Đăng Ký bên dưới để tạo cho mình 1 TK";
                            ShowBuilder(mes);
                        }

                    }
                }


            }
        });
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, SingUpActivity.class);
                startActivity(in);
            }
        });
        txt_quenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, ForgotPassWordActivity.class);
                startActivity(in);
            }
        });
    }


    private void anhxa(){
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);
        check_save = findViewById(R.id.check_save);
        btn_dangky = findViewById(R.id.btn_dangky);
        txt_quenMK = findViewById(R.id.txt_quenMK);
    }
    private void writeMessage(String u,String p){
        try {
            FileOutputStream fileout = openFileOutput("datalogin.txt", MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(u + "," + p + ";\n"); //csv
            outputWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
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
            item.setTitle("Hướng dẫn");
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

    private void ShowBuilder(String mes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("THÔNG BÁO");
        builder.setMessage(mes);
        builder.setPositiveButton("TÔI BIẾT RỒI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}