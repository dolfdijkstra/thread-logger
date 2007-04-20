package com.fatwire.cs.logging.log4j;

import junit.framework.TestCase;

import org.apache.commons.logging.LogFactory;

public class ThreadAppenderTest extends TestCase {

	public void testSubAppendLoggingEvent() {
		Runnable r = new Runnable() {

			public void run() {
				for (int i = 0; i < 5; i++) {
					LogFactory.getLog(getClass()).error("Hello World");
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				

			}

		};
		Thread[] t = new Thread[5];
		for (int i=0;i<t.length;i++){
			t[i]= new Thread(r,"t-" +i);
			t[i].start();
		}
		for (int i=0;i<t.length;i++){
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
