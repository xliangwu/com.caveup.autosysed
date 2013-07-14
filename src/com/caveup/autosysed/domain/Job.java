package com.caveup.autosysed.domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class Job {
	private static final Map<JobType, String> imageMap = new HashMap<JobType, String>();
	private int offset;
	private int length;
	/** the max offset to find the nearest job **/
	private int startOffset;
	private int endOffset;
	private JobType jobType = JobType.COMMAND;
	private List<Job> subJobs;
	private Job parentJob;
	private boolean textSelection;
	private Image image;

	private String jobName;
	private String boxName;
	private String machine;
	static {
		imageMap.put(JobType.BOX, "/icons/box.png");
		imageMap.put(JobType.CALENDAR, "/icons/calendar.gif");
		imageMap.put(JobType.FILEWATCH, "/icons/eye.png");
		imageMap.put(JobType.COMMAND, "/icons/command.png");
	}

	public Job() {
	}

	public Job(String jobName) {
		this.jobName = jobName;
	}

	public Image getImage() {
		if (this.image != null) {
			return this.image;
		}
		this.image = createImage(imageMap.get(this.getJobType()));
		return this.image;
	}

	private Image createImage(String urlPath) {
		URL resource = Job.class.getResource(urlPath);
		ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(resource);
		if (imgDescriptor == null) {
			return null;
		}
		return imgDescriptor.createImage();
	}

	public void addJob(Job job) {
		if (subJobs == null)
			subJobs = new ArrayList<Job>();
		subJobs.add(job);
	}

	public List<Job> getSubJobs() {
		return subJobs;
	}

	public void setSubJobs(List<Job> subJobs) {
		this.subJobs = subJobs;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getBoxName() {
		return boxName;
	}

	public boolean isTextSelection() {
		return textSelection;
	}

	public void setTextSelection(boolean textSelection) {
		this.textSelection = textSelection;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public JobType getJobType() {
		return jobType;
	}

	public Job getParentJob() {
		return parentJob;
	}

	public void setParentJob(Job parentJob) {
		this.parentJob = parentJob;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}

	public enum JobType {
		COMMAND, FILEWATCH, BOX, CALENDAR,
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		return true;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}
}
