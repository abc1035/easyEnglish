package easyEnglish;
import java.sql.*;
import java.io.*;
public class UserPhoneNumberLogin {
    public int Login(String userphone,String userpassword){
        ConnectMessage a=new ConnectMessage();
        Connection con;
        String driver="com.mysql.jdbc.Driver";
        String url=a.GetUrl();
        String user=a.GetUser();
        String password=a.GetPassword();
        Statement state=null;
        try
        {
            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            if(!con.isClosed())
                System.out.println("���������ݿ�");
            String sql1="select * from usermessagesheet where Userphonenumber='"+userphone+"'";
            ResultSet rs=state.executeQuery(sql1);
            int flag=0,res=0;
            //System.out.println("usermessagesheet�����м�¼����:");
            while(rs.next()){
                flag=flag+1;
                String word=rs.getString("Userpassword");
                if(userpassword.equals(word)){
                    res=1;
                    break;
                }
            }
            con.close();
            state.close();
            if(flag==0){
                return 0;
                //System.out.println("���û�������");
            }
            else{
                if(res==0){
                    return 1;
                    //System.out.println("�û������������");
                }
                else{
                    return 2;
                    //System.out.println("��¼�ɹ�");
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }

    }
}
