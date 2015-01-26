package ti.android.ble.sensortag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ti.android.sqlite.SensorDatapojo;
import ti.android.sqlite.SqliteopenHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SensorDataViewActivity extends Activity {
	
	ListView list;
	ProgressDialog pDialog;
	SensorDataViewAdapterActivity adapter;
	SqliteopenHelper db;
	 ArrayList<SensorDatapojo> sensorData;
	Button btnsavetofile;
	String extStorageDirectory = null;
	private static String TAG = "SAVE_FILE_TO_SD";
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.sensordatalistview);
	        SensorDatapojo sensordata= new SensorDatapojo();
	        
	        btnsavetofile=(Button)findViewById(R.id.btnsavetofile);
	        btnsavetofile.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					new ExportTOFile().execute();
				}
				
				
			});
	        db= new SqliteopenHelper(getApplicationContext());
//	        for(int i=0;i<10;i++)
//	        {
//	        	sensordata=new SensorDatapojo();
//	        	sensordata.setAccelerometer("+1190.5");
//	        	sensordata.setAMBTemperature("+1190.5");
//	        	sensordata.setBarometer("+1190.5");
//	        	sensordata.setGyroscope("+1190.5");
//	        	sensordata.setHumidity("+1190.5");
//	        	sensordata.setIRTemperature("+1190.5");
//	        	sensordata.setMagnometer("+1190.5");
//	        	
//	        	db.createSensorData(sensordata);
//	        }
	        
	        list= (ListView)findViewById(R.id.listView1);
	       
	        
	      
	        
	        new GetSensorDataFromDB().execute();
	        
	 }
	 
	 private class GetSensorDataFromDB extends AsyncTask<Void, Void, Void> {

	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            pDialog = new ProgressDialog(SensorDataViewActivity.this);
	            pDialog.setMessage("Fetching Scores...");
	            pDialog.setCancelable(false);
	            pDialog.show();

	        }

	        @Override
	        protected Void doInBackground(Void... params) {

	        	SensorDataViewActivity.this.sensorData = new ArrayList<SensorDatapojo>();
	        	SensorDataViewActivity.this.sensorData = db.GetAllSensorData();
	        	SensorDataViewActivity.this.adapter=  new SensorDataViewAdapterActivity(SensorDataViewActivity.this,  SensorDataViewActivity.this.sensorData);
                
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            // Dismiss the progress dialog
	            if (pDialog.isShowing())
	                pDialog.dismiss();
	            if(SensorDataViewActivity.this.adapter.getCount()>0)
	            	SensorDataViewActivity.this.list.setAdapter(SensorDataViewActivity.this.adapter);
	            else
	            {
	               
	            }


	        }
	    }
	 
	 private class ExportTOFile extends AsyncTask<Void,Void,Void>{

		 @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            pDialog = new ProgressDialog(SensorDataViewActivity.this);
	            pDialog.setMessage("Exporting Data...");
	            pDialog.setCancelable(false);
	            pDialog.show();

	        }

	        @Override
	        protected Void doInBackground(Void... params) {

	        	SensorDataViewActivity.this.sensorData = new ArrayList<SensorDatapojo>();
	        	SensorDataViewActivity.this.sensorData = db.GetAllSensorData();
	        	SensorDataViewActivity.this.adapter=  new SensorDataViewAdapterActivity(SensorDataViewActivity.this,  SensorDataViewActivity.this.sensorData);
             
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            // Dismiss the progress dialog
	            if (pDialog.isShowing())
	                pDialog.dismiss();
	            if(SensorDataViewActivity.this.adapter.getCount()>0)
	            	ExportToFile(SensorDataViewActivity.this.sensorData);
	            else
	            {
	               
	            }


	        }
		 
	 }
	 
	 public void ExportToFile(ArrayList<SensorDatapojo> data)
	 {
		 String texttosave="";
			if(sensorData!=null)
			{
			
				texttosave+="|AMBTemp | IRTemp  | Humidity  | BAROMETER  | TIME  |";
				texttosave+="||+++++++++++++++++++++++++++++++++++++++++++++++++++||";
			for(int i=0;i<data.size();i++)
			{
				texttosave+= sensorData.get(i).getAMBTemperature()+ " |  "+sensorData.get(i).getIRTemperature()+
						" |  "+sensorData.get(i).getHumidity()+" |  "+sensorData.get(i).getBarometer()+ " |  "+sensorData.get(i).getDateTime()+ " || ";
				//texttosave+="\n";
			 
			}
 		
     	//get the external storage directory
 		extStorageDirectory = Environment.getExternalStorageDirectory().toString();
 		Log.i(TAG, "External Directory: " + extStorageDirectory);
 		
     	//save operation
     	try
     	{
     		//make a directory for the file
     		File myDirectory = new File("/sdcard/Save_to_File/mySaveFile.txt");
     		myDirectory.mkdirs();
     		Log.i(TAG, "Made the directory");
     		
     		//make the files 
     		Log.i(TAG, "Directory is: " + extStorageDirectory + "/Save_to_File/");
     		
     		File mySaveFile = new File(myDirectory,"sensordata.txt");
     		mySaveFile.createNewFile();
     		
     		FileOutputStream fOut = new FileOutputStream(mySaveFile);
             OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
     		
             myOutWriter.append(texttosave);
             
             myOutWriter.close();
             fOut.close();
             
             Log.i(TAG, "File Successfully written to SD card");
     	}
     	catch(IOException e)
     	{
     		Log.i(TAG, "FAILURE WRITTING TO SD: " + e.getMessage().toString());
     	}
		}
			else
			{
				Toast.makeText(getApplicationContext(), "No data in sqlite", Toast.LENGTH_LONG).show();
			}
	 }

}