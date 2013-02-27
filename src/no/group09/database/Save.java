package no.group09.database;

import java.util.HashMap;

import no.group09.database.objects.App;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Save {

	private static final String TAG = "Save";

	public SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	
	protected HashMap<String, Cursor> tables;
	
	public Save(Context context){
		dbHelper = new DatabaseHandler(context);
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public void open(){
		db = dbHelper.getWritableDatabase();
	}

//	private static synchronized SQLiteDatabase getDb(){
//		if(db == null || !db.isOpen()) {
//			db = Db.openDb(Db.DATABASE_NAME , SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READONLY);
//		}
//
//		return db;
//	}



	/**
	 * Selects all the records in the database
	 * @return return a Cursor[] with all the values. Iterate over each cursor to get out the values.
	 * Check for each Cursor[i] if its is null!
	 */
	public HashMap<String, Cursor> selectRecords() {

		tables = new HashMap<String, Cursor>();
		Cursor tempCursor = null;

		//app(appid, name, description, developerid, icon) 
		String[] app = new String[] {Constants.APP_ID, Constants.APP_NAME, Constants.APP_DESCRIPTION, Constants.APP_DEVELOPERID};  
		tempCursor = db.query(true, Constants.APP_TABLE, app, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();  
			tables.put("app", tempCursor);
			tempCursor = null;
		}

		//version(versionid, appid, version, fileURL, filesize)
		String[] version = new String[] {Constants.VERSION_ID, Constants.VERSION_APPID, Constants.VERSION_VERSION, Constants.VERSION_FILEURL, Constants.VERSION_FILESIZE};
		tempCursor = db.query(true, Constants.VERSION_TABLE, version, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();
			tables.put("version", tempCursor);
			tempCursor = null;
		}

		//ratings(ratingid, versionid, title, rating)
		String[] ratings = new String[] {Constants.RATINGS_ID, Constants.RATINGS_VERSIONID, Constants.RATINGS_TITLE, Constants.RATINGS_RATING};
		tempCursor = db.query(true, Constants.RATINGS_TABLE, ratings, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();
			tables.put("ratings", tempCursor);
			tempCursor = null;
		}

		//developer(developerid, name, website)
		String[] developer = new String[] {Constants.DEVELOPER_ID, Constants.DEVELOPER_NAME, Constants.DEVELOPER_WEBSITE};
		tempCursor = db.query(true, Constants.DEVELOPER_TABLE, developer, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst(); 
			tables.put("developer", tempCursor);
			tempCursor = null;
		}

		//pictures(pictureid, appid, fileURL)
		String[] pictures = new String[] {Constants.PICTURES_ID, Constants.PICTURES_APPID, Constants.PICTURES_FILEURL};
		tempCursor = db.query(true, Constants.PICTURES_TABLE, pictures, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst(); 
			tables.put("pictures", tempCursor);
			tempCursor = null;
		}

		//hardware(hardwareid, name, description)
		String[] hardware = new String[] {Constants.HARDWARE_ID, Constants.HARDWARE_NAME, Constants.HARDWARE_DESCRIPTION};
		tempCursor = db.query(true, Constants.HARDWARE_TABLE, hardware, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();  
			tables.put("hardware", tempCursor);
			tempCursor = null;
		}

		//platform(platformid, name, description, ramSize, romSize)
		String[] platform = new String[] {Constants.PLATFORM_ID, Constants.PLATFORM_NAME, Constants.PLATFORM_DESCRIPTION, Constants.PLATFORM_RAMSIZE, Constants.PLATFORM_ROMSIZE};
		tempCursor = db.query(true, Constants.PLATFORM_TABLE, platform, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();  
			tables.put("platform", tempCursor);
			tempCursor = null;
		}

		//btdevices(btdeviceid, name, mac-address, installedApp)
		String[] btdevices = new String[] {Constants.BTDEVICES_ID, Constants.BTDEVICES_NAME, Constants.BTDEVICES_MACADDRESS, Constants.BTDEVICES_INSTALLEDAPP};
		tempCursor = db.query(true, Constants.BTDEVICES_TABLE, btdevices, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();
			tables.put("btdevices", tempCursor);
			tempCursor = null;
		}

		//appusespins(appid, hardwareid)
		String[] appusespins = new String[] {Constants.APPUSESPINS_APPID, Constants.APPUSESPINS_HARDWAREID};
		tempCursor = db.query(true, Constants.APPUSESPINS_TABLE, appusespins, null, null, null, null, null, null); 
		if (tempCursor != null){
			tempCursor.moveToFirst();  
			tables.put("appusespins", tempCursor);
			tempCursor = null;
		}

		return tables;
	}

	public HashMap<String, Cursor> getTables(){
		return this.tables;
	}

	/** Get the the requested app from the database */
	public synchronized App getApp(String name) {

		Cursor c = null;
		App app = null;
		try {
//			db = getDb();
			c = db.rawQuery(Constants.SELECT_APP, new String[] { name });

			if (c.moveToFirst()) {
				app = new App(
						c.getInt(0),
						c.getString(1),
						c.getString(2),
						c.getInt(3),
						c.getBlob(4));
			}
		}

		catch (SQLiteException e) {
			Log.e(TAG, e.getMessage());
		}

		catch (IllegalStateException e) {
			Log.e(TAG, e.getMessage());
		}

		finally {
			if (c != null) {
				c.close();
			}
		}

		return app;
	}

	public void insertApp(App app) {
		
		db = Db.openDb(Db.DATABASE_NAME, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READWRITE);
		
		try {
			if (db.isOpen()) {
				SQLiteStatement insertStmt = db.compileStatement(Constants.INSERT_APP);
				insertStmt.clearBindings();
				insertStmt.bindString(1, app.getName());
				insertStmt.bindString(2, app.getDescription());
				insertStmt.bindString(3, String.valueOf(app.getDeveloperID()));
//				insertStmt.bindBlob(4, app.getIcon());
				insertStmt.executeInsert();
			}
		}
		
		catch (SQLiteException e) {
			Log.e(TAG, e.getMessage());
		}
		
		finally {
			close();
		}
	}
}