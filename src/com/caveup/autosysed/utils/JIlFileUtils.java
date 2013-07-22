package com.caveup.autosysed.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.TreeNode;

import com.caveup.autosysed.domain.Job;
import com.caveup.autosysed.domain.Job.JobType;
import com.caveup.autosysed.editors.AutosysAttEnum;

public final class JIlFileUtils {

	public static List<Job> parse(IDocument docuemnt) {
		List<Job> allJobs = new ArrayList<Job>();
		Map<String, Job> cacheJobMap = new HashMap<String, Job>();
		try {
			Job job = null;
			for (int i = 0; i < docuemnt.getNumberOfLines(); i++) {
				IRegion region = docuemnt.getLineInformation(i);
				String line = docuemnt.get(region.getOffset(), region.getLength());
				// skip the comment and space line
				if (line.length() == 0)
					continue;

				if (line.startsWith("/*") || line.startsWith("//") || line.trim().startsWith("*"))
					continue;

				StringTokenizer token = new StringTokenizer(line, ": ");
				while (token.hasMoreElements()) {
					String key = token.nextToken().toLowerCase().trim();
					if (AutosysAttEnum.INSERT_JOB.getContent().equals(key)
							|| AutosysAttEnum.DELETE_JOB.getContent().equals(key)
							|| AutosysAttEnum.UPDATE_JOB.getContent().equals(key)) {
						String jobName = token.nextToken().trim();
						// set the max offset
						if (job != null)
							job.setEndOffset(docuemnt.getLineOffset(i) - 1);

						job = new Job();
						job.setJobName(jobName);

						int jobOffset = line.indexOf(job.getJobName());
						job.setOffset(docuemnt.getLineOffset(i) + jobOffset);
						job.setStartOffset(docuemnt.getLineOffset(i));
						job.setLength(job.getJobName().length());
						cacheJobMap.put(jobName, job);
						allJobs.add(job);
					} else if (AutosysAttEnum.BOX_NAME.getContent().equals(key)) {
						job.setBoxName(token.nextToken());
					} else if (AutosysAttEnum.DATE_CONDITIONS.getContent().equals(key)) {
						job.setDateCondition(token.nextToken());
					} else if (AutosysAttEnum.START_TIMES.getContent().equals(key)) {
						job.setStartTimes(token.nextToken());
					} else if (AutosysAttEnum.START_MINS.getContent().equals(key)) {
						job.setStartMins(token.nextToken());
					} else if (AutosysAttEnum.DAYS_OF_WEEK.getContent().equals(key)) {
						job.setDayOfWeeks(token.nextToken());
					} else if (AutosysAttEnum.JOB_TYPE.getContent().equals(key)) {
						String jobType = token.nextToken().toLowerCase().trim();
						if (jobType.equals("b") || jobType.equals("box")) {
							job.setJobType(JobType.BOX);
						} else if (jobType.equals("f") || jobType.equals("fw")) {
							job.setJobType(JobType.FILEWATCH);
						} else {
							job.setJobType(JobType.COMMAND);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Job> res = new ArrayList<Job>();
		Set<Job> existedJobSet = new HashSet<Job>();
		for (Job job : allJobs) {
			// root job
			if (job.getBoxName() == null) {
				res.add(job);
				existedJobSet.add(job);
			} else {
				Job parentJob = cacheJobMap.get(job.getBoxName());
				if (parentJob != null) {
					parentJob.addJob(job);
					job.setParentJob(parentJob);
				} else {
					res.add(job);
					existedJobSet.add(job);
				}
			}
		}
		return res;
	}

	public static TreeNode[] buildTreeNode(List<Job> allJobs) {
		TreeNode[] nodes = new TreeNode[allJobs.size()];
		for (int i = 0; i < allJobs.size(); i++) {
			Job job = allJobs.get(i);
			TreeNode node = new TreeNode(job);
			if (job.getSubJobs() != null && !job.getSubJobs().isEmpty()) {
				node.setChildren(buildTreeNode(job.getSubJobs()));
			}
			nodes[i] = node;
		}
		return nodes;
	}

	public static void main(String[] args) {
		String line = "insert_job:161534_IRIS_OTC_StopServer1     job_type:  c";
		StringTokenizer token = new StringTokenizer(line, ": \t\n\r\f");
		while (token.hasMoreElements()) {
			String key = token.nextToken();
			System.out.println(key);
		}

	}
}
