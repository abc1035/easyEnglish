package easyEnglish;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.util.*;
import java.sql.*;

public class UserMailRegister {
     public int send(String usermail) {
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
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            String sql1;
            sql1="insert temporarycode value('"+usermail+"','"+vericode+"',now())";
            state.execute(sql1);
            return 1;

        } catch (Exception e) {
            System.out.println("发送失败");
            e.printStackTrace();
        }

        return 0;
    }
    public int register(String mail,String name,String userspassword,String vericode){
         try{//写数据库查重
             ConnectMessage a=new ConnectMessage();
             Connection con;
             String driver="com.mysql.jdbc.Driver";
             String url=a.GetUrl();
             String user=a.GetUser();
             String password=a.GetPassword();
             Statement state=null;

             Class.forName(driver);
             con=DriverManager.getConnection(url, user, password);
             state=con.createStatement();
             String sql1;
             sql1="select * from usermessagesheet where Usermail='"+mail+"'";
             ResultSet rs=state.executeQuery(sql1);
             int flag=0;
             while(rs.next()){//检查是否数据库是否已存在注册邮箱信息
                 flag=flag+1;
                 break;
             }
             if(flag==0){   //检验验证码
                 int status=0;
                 String idcode=null;
                 sql1="select verificationcode from temporarycode where user='"+mail+"'";
                 rs=state.executeQuery(sql1);
                 while(rs.next()){
                     idcode=rs.getString("verificationcode");
                     break;
                 }
                 if(idcode.equals(vericode)){
                     sql1="select * from usermessagesheet";
                     rs=state.executeQuery(sql1);
                     rs.last();
                     int id=rs.getRow()+1;

                     sql1="insert into usermessagesheet value("+id+",'"+name+"','"+mail+"',"+id+",'"+userspassword+"')";
                     state.execute(sql1);
                     return 3;//返回注册成功
                 }
                 else{
                     return 2;//返回验证码错误或超时
                 }


             }
             else{          //返回该邮箱已注册
                return 1;
             }

         }
         catch(Exception e){
             System.out.println("注册失败");
             e.printStackTrace();
         }
         return 0;//返回注册失败
    }
}
