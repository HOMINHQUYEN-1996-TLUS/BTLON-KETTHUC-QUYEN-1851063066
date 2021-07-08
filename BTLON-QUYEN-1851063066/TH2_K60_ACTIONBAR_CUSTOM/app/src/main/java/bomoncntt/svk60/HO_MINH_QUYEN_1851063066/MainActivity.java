package bomoncntt.svk60.HO_MINH_QUYEN_1851063066;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.ArrayList;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.DatabaseHelper;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper.MyListAdapter;
import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.SinhVien;

import static bomoncntt.svk60.HO_MINH_QUYEN_1851063066.LoginActivity.MyPREFERENCES;

public class MainActivity extends AppCompatActivity {
    public static DatabaseHelper mydb;
    ArrayList<SinhVien> arrayListSV=null;//chứa tất cả các phần tử trong csdl
    MyListAdapter adapter=null; //adapter custom
    private ListView lvsv = null;
    ArrayList<SinhVien> StudentCheckedItemList; //mảng array chứa các phần tử mà được check
    CheckBox checked;
    SharedPreferences pref;// khai báo
    SharedPreferences.Editor editor;//chỉnh sửa dữ liệu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("QLSV");
        checked = findViewById(R.id.check_save);
        pref=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        StudentCheckedItemList=new ArrayList<>();
        mydb = new DatabaseHelper(this);
        arrayListSV=new ArrayList<>();//Khởi tạo mảng lưu các đối tượng
        Log.d("tag","abc");
        Log.d("tag","abc");
        lvsv = findViewById(R.id.lvsinhvien); // ánh xạ từ listview sang Java
        Cursor cursor =mydb.showData(); //đổ dữ liệu từ trong sqlite ra cursor

