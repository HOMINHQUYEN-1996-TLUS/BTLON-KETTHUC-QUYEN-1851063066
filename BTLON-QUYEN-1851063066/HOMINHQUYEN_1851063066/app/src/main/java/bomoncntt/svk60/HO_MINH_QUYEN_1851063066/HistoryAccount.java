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
        setTitle("List Account");
        historyAccount = findViewById(R.id.List_Account);
        showAccount();

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showAccount() {
        accountDb = new UserControl(this);
        arrayListAccount = new ArrayList<>();
        Cursor cursor = accountDb.showAccount();
        while(cursor.moveToNext()){
            Account acc = new Account(cursor.getString(0),cursor.getString(1));
            arrayListAccount.add(acc);
        }
        adapterAccount = new AccountAdapter(this,arrayListAccount);
        historyAccount.setAdapter(adapterAccount);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}