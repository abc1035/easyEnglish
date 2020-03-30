package easyEnglish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import com.ucpaas.restDemo.client.AbsRestClient;
import com.ucpaas.restDemo.client.JsonReqClient;


public class RestTest {

	static AbsRestClient InstantiationRestAPI() {
		return new JsonReqClient();
	}

	public static void testSendSms(String sid, String token, String appid, String templateid, String param, String mobile, String uid){
		try {
			String result=InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static int send(String phone){
		try {

			Random rand =new Random();
			int vericode=rand.nextInt(9000)+1000;

			String sid = "74b2e95b4007bc014bcbdc2a9a7f54b6";
			String token = "d5a275de5673db0b370b572d824f8ca5";
			String appid = "ad1eb9636a70497fb7d82222e06e821f";
			String templateid = "534435";
			String param = ""+vericode;
			String mobile = phone;
			String uid = "";
			testSendSms(sid, token, appid, templateid, param, mobile, uid);
			//��ע����������ݿ�
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
			sql1="insert temporarycode value('"+phone+"','"+vericode+"',now())";
			state.execute(sql1);
			System.out.println("���ͳɹ�");
			return 1;

		} catch (Exception e) {
			System.out.println("����ʧ��");
			e.printStackTrace();
		}
		return 0;
	}
	public int register(String phone,String name,String userspassword,String vericode){
		try{//д���ݿ����
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
			sql1="select * from usermessagesheet where Userphonenumber='"+phone +"'";
			ResultSet rs=state.executeQuery(sql1);
			int flag=0;
			while(rs.next()){//����Ƿ����ݿ��Ƿ��Ѵ���ע���ֻ�����Ϣ
				flag=flag+1;
				break;
			}
			if(flag==0){   //������֤��
				int status=0;
				String idcode=null;
				sql1="select verificationcode from temporarycode where user='"+phone+"'";
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

					sql1="insert into usermessagesheet value("+id+",'"+name+"','"+id+"',"+phone+",'"+userspassword+"')";
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
