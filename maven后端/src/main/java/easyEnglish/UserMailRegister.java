package easyEnglish;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import java.util.*;
import java.sql.*;

public class UserMailRegister {
     public int send(String usermail) {
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
            System.out.println("����ʧ��");
            e.printStackTrace();
        }

        return 0;
    }
    public int register(String mail,String name,String userspassword,String vericode){
         try{//д���ݿ����
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
             while(rs.next()){//����Ƿ����ݿ��Ƿ��Ѵ���ע��������Ϣ
                 flag=flag+1;
                 break;
             }
             if(flag==0){   //������֤��
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
                     return 3;//����ע��ɹ�
                 }
                 else{
                     return 2;//������֤������ʱ
                 }


             }
             else{          //���ظ�������ע��
                return 1;
             }

         }
         catch(Exception e){
             System.out.println("ע��ʧ��");
             e.printStackTrace();
         }
         return 0;//����ע��ʧ��
    }
}
