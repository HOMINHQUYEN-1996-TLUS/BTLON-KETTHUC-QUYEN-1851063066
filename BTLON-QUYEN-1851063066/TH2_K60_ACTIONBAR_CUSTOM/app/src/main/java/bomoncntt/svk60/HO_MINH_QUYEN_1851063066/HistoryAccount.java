package bomoncntt.svk60.HO_MINH_QUYEN_1851063066;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.AccountAdapter;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.UserControl;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.Account;

public class HistoryAccount extends AppCompatActivity {
    ListView historyAccount;
    ArrayAdapter<Account> adapterAccount = null;
    ArrayList<Account> arrayListAccount=null;
    UserControl accountDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_account);

        historyAccount = findViewById(R.id.List_Account);
        showAccount();
    }

    public void showAccount() {
        accountDb = new UserControl(this);
        arrayListAccount = new ArrayList<>();
        Cursor cursor = accountDb.showAccount();
        while(cursor.moveToNext()){
            Account acc =new Account(cursor.getString(0),cursor.getString(1));
            arrayListAccount.add(acc);
        }
        adapterAccount = new AccountAdapter(this,arrayListAccount);
        historyAccount.setAdapter(adapterAccount);
    }

    private void showD(int i){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn muốn xóa Account này ?");

        builder.setPositiveButton("Yes",(dialog, which) -> {

            Integer deletekq=accountDb.delete(arrayListAccount.get(i).getUsername());
            if(deletekq>0) {
                Toast.makeText(HistoryAccount.this,"Delete thành công", Toast.LENGTH_SHORT).show();
                arrayListAccount.remove(i);
                adapterAccount.notifyDataSetChanged();

            }
            else{
                Toast.makeText(HistoryAccount.this,"Delete không thành công", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //Đóng cửa thông báo
            }
        });
        android.app.AlertDialog alert=builder.create();
        alert.show();
    }
}