package com.example.administrator.zhixiao10.utils;

import android.os.Handler;

public class ThreadUtils {

	/**
	_ * ���������߳�(��������)
	 *
	 */
	public static void runInSubThread(Runnable r) {
		new Thread(r).start();
	}

	private static Handler handler = new Handler();

	/**
	 * ���������߳�(UI �߳� ���½���)
	 *
	 */
	public static void runInUiThread(Runnable r) {
		handler.post(r);// Message-->handler.sendMessage-->handleMessage()
						// ���߳�-->r.run();
	}
}
