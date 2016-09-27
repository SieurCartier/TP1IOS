package fr.lille1.ios;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import fr.lille1.ios.api.Service;
import fr.lille1.ios.lib.DirectoryChecker;

public class Activator implements BundleActivator {

	private ServiceRegistration<?> sAck;

	public void start(BundleContext context) throws Exception {

		// Change /home/m2miage/lemaireg/auto_deploy with the directory you want to be watched
		
		// Service s = new DirectoryChecker("C:\\Users\\gasto\\Desktop\\test",
		// context);
		
		Service s = new DirectoryChecker("/home/m2miage/lemaireg/auto_deploy", context);
		s.start();
		sAck = context.registerService(Service.class.getName(), s, null);
		System.out.println("Auto deploy starts...");
	}

	public void stop(BundleContext context) throws Exception {
		sAck.unregister();
		System.out.println("Auto deploy stops...");
	}

}
