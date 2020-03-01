package com.google.utils;

import java.io.File;
import java.util.logging.Logger;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.FluentCreate;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

/**
 * @author Vikas Mathur
 *
 *         This class creates JIRA defects for issues identified by automation
 *         execution
 *
 */
public class JiraDefect {

	private JiraClient jira;
	private String projectKey;
	private Logger log = Logger.getLogger("JIRA Class");

	/**
	 * @param jiraUrl
	 * @param username
	 * @param password
	 * @param project
	 */
	public JiraDefect(String jiraUrl, String username, String password, String projectKey) {
		BasicCredentials creds = new BasicCredentials(username, password);
		jira = new JiraClient(jiraUrl, creds);
		this.projectKey = projectKey;
	}

	/**
	 * This method creates a JIRA issue in case of defect identified by
	 * automation scripts
	 * 
	 * @param issueType
	 * @param summary
	 * @param description
	 */
	public void createJiraDefect(String browser, String summary, String description, String attachment) {
		try {
			FluentCreate newIssueFluentCreate = jira.createIssue(projectKey, Constants.ISSUE_TYPE);
			newIssueFluentCreate.field(Field.SUMMARY, "Automation Defect: " + summary);
			newIssueFluentCreate.field(Field.DESCRIPTION, description);
			
			Issue newIssue = newIssueFluentCreate.execute();
			
			File file=new File(attachment);
			newIssue.addAttachment(file);
			
			log.info("New issue created. Jira ID : " + newIssue);
		} catch (JiraException e) {
			log.info("Issue not created: " + e);
		}
	}
}
