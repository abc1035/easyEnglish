package easyEnglish;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.nlp.AipNlp;

public class BaiduSample {
	public static final String APP_ID = "18751016";
    public static final String API_KEY = "V0IuVW1mZAWPsgsctqcCxgGF";
    public static final String SECRET_KEY = "t2k1VciVyICBdhutYzKKDFf6um9YGX3E";

    public static void main(String[] args) {
        // ��ʼ��һ��AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // ��ѡ�������������Ӳ���
        //client.setConnectionTimeoutInMillis(2000);
        //client.setSocketTimeoutInMillis(60000);

        // ��ѡ�����ô����������ַ, http��socket��ѡһ�����߾�������
        //client.setHttpProxy("proxy_host", proxy_port);  // ����http����
        //client.setSocketProxy("proxy_host", proxy_port);  // ����socket����

        // ���ýӿ�
        String text = "he is a elegant.";
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.ecnet(text, options);
        System.out.println(res.toString(2));

    }

}
