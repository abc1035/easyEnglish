package easyEnglish;
import java.sql.*;

public class UserMailLogin {
    public int Login(String usermail,String userpassword){
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
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            if(!con.isClosed())
                System.out.println("已连接数据库");
            String sql1="select * from usermessagesheet where Userphonenumber='"+usermail+"'";
            ResultSet rs=state.executeQuery(sql1);
            int flag=0,res=0;
            //System.out.println("usermessagesheet中所有记录如下:");
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
                //System.out.println("该用户不存在");
            }
            else{
                if(res==0){
                    return 1;
                    //System.out.println("用户名或密码错误");
                }
                else{
                    return 2;
                    //System.out.println("登录成功");
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }



    }
}
