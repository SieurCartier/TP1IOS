package fr.lille1.ios.lib;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class DirectoryChecker extends Thread {

	private String directory;
	private BundleContext context;
	private Hashtable<File, Long> installedJars = new Hashtable<File, Long>();

	public DirectoryChecker(String directory, BundleContext context) {
		super();
		this.directory = directory;
		this.context = context;
	}

	@Override
	public void run() {
		try {
			File f = new File(directory);

			if (!f.isDirectory())
				throw new NotDirectoryException(directory);

			while (!isInterrupted()) {
				File[] inDir = f.listFiles(new JarFileFilter());

				for (File currentFile : inDir) {
					Long lastModified = currentFile.lastModified();
					if (!((installedJars.contains(currentFile)) && (installedJars.get(currentFile) >= lastModified))) {
						// Si le bundle est inconnu ou sa date de MAJ est
						// dépassée
						installedJars.put(currentFile, lastModified);
						context.installBundle(currentFile.toURI().toString());
					}
				}

				// Ensemble des jars supprimés entre le dernier check et
				// maintenant
				HashSet<File> deleted = new HashSet<File>();
				deleted.addAll(installedJars.keySet());
				deleted.removeAll(Arrays.asList(inDir));
				
				for (File deletedFile : deleted) {
					installedJars.remove(deletedFile);
					Bundle b = context.getBundle(deletedFile.toURI().toString());
					b.stop();
					b.uninstall();
				}

				sleep(1000);

			}
		} catch (NotDirectoryException e) {
			System.out.println("Le répertoire : " + directory + "n'est pas un dossier");
		} catch (BundleException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		}

	}

}
