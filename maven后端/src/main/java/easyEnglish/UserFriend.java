package javabackground;
import java.sql.*;
import java.util.Random;
/*
    Userfriend   返回该用户所有好友的昵称，按%分隔
    Findfriend   返回关于该用户名的检索结果，按%分隔
    FinduserId   用户名->id号
    userinfo     id号->用户信息
    Append       输入用户、好友id，添加好友
    Delete       输入用户、好友id，删除好友
    VocPK        输入当前难度，上次胜负情况，返回单词及释义
    Findtheword  返回8*8单词格子
 */
public class UserFriend {
    public String UserFriend(String users){//查找用户好友
        //返回该用户所有好友的昵称，按%分隔
        try{
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();

            String sql1;
            sql1="select Friendid from Friendsheet where Userid='"+users+"'";
            ResultSet rs=state.executeQuery(sql1);
            rs.next();
            String s=rs.getString("Friendid");
            con.close();
            return s;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String FindFriend(String friend){//查找好友(好友名)
        //返回关于该用户名的检索结果，按空格分隔
        try{
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            String sql1;
            sql1="select * from usermessagesheet where Username like '%"+friend+"%'";
            ResultSet rs=state.executeQuery(sql1);
            String s="";
            String t="";
            while(rs.next()){
                t=rs.getString("Username");
                s=s+t+"%";
            }
            con.close();
            return s;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String FindUserId(String friend){
        //返回关于该用户名的id号
        try{
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            String sql1;
            sql1="select * from usermessagesheet where Username='"+friend+"'";
            ResultSet rs=state.executeQuery(sql1);
            String s="";
            while(rs.next()){
                s=rs.getString("Userid");
            }
            con.close();
            return s;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String Userinfo(String id){
        //id号->id%用户名%邮箱%手机号
        try{
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            String sql1;
            sql1="select * from usermessagesheet where Userid='"+id+"'";
            ResultSet rs=state.executeQuery(sql1);
            String s="";
            while(rs.next()){
                s=rs.getString("Userid")+"%";
                s=s+rs.getString("Username")+"%";
                s=s+rs.getString("Usermail")+"%";
                s=s+rs.getString("Userphonenumber")+"%";
            }
            con.close();
            return s;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public int Append(String users,String friend){//添加好友
        try{
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();

            String sql1;
            sql1="select * from Friendsheet where userid='"+users+"' and friendid like '%"+friend+"%'";
            ResultSet rs=state.executeQuery(sql1);
            int flag=0;
            while(rs.next()){
                flag=flag+1;
                break;
            }
            if(flag==0){
                sql1="select Friendid from Friendsheet where userid='"+users+"'";
                rs=state.executeQuery(sql1);
                rs.next();
                String friends=rs.getString("Friendid");
                int len=friends.length();
                friends=friends.substring(0,len-1)+"%"+friend+"}";
                sql1="update Friendsheet set Friendid='"+friends+"' where Userid='"+users+"'";
                state.execute(sql1);
                con.close();
                return 2;
            }
            else{
                con.close();
                return 1;
                //System.out.println("已加为好友");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
        //返回2添加成功 返回1已加为好友 返回0添加失败
    }
    public int Delete(String users,String friend){//删除好友
        try{
            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();
            String sql1;
            sql1="select * from Friendsheet where userid='"+users+"' and friendid like '%"+friend+"%'";
            ResultSet rs=state.executeQuery(sql1);
            int flag=0;
            while(rs.next()){
                flag=flag+1;
                break;
            }
            if(flag==1){
                sql1="select Friendid from Friendsheet where Userid='"+users+"'";
                rs=state.executeQuery(sql1);
                rs.next();
                String s=rs.getString("Friendid");

                String[] sp=s.split("%"+friend);
                s=sp[0]+sp[1];
                sql1="update Friendsheet set Friendid='"+s+"' where Userid='"+users+"'";
                state.execute(sql1);

                con.close();
                return 2;
            }
            else{
                con.close();
                return 1;
                //System.out.println("好友不存在");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
        //返回2删除成功 返回1好友不存在 返回0删除失败
    }

    public String VocPK(int diff,int rate){//单词对战
        try{
            if(rate==2){//2人均答对
                diff=diff==100?diff:diff+2;
            }
            else if(rate==1){//1人答对

            }
            else{//均未答对
                diff=diff==2?diff:diff-2;
            }

            ConnectMessage a=new ConnectMessage();
            Connection con;
            String driver="com.mysql.jdbc.Driver";
            String url=a.GetUrl();
            String user=a.GetUser();
            String password=a.GetPassword();
            Statement state=null;

            Class.forName(driver);
            con= DriverManager.getConnection(url, user, password);
            state=con.createStatement();

            String sql1;
            sql1="select * from Vocabularysheet where wordproblem='"+diff+"' order by rand() limit 1";
            ResultSet rs=state.executeQuery(sql1);
            rs.next();
            String word=rs.getString("WordEnglish");
            String tran[]=new String[4];
            tran[0]=rs.getString("wordchinese");

            sql1="select * from Vocabularysheet where wordproblem<>'diff' order by rand() limit 3";
            rs=state.executeQuery(sql1);
            for(int i=1;i<=3;i++){
                rs.next();
                tran[i]=rs.getString("wordchinese");
            }
            Random rand =new Random();
            int t=rand.nextInt(4);
            String temp=tran[0];
            tran[0]=tran[t];
            tran[t]=temp;
            con.close();
            return word+"%"+t+"%"+tran[0]+"%"+tran[1]+"%"+tran[2]+"%"+tran[3]+"%";

            /*
            int flag=0;
            while(rs.next()){
                flag=flag+1;
                break;
            }
            */
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    public String Findtheword(){//找单词游戏
        //8*8格子 行与行间用%分隔开
        try{
            int d=8;
            char s[][]=new char[d][d];
            Random rand =new Random();
            for(int i=0;i<d;i++){
                for(int j=0;j<d;j++){
                    int t=rand.nextInt(25)+97;
                    s[i][j]=(char)t;
                }
            }

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
            String sql1="SELECT *\n" +
                    "FROM Vocabularysheet AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(wordproblem) FROM Vocabularysheet)-(SELECT MIN(wordproblem) FROM Vocabularysheet))+(SELECT MIN(wordproblem) FROM Vocabularysheet)) AS wordproblem) AS t2\n" +
                    "WHERE t1.wordproblem >= t2.wordproblem\n" +
                    "ORDER BY t1.Wordsentence LIMIT 200;";
            String sql2="select * from Vocabularysheet order by wordproblem limit 200";
            ResultSet rs=state.executeQuery(sql1);

            rs.next();

            for(int cas=0;cas<20;cas++){
                int t=rand.nextInt(5)+1;
                int flag=1;
                if(flag==0) break;
                for(int cir=0;cir<t;cir++){
                    if(rs.next()){
                        flag=0;
                        break;
                    }
                }
                String s1=rs.getString("WordEnglish");
                int len=s1.length();
                //System.out.println("firstthingsfirst:"+s1);
                if(len>d) continue;
                //System.out.println("first:"+s1);
                int x=rand.nextInt(d+1-len);
                int y=rand.nextInt(d+1-len);
                int pos=rand.nextInt(2);
                if(pos==0){//横置
                    for(int i=0;i<len;i++)
                        s[x][y+i]=s1.charAt(i);
                }
                else{//纵置
                    for(int i=0;i<len;i++)
                        s[x+i][y]=s1.charAt(i);
                }
            }
            ResultSet rs2=state.executeQuery(sql2);
            for(int cas=0;cas<20;cas++){
                int t=rand.nextInt(5)+1;
                int flag=1;
                if(flag==0) break;
                for(int cir=0;cir<t;cir++){
                    if(rs2.next()){
                        flag=0;
                        break;
                    }
                }
                String s1=rs2.getString("WordEnglish");
                int len=s1.length();
                if(len>d) continue;
                if(s1.equals("God"))
                    s1="god";
                //System.out.println(s1);
                int x=rand.nextInt(d+1-len);
                int y=rand.nextInt(d+1-len);
                int pos=rand.nextInt(2);
                if(pos==0){//横置
                    for(int i=0;i<len;i++)
                        s[x][y+i]=s1.charAt(i);
                }
                else{//纵置
                    for(int i=0;i<len;i++)
                        s[x+i][y]=s1.charAt(i);
                }
            }

        /*
        for(int i=0;i<d;i++){
            for(int j=0;j<d;j++){
                System.out.print(s[i][j]);
                System.out.print(" ");
            }
            System.out.println('\n');
        }
        */
            String str= "";
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++)
                    str=str+ Character.toString(s[i][j]);
                str=str+ Character.toString('%');
            }

            con.close();
            return str;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
