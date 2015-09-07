/*

    HTTPģ����
    ����ߣ�����
    ������ڣ�2009-06-09
    �������ʹ��
    blog��http://nokiaguy.blogjava.net
    email��techcast@126.com
    msn��asklining@hotmail.com

*/
package part1;

import java.net.*;
import java.io.*;

public class HttpSimulator
{
	private Socket socket;
	private int port = 80;
	private String host = "localhost";
	private String request = ""; // HTTP������Ϣ
	private boolean isPost, isHead;

	// �ӿ���̨��ȡhost��port���������ַ�"q"ʱ������false,���򣬷���true
	private boolean readHostAndPort(BufferedReader consoleReader)
			throws Exception
	{
		System.out.print("host:port>");
		String[] ss = null;
		String s = consoleReader.readLine();
		if (s.equals("q"))
			return false;
		else
		{
			ss = s.split("[:]");
			if (!ss[0].equals(""))
				host = ss[0];
			if (ss.length > 1)
				port = Integer.parseInt(ss[1]);
			System.out.println(host + ":" + String.valueOf(port));
			return true;
		}
	}
	// �ӿ���̨��ȡHTTP������Ϣ������������Ϣ������request��
	private void readHttpRequest(BufferedReader consoleReader) throws Exception
	{
		System.out.println("������HTTP����:");
		String s = consoleReader.readLine();
		request = s + "\r\n";
		boolean isPost = s.substring(0, 4).equals("POST");
		boolean isHead = s.substring(0, 4).equals("HEAD");
		while (!(s = consoleReader.readLine()).equals(""))
			request = request + s + "\r\n";
		request = request + "\r\n";
		if (isPost)
		{
			System.out.println("������POST����������:");
			s = consoleReader.readLine();
			request = request + s;
		}
	}
	// �����������HTTP������Ϣ
	private void sendHttpRequest() throws Exception
	{
		socket = new Socket();
		socket.setSoTimeout(10 * 1000);
		System.out.println("中文服务器：");
		socket.connect(new InetSocketAddress(host, port), 10 * 1000);
		System.out.println("���������ӳɹ���");
		OutputStream out = socket.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(request);
		writer.flush();
	}
	// ��ȡ�ӷ��������ص�HTTP��Ӧ��Ϣ
	private void readHttpResponse(BufferedReader consoleReader)
	{
		String s = "";
		try
		{
			InputStream in = socket.getInputStream();
			InputStreamReader inReader = new InputStreamReader(in);
			BufferedReader socketReader = new BufferedReader(inReader);
			System.out.println("---------HTTPͷ---------");
			boolean b = true; // true: δ��ȡ��Ϣͷ false: �Ѿ���ȡ��Ϣͷ
			while ((s = socketReader.readLine()) != null)
			{
				if (s.equals("") && b == true && !isHead)
				{
					System.out.println("------------------------");
					b = false;
					System.out.print("�Ƿ���ʾHTTP������(Y/N):");
					String choice = consoleReader.readLine();
					if (choice.equals("Y") || choice.equals("y"))
					{
						System.out.println("---------HTTP����---------");
						continue;
					}
					else
						break;
				}
				else
					System.out.println(s);
			}
		}
		catch (Exception e)
		{
			System.out.println("err:" + e.getMessage());
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch (Exception e)
			{
			}
		}
		System.out.println("------------------------");
	}
	public void run() throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (true)
		{
			try
			{
				if (!readHostAndPort(reader))
					break;
				readHttpRequest(reader);
				sendHttpRequest();
				readHttpResponse(reader);
			}
			catch (Exception e)
			{
				System.out.println("err:" + e.getMessage());
			}
		}
	}
	public static void main(String[] args) throws Exception
	{
		new HttpSimulator().run();
	}
}
