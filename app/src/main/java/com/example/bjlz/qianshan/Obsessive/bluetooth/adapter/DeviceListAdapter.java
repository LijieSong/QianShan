package com.example.bjlz.qianshan.Obsessive.bluetooth.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bjlz.qianshan.R;

import java.util.List;

public class DeviceListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	@SuppressWarnings("unused")
	private Context mContext = null;
	private List<BluetoothDevice> devices = null;
	
	public DeviceListAdapter(Context context, List<BluetoothDevice> devices){
		this.mContext = context;
		this.devices = devices;
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void setDevices(List<BluetoothDevice> devices) {
		this.devices = devices;
	}

	@Override
	public int getCount() {
		int count = 0;
        if (null != devices){
            count = devices.size();
        }
        return count;
	}

	@Override
	public BluetoothDevice getItem(int position) {
		BluetoothDevice device = null;
        if (null != devices){
            device = devices.get(position);
        }
        return device;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
         Log.v("MyListViewBase", "getView " + position + " " + convertView);
         if (convertView == null) {
             convertView = mInflater.inflate(R.layout.device_item,null);
             holder = new ViewHolder();
             holder.name = (TextView) convertView.findViewById(R.id.name);
             holder.address = (TextView) convertView.findViewById(R.id.address);
             convertView.setTag(holder);          
         }else{
             holder = (ViewHolder)convertView.getTag();                  
         }
         
         BluetoothDevice device = getItem(position);
         if (null != device){
        	 holder.name.setText(device.getName());
             holder.address.setText(device.getAddress());
         }
         return convertView;
	}

	public final class ViewHolder{
	    public TextView name;
	    public TextView address;
    }
}
