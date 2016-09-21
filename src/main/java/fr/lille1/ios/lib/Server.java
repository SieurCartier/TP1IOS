package fr.lille1.ios.lib;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.HashSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Server implements fr.lille1.ios.api.Service {

	private boolean running = true;
	private BundleContext context;

	public Server(BundleContext context) {
		this.context = context;
	}

	public void startScan(String directory) {
		try {
			File f = new File(directory);
			HashSet<File> jars = new HashSet<File>();
			if (!f.isDirectory())
				throw new NotDirectoryException(directory);

			while (running) {
				File[] inDir = f.listFiles(new JarFileFilter());

				for (File cf : inDir) {
					if (jars.add(cf)) {
						context.installBundle(cf.getAbsolutePath());
					}
				}
			}
		} catch (NotDirectoryException e) {
			System.out.println("Le répertoire : " + directory + "n'est pas un dossier");
		} catch (BundleException e) {
			e.printStackTrace();
		}

	}

}
