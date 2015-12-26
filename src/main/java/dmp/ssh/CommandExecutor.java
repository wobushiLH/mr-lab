package dmp.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;

/** * @parameter * @return */
public class CommandExecutor {

	private String username;
	private String password;
	private Connection conn = new Connection("远程主机地址");
	private Session ssh = null;

	public CommandExecutor(String hostname, String username, String password) {
		this.username = username;
		this.password = password;
		conn = new Connection(hostname);
	}

	public void connectOpen() {
		try {
			// 连接到主机
			conn.connect();
			// 使用用户名和密码校验
			boolean isconn = conn.authenticateWithPassword(username, password);
			if (!isconn) {
				System.err.println("用户名称或者是密码不正确");
			} else {
				System.out.println("连接成功");
			}
		} catch (IOException e) {

			System.err.println("连接错误");
		}

	}

	public void connectClose() {
		conn.close();
		System.out.println("连接关闭");
	}

	
	public List<String> sendCommand(String command) {

		List<String> list = new ArrayList<String>();

		
		try {

			ssh = conn.openSession();
			// 使用多个命令用分号隔开,只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
			ssh.execCommand(command);
			// 将屏幕上的文字全部打印出来
			InputStream is = new StreamGobbler(ssh.getStdout());
			BufferedReader brs = new BufferedReader(new InputStreamReader(is));
			while (true) {
				String line = brs.readLine();
				if (line == null) {
					break;
				}
				//System.out.println(line);
				list.add(line);

			}

			// 关闭Session
			ssh.close();
			return list;
		} catch (IOException e) {

			System.err.println("连接错误");
			return null;
		}

	}

}
