package com.example.bjlz.qianshan.Obsessive.bluetooth.utils;

import android.bluetooth.BluetoothGattCharacteristic;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class Utils {
	
	private static final String UNKNOWN = "UNKNOWN";

	private static HashMap<String, String> UUIDInstructions = new HashMap<String, String>();
	static {
		UUIDInstructions.put("00001800-0000-1000-8000-00805f9b34fb",
				"Generic Access");
		UUIDInstructions.put("00001801-0000-1000-8000-00805f9b34fb",
				"Generic Attribute");
		UUIDInstructions.put("00001802-0000-1000-8000-00805f9b34fb",
				"Immediate Alert");
		UUIDInstructions.put("00001803-0000-1000-8000-00805f9b34fb",
				"Link Loss");
		UUIDInstructions
				.put("00001804-0000-1000-8000-00805f9b34fb", "Tx Power");
		UUIDInstructions.put("00001805-0000-1000-8000-00805f9b34fb",
				"Current Time Service");
		UUIDInstructions.put("00001806-0000-1000-8000-00805f9b34fb",
				"Reference Time Update Service");
		UUIDInstructions.put("00001807-0000-1000-8000-00805f9b34fb",
				"Next DST Change Service");
		UUIDInstructions.put("00001808-0000-1000-8000-00805f9b34fb", "Glucose");
		UUIDInstructions.put("00001809-0000-1000-8000-00805f9b34fb",
				"Health Thermometer");
		UUIDInstructions.put("0000180a-0000-1000-8000-00805f9b34fb",
				"Device Information");
		UUIDInstructions.put("0000180b-0000-1000-8000-00805f9b34fb",
				"Network Availability Service");
		UUIDInstructions
				.put("0000180c-0000-1000-8000-00805f9b34fb", "Watchdog");
		UUIDInstructions.put("0000180d-0000-1000-8000-00805f9b34fb",
				"Heart Rate");
		UUIDInstructions.put("0000180e-0000-1000-8000-00805f9b34fb",
				"Phone Alert Status Service");
		UUIDInstructions.put("0000180f-0000-1000-8000-00805f9b34fb",
				"Battery Service");
		UUIDInstructions.put("00001810-0000-1000-8000-00805f9b34fb",
				"Blood Pressure");
		UUIDInstructions.put("00001811-0000-1000-8000-00805f9b34fb",
				"Alert Notification Service");
		UUIDInstructions.put("00001812-0000-1000-8000-00805f9b34fb",
				"Human Interface Device");
		UUIDInstructions.put("00001813-0000-1000-8000-00805f9b34fb",
				"Scan Parameters");
		UUIDInstructions.put("00001814-0000-1000-8000-00805f9b34fb",
				"RUNNING SPEED AND CADENCE");
		UUIDInstructions.put("00001815-0000-1000-8000-00805f9b34fb",
				"Automation IO");
		UUIDInstructions.put("00001816-0000-1000-8000-00805f9b34fb",
				"Cycling Speed and Cadence");
		UUIDInstructions.put("00001817-0000-1000-8000-00805f9b34fb",
				"Pulse Oximeter");
		UUIDInstructions.put("00001818-0000-1000-8000-00805f9b34fb",
				"Cycling Power Service");
		UUIDInstructions.put("00001819-0000-1000-8000-00805f9b34fb",
				"Location and Navigation Service");
		UUIDInstructions.put("0000181a-0000-1000-8000-00805f9b34fb",
				"Continous Glucose Measurement Service");
		UUIDInstructions.put("00002a00-0000-1000-8000-00805f9b34fb",
				"Device Name");
		UUIDInstructions.put("00002a01-0000-1000-8000-00805f9b34fb",
				"Appearance");
		UUIDInstructions.put("00002a02-0000-1000-8000-00805f9b34fb",
				"Peripheral Privacy Flag");
		UUIDInstructions.put("00002a03-0000-1000-8000-00805f9b34fb",
				"Reconnection Address");
		UUIDInstructions.put("00002a04-0000-1000-8000-00805f9b34fb",
				"Peripheral Preferred Connection Parameters");
		UUIDInstructions.put("00002a05-0000-1000-8000-00805f9b34fb",
				"Service Changed");
		UUIDInstructions.put("00002a06-0000-1000-8000-00805f9b34fb",
				"Alert Level");
		UUIDInstructions.put("00002a07-0000-1000-8000-00805f9b34fb",
				"Tx Power Level");
		UUIDInstructions.put("00002a08-0000-1000-8000-00805f9b34fb",
				"Date Time");
		UUIDInstructions.put("00002a09-0000-1000-8000-00805f9b34fb",
				"Day of Week");
		UUIDInstructions.put("00002a0a-0000-1000-8000-00805f9b34fb",
				"Day Date Time");
		UUIDInstructions.put("00002a0b-0000-1000-8000-00805f9b34fb",
				"Exact Time 100");
		UUIDInstructions.put("00002a0c-0000-1000-8000-00805f9b34fb",
				"Exact Time 256");
		UUIDInstructions.put("00002a0d-0000-1000-8000-00805f9b34fb",
				"DST Offset");
		UUIDInstructions.put("00002a0e-0000-1000-8000-00805f9b34fb",
				"Time Zone");
		UUIDInstructions.put("00002a1f-0000-1000-8000-00805f9b34fb",
				"Local Time Information");
		UUIDInstructions.put("00002a10-0000-1000-8000-00805f9b34fb",
				"Secondary Time Zone");
		UUIDInstructions.put("00002a11-0000-1000-8000-00805f9b34fb",
				"Time with DST");
		UUIDInstructions.put("00002a12-0000-1000-8000-00805f9b34fb",
				"Time Accuracy");
		UUIDInstructions.put("00002a13-0000-1000-8000-00805f9b34fb",
				"Time Source");
		UUIDInstructions.put("00002a14-0000-1000-8000-00805f9b34fb",
				"Reference Time Information");
		UUIDInstructions.put("00002a15-0000-1000-8000-00805f9b34fb",
				"Time Broadcast");
		UUIDInstructions.put("00002a16-0000-1000-8000-00805f9b34fb",
				"Time Update Control Point");
		UUIDInstructions.put("00002a17-0000-1000-8000-00805f9b34fb",
				"Time Update State");
		UUIDInstructions.put("00002a18-0000-1000-8000-00805f9b34fb",
				"Glucose Measurement");
		UUIDInstructions.put("00002a19-0000-1000-8000-00805f9b34fb",
				"Battery Level");
		UUIDInstructions.put("00002a1a-0000-1000-8000-00805f9b34fb",
				"Battery Power State");
		UUIDInstructions.put("00002a1b-0000-1000-8000-00805f9b34fb",
				"Battery Level State");
		UUIDInstructions.put("00002a1c-0000-1000-8000-00805f9b34fb",
				"Temperature Measurement");
		UUIDInstructions.put("00002a1d-0000-1000-8000-00805f9b34fb",
				"Temperature Type");
		UUIDInstructions.put("00002a1e-0000-1000-8000-00805f9b34fb",
				"Intermediate Temperature");
		UUIDInstructions.put("00002a1f-0000-1000-8000-00805f9b34fb",
				"Temperature in Celsius");
		UUIDInstructions.put("00002a20-0000-1000-8000-00805f9b34fb",
				"Temperature in Fahrenheit");
		UUIDInstructions.put("00002a21-0000-1000-8000-00805f9b34fb",
				"Measurement Interval");
		UUIDInstructions.put("00002a22-0000-1000-8000-00805f9b34fb",
				"Boot Keyboard Input Report");
		UUIDInstructions.put("00002a23-0000-1000-8000-00805f9b34fb",
				"System ID");
		UUIDInstructions.put("00002a24-0000-1000-8000-00805f9b34fb",
				"Model Number String");
		UUIDInstructions.put("00002a25-0000-1000-8000-00805f9b34fb",
				"Serial Number String");
		UUIDInstructions.put("00002a26-0000-1000-8000-00805f9b34fb",
				"Firmware Revision String");
		UUIDInstructions.put("00002a27-0000-1000-8000-00805f9b34fb",
				"Hardware Revision String");
		UUIDInstructions.put("00002a28-0000-1000-8000-00805f9b34fb",
				"Software Revision String");
		UUIDInstructions.put("00002a29-0000-1000-8000-00805f9b34fb",
				"Manufacturer Name String");
		UUIDInstructions.put("00002a2a-0000-1000-8000-00805f9b34fb",
				"IEEE 11073-20601 Regulatory Certification Data List");
		UUIDInstructions.put("00002a2b-0000-1000-8000-00805f9b34fb",
				"Current Time");
		UUIDInstructions.put("00002a2c-0000-1000-8000-00805f9b34fb",
				"Elevation");
		UUIDInstructions
				.put("00002a2d-0000-1000-8000-00805f9b34fb", "Latitude");
		UUIDInstructions.put("00002a2e-0000-1000-8000-00805f9b34fb",
				"Longitude");
		UUIDInstructions.put("00002a2f-0000-1000-8000-00805f9b34fb",
				"Position 2D");
		UUIDInstructions.put("00002a30-0000-1000-8000-00805f9b34fb",
				"Position 3D");
		UUIDInstructions.put("00002a31-0000-1000-8000-00805f9b34fb",
				"Scan Refresh");
		UUIDInstructions.put("00002a32-0000-1000-8000-00805f9b34fb",
				"Boot Keyboard Output Report");
		UUIDInstructions.put("00002a33-0000-1000-8000-00805f9b34fb",
				"Boot Mouse Input Report");
		UUIDInstructions.put("00002a34-0000-1000-8000-00805f9b34fb",
				"Glucose Measurement Context");
		UUIDInstructions.put("00002a35-0000-1000-8000-00805f9b34fb",
				"Blood Pressure Measurement");
		UUIDInstructions.put("00002a36-0000-1000-8000-00805f9b34fb",
				"Intermediate Cuff Pressure");
		UUIDInstructions.put("00002a37-0000-1000-8000-00805f9b34fb",
				"Heart Rate Measurement");
		UUIDInstructions.put("00002a38-0000-1000-8000-00805f9b34fb",
				"Body Sensor Location");
		UUIDInstructions.put("00002a39-0000-1000-8000-00805f9b34fb",
				"Heart Rate Control Point");
		UUIDInstructions.put("00002a3a-0000-1000-8000-00805f9b34fb",
				"Removable");
		UUIDInstructions.put("00002a3b-0000-1000-8000-00805f9b34fb",
				"Service Required");
		UUIDInstructions.put("00002a3c-0000-1000-8000-00805f9b34fb",
				"Scientific Temperature in Celsius");
		UUIDInstructions.put("00002a3d-0000-1000-8000-00805f9b34fb", "String");
		UUIDInstructions.put("00002a3e-0000-1000-8000-00805f9b34fb",
				"Network Availability");
		UUIDInstructions.put("00002a3g-0000-1000-8000-00805f9b34fb",
				"Alert Status");
		UUIDInstructions.put("00002a40-0000-1000-8000-00805f9b34fb",
				"Ringer Control Point");
		UUIDInstructions.put("00002a41-0000-1000-8000-00805f9b34fb",
				"Ringer Setting");
		UUIDInstructions.put("00002a42-0000-1000-8000-00805f9b34fb",
				"Alert Category ID Bit Mask");
		UUIDInstructions.put("00002a43-0000-1000-8000-00805f9b34fb",
				"Alert Category ID");
		UUIDInstructions.put("00002a44-0000-1000-8000-00805f9b34fb",
				"Alert Notification Control Point");
		UUIDInstructions.put("00002a45-0000-1000-8000-00805f9b34fb",
				"Unread Alert Status");
		UUIDInstructions.put("00002a46-0000-1000-8000-00805f9b34fb",
				"New Alert");
		UUIDInstructions.put("00002a47-0000-1000-8000-00805f9b34fb",
				"Supported New Alert Category");
		UUIDInstructions.put("00002a48-0000-1000-8000-00805f9b34fb",
				"Supported Unread Alert Category");
		UUIDInstructions.put("00002a49-0000-1000-8000-00805f9b34fb",
				"Blood Pressure Feature");
		UUIDInstructions.put("00002a4a-0000-1000-8000-00805f9b34fb",
				"HID Information");
		UUIDInstructions.put("00002a4b-0000-1000-8000-00805f9b34fb",
				"Report Map");
		UUIDInstructions.put("00002a4c-0000-1000-8000-00805f9b34fb",
				"HID Control Point");
		UUIDInstructions.put("00002a4d-0000-1000-8000-00805f9b34fb", "Report");
		UUIDInstructions.put("00002a4e-0000-1000-8000-00805f9b34fb",
				"Protocol Mode");
		UUIDInstructions.put("00002a4g-0000-1000-8000-00805f9b34fb",
				"Scan Interval Window");
		UUIDInstructions.put("00002a50-0000-1000-8000-00805f9b34fb", "PnP ID");
		UUIDInstructions.put("00002a51-0000-1000-8000-00805f9b34fb",
				"Glucose Features");
		UUIDInstructions.put("00002a52-0000-1000-8000-00805f9b34fb",
				"Record Access Control Point");
		UUIDInstructions.put("00002a53-0000-1000-8000-00805f9b34fb",
				"RSC Measurement");
		UUIDInstructions.put("00002a54-0000-1000-8000-00805f9b34fb",
				"RSC Feature");
		UUIDInstructions.put("00002a55-0000-1000-8000-00805f9b34fb",
				"SC Control Point");
		UUIDInstructions.put("00002a56-0000-1000-8000-00805f9b34fb",
				"Digital Input");
		UUIDInstructions.put("00002a57-0000-1000-8000-00805f9b34fb",
				"Digital Output");
		UUIDInstructions.put("00002a58-0000-1000-8000-00805f9b34fb",
				"Analog Input");
		UUIDInstructions.put("00002a59-0000-1000-8000-00805f9b34fb",
				"Analog Output");
		UUIDInstructions.put("00002a5a-0000-1000-8000-00805f9b34fb",
				"Aggregate Input");
		UUIDInstructions.put("00002a5b-0000-1000-8000-00805f9b34fb",
				"CSC Measurement");
		UUIDInstructions.put("00002a5c-0000-1000-8000-00805f9b34fb",
				"CSC Feature");
		UUIDInstructions.put("00002a5d-0000-1000-8000-00805f9b34fb",
				"Sensor Location");
		UUIDInstructions.put("00002a5e-0000-1000-8000-00805f9b34fb",
				"Pulse Oximetry Spot-check Measurement");
		UUIDInstructions.put("00002a5f-0000-1000-8000-00805f9b34fb",
				"Pulse Oximetry Continuous Measurement");
		UUIDInstructions.put("00002a60-0000-1000-8000-00805f9b34fb",
				"Pulse Oximetry Pulsatile Event");
		UUIDInstructions.put("00002a61-0000-1000-8000-00805f9b34fb",
				"Pulse Oximetry Features");
		UUIDInstructions.put("00002a62-0000-1000-8000-00805f9b34fb",
				"Pulse Oximetry Control Point");
		UUIDInstructions.put("00002a63-0000-1000-8000-00805f9b34fb",
				"Cycling Power Measurement Characteristic");
		UUIDInstructions.put("00002a64-0000-1000-8000-00805f9b34fb",
				"Cycling Power Vector Characteristic");
		UUIDInstructions.put("00002a65-0000-1000-8000-00805f9b34fb",
				"Cycling Power Feature Characteristic");
		UUIDInstructions.put("00002a66-0000-1000-8000-00805f9b34fb",
				"Cycling Power Control Point Characteristic");
		UUIDInstructions.put("00002a67-0000-1000-8000-00805f9b34fb",
				"Location and Speed Characteristic");
		UUIDInstructions.put("00002a68-0000-1000-8000-00805f9b34fb",
				"Navigation Characteristic");
		UUIDInstructions.put("00002a69-0000-1000-8000-00805f9b34fb",
				"Position Quality Characteristic");
		UUIDInstructions.put("00002a6a-0000-1000-8000-00805f9b34fb",
				"LN Feature Characteristic");
		UUIDInstructions.put("00002a6b-0000-1000-8000-00805f9b34fb",
				"LN Control Point Characteristic");
		UUIDInstructions.put("00002a6c-0000-1000-8000-00805f9b34fb",
				"CGM Measurement Characteristic");
		UUIDInstructions.put("00002a6d-0000-1000-8000-00805f9b34fb",
				"CGM Features Characteristic");
		UUIDInstructions.put("00002a6e-0000-1000-8000-00805f9b34fb",
				"CGM Status Characteristic");
		UUIDInstructions.put("00002a6f-0000-1000-8000-00805f9b34fb",
				"CGM Session Start Time Characteristic");
		UUIDInstructions.put("00002a70-0000-1000-8000-00805f9b34fb",
				"Application Security Point Characteristic");
		UUIDInstructions.put("00002a71-0000-1000-8000-00805f9b34fb",
				"CGM Specific Ops Control Point Characteristic");
	}
	
	public static final String PROPERTY_SIGNED_WRITE = "authenticatedSignedWrites";
	public static final String PROPERTY_BROADCAST = "broadcast";
	public static final String PROPERTY_EXTENDED_PROPS = "extendedProperties";
	public static final String PROPERTY_INDICATE = "indicate";
	public static final String PROPERTY_NOTIFY = "notify";
	public static final String PROPERTY_READ = "read";
	public static final String PROPERTY_WRITE = "write";
	public static final String PROPERTY_WRITE_NO_RESPONSE = "writeWithoutResponse";
	
	private static HashMap<Integer, String> propertys = new HashMap<Integer, String>();
	static {
		propertys.put(1, PROPERTY_BROADCAST);
		propertys.put(2, PROPERTY_READ);
		propertys.put(4, PROPERTY_WRITE_NO_RESPONSE);
		propertys.put(8, PROPERTY_WRITE);
		propertys.put(16, PROPERTY_NOTIFY);
		propertys.put(32, PROPERTY_INDICATE);
		propertys.put(64, PROPERTY_SIGNED_WRITE);
		propertys.put(128, PROPERTY_EXTENDED_PROPS);
	}

	public static String lookup(UUID uuid) {
		String instruction = UUIDInstructions.get(uuid.toString());
		return instruction == null ? UNKNOWN : instruction;
	}
	
	public static String lookup(int propertySum, int property) {
		if ((propertySum & property) == property) {
			String propertyName = propertys.get(property);
			return propertyName == null ? null : propertyName;
		} else {
			return null;
		}
	}
	
	public static JSONArray decodeProperty(int property) {
		JSONArray properties = new JSONArray();
		String strProperty = null;
		if ((strProperty = lookup(property, BluetoothGattCharacteristic.PROPERTY_BROADCAST)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_READ)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_WRITE)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_NOTIFY)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_INDICATE)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE)) != null) {
			properties.put(strProperty);
		}
		if ((strProperty = lookup(property,
				BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS)) != null) {
			properties.put(strProperty);
		}
		return properties;
	}
	// byte转十六进制字符串
	public static String bytes2HexString(byte[] bytes) {
		String ret = "";
		for (byte aByte : bytes) {
			String hex = Integer.toHexString(aByte & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase(Locale.CHINA);
		}
		return ret;
	}

	/**
	 * 十六进制的字节数组转换成byte数组
	 * @param data
	 * @return
     */
	public static byte[] HexCommandtoByte(byte[] data) {
		if (data == null) {
			return null;
		}
		int nLength = data.length;

		String strTemString = new String(data, 0, nLength);
		String[] strings = strTemString.split(" ");
		nLength = strings.length;
		data = new byte[nLength];
		for (int i = 0; i < nLength; i++) {
			if (strings[i].length() != 2) {
				data[i] = 00;
				continue;
			}
			try {
				data[i] = (byte) Integer.parseInt(strings[i], 16);
			} catch (Exception e) {
				data[i] = 00;
				continue;
			}
		}

		return data;
	}

	/** * 将16进制的字符串转换为字节数组 *
	 * @param message * @return 字节数组 */
	public static byte[] getHexBytes(String message) {
		int len = message.length() / 2;
		char[] chars = message.toCharArray();
		String[] hexStr = new String[len];
		byte[] bytes = new byte[len];
		for (int i = 0, j = 0; j < len; i += 2, j++) {
			hexStr[j] = "" + chars[i] + chars[i + 1];
			bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
		}
		return bytes;
	}

	/**
	 * 蓝牙写入数据
	 * @param value
	 * @return
     */
	public static byte[] wirteCharacteristic(String value){
		 byte[] tmp_byte = null;
		 byte[] write_msg_byte =null;
		if (0 == value.length())
			return null;
		tmp_byte = value.getBytes();
		write_msg_byte = new byte[tmp_byte.length / 2 + tmp_byte.length % 2];
		for (int i = 0; i < tmp_byte.length; i++) {
			if ((tmp_byte[i] <= '9') && (tmp_byte[i] >= '0')) {
				if (0 == i % 2)
					write_msg_byte[i / 2] = (byte) (((tmp_byte[i] - '0') * 16) & 0xFF);
				else
					write_msg_byte[i / 2] |= (byte) ((tmp_byte[i] - '0') & 0xFF);
			} else {
				if (0 == i % 2)
					write_msg_byte[i / 2] = (byte) (((tmp_byte[i] - 'a' + 10) * 16) & 0xFF);
				else
					write_msg_byte[i / 2] |= (byte) ((tmp_byte[i] - 'a' + 10) & 0xFF);
			}
		}
		return write_msg_byte;
	}
}
