package com.example.yishutansuo;

import com.lvrenyang.pos.IO;
import com.lvrenyang.utils.Utils;

public class ReadThread extends Thread{
	public boolean isRun = true;
	@Override
	public void run() {
		
		while (isRun) {
			byte[] buffer = new byte[10];
			int a= IO.ReadAND(buffer,0,10,1000);
			System.out.println(a+"===BT数据=====>"+ Utils.bytesToHexString(buffer,a));
			
		}
		
	}
	
	
}
