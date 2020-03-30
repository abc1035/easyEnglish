package easyEnglish;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.nlp.AipNlp;

public class BaiduSample {
	public static final String APP_ID = "18751016";
    public static final String API_KEY = "V0IuVW1mZAWPsgsctqcCxgGF";
    public static final String SECRET_KEY = "t2k1VciVyICBdhutYzKKDFf6um9YGX3E";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        //client.setConnectionTimeoutInMillis(2000);
        //client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        String text = "he is a elegant.";
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.ecnet(text, options);
        System.out.println(res.toString(2));

    }

}
