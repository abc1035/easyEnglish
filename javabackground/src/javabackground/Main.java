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
            mail.setHostName("smtp.qq.com");//�����ʼ��ķ�����
            mail.setCharset("utf-8");
            mail.setAuthentication("1653155381@qq.com","zdrlbelheuqtdbha");//�ոռ�¼����Ȩ�룬�ǿ���SMTP������
            mail.setFrom("1653155381@qq.com","misaka19001");  //�����ʼ�������ͷ�����
            mail.setSSLOnConnect(true); //ʹ�ð�ȫ����
            mail.addTo(usermail);//���յ�����
            //System.out.println("email"+email);
            mail.setSubject("ע����֤��");//�����ʼ�������
            Random rand =new Random();
            int vericode=rand.nextInt(9000)+1000;
            mail.setMsg("�𾴵��û�:���!\n ע����֤��Ϊ:" +vericode+ "\n"+"     (��Ч��Ϊ2����)");//�����ʼ�������
            mail.send();//����
            System.out.println("���ͳɹ�");
            //��ע����������ݿ�


        } catch (Exception e) {
            System.out.println("����ʧ��");
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
