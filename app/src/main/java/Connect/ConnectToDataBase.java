package Connect;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import java.sql.*;
import java.lang.String;
public class ConnectToDataBase
{
    ConnectMessage a=new ConnectMessage();
    public int userlogin(String Account,String Password)//传递账号（手机号，邮箱任意）和密码，返回登录结果
    {
        boolean judge=false;
        for(int i=0;i<Account.length();i++)
        {
            if(Account.charAt(i)=='@')
            {
                judge=true;
                break;
            }
        }
        if(judge==true)
        {
            return studentmaillogin(Account,Password);
        }
        else
        {
            return studentphonelogin(Account,Password);
        }

    }
    public int studentphonelogin(String Userphonenumber,String Password)//手机号登录，成功返回1，不成功返回0
    {
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
        }catch(Exception e)
        {
            String TAG="abc";
            Log.e(TAG,Log.getStackTraceString(e));
            return 0;
        }

        String Sql="select * from usermessagesheet where Userphonenumber='"+Userphonenumber+"';";
        try
        {
            ResultSet res=state.executeQuery(Sql);
            if(res==null)
            {
                res.close();
                return 0;
            }
            else
            {
                res.next();
                String mima=res.getString("password");
                res.close();
                if(mima.equals(Password))
                {
                    return 1;
                }
                else return 0;
            }
        }catch(Exception e)
        {
            return 0;
        }
        finally
        {
            try
            {
                state.close();
                con.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }
    public int studentmaillogin(String Usermail,String Password)//密码登录，成功返回1，不成功返回0
    {
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
        }catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }

        String Sql="select * from usermessagesheet where Usermail='"+Usermail+"';";
        try
        {
            ResultSet res=state.executeQuery(Sql);
            if(res==null)
            {
                res.close();
                return 0;
            }
            else
            {
                res.next();
                String mima=res.getString("password");
                res.close();
                if(mima.equals(Password))
                {
                    return 1;
                }
                else return 0;
            }
        }catch(Exception e)
        {
            return 0;
        }
        finally
        {
            try
            {
                state.close();
                con.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
