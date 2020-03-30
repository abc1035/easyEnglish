package javabackground;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.sql.*;
import java.io.*;
import java.util.*;
public class Main{
    public static void main(String[] args) throws Exception{
        String usermail="hywgn@sohu.com";
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器
            mail.setCharset("utf-8");
            mail.setAuthentication("1653155381@qq.com","zdrlbelheuqtdbha");//刚刚记录的授权码，是开启SMTP的密码
            mail.setFrom("1653155381@qq.com","misaka19001");  //发送邮件的邮箱和发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(usermail);//接收的邮箱
            //System.out.println("email"+email);
            mail.setSubject("注册验证码");//设置邮件的主题
            Random rand =new Random();
            int vericode=rand.nextInt(9000)+1000;
            mail.setMsg("尊敬的用户:你好!\n 注册验证码为:" +vericode+ "\n"+"     (有效期为2分钟)");//设置邮件的内容
            mail.send();//发送
            System.out.println("发送成功");
            //将注册码存入数据库


        } catch (Exception e) {
            System.out.println("发送失败");
            e.printStackTrace();
        }

        /*
        try{

            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url="jdbc:mysql://123.57.129.105:3306/English?serverTimezone=UTC&useSSL=true";
            String user="abc1035";
            String password="123456abc";
            Statement state=null;

            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
            state=con.createStatement();

            String sql1="select * from usermessagesheet";
            Random rand =new Random();
            int t=rand.nextInt(9000)+1000;
            sql1=sql1+t;
            System.out.println(sql1);
            //ResultSet rs=state.executeQuery(sql1);
            //rs.last();
            //int t=rs.getRow();
            //System.out.println(t);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */
    }
}

//public class Main 
//{
//	 public static void main(String[] args) 
//	 { 
//	    Server server1=new Server(7777);
//	    server1.start();
//	 }
//}
