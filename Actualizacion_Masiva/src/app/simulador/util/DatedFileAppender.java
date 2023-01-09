/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.util;

/**
 *
 * @author jbueno
 */
import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.LoggingEvent;

public final class DatedFileAppender extends FileAppender implements Serializable {
	private static final long serialVersionUID = 2321756350751592596L;

	private String mDirectory = "logs";

	private String mPrefix = "tomcat.";

	private String mSuffix = ".log";

	private File mPath = null;

	private Calendar mCalendar = null;

	private long mTomorrow = 0L;

	public DatedFileAppender() {
	}

	public DatedFileAppender(String directory, String prefix, String suffix) {
		this.mDirectory = directory;
		this.mPrefix = prefix;
		this.mSuffix = suffix;
		activateOptions();
	}

	public String getDirectory() {
		return this.mDirectory;
	}

	public void setDirectory(String directory) {
		this.mDirectory = directory;
	}

	public String getPrefix() {
		return this.mPrefix;
	}

	public void setPrefix(String prefix) {
		this.mPrefix = prefix;
	}

	public String getSuffix() {
		return this.mSuffix;
	}

	public void setSuffix(String suffix) {
		this.mSuffix = suffix;
	}

	public void activateOptions() {
		if (this.mPrefix == null)
			this.mPrefix = "";
		if (this.mSuffix == null)
			this.mSuffix = "";
		if (this.mDirectory == null || this.mDirectory.length() == 0)
			this.mDirectory = ".";
		this.mPath = new File(this.mDirectory);
		if (!this.mPath.isAbsolute()) {
			String base = System.getProperty("user.dir");
			if (base != null)
				this.mPath = new File(base, this.mDirectory);
		}
		this.mPath.mkdirs();
		if (this.mPath.canWrite())
			this.mCalendar = Calendar.getInstance();
	}

	public void append(LoggingEvent event) {
		if (this.layout == null) {
			this.errorHandler.error("No layout set for the appender named [" + this.name + "].");
			return;
		}
		if (this.mCalendar == null) {
			this.errorHandler.error("Improper initialization for the appender named [" + this.name + "].");
			return;
		}
		long n = System.currentTimeMillis();
		if (n >= this.mTomorrow) {
			this.mCalendar.setTime(new Date(n));
			String datestamp = datestamp(this.mCalendar);
			tomorrow(this.mCalendar);
			this.mTomorrow = this.mCalendar.getTime().getTime();
			File newFile = new File(this.mPath, this.mPrefix + datestamp + this.mSuffix);
			this.fileName = newFile.getAbsolutePath();
			super.activateOptions();
		}
		if (this.qw == null) {
			this.errorHandler.error("No output stream or file set for the appender named [" + this.name + "].");
			return;
		}
		subAppend(event);
	}

	public static String datestamp(Calendar calendar) {
		int year = calendar.get(1);
		int month = calendar.get(2) + 1;
		int day = calendar.get(5);
		StringBuffer sb = new StringBuffer();
		if (year < 1000) {
			sb.append('0');
			if (year < 100) {
				sb.append('0');
				if (year < 10)
					sb.append('0');
			}
		}
		sb.append(Integer.toString(year));
		sb.append('-');
		if (month < 10)
			sb.append('0');
		sb.append(Integer.toString(month));
		sb.append('-');
		if (day < 10)
			sb.append('0');
		sb.append(Integer.toString(day));
		return sb.toString();
	}

	public static void tomorrow(Calendar calendar) {
		int year = calendar.get(1);
		int month = calendar.get(2);
		int day = calendar.get(5) + 1;
		calendar.clear();
		calendar.set(year, month, day);
	}

}
