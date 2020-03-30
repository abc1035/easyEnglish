package easyEnglish;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VocabularyLevel {
	public static void main(String[] args)
	{
		VocabularyLevel a=new VocabularyLevel();
		a.update();
	}
	public void update()
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
			state2=con.createStatement();
			if(!con.isClosed())
            System.out.println("已连接数据库");
			String sql="select * from English.Vocabularysheet where wordproblem='0';";
			ResultSet res=state.executeQuery(sql);
			int cnt=0;
			while(res.next())
			{
				String word=res.getString("WordEnglish");
				int fre=Integer.parseInt(res.getString("frequency"));
				int wordproblem=0;
				if(fre>50)
				{
					wordproblem=0;
				}
				else if(fre>30)
				{
					wordproblem=4;
				}
				else if(fre>10)
				{
					wordproblem=8;
				}
				else if(fre>5)
				{
					wordproblem=12;
				}
				else if(fre>1)
				{
					wordproblem=16;
				}
				else
				{
					wordproblem=20;
				}
				wordproblem+=word.length()*2;
				wordproblem+=40;
				String Sql="update English.Vocabularysheet set wordproblem='"+wordproblem+"' where WordEnglish='"+word+"';";
				state2.execute(Sql);
				cnt++;
				if(cnt==1)
				{
					System.out.println(Sql);
				}
				if(cnt%100==0)
				{
					System.out.println("已处理"+cnt+"条");
				}
			}
			System.out.println("处理完成");
			con.close();
			res.close();
			state.close();
			state2.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
