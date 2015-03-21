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


	// Umer abbassi: This whole activity is for Viewing the SensorData stored in our DB by fetching data from DB and populating our layout
public class SensorDataViewActivity extends Activity {
	
	
 	// Umer abbassi: here declaring the variables for SensorDataViewActivity
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
	     	// Umer abbassi: here setting the layout file with activity, this layout canbe found in res>layout folder with "sensordatalistview" name
	        setContentView(R.layout.sensordatalistview);
	        
	     	// Umer abbassi: here again initializing the SensorDataPojo object for holding the result fetched from DB
	        SensorDatapojo sensordata= new SensorDatapojo();
	        
	        
	     	// Umer abbassi: here finding the Export button by it's id in layout
	        btnsavetofile=(Button)findViewById(R.id.btnsavetofile);
	        
	     	// Umer abbassi: here setting the Click event of export button
	        btnsavetofile.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				 	// Umer abbassi: here calling our new Asynchronous task to fetch Data from DB save to a file
					new ExportTOFile().execute();
				}
				
				
			});
	        
	     	// Umer abbassi: here creating the db object again
	        db= new SqliteopenHelper(getApplicationContext());
	        
//	        for(int i=0;i<10;i++)
//	        {
//	        	sensordata=new SensorDatapojo();
//	        	sensordata.setAccelerometer("+90");
//	        	sensordata.setAMBTemperature("+90");
//	        	sensordata.setBarometer("+90");
//	        	sensordata.setGyroscope("+90");
//	        	sensordata.setHumidity("+90");
//	        	sensordata.setIRTemperature("+90");
//	        	sensordata.setMagnometer("+90");
//	        	
//	        	db.createSensorData(sensordata);
//	        }
	        
	        
	     	// Umer abbassi: here finding the listview control in which the data would be populated
	        list= (ListView)findViewById(R.id.listView1);
	       
	        
	      
	        // 	// Umer abbassi: here calling the asynchronous task to fetch data from DB
	        new GetSensorDataFromDB().execute();
	        
	 }
	 
	
 	// Umer abbassi: This is asynchronous data fetching Asynchronous task
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

	        	
	         	// Umer abbassi: here initializing the ArrayList<SensorDataPojo> type which means it can hold n*n types of SensorDataPojo classes object
	        	SensorDataViewActivity.this.sensorData = new ArrayList<SensorDatapojo>();
	        	
	         	// Umer abbassi: here the DB call from get data
	        	SensorDataViewActivity.this.sensorData = db.GetAllSensorData();
	        	
	         	// Umer abbassi: here calling the adapter activity to populate the listview by giving it Data object and current activity context object as an argument
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
	            	
	             	// Umer abbassi: here now the adapter is filled with data and UI rows inflated with data now we will attach this adapter to our Listview in which rows will be seen
	            	SensorDataViewActivity.this.list.setAdapter(SensorDataViewActivity.this.adapter);
	            else
	            {
	               
	            }


	        }
	    }
	 
	 
	 	// Umer abbassi: here export functionality has been done
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

	         	// Umer abbassi: here same tasks as done in first Asyncrhonous task
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
	             	// Umer abbassi: here giving the exporttofile function data obejct for writing it in file
	            	ExportToFile(SensorDataViewActivity.this.sensorData);
	            else
	            {
	               
	            }


	        }
		 
	 }
	 
	 	// Umer abbassi: this is the function
	 
	 public void ExportToFile(ArrayList<SensorDatapojo> data)
	 {
		 
		 	// Umer abbassi: making string for rows in file
		 String texttosave="";
			if(sensorData!=null)
			{
			
				texttosave+="|AMBTemp °c | IRTemp °c  | Humidity %rH | BAROMETER hPA-m | TIME  |";
				//texttosave+="||+++++++++++++++++++++++++++++++++++++++++++++++++++||";
				texttosave+=System.lineSeparator();
				
			 	// Umer abbassi: here looping through the data object and concatenating the string one by one
			for(int i=0;i<data.size();i++)
			{
				texttosave+= sensorData.get(i).getAMBTemperature()+" |  "+sensorData.get(i).getIRTemperature()+
						" |  "+sensorData.get(i).getHumidity()+" |  "+sensorData.get(i).getBarometer()+ " |  "+sensorData.get(i).getDateTime()+ " || ";
				//texttosave+="\n";
				texttosave+=System.lineSeparator();
			 
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