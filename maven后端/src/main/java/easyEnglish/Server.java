package javabackground;
import java.io.*;
import java.net.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class Server extends Thread
{
    public Server(int port)
    {
        try
        {
            this. port=port;
            server=new ServerSocket(port);
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
    int port;
    ServerSocket server;
    Socket socket;
    PrintWriter  out = null;
    BufferedReader in = null;

    public void run()
    {
        try
        {
            server=new ServerSocket(6666);
            while(true)
            {
                System.out.println("服务器回归原点");//调试信息
                socket=server.accept();
                System.out.println("接到客户端socket请求");//调试信息
                OutputStream os=socket.getOutputStream();
                DataOutputStream out=new DataOutputStream(os);
                InputStream is1=socket.getInputStream();
                DataInputStream in=new DataInputStream(is1);
                String str=in.readUTF();
                JSONObject object=JSONObject.parseObject(str);
                String id=object.getString("jsonid");

                if(id.equals("1"))//手机号登录
                {
                    String phone=object.getString("userphonenumber");
                    String password=object.getString("userpassword");
                    System.out.println(phone+" "+password);
                    UserPhoneNumberLogin a=new UserPhoneNumberLogin();
                    int t=a.Login(phone,password);
                    object.put("status",t);
                    //System.out.println("t="+t);
                    //t=0 该用户不存在
                    //t=1 用户名或密码错误
                    //t=2 登录成功
                }
                if(id.equals("2"))//邮箱登录
                {
                    String mail=object.getString("mail");
                    String password=object.getString("userpassword");
                    UserMailLogin a=new UserMailLogin();
                    int t=a.Login(mail,password);
                    object.put("status",t);
                    //t=0 该用户不存在
                    //t=1 用户名或密码错误
                    //t=2 登录成功
                }
                if(id.equals("3"))//电子邮件注册(发送验证码)
                {
                    String mail=object.getString("mail");
                    UserMailRegister a=new UserMailRegister();
                    int t=a.send(mail);
                    object.put("status",t);
                    //t=0 发送失败
                    //t=1 发送成功

                }
                if(id.equals("4"))//电子邮件注册(注册)
                {
                    String mail=object.getString("mail");
                    String username=object.getString("username");
                    String password=object.getString("userpassword");
                    String vericode=object.getString("verificationcode");
                    UserMailRegister a=new UserMailRegister();

                    int t=a.register(mail,username,password,vericode);
                    object.put("status",t);
                    //t=0 注册失败
                    //t=1 该邮箱已注册
                    //t=2 验证码错误或超时
                    //t=3 注册成功
                }


                if(id.equals("5"))//手机注册(发送验证码)
                {
                    String phone=object.getString("userphonenumber");
                    RestTest a=new RestTest();
                    int t=a.send(phone);
                    object.put("status",t);
                    //t=0 发送失败
                    //t=1 发送成功

                }

                if(id.equals("6"))//手机注册(注册)
                {
                    String phone=object.getString("userphonenumber");
                    String username=object.getString("username");
                    String password=object.getString("userpassword");
                    String vericode=object.getString("verificationcode");
                    RestTest a=new RestTest();
                    int t=a.register(phone,username,password,vericode);
                    object.put("status",t);
                    //t=0 注册失败
                    //t=1 该手机号已注册
                    //t=2 验证码错误或超时
                    //t=3 注册成功
                }
                if(id.equals("7"))//查询用户好友
                {
                    String userid=object.getString("userid");
                    UserFriend a=new UserFriend();
                    String t=a.UserFriend(userid);
                    object.put("friendinformation",t);
                    //返回该用户所有好友的昵称，按%分隔
                }

                if(id.equals("8"))//按用户名搜索
                {
                    String username=object.getString("username");
                    UserFriend a=new UserFriend();
                    String t=a.FindFriend(username);
                    object.put("friendinformation",t);
                    //返回关于该用户名的检索结果，按%分隔
                }
                if(id.equals("9"))//用户名->id号
                {
                    String username=object.getString("username");
                    UserFriend a=new UserFriend();
                    String t=a.FindUserId(username);
                    object.put("friendinformation",t);

                }
                if(id.equals("10"))//添加好友
                {
                    String userid=object.getString("username");
                    String friendid=object.getString("friendinformation");
                    UserFriend a=new UserFriend();
                    int t=a.Append(userid,friendid);
                    object.put("friendinformation",t);
                    //返回2添加成功 返回1已加为好友 返回0添加失败
                }
                if(id.equals("11"))//删除好友
                {
                    String userid=object.getString("username");
                    String friendid=object.getString("friendinformation");
                    UserFriend a=new UserFriend();
                    int t=a.Delete(userid,friendid);
                    object.put("friendinformation",t);
                    //返回2删除成功 返回1好友不存在 返回0删除失败
                }
                if(id.equals("12"))//找单词游戏
                {
                    UserFriend a=new UserFriend();
                    String t=a.Findtheword();
                    object.put("game",t);
                    //返回8*8格子 行与行间用%分隔开
                }
                final String result=object.toString();
                out.writeUTF(result);

                in.close();
                out.close();
                //socket.close();
                //server.close();
            }

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
