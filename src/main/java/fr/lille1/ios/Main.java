package fr.lille1.ios;

import fr.lille1.ios.api.Service;
import fr.lille1.ios.lib.DirectoryChecker;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Service s = new DirectoryChecker("/home/m2miage/lemaireg/auto_deploy", null);
		s.start();
		// sAck = context.registerService(Service.class.getName(), s, null);
		System.out.println("Bundle starts...");

		Thread.sleep(30000);

		s.interrupt();

		System.out.println("Bundle stops...");

	}

}
