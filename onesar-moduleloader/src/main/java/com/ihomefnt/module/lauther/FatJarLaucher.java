package com.ihomefnt.module.lauther;

import java.util.List;

import org.springframework.boot.loader.ExecutableArchiveLauncher;
import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.Archive.Entry;
import org.springframework.boot.loader.archive.Archive.EntryFilter;

public class FatJarLaucher extends ExecutableArchiveLauncher {

	static final String BOOT_INF_CLASSES = "BOOT-INF/classes/";

	static final String BOOT_INF_LIB = "BOOT-INF/lib/";

	Archive archive;

	public FatJarLaucher(Archive archive) {
		super(archive);
		this.archive = archive;
	}

	@Override
	protected boolean isNestedArchive(Archive.Entry entry) {
		if (entry.isDirectory()) {
			return entry.getName().equals(BOOT_INF_CLASSES);
		}
		return entry.getName().startsWith(BOOT_INF_LIB);
	}

	public void laucher(String[] args) throws Exception {
		launch(args);
	}

	public List<Archive> queryJarNestArchives() throws Exception {
		return getClassPathArchives();
	}

	@Override
	protected List<Archive> getClassPathArchives() throws Exception {
		List<Archive> archives = this.archive.getNestedArchives(new EntryFilter() {

			@Override
			public boolean matches(Entry entry) {
				return isNestedArchive(entry);
			}

		});
		postProcessClassPathArchives(archives);
		return archives;
	}
}
