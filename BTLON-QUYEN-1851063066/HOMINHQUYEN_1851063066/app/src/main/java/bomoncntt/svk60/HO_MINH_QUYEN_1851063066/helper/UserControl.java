package bomoncntt.svk60.HO_MINH_QUYEN_1851063066.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import bomoncntt.svk60.HO_MINH_QUYEN_1851063066.model.Account;

public class UserControl extends SQLiteOpenHelper {
    public static final String Database_name="UserLogin.db";
    public static final String Table_name="LOGIN";
    public static final String col_user="username";
    public static final String col_pass="password";
    public UserControl(@Nullable Context context) {
        super(context, Database_name, null, 1);
    }

    SQLiteDatabase db;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table_name+" (username TEXT primary key,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Table_name);
        onCreate(db);//tạo lại
    }
    public Cursor showAccount(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from "+Table_name,null);
        return cursor;

    }
    public Integer delete(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Table_name,"username = ?", new String[]{username});
    }
    public boolean insertDataUser(Account account){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_user,account.getUsername());
        cv.put(col_pass,account.getPassword());
        Long result=db.insert(Table_name,null,cv);
        if(result==-1){
            return false;//insert không thành công
        }else
        {
            return true;
        }
    }
    public Cursor showPass(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select password from "+Table_name,null);
        return cursor;
    }
    public String serchPass(String tk){
        db = this.getReadableDatabase();
        String query = "select username,password from "+Table_name;

        Cursor cursor = db.rawQuery(query,null);
        String username;
        String pass = "Not found";
        if(cursor.moveToFirst()){
            do{
                username = cursor.getString(0);
                if(username.equals(tk)) {
                    pass = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        return pass;
    }
    public boolean serchUser(String tk){
        db = this.getReadableDatabase();
        String query = "select username from "+Table_name;
        Cursor cursor = db.rawQuery(query,null);
        String username;
        if(cursor.moveToFirst()){
            do{
                username = cursor.getString(0);
                if(username.equals(tk)) {
                    db.close();
                    return true;
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        return false;
    }
}
