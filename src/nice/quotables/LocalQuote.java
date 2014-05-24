package nice.quotables;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalQuote extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "quotables";

	public LocalQuote(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table quotes (id integer primary key autoincrement, content text not null, author text not null)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS quotes");
		onCreate(db);
	}

	
	public void createQuote(String id, String content, String author){
		SQLiteDatabase db = this.getWritableDatabase();
		try{
	        String sqlDataStore = "insert into quotes (id, content, author) values ('"+id+"', '"+content+"', '"+author+"')";
	        db.execSQL(sqlDataStore);
	        db.close();
		}catch(Exception e){
			db.close();
		}
		
	}
	
	public List<String> getQuote(){
		List<String> array = new ArrayList<String>();
		String countQuery = "SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        String id = "";
        while(cursor.moveToNext()){
            id = cursor.getString(cursor.getColumnIndex("id"));
            array.add(cursor.getString(cursor.getColumnIndex("content")));
            array.add(cursor.getString(cursor.getColumnIndex("author")));
            array.add(id);
        }
        cursor.close();
        db.close();
        return array;
		
	}
	
	
}
