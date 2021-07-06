package bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.R;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.Account;

public class AccountAdapter extends ArrayAdapter<Account> {
    private final Activity context;
    private final ArrayList<Account> listAccount;

    private static class ViewHolder {
        //phụ thuộc vào item của layout
        //khai báo thuộc tính
        TextView username;
        TextView passWord;

    }
    public AccountAdapter(Activity context, ArrayList<Account> data) {
        super(context, R.layout.layout_item_account, data);
        this.context = context;
        this.listAccount = data;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        //position là vị trí của listview mà khi click
        //getItem(position)-> trà về object (Phụ thuộc vào adapter)
        Account dataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            //get item mà trong layout
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //layout_item_sv chỉ thay đổi item
            convertView = inflater.inflate(R.layout.layout_item_account, parent, false);

            //ánh xạ từ xml sang java
            viewHolder.username =  convertView.findViewById(R.id.txt_get_item_User);
            viewHolder.passWord =  convertView.findViewById(R.id.txt_get_item_Pass);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.username.setText(dataModel.getUsername());
        viewHolder.passWord.setText(dataModel.getPassword());
        return convertView;

    };
}
