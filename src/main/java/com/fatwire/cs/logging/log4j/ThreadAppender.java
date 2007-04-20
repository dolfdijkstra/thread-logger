package com.fatwire.cs.logging.log4j;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

public class ThreadAppender extends RollingFileAppender {
	private final ThreadLocal threadLocal = new AppenderThreadLocal();

	private File directory;

	public ThreadAppender() {
		super();
	}

	public ThreadAppender(final Layout layout, final String file)
			throws IOException {
		super(layout, file);
	}

	public ThreadAppender(final Layout layout, final String file,
			final boolean append) throws IOException {
		super(layout, file, append);
	}

	public void subAppend(final LoggingEvent event) {
		System.out.println("subAppend()");
		super.subAppend(event);
		getDelegate().doAppend(event);
	}

	private Appender getDelegate() {
		return (Appender) threadLocal.get();
	}

	public void setDirectory(final String dir) {
		directory = new File(dir);
	}

	public void activateOptions() {
		if (directory == null || directory.length() == 0) {
			directory = new File("logs");
		}
		directory.mkdirs();
		super.activateOptions();
	}

	class AppenderThreadLocal extends ThreadLocal {
		private ThreadNameToFileNameTransformer transformer = new ThreadNameToFileNameTransformer();

		private int counter = 0;

		protected Object initialValue() {

			final RollingFileAppender appender = new RollingFileAppender();
			appender.setAppend(ThreadAppender.this.getAppend());
			appender.setBufferedIO(ThreadAppender.this.getBufferedIO());
			appender.setFile(new File(directory, getSafeFileName() + ".log")
					.getAbsolutePath());
			appender.setLayout(ThreadAppender.this.getLayout());
			appender.setBufferSize(ThreadAppender.this.getBufferSize());
			appender.setEncoding(ThreadAppender.this.getEncoding());
			appender.setErrorHandler(ThreadAppender.this.getErrorHandler());
			appender.setImmediateFlush(ThreadAppender.this.getImmediateFlush());
			appender.setMaxBackupIndex(ThreadAppender.this.getMaxBackupIndex());
			appender.setMaximumFileSize(ThreadAppender.this
					.getMaximumFileSize());
			appender.setName("ThreadAppender for "
					+ Thread.currentThread().getName());
			appender.activateOptions();
			return appender;
		}

		String getSafeFileName() {
			return transformer.getFilename(counter++);
		}
	}
}