        while (cursor.moveToNext()) {
            SinhVien sv = new SinhVien(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
            arrayListSV.add(sv);

        }

        adapter = new MyListAdapter(this, arrayListSV,true);//gán data mảng vào adapter mà mình custom
        lvsv.setAdapter(adapter);
        lvsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bắt check box
                CheckBox itemCheckbox=(CheckBox)view.findViewById(R.id.bomoncntt);
                boolean checkChecked=false;
                if(itemCheckbox.isChecked()){
                    itemCheckbox.setChecked(false);
                    checkChecked=false;
                }
                else{
                    itemCheckbox.setChecked(true);
                    checkChecked=true;
                }
                SinhVien sv=new SinhVien();
                sv.setMasv(arrayListSV.get(position).getMasv());
                sv.setTensv(arrayListSV.get(position).getTensv());
                sv.setGt(arrayListSV.get(position).getGt());
                sv.setLop(arrayListSV.get(position).getLop());
                sv.setHinhanh(arrayListSV.get(position).getHinhanh());
                Log.d("tagcheck","++"+checkChecked);
                addCheckListItem(sv,checkChecked);
            }
        });
        lvsv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Hàm nào đó show thông báo chắc xóa kg?
                //position vị trí mà chọn trên listview
                showD(position);
                return false;
            }
        });
    }

    private void showD(int i){
        //parttern buider
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Are you sure to delete this item?");
        builder.setPositiveButton("Yes",(dialog, which) -> {

            Integer deletekq=mydb.delete(arrayListSV.get(i).getMasv());
            if(deletekq>0) {
                Toast.makeText(MainActivity.this,"Delete thành công", Toast.LENGTH_SHORT).show();
                arrayListSV.remove(i);
                adapter.notifyDataSetChanged();

            }
            else{
                Toast.makeText(MainActivity.this,"Delete không thành công", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //Đóng cửa thông báo
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private String getStudent(){
        StringBuffer b=new StringBuffer();
        if(StudentCheckedItemList!=null) {
            int size = StudentCheckedItemList.size();//lấy kích mà đã chọn
            for (int i = 0; i < size; i++) {
                SinhVien sv = StudentCheckedItemList.get(i);
                b.append(sv.getMasv());
                b.append(" ");
            }
        }
        return b.toString().trim();
    }

    private void addCheckListItem(SinhVien user,boolean add){

        if(StudentCheckedItemList!=null){
            boolean accountExist=false;
            int existPosion=-1;// vị trí chưa chon
            int size=StudentCheckedItemList.size();//lấy kích mà đã chọn
            for(int i=0;i<size;i++){
                SinhVien sv=StudentCheckedItemList.get(i);
                if(sv.getMasv().equals(user.getMasv())){
                    accountExist=true; //đã tồn tại
                    existPosion=i;
                    break;
                }
            }
            if(add){ //checked
                if(!accountExist){ //chưa có trong trong mảng

                    StudentCheckedItemList.add(user);//đây là thằng sinh viên mình tìm
                }

            }else //bỏ được check thì kiểm tra có hay trong trong mảng
            {
                    if(accountExist) //mục đang chọn có xuất hiện trong mảng (bỏ dấu check)
                    {
                        if(existPosion!=-1){
                            StudentCheckedItemList.remove(existPosion);
                        }
                    }
            }
        }else{

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.item_action_bar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId=item.getItemId();
        if(itemId==R.id.menu_add){
            Log.v("add","ok");
            //1 Mở cửa sổ InfoSVActivity
            Intent in=new Intent(this,InfoSVActivity.class);
            in.putExtra("Flag","ADD");
            in.putExtra("MASV","");
            in.putExtra("TENSV","");
            in.putExtra("GT","");
            in.putExtra("LOP","");
            in.putExtra("HINHANH","");
            startActivity(in);
        }
        else if(itemId==R.id.menu_logout){
            editor=pref.edit(); //chỉnh sửa file  MYPREFS.xml
            editor.remove("USERNAME");
            editor.remove("PASSWORD");
            editor.commit();
            finish();
            Intent in = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(in);
        }
        else if(itemId==R.id.menu_delete)
        {
            if(StudentCheckedItemList!=null){// chọn ít nhất 1 phần tử
                int size=StudentCheckedItemList.size();//lấy số tự thực sự đã check
                if(size==0) {
                    showDiaLog();
                }
                else{ //chọn 1 ít nhất 1 phần tử
                    while (size!=0){
                        for(int i=0;i<StudentCheckedItemList.size();i++){
                            SinhVien sv=StudentCheckedItemList.get(i);
                            Integer delete=mydb.delete(sv.getMasv()); //xóa trong csdl
                            if(delete>0){
                                StudentCheckedItemList.remove(i);
                                size=StudentCheckedItemList.size();//update lại kích thước sau khi sau xóa
                            }else {
                                Toast.makeText(this,"Xóa thất bại", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    arrayListSV.clear();//xóa hết trên array
                    Cursor cursor=mydb.showData();
                    while(cursor.moveToNext()){
                        SinhVien sv=new SinhVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                        arrayListSV.add(sv);
                    }
                    adapter=new MyListAdapter(this,arrayListSV,true);
                    lvsv.setAdapter(adapter);
                }
            }
        }else {
            if(StudentCheckedItemList!=null){// chọn ít nhất 1 phần tử
                int size=StudentCheckedItemList.size();//lấy số tự thực sự đã check
                if(size!=1){
                    showDiaLog();
                }else {
                    SinhVien sv=StudentCheckedItemList.get(0);
                    Intent in = new Intent(this, InfoSVActivity.class);
                    in.putExtra("Flag", "EDIT");
                    in.putExtra("MASV", sv.getMasv());
                    in.putExtra("TENSV", sv.getTensv());
                    in.putExtra("GT", sv.getGt());
                    in.putExtra("LOP", sv.getLop());
                    in.putExtra("HINHANH",sv.getHinhanh());
                    startActivity(in);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDiaLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thong bao");
        builder.setMessage("Vui long chon 1 SV để thao tác");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setCancelable(false);
        builder.setPositiveButton("tôi biết rồi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}