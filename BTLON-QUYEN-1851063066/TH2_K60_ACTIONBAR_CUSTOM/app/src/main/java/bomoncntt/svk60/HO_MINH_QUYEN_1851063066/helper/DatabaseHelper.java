package bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.SinhVien;

//abstract class : Lớp trừu tượng
//DatabaseHelper.Database_name
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_name="Students.db";
    public static final String Table_name="student_table";
    public static final String col_masv="masv";
    public static final String col_tensv="tensv";
    public static final String col_gt="gt";
    public static final String col_lop="lop";
    public static final String col_image="hinhanh";
    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_name,null,1);
        //chay vô đây trước

    }


    //gọi kế tiếp
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_name+" (masv TEXT primary key,tensv TEXT, gt TEXT,lop TEXT,hinhanh TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+Table_name);
            onCreate(db);//tạo lại

    }
    public boolean insertDataObject(SinhVien sv){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_masv,sv.getMasv());
        cv.put(col_tensv,sv.getTensv());
        cv.put(col_gt,sv.getGt());
        cv.put(col_lop,sv.getLop());
        cv.put(col_image,sv.getHinhanh());
        Long result=db.insert(Table_name,null,cv);
        if(result==-1){
            return false;//insert không thành công
        }else
        {
            return true;
        }

    }
    //insert data
    public boolean insertData(String masv,String tensv,String gt,String lop,String hinhanh){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_masv,masv);
        cv.put(col_tensv,tensv);
        cv.put(col_gt,gt);
        cv.put(col_lop,lop);
        cv.put(col_image,hinhanh);
        Long result=db.insert(Table_name,null,cv);
        if(result==-1){
            return false;//insert không thành công
        }else
        {
            return true;
        }

    }
    public Cursor showData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from "+Table_name,null);
        return cursor;

    }
    public Integer delete(String masv){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Table_name,"masv = ?", new String[]{masv});
    }
    public boolean update(String masv,String tensv,String gt, String lop,String hinhanh){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_masv,masv);
        cv.put(col_tensv,tensv);
        cv.put(col_gt,gt);
        cv.put(col_lop,lop);
        cv.put(col_image,hinhanh);
       int result= db.update(Table_name,cv,"masv = ?",new String[]{masv});
        if(result==-1){
            return false;//insert không thành công
        }else
        {
            return true;
        }

    }
}
