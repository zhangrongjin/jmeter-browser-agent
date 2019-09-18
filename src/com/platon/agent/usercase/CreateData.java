package com.platon.agent.usercase;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class CreateData {
	
	public static void main(String[] args) throws Exception {
		
		for (int i = 1; i <= 1000; i++) {
			createProposalFile(i);
		}
	}
	
	public static void createProposalFile(int number) throws Exception {
		String temp = "---\r\n" + 
				"PIP:  {number}\r\n" + 
				"Topic: Topic of {number} \r\n" + 
				"Author: vivi \r\n" + 
				"Status: Processing/Rejected/Approved \r\n" + 
				"Type: Finance\r\n" + 
				"Description: Description of {number} \r\n" + 
				"Created: 2019-08-20\r\n" + 
				"---\r\n" + 
				"\r\n" + 
				"# PIP-X：Example Title\r\n" + 
				"\r\n" + 
				"## Abstract\r\n" + 
				"\r\n" + 
				"This is a section for an abstract.\r\n" + 
				"\r\n" + 
				"## Motivation\r\n" + 
				"\r\n" + 
				"This is a section for a motivation.\r\n" + 
				"\r\n" + 
				"## Body\r\n" + 
				"\r\n" + 
				"This is a section for a body. The title of the section should be changed\r\n" + 
				"and the section can be split into multiple sections and subsections.\r\n" + 
				"\r\n" + 
				"## References\r\n" + 
				"\r\n" + 
				"This is a section for references such as links to other documents (BIP or SLIP)\r\n" + 
				"or to reference implementations.";
		
		String data = temp.replaceAll("\\{number\\}", String.valueOf(number));
	
		
		
		File descFile = new File(String.format("C:\\work\\workspace\\workspace-tools\\updateInfo0.7.1\\pip\\PIP-%s.md",number));
		if(!descFile.exists()) {
			descFile.createNewFile();
		}
		FileUtils.write(descFile, data, "UTF-8");
	}
}
