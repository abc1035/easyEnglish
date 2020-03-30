package easyEnglish;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatisticsWord 
{
	public static void main(String[] args)
	{
		try
		{
			FileInputStream  file_dict=new FileInputStream ("D:\\简爱+英文版.txt");
			InputStreamReader  f=new InputStreamReader (file_dict,"UTF-8");
			BufferedReader buf1=new BufferedReader(f);
			HashMap<String,String>Map=new HashMap();
			Getprototype origin=new Getprototype();
			String str=null;
			int cnt=0;
			while((str=buf1.readLine())!=null)
			{
				str=str.toLowerCase();
				char ch[]=str.toCharArray();
				int len=str.length();
				int i=0,j=0;
				for(i=0;i<len;i++)
				{
					if(ch[i]>='a'&&ch[i]<='z')
					{
						for(j=i;j<len;j++)
						{
							if((ch[j]>='a'&&ch[j]<='z')==false)
							{
								String word=str.substring(i,j);
								word=origin.wordJudge(word);
								if(Map.get(word)!=null)
								{
									int num=Integer.parseInt(Map.get(word).toString())+1;
									Map.put(word,num+"");
								}
								else
								{
									Map.put(word,"1");
								}
								cnt++;
								if(cnt%100==0)
								{
									System.out.println("已处理"+cnt+"个单词");
								}
								i=j;
								break;
							}
						}
					}
				}
			}
			System.out.println("单词处理完毕");
			ConnectMessage a=new ConnectMessage();
	        Connection con;
	        String driver="com.mysql.jdbc.Driver";
	        String url=a.GetUrl();
	        String user=a.GetUser();
	        String password=a.GetPassword();
	        Statement state=null;
	        Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            if(!con.isClosed())
                System.out.println("已连接数据库");
            Iterator iter = Map.entrySet().iterator();
            int dd=0;
			while (iter.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iter.next();
				String word = entry.getKey().toString();
				String num = entry.getValue().toString();
				String sql="update Vocabularysheet set frequency='"+num+"' where WordEnglish='"+word+"';";
				dd++;
				if(dd==1)
				{
					System.out.println(sql);
				}
				state.execute(sql);
				if(dd%100==0)
				{
					System.out.println("数据库更新已完成"+dd+"/"+Map.size()+"次");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
