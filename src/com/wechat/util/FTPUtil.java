package com.wechat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Random;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil {
	public static final String DIRSPLITER = "/";
	/**
	 * 连接FTP
	 * 
	 * @param ip
	 * @param username
	 * @param password
	 * @param port
	 */
	public static FTPClient connctServer(String ip, String username,
			String password, int port, String clientEnCode) throws Exception {
		FTPClient ftp = new FTPClient();
		try {
			if (port == 0) {
				ftp.connect(ip);

			} else {
				ftp.connect(ip, port);
			}
		} catch (SocketException e) {
			// 当出现SocketException异常时，多为网络问题。如：连接超时或者网络不通
			return null;
		} catch (IOException e) {
			throw new Exception(e);
		}

		// 登录
		boolean isLogin = ftp.login(username, password);

		if (!isLogin) {
			return null;
		}

		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return null;
		} else {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);// 设置为二进制传输模式
			ftp.enterLocalPassiveMode();// 本地客户端需要主动连接活动的ftpServer
			ftp.setControlEncoding(clientEnCode);
			FTPClientConfig conf = new FTPClientConfig(
					getSystemKey(ftp.getSystemName()));
			conf.setServerLanguageCode("zh");
			ftp.configure(conf);
		}
		return ftp;
	}

	/**
	 * 测试是否连接成功
	 * 
	 * @param ip
	 * @param username
	 * @param password
	 * @param port
	 * @param folder
	 *            ftp目录
	 * @param ftpEnCode
	 *            ftp服务端编码
	 * @param clientEnCode
	 *            客户端服务器编码
	 * @return
	 * @throws Exception
	 */
	public static boolean isConnect(String ip, String username,
			String password, int port, String folder, String ftpEnCode,
			String clientEnCode) throws Exception {
		boolean b = true;
		FTPClient ftp = connctServer(ip, username, password, port, clientEnCode);
		if (ftp == null) {
			b = false;
		} else {
			if (folder != null && !"".equals(folder)) {
				// 将文件目录转换为ftp服务端编码
				folder = CharsetUtil.changeCharset(folder, clientEnCode,
						ftpEnCode);
				b = ftp.changeWorkingDirectory(folder);
			}
		}

		return b;
	}

	public static String getSystemKey(String systemName) {
		String[] values = systemName.split(" ");
		if (values != null && values.length > 0) {
			return values[0];
		} else {
			return null;
		}

	}

	/**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	public static String getRandomString() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 建立FTPClient
	 * 
	 * @return
	 * @throws Exception
	 * @author sean
	 * @version 1.0 </pre> Created on :2013-9-3 下午8:15:03 LastModified: History:
	 *          </pre>
	 */
	private static FTPClient creatFTPClient() throws Exception {
		// 获取ftp参数
		String ip = SystemProperty.getInstance("config").getProperty("FTP_IP");
		String portStr = SystemProperty.getInstance("config").getProperty("FTP_PORT");
		int port = Integer.parseInt(portStr);
		String username = SystemProperty.getInstance("config").getProperty("FTP_USERNAME");
		String password = SystemProperty.getInstance("config").getProperty("FTP_PASSWORD");
		// 获取FTPClient
		FTPClient ftpClient = connctServer(ip, username, password, port,
				"UTF-8");
		return ftpClient;
	}

	public static String uploadFile(File file, String path, String fileName)
			throws Exception {
		FTPClient ftpClient = creatFTPClient();
		// 设置访问目录
		ftpCreateDirectoryTree(ftpClient, path);
		// FTP上传文件
		// byte[] bytes = file.getBytes();
		if (file.length() != 0) {
			InputStream is = new FileInputStream(file);
			ftpClient.storeFile(fileName, is);
		}
		ftpClient.disconnect();
		String pathAndName = "";
		String pathLastStr = path.substring(path.length() - 1, path.length());
		if (pathLastStr.equals("/")) {
			pathAndName = path.substring(0, path.length() - 1) + fileName;
		} else {
			pathAndName = path + "/" + fileName;
		}
		return pathAndName;
	}

	public static String uploadFile(InputStream is, String path, String fileName)
			throws Exception {
		FTPClient ftpClient = creatFTPClient();
		// 设置访问目录
		ftpCreateDirectoryTree(ftpClient, path);
		// FTP上传文件
		ftpClient.storeFile(fileName, is);
		ftpClient.disconnect();
		String pathAndName = "";
		String pathLastStr = path.substring(path.length() - 1, path.length());
		if (pathLastStr.equals("/")) {
			pathAndName = path.substring(0, path.length() - 1) + fileName;
		} else {
			pathAndName = path + "/" + fileName;
		}
		return pathAndName;
	}

	private static void ftpCreateDirectoryTree(FTPClient client, String dirTree)
			throws IOException {
		boolean dirExists = true;
		// tokenize the string and attempt to change into each directory level.
		// If you cannot, then start creating.
		String[] directories = dirTree.split("/");
		for (String dir : directories) {
			if (!dir.isEmpty()) {
				if (dirExists) {
					dirExists = client.changeWorkingDirectory(dir);
				}
				if (!dirExists) {
					if (!client.makeDirectory(dir)) {
						throw new IOException(
								"Unable to create remote directory '" + dir
										+ "'.  error='"
										+ client.getReplyString() + "'");
					}
					if (!client.changeWorkingDirectory(dir)) {
						throw new IOException(
								"Unable to change into newly created remote directory '"
										+ dir + "'.  error='"
										+ client.getReplyString() + "'");
					}
				}
			}
		}
	}

	public static void main(String[] args) {

	}
}
