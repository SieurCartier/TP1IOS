package fr.lille1.ios;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import fr.lille1.ios.api.Service;
import fr.lille1.ios.lib.DirectoryChecker;

public class Activator implements BundleActivator {

	private ServiceRegistration<?> sAck;

	public void start(BundleContext context) throws Exception {
		Service s = new DirectoryChecker("/home/m2miage/lemaireg/auto_deploy", context);
		s.start();
		sAck = context.registerService(Service.class.getName(), s, null);
		System.out.println("Bundle starts...");
	}

	public void stop(BundleContext context) throws Exception {
		sAck.unregister();
		System.out.println("Bundle stops...");
	}

}
