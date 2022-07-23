package basic.contactadd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbmanager extends SQLiteOpenHelper {
    private static final String dbname = "dbcontact";

    public Dbmanager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "create table tbl_contct(id integer primary key autoincrement, name text, phone text,image blob)";
        sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String qry = "DROP TABLE IF EXISTS tbl_contct";
        sqLiteDatabase.execSQL(qry);
        onCreate(sqLiteDatabase);
    }

    public String addrecord(String name, String phone, byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("image", img);
        float res = db.insert("tbl_contct", null, cv);

        if (res == -1) {
            return "Fail";
        } else {
            return "Successfully Inserted";
        }


    }

    public Cursor readable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "SELECT * FROM tbl_contct";
        Cursor cursor = db.rawQuery(qry, null);
        return cursor;

    }

    public void deleteEntry(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from tbl_contct where name=?", new String[]{name});
        db.delete("tbl_contct", "name=?", new String[]{name});
    }
}
