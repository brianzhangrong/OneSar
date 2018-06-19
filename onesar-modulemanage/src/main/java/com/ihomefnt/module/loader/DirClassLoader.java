package com.ihomefnt.module.loader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.google.common.collect.Lists;

public class DirClassLoader {
	JarClassLoader classLoader;

	public DirClassLoader(String path) throws Exception {
		classLoader = new JarClassLoader(selectURL(path));
	}

	public URL[] getUrls() {

		return classLoader.getURLs();
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return classLoader.loadClass(name);
	}

	private URL[] selectURL(String path) throws Exception {
		File dir = new File(path);
		File[] files = dir.listFiles();
		if(files==null||files.length==0){
			throw new Exception("lib dir contains none file");
		}
		List<URL> urlList = Lists.newArrayList();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().endsWith("jar")) {
				URL url = files[i].toURL();
				urlList.add(url);
			}
		}
		return urlList.toArray(new URL[] {});
	}

	class JarClassLoader extends URLClassLoader {

		public JarClassLoader(URL[] urls) {
			super(urls);
		}

	}

}
