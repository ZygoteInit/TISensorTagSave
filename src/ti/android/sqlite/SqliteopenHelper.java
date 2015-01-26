package ti.android.sqlite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteopenHelper extends SQLiteOpenHelper {

	 private static final int DATABASE_VERSION = 1;

	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // Database Name
    private static final String DATABASE_NAME = "TISensorTAG";

    // Table Names
    private static final String TABLE_SENSOR_DATA = "TISensorData";

   // private static final String TABLE_LIKEINFORMATIONS="LikeSuggestions";
    // Common column names
    private static final String ACCERELOMETER = "accerelometer";
    private static final String MAGNOMETER = "magnometer";
    private static final String GYROSCOPE = "gyroscope";
    private static final String IRTEMPERATURE = "irtemperature";
    private static final String AMBTEMPERATURE = "ambtemperature";
    private static final String ID = "id";
    private static final String BAROMETER = "barometer";
    private static final String HUMIDITY = "humidity";
    private static final String DATETIME= "createddate";
    

    private static final String CREATE_TABLE_TISENSORDATA = "CREATE TABLE "
            + TABLE_SENSOR_DATA + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ACCERELOMETER + " TEXT," + MAGNOMETER + " TEXT," + GYROSCOPE + " TEXT,"+ IRTEMPERATURE+ " TEXT,"+ AMBTEMPERATURE+ " TEXT,"+ BAROMETER + " TEXT,"+ HUMIDITY + " TEXT,"+ DATETIME + " TEXT"+")";

	public SqliteopenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_TISENSORDATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		  db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENSOR_DATA);
         onCreate(db);
		
	}
	
	   public long createSensorData(SensorDatapojo resultspojo) {

          SQLiteDatabase db = this.getWritableDatabase();
          Calendar cal = Calendar.getInstance();
          ContentValues values = new ContentValues();
          values.put(ACCERELOMETER, resultspojo.getAccelerometer());
          values.put(MAGNOMETER, resultspojo.getMagnometer());
          values.put(GYROSCOPE,resultspojo.getGyroscope());
          values.put(IRTEMPERATURE,resultspojo.getIRTemperature());
          values.put(AMBTEMPERATURE, resultspojo.getAMBTemperature());
          values.put(HUMIDITY,resultspojo.getHumidity());
          values.put(BAROMETER,resultspojo.getBarometer());
          values.put(DATETIME,dateFormat.format(cal.getTime()).toString());
        
          long result_id = db.insert(TABLE_SENSOR_DATA, null, values);
          return result_id;
      }
	   
	   public ArrayList<SensorDatapojo> GetAllSensorData() {

		   ArrayList<SensorDatapojo> SensorDataList=new ArrayList<SensorDatapojo>();
          SQLiteDatabase db = this.getReadableDatabase();

          String selectQuery = "SELECT  * FROM " + TABLE_SENSOR_DATA + " ORDER BY "+ID+ " ASC";
          Cursor c = db.rawQuery(selectQuery, null);

          if (c != null) {
             for(int i=0;i<c.getCount();i++)
             {
                c.moveToPosition(i);
                SensorDatapojo rp = new SensorDatapojo();
                rp.setAccelerometer(c.getString(c.getColumnIndex(ACCERELOMETER)));
                rp.setBarometer(c.getString(c.getColumnIndex(BAROMETER)));
                rp.setGyroscope(c.getString(c.getColumnIndex(GYROSCOPE)));
                rp.setHumidity(c.getString(c.getColumnIndex(HUMIDITY)));
                rp.setIRTemperature(c.getString(c.getColumnIndex(IRTEMPERATURE)));
                rp.setAMBTemperature(c.getString(c.getColumnIndex(AMBTEMPERATURE)));
                rp.setDateTime(c.getString(c.getColumnIndex(DATETIME)));
                SensorDataList.add(rp);
             }
          }
          return SensorDataList;
      }

	

}
