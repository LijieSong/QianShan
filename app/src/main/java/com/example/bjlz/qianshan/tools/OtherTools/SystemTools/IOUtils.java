package com.example.bjlz.qianshan.tools.OtherTools.SystemTools;

import com.example.bjlz.qianshan.tools.OtherTools.CatchTools.LogUtils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtils.error(e.getMessage());
			}
		}
		return true;
	}
}