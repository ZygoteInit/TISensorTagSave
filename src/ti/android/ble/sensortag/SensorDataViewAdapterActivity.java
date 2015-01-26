package ti.android.ble.sensortag;

import java.util.ArrayList;

import ti.android.sqlite.SensorDatapojo;
import ti.android.sqlite.SqliteopenHelper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SensorDataViewAdapterActivity extends BaseAdapter{

	Context ctx;
	ArrayList<SensorDatapojo> listviewdata= new ArrayList<SensorDatapojo>();
	SqliteopenHelper db;
	
     public SensorDataViewAdapterActivity(SensorDataViewActivity ctx,ArrayList<SensorDatapojo> data) {
		// TODO Auto-generated constructor stub
    	 
    	 this.ctx=ctx;
    	 this.listviewdata=data;
    	 this.db= new SqliteopenHelper(this.ctx);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listviewdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		 final Holder mHolder;
	        int imgpos;
	        LayoutInflater layoutInflater;
	        if (convertView == null) {
	            layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = layoutInflater.inflate(R.layout.sensordatalistviewrow, null);
	            mHolder = new Holder();
	           // mHolder.txt_magnometer = (TextView) convertView.findViewById(R.id.textmagnometer);
	           // mHolder.txt_accelerometer = (TextView) convertView.findViewById(R.id.textaccerelometer);
	           // mHolder.txt_gyroscope = (TextView) convertView.findViewById(R.id.textgyroscope);
	            mHolder.txt_irtemperature = (TextView) convertView.findViewById(R.id.textirtemp);
	            mHolder.txt_ambtemperature = (TextView) convertView.findViewById(R.id.textambtemp);
	            mHolder.txt_humidity = (TextView) convertView.findViewById(R.id.texthumidity);
	            mHolder.txt_barometer = (TextView) convertView.findViewById(R.id.textbarometer);
	            mHolder.txt_datetime = (TextView) convertView.findViewById(R.id.textdatetime);
	            convertView.setTag(mHolder);
	        } else {
	            mHolder = (Holder) convertView.getTag();
	        }
	        
	        // mHolder.txt_accelerometer.setText(listviewdata.get(position).getAccelerometer());
	         mHolder.txt_barometer.setText(listviewdata.get(position).getBarometer());
	         mHolder.txt_humidity.setText(listviewdata.get(position).getHumidity());
	         mHolder.txt_irtemperature.setText(listviewdata.get(position).getIRTemperature());
	         mHolder.txt_ambtemperature.setText(listviewdata.get(position).getAMBTemperature());
	         //mHolder.txt_magnometer.setText(listviewdata.get(position).getMagnometer());
	         mHolder.txt_datetime.setText(listviewdata.get(position).getDateTime());
	        // mHolder.txt_gyroscope.setText(listviewdata.get(position).getGyroscope());
		return convertView;
	}
	
	public class Holder {
//        TextView txt_magnometer;
//        TextView txt_accelerometer;
//        TextView txt_gyroscope;
        TextView txt_irtemperature;
        TextView txt_ambtemperature;
        TextView txt_humidity;
        TextView txt_barometer;
        TextView txt_datetime;
        

    }


}

