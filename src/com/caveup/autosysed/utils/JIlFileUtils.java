package com.caveup.autosysed.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.TreeNode;

import com.caveup.autosysed.domain.Job;
import com.caveup.autosysed.domain.Job.JobType;

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
					if (key.equals("insert_job") || key.equals("delete_job") || key.equals("update_job")) {
						String jobName = token.nextToken().trim();
						// set the max offset
						if (job != null)
							job.setEndOffset(docuemnt.getLineOffset(i) - 1);

						if (cacheJobMap.containsKey(jobName)) {
							job = cacheJobMap.get(jobName);
						} else {
							job = new Job();
							job.setJobName(jobName);
						}
						int jobOffset = line.indexOf(job.getJobName());
						job.setOffset(docuemnt.getLineOffset(i) + jobOffset);
						job.setStartOffset(docuemnt.getLineOffset(i));
						job.setLength(job.getJobName().length());
						allJobs.add(job);
					} else if (key.equals("box_name")) {
						job.setBoxName(token.nextToken());
						if (!cacheJobMap.containsKey(job.getBoxName())) {
							Job boxJob = new Job();
							boxJob.setJobName(job.getBoxName());
							cacheJobMap.put(job.getBoxName(), boxJob);
						}
					} else if (key.equals("job_type")) {
						String jobType = token.nextToken().toLowerCase().trim();
						if (jobType.equals("b") || jobType.equals("box")) {
							job.setJobType(JobType.BOX);
						} else if (jobType.equals("f") || jobType.equals("file")) {
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
		for (Job job : allJobs) {
			// root job
			if (job.getBoxName() == null) {
				res.add(job);
			} else {
				Job parentJob = cacheJobMap.get(job.getBoxName());
				parentJob.addJob(job);
				job.setParentJob(parentJob);
			}
			cacheJobMap.put(job.getJobName(), job);
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

	public static List<Job> parse(String file) {
		List<Job> allJobs = new ArrayList<Job>();
		Map<String, Job> cacheJobMap = new HashMap<String, Job>();
		InputStream fis = null;
		BufferedReader bufferedReader = null;
		try {
			Job job = null;

			String line;
			fis = new FileInputStream(file);
			bufferedReader = new BufferedReader(new InputStreamReader(fis));
			while ((line = bufferedReader.readLine()) != null) {
				// skip the comment and space line
				if (line.length() == 0)
					continue;

				if (line.startsWith("/*") || line.startsWith("//") || line.startsWith("*"))
					continue;

				StringTokenizer token = new StringTokenizer(line, ": ");
				while (token.hasMoreElements()) {
					String key = token.nextToken();
					if (key.equals("insert_job") || key.equals("delete_job") || key.equals("update_job")) {
						String jobName = token.nextToken();
						if (cacheJobMap.containsKey(jobName)) {
							job = cacheJobMap.get(jobName);
						} else {
							job = new Job();
							job.setJobName(jobName);
						}
						int jobOffset = line.indexOf(job.getJobName());
						job.setOffset(jobOffset);
						job.setLength(job.getJobName().length());
						allJobs.add(job);
					} else if (key.equals("box_name")) {
						job.setBoxName(token.nextToken());
						if (!cacheJobMap.containsKey(job.getBoxName())) {
							Job boxJob = new Job();
							boxJob.setJobName(job.getBoxName());
							cacheJobMap.put(job.getBoxName(), boxJob);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
					fis.close();
				} catch (IOException e) {
					// nothing to do
				}
			}
		}

		List<Job> res = new ArrayList<Job>();
		for (Job job : allJobs) {
			// root job
			if (job.getBoxName() == null) {
				res.add(job);
			} else {
				Job parentJob = cacheJobMap.get(job.getBoxName());
				parentJob.addJob(job);
				job.setParentJob(parentJob);
			}
			cacheJobMap.put(job.getJobName(), job);
		}
		return res;
	}

	public static void main(String[] args) {
		String line = "insert_job:161534_IRIS_OTC_StopServer1     job_type:  c";
		StringTokenizer token = new StringTokenizer(line, ": \t\n\r\f");
		while (token.hasMoreElements()) {
			String key = token.nextToken();
			System.out.println(key);
		}
		List<Job> res = JIlFileUtils.parse("D:\\java\\runtime-EclipseApplication\\Test\\src\\prod.jil");
		System.out.println(res.size());
		TreeNode[] dp = buildTreeNode(res);
		System.out.println(dp.length);
	}
}
