package javabackground;
import java.io.*;
import java.net.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class Server extends Thread
{
	public Server(int port) 
	{ 
	    try 
	    { 
	    	this. port=port; 
	    	server=new ServerSocket(port); 
	    } 
	    catch (Exception e) 
	    { 
	    	System.out.println(e.toString()); 
	    } 
	} 
	int port; 
	ServerSocket server; 
	Socket socket; 
	PrintWriter  out = null;
    BufferedReader in = null;
    public void run()
	{
		while(true)
		{
			try 
			{ 
				socket=server.accept(); 
				out = new PrintWriter(socket.getOutputStream());
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
				String str=in.readLine();
				JSONObject object=new JSONObject().parseObject(str);
				String id=object.getString("jsonid");
				if(id.equals("1"))//�ֻ��ŵ�¼
				{
					String phone=object.getString("userphonenumber");
					String password=object.getString("userpassword");
					UserPhoneNumberLogin a=new UserPhoneNumberLogin();
					int b=a.Login(phone,password);//0�û�������,1�������,��¼�ɹ�

					//......
				}
				if(id.equals("2"))//�����¼
				{
					String mail=object.getString("mail");
					String password=object.getString("userpassword");
					UserMailLogin a=new UserMailLogin();
					a.Login(mail,password);//0�û�������,1�������,��¼�ɹ�

					//......
				}
				socket.close();
				in.close();
				out.close();
				server.close();
			}
			catch (Exception e) 
			{ 
				System.out.println(e.toString()); 
			}
		}
	}
}
