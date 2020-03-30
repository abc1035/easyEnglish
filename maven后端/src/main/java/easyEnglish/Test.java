package easyEnglish;
import java.sql.*;
import java.io.*;
public class Test{
    public static void main(String[] args) throws Exception{
    	FileWriter f=new FileWriter("C:\\Users\\apple\\Desktop\\vocabulary");
    	BufferedWriter  buf1 = new BufferedWriter(f);
    	Connection con;
        String driver="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://123.57.129.105/English?serverTimezone=UTC";
        String user="abc1035";
        String password="123456abc";
        try{
            Class.forName(driver);
            con=DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("已连接数据库");
            Statement statement=con.createStatement();
            String S[]=new String[50000];
            int cnt=0;
            String sql="SELECT * FROM English.Vocabularysheet;";
            ResultSet res=statement.executeQuery(sql);
            System.out.println("获取完成");
            while(res.next())
            {
            	buf1.write(res.getString("WordEnglish"));
            	cnt++;
            	if(cnt%50==0)
            	{
            		System.out.println("已完成"+String.valueOf(cnt)+"条");
            	}
            }
            buf1.close();
            con.close();
            statement.close();
            res.close();
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
