package easyEnglish;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

public class recover 
{
	public static void main(String[] args)
	{
		recover a=new recover();
		a.recoverwordlabel();
	}
	public void recoverwordlabel()
	{
		try
		{
			ConnectMessage a=new ConnectMessage();	
			Connection con;
			String driver="com.mysql.jdbc.Driver";
			String url=a.GetUrl();
			String user=a.GetUser();
			String password=a.GetPassword();
			Statement state=null;
			Statement state2=null;
			Class.forName(driver);
			con= DriverManager.getConnection(url, user, password);
			state=con.createStatement();
			FileInputStream  file_dict=new FileInputStream ("D:\\Level8_2.json");
			InputStreamReader  f=new InputStreamReader (file_dict,"UTF-8");
			BufferedReader buf1=new BufferedReader(f);
			String str;
			int cnt=0;
			while((str=buf1.readLine())!=null)
			{
				JSONObject object=new JSONObject().parseObject(str);
				String word=object.getString("headWord");
				String sql="update English.Vocabularysheet set wordlabel=concat(wordlabel,'%专八') where WordEnglish='"+word+"';";
				state.execute(sql);
				cnt++;
				if(cnt%50==0)
				{
					System.out.println("已完成"+cnt+"条");
				}
			}
			System.out.println("已完成");
			con.close();
			state.close();
			buf1.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
