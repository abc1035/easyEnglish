package easyEnglish;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class Getprototype{
	public static HashMap<String,String>nMap=null;
	public static HashMap<String,String>vMap=null;
	public static HashMap<String,String>dMap=null;
	public Getprototype()
	{
		if(nMap==null)
		{
			loadIrNoun();
		}
		if(vMap==null)
		{
			loadIrVerb();
		}
		if(dMap==null)
		{
			loaddict();
		}
	}
	public static void main(String[] args) {
		Getprototype a=new Getprototype();
		while(true)
		{
			System.out.println("请输入单词：");
			Scanner cin=new Scanner(System.in);
			String str;
			String word="";
			try
			{
				word=cin.next();
				str=a.wordJudge(word);
			}catch(Exception e)
			{
				e.printStackTrace();
				str=word;
			}
			System.out.println("单词原型:"+str);
		}
    }
	public String getwordscore(String word)
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
			//if(!con.isClosed())
				//System.out.println("已连接数据库");
			String sql="SELECT * FROM English.Vocabularysheet where wordEnglish='"+word+"';";
			ResultSet res=state.executeQuery(sql);
			while(res.next())
			{
				return res.getString("wordproblem");
			}
			return "0";
		}catch(Exception e)
		{
			return "0";
		}
	}
	public void loaddict()
	{
		try
		{
			FileInputStream  file_dict=new FileInputStream ("others//vocabulary.txt");
			InputStreamReader  f=new InputStreamReader (file_dict,"UTF-8");
			BufferedReader buf1=new BufferedReader(f);
			HashMap<String,String>Map=new HashMap();
			String str;
			while((str=buf1.readLine())!=null)
			{
				Map.put(str,"1");
			}
			dMap=Map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			dMap=null;
		}
	}
	public HashMap loadIrVerb()
	{
		try
		{
			FileInputStream  file_dict=new FileInputStream ("others//irregular verbs.txt");
			InputStreamReader  f=new InputStreamReader (file_dict,"UTF-8");
			BufferedReader buf1=new BufferedReader(f);
			HashMap<String,String>Map=new HashMap();
			String str;
			int cnt=0;
			while((str=buf1.readLine())!=null)
			{
				String proto=null;
				String nnew=null;
				int i=0,j=0;
				int len=str.length();
				char ch[]=str.toCharArray();
				for(i=0;i<len;i++)
				{
					if((ch[i]<='z'&&ch[i]>='a')==false)
					{
						break;
					}
				}
				proto=str.substring(0,i);
				for(i=i;i<len;i++)
				{
					//System.out.println(ch[i]);
					if((ch[i]<='z'&&ch[i]>='a')==true)
					{
						//System.out.println("fuck");
						for(j=i;j<len;j++)
						{
							//System.out.println(ch[j]);
							if((ch[j]<='z'&&ch[j]>='a')==false)
							{
								//System.out.println("fuck");
								nnew=str.substring(i,j);
								i=j;
								Map.put(nnew,proto);
								break;
							}
						}
					}
				}
			}
			/*Iterator iter = Map.entrySet().iterator();
			while (iter.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				System.out.println(key+" "+val);
			}*/
			vMap=Map;
			return Map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public HashMap loadIrNoun()
	{
		try
		{
			FileInputStream  file_dict=new FileInputStream ("others//irregular nouns.txt");
			InputStreamReader  f=new InputStreamReader (file_dict,"UTF-8");
			BufferedReader buf1=new BufferedReader(f);
			HashMap<String,String>Map=new HashMap();
			String str;
			int cnt=0;
			while((str=buf1.readLine())!=null)
			{
				int len=str.length();
				boolean judge1=false;
				boolean judge2=false;
				char ch[]=str.toCharArray();
				String proto=null;
				String nnew=null;
				for(int i=0;i<len;i++)
				{
					if(ch[i]<='z'&&ch[i]>='a'&&judge1==false)
					{
						int j;
						for(j=i;j<len;j++)
						{
							if(ch[j]<='z'&&ch[j]>='a')
							{
								continue;
							}
							else
							{
								break;
							}
						}
						proto=str.substring(i,j);
						judge1=true;
						i=j;
					}
					else if(ch[i]<='z'&&ch[i]>='a'&&judge1==true)
					{
						int j;
						for(j=i;j<len;j++)
						{
							if(ch[j]<='z'&&ch[j]>='a')
							{
								continue;
							}
							else
							{
								break;
							}
						}
						nnew=str.substring(i,j);
						judge2=true;
					}
					if(judge1==true&&judge2==true)
					{
						Map.put(nnew,proto);
						break;
					}
				}
			}
			/*Iterator iter = Map.entrySet().iterator();
			while (iter.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				System.out.println(key);
				System.out.println(val);
			}*/
			nMap=Map;
			return Map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public String wordJudge(String word)
	{
		try
		{
			if(nMap.get(word)!=null)
			{
				return nMap.get(word).toString();
			}
			if(vMap.get(word)!=null)
			{
				return vMap.get(word).toString();
			}
			//System.out.println("fuck");
			boolean flag=false;
			int len=word.length();
			if(len>=3&&word.substring(len-3,len).equals("ies"))
			{
				String temp=word.substring(0,len-3);
				return temp+"y";
			}
			if(flag==false)
			{
				if(len>=2&&word.substring(len-2,len).equals("es"))
				{
					String temp=word.substring(0,len-2);
					if(dMap.get(temp)!=null)
					{
						return temp;
					}
					else if(dMap.get(temp+"e")!=null)
					{
						return temp+"e";
					}
				}
			}
			if(flag==false)
			{
				if(word.substring(len-1,len).equals("s"))
				{
					String temp=word.substring(0,len-1);
					return temp;
				}
			}
			if(flag==false)
			{
				if(len>=3&&word.substring(len-3,len).equals("ing"))
				{
					String temp=word.substring(0,len-3);
					//System.out.println(temp);
					//String last=word.substring(len-4,len-3);
					if(dMap.get(temp)!=null)
					{
						return temp;
					}
					else if(len>=4&&dMap.get(word.substring(0,len-4))!=null)
					{
						return word.substring(0,len-4);
					}
				}
			}
			if(flag==false)
			{
				if(len>=4&&word.substring(len-4,len).equals("ying"))
				{
					String temp=word.substring(0,len-4);
					return temp+"ie";
				}
			}
			if(flag==false)
			{
				if(len>=3&&word.substring(len-3,len).equals("ied"))
				{
					String temp=word.substring(0,len-3);
					return temp+"y";
				}
			}
			if(flag==false)
			{
				if(len>=3&&word.substring(len-3,len).equals("ves"))
				{
					String temp=word.substring(0,len-3);
					temp+="f";
					if(dMap.get(temp)!=null)
					{
						return temp;
					}
					else if(dMap.get(temp+"e")!=null)
					{
						return temp+"e";
					}
				}
			}
			if(flag==false)
			{
				if(len>=2&&word.substring(len-2,len).equals("ed"))
				{
					String temp=word.substring(0,len-2);
					if(dMap.get(temp)!=null)
					{
						return temp;
					}
					else if(dMap.get(temp+"e")!=null)
					{
						return temp+"e";
					}
				}
			}
			return word;
		}catch(Exception e)
		{
			return word;
		}
	}
}