package com.caveup.autosysed.validate;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.caveup.autosysed.domain.Job;

public class HostValidate implements Validate<Job> {

	@Override
	public boolean validate(Job element) {
		if (element == null || element.getMachine() == null)
			return true;
		try {
			Lookup lookup = new Lookup(element.getMachine(), Type.MX);
			lookup.run();
			if (lookup.getResult() != Lookup.SUCCESSFUL) {
				return false;
			}
			return true;
		} catch (TextParseException e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println("### ### Validate ### ###");

		HostValidate client = new HostValidate();
		Job job = new Job();
		job.setMachine("baidu.com");
		System.out.println(client.validate(job));

	}
}
