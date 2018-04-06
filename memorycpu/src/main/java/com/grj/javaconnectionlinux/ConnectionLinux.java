package com.grj.javaconnectionlinux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class ConnectionLinux {
	//top -n 1 -b | grep "Cpu"| awk '{print $2}' > /grj/project/ProjectMonitorLinuxCPUmemory/memorycpu.txt"
	public static final String EXEC_COMMOND = "top -n 1 -b | grep \"Cpu\"| awk '{print $2}' > /grj/project/ProjectMonitorLinuxCPUmemory/memorycpu.txt";
	public static final String HOST_NAME = "192.168.197.131";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "root";
	
	public static void main(String[] args) {
		

		try {
			/* Create a connection instance */
			Connection conn = new Connection(HOST_NAME);

			/* Now connect */
			conn.connect();

			/*
			 * Authenticate. If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = 
					conn.authenticateWithPassword(USERNAME, PASSWORD);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand(EXEC_COMMOND);

			System.out.println("Here is some information about the remote host:");

			/*
			 * This basic example does not handle stderr, which is sometimes
			 * dangerous (please read the FAQ).
			 */
			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */
			System.out.println("ExitCode: " + sess.getExitStatus());

			/* Close this session */
			sess.close();

			/* Close the connection */
			conn.close();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
