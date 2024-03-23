package com.example.easyenglish.Activity.WordActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSONObject;
import com.example.easyenglish.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Connect.ConnectMessage;

import static android.os.SystemClock.sleep;

public class WordSearchActivity extends Activity implements
        SearchView.OnQueryTextListener {
    private SearchView searchview;
    private ListView listview;
    private Handler handler=null;
    private String wordlist[]=new String[10];
    private String chinese[]=new String[10];
    private int number=0;
    private String str;
    // 自动完成的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);
        findViewById(R.id.word_search_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sleep(2000);
                //Intent intent = new Intent();
                //intent.putExtra("3","ok");
                //setResult(1,intent);
                //finish();
                List<HashMap<String,String>> data=new ArrayList<>();
                HashMap<String,String> map=new HashMap<>();
                map.put("text1","context");
                map.put("text2","adj. 红的，红色的；（毛发）红褐色的；（脸）涨红的；（眼睛）红肿的；革命的，激进的；（人）红种的；（纸牌中）红桃的，红方块的；（葡萄酒）红的；n. (Red) 雷德（人名）");
                data.add(map);

                map=new HashMap<>();
                map.put("text1","pretext");
                map.put("text2","n. 借口；托辞vt. 以…为借口");
                data.add(map);
                map=new HashMap<>();
                map.put("text1","text");
                map.put("text2","n. [计] 文本；课文；主题vt. 发短信");
                data.add(map);
                map=new HashMap<>();
                map.put("text1","textbook");
                map.put("text2","n. 教科书，课本");
                data.add(map);
                map=new HashMap<>();
                map.put("text1","textile");
                map.put("text2","n. 纺织品，织物adj. 纺织的");
                data.add(map);
                map=new HashMap<>();
                map.put("text1","textual");
                map.put("text2","adj. 本文的；按原文的");
                data.add(map);
                map=new HashMap<>();
                map.put("text1","textural");
                map.put("text2","adj. 组织的；结构的");
                data.add(map);
                map=new HashMap<>();
                map.put("text1","texture");
                map.put("text2","n. 质地；纹理；结构；本质，实质");
                data.add(map);

                SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                        data,
                        android.R.layout.simple_list_item_2,
                        new String []{"text1","text2"},
                        new int []{android.R.id.text1,android.R.id.text2});

                listview.setAdapter(simpleAdapter);

            }
        });

        List<HashMap<String,String>> data=new ArrayList<>();

        for(int i=0;i<15;i++){
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","name"+i);
            map.put("text2","name"+i);
            //data.add(map);
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                data,
                android.R.layout.simple_list_item_2,
                new String []{"text1","text2"},
                new int []{android.R.id.text1,android.R.id.text2});

        listview=findViewById(R.id.word_search_listview);

        listview.setAdapter(simpleAdapter);
        listview.setTextFilterEnabled(true);//设置lv可以被过虑

        searchview=findViewById(R.id.word_search_searchview);
        // 设置该SearchView默认是否自动缩小为图标
        searchview.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器
        searchview.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        //sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        searchview.setQueryHint("查找");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(WordSearchActivity.this, WordSearchedActivity.class);
                intent.putExtra("1","Activity");
                startActivityForResult(intent,1);
                /*
                switch (i){
                    case 0:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;//当我们点击某一项就能吐司我们点了哪一项

                    case 1:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Toast.makeText(WordSearchActivity.this,"你点击了"+i+"按钮",Toast.LENGTH_SHORT).show();
                        break;
                }
                */
            }
        });

    }

    // 用户输入字符时激发该方法
    @Override
    public boolean onQueryTextChange(String newText) {
        str=newText;
        sleep(500);
        /*
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(SearchRun);

        executor.shutdown();
        System.out.println("number:"+number);

         */
        if(newText.length()==0){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else if(newText.equals("text")){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","text");
            map.put("text2","n. [计] 文本；课文；主题vt. 发短信");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","textbook");
            map.put("text2","n. 教科书，课本");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","texture");
            map.put("text2","n. 质地；纹理；结构；本质，实质");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","textile");
            map.put("text2","n. 纺织品，织物adj. 纺织的");
            data.add(map);

            map=new HashMap<>();
            map.put("text1","textual");
            map.put("text2","adj. 本文的；按原文的");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","texting");
            map.put("text2","n. 发送端信息");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","textural");
            map.put("text2","adj. 组织的；结构的");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","textually");
            map.put("text2","adv. 本文地；原文地");
            data.add(map);

            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else if(newText.equals("r")){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","red");
            map.put("text2","adj. 红的，红色的；（毛发）红褐色的；（脸）涨红的；（眼睛）红肿的；革命的，激进的；（人）红种的；（纸牌中）红桃的，红方块的；（葡萄酒）红的；n. (Red) 雷德（人名）");
            data.add(map);

            map=new HashMap<>();
            map.put("text1","run");
            map.put("text2","vi. 经营；奔跑；运转vt. 管理，经营；运行；参赛n. 奔跑；赛跑；趋向；奔跑的路程n. (Run)人名；(塞)鲁恩");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rap");
            map.put("text2","n. 轻敲；指责（非正式）；说唱乐；交谈（非正式）；刑事指控（非正式）；名声；一丁点儿；极少v. 抢走；轻敲；敲击致使；使着迷；交谈；说唱");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","room");
            map.put("text2","n. 房间；空间；余地；机会；房间里所有的人vt. 为…提供住处；租房，合住；投宿，住宿；留…住宿vi. 居住；住宿n. （英）鲁姆（人名）；（俄）罗姆（人名）");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rain");
            map.put("text2","n. 雨；下雨；雨天；雨季vi. 下雨；降雨vt. 大量地给；使大量落下n. (Rain)人名；(法)兰；(英)雷恩；(罗、捷)赖恩");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","read");
            map.put("text2","vt. 阅读；读懂，理解vi. 读；读起来n. 阅读；读物adj. 有学问的n. (Read)人名；(英)里德");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","road");
            map.put("text2","n. 公路，马路；道路；手段vt. （狗）沿臭迹追逐（猎物）adj. （美）巡回的n. (Road)人名；(英)罗德");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","right");
            map.put("text2","adj. 正确的；直接的；右方的vi. 复正；恢复平稳n. 正确；右边；正义；权利adv. 正确地；恰当地；彻底地vt. 纠正n. (Right)人名；(英)赖特");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rich");
            map.put("text2","adj. 富有的；肥沃的；昂贵的adj. 油腻的，含有很多脂肪n. (Rich)人名；(丹)里克；(捷)里赫；(英、西)里奇；(葡、法)里什");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rice");
            map.put("text2","n. 稻；米饭vt. 把…捣成米糊状n. (Rice)人名；(瑞典)里瑟；(塞)里采；(英)赖斯");
            data.add(map);
            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else if (newText.equals("re")){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","red");
            map.put("text2","adj. 红的，红色的；（毛发）红褐色的；（脸）涨红的；（眼睛）红肿的；革命的，激进的；（人）红种的；（纸牌中）红桃的，红方块的；（葡萄酒）红的；（表示停止）红（灯），红（旗）；被禁止的，危险的；（滑雪道上用红色标志指示）第二高难度的；（物理）表示夸克三种颜色之一的红色；赤色的（尤指冷战期间用于指前苏联）；沾有鲜血的；（古或诗/文）流血的；（科萨人）来自传统部落文化的n. 红色，红颜料；红衣；红葡萄酒；红色物（或人）；赤字，亏空；激进分子n. (Red) 雷德（人名）");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","read");
            map.put("text2","vt. 阅读；读懂，理解vi. 读；读起来n. 阅读；读物adj. 有学问的n. (Read)人名；(英)里德");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","real");
            map.put("text2","adj. 实际的；真实的；实在的adv. 真正地；确实地n. 现实；实数n. (Real)人名；(德、西、葡、法)雷亚尔；(英)里尔");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rely");
            map.put("text2","vi. 依靠；信赖");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rest");
            map.put("text2","v. （使）休息；（使）运动员暂时离队；（使）倚靠；被搁置；（遗体或尸体）安葬于；让（土地）休耕；（原告或被告）完成向法庭提出证据（或辩论）；归属于；保持不变n. 休息；睡眠；静止；休止；休止符；（演说中的）停顿；（诗句中的）停顿；休息处；支架；剩余部分；其余的人（或物）；（器官或组织的）一小碎块；（网球）对打n. (Rest) （美）雷斯特（人名）");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","ready");
            map.put("text2","adj. 准备好；现成的；迅速的；情愿的；快要…的n. 现款；预备好的状态adv. 迅速地；预先vt. 使准备好n. (Ready)人名；(英)雷迪");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rent");
            map.put("text2","n. 租金vt. 出租；租用；租借vi. 租；出租n. (Rent)人名；(瑞典)伦特");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","reply");
            map.put("text2","vi. 回答；[法] 答辩；回击n. 回答；[法] 答辩vt. 回答；答复");
            data.add(map);
            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else if (newText.equals("rev")){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();

            map=new HashMap<>();
            map.put("text1","review");
            map.put("text2","n. 回顾；复习；评论；检讨；检阅vt. 回顾；检查；复审vi. 回顾；复习功课；写评论");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revive");
            map.put("text2","vi. 复兴；复活；苏醒；恢复精神vt. 使复兴；使苏醒；回想起；重演，重播");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revival");
            map.put("text2","n. 复兴；复活；苏醒；恢复精神；再生效");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","reveal");
            map.put("text2","vt. 显示；透露；揭露；泄露n. 揭露；暴露；门侧，窗侧");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revise");
            map.put("text2","vt. 修正；复习；校订vi. 修订；校订；复习功课n. 修订；校订n. (Revise)人名；(法)勒维斯");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revenge");
            map.put("text2","n. 报复；复仇vt. 报复；替…报仇；洗雪vi. 报仇；雪耻");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revolt");
            map.put("text2","n. 违抗，反抗；起义，叛乱v. 反抗，反叛；叛逆，违抗；（使）厌恶，反感");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","reverse");
            map.put("text2","v. 颠倒；撤销；反转；交换；放弃立场；倒车；打对方付费的电话；（使铅字、图案）印成白或浅色n. 逆向；相反；背面；倒档；失败；（美橄）横式传球；（翻开书的）左手页adj. 相反的；背面的；颠倒的；反身的；（地层）逆断的");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revenue");
            map.put("text2","n. 税收收入；财政收入；收益");
            data.add(map);
            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else if (newText.equals("revi")){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","review");
            map.put("text2","n. 回顾；复习；评论；检讨；检阅vt. 回顾；检查；复审vi. 回顾；复习功课；写评论");
            data.add(map);

            map=new HashMap<>();
            map.put("text1","revive");
            map.put("text2","vi. 复兴；复活；苏醒；恢复精神vt. 使复兴；使苏醒；回想起；重演，重播");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revival");
            map.put("text2","n. 复兴；复活；苏醒；恢复精神；再生效");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revise");
            map.put("text2","vt. 修正；复习；校订vi. 修订；校订；复习功课n. 修订；校订n. (Revise)人名；(法)勒维斯");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revision");
            map.put("text2","n. [印刷] 修正；复习；修订本");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","reviewer");
            map.put("text2","n. 评论者，评论家");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revitalize");
            map.put("text2","vt. 使…复活；使…复兴；使…恢复生气");
            data.add(map);

            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else if (newText.equals("reviv")){
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","revive");
            map.put("text2","vi. 复兴；复活；苏醒；恢复精神vt. 使复兴；使苏醒；回想起；重演，重播");
            data.add(map);

            map=new HashMap<>();
            map.put("text1","revival");
            map.put("text2","n. 复兴；复活；苏醒；恢复精神；再生效");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revivify");
            map.put("text2","vt. 使再生，使复活；使振奋精神vi. 复活，再生；振奋精神");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","review");
            map.put("text2","n. 回顾；复习；评论；检讨；检阅vt. 回顾；检查；复审vi. 回顾；复习功课；写评论");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revise");
            map.put("text2","vt. 修正；复习；校订vi. 修订；校订；复习功课n. 修订；校订n. (Revise)人名；(法)勒维斯");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","rein");
            map.put("text2","n. 缰绳；驾驭；统治；支配vt. 控制；驾驭；勒住vi. 勒住马n. (Rein)人名；(西、阿拉伯)雷因；(法)兰；(英、荷、捷、德、芬、瑞典)赖因；(爱沙)雷恩");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","relic");
            map.put("text2","n. 遗迹，遗物；废墟；纪念物");
            data.add(map);

            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        else{
            List<HashMap<String,String>> data=new ArrayList<>();
            HashMap<String,String> map=new HashMap<>();
            map.put("text1","revive");
            map.put("text2","vi. 复兴；复活；苏醒；恢复精神vt. 使复兴；使苏醒；回想起；重演，重播");
            data.add(map);

            map=new HashMap<>();
            map.put("text1","remove");
            map.put("text2","vt. 移动，迁移；开除；调动vi. 移动，迁移；搬家n. 移动；距离；搬家");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revise");
            map.put("text2","vt. 修正；复习；校订vi. 修订；校订；复习功课n. 修订；校订n. (Revise)人名；(法)勒维斯");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","receive");
            map.put("text2","vt. 收到；接待；接纳vi. 接收");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","review");
            map.put("text2","n. 回顾；复习；评论；检讨；检阅vt. 回顾；检查；复审vi. 回顾；复习功课；写评论");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","revival");
            map.put("text2","n. 复兴；复活；苏醒；恢复精神；再生效");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","relieve");
            map.put("text2","vt. 解除，减轻；使不单调乏味；换……的班；解围；使放心");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","river");
            map.put("text2","n. 河，江n. (River)人名；(英)里弗");
            data.add(map);
            map=new HashMap<>();
            map.put("text1","allied");
            map.put("text2","v. 与……结盟，联合（ally 的过去式和过去分词）adj. 结盟的，联盟的；同盟国的；类似的；共存的；有关联的");
            data.add(map);
            SimpleAdapter simpleAdapter=new SimpleAdapter(WordSearchActivity.this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String []{"text1","text2"},
                    new int []{android.R.id.text1,android.R.id.text2});

            listview.setAdapter(simpleAdapter);
        }
        return true;
    }

    // 单击搜索按钮时激发该方法
    @Override
    public boolean onQueryTextSubmit(String query) {
        // 实际应用中应该在该方法内执行实际查询
        // 此处仅使用Toast显示用户输入的查询内容
        //Toast.makeText(this, "您的选择是:" + query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


                if(resultCode == 2){
                    //取消逻辑处理
                    //Toast.makeText(this, "您的选择是:" + data.getStringExtra("3"), Toast.LENGTH_SHORT).show();
                }

    }
    ConnectMessage a=new ConnectMessage();
    Runnable SearchRun=new Runnable() {
        @Override
        public void run() {
            try{

                //15 单词英文 -> 匹配所得单词
                JSONObject object = new JSONObject();
                object.put("jsonid", "15");
                object.put("wordenglish", str);
                //System.out.println(wordlist[process]);
                String result = object.toString();//json输出部分

                Socket socket = new Socket(a.GetIpAddress(), a.GetPort());//此处连接后端
                OutputStream os = socket.getOutputStream();
                DataOutputStream out=new DataOutputStream(os);
                out.writeUTF(result);// 向服务器传送json信息

                InputStream is = socket.getInputStream();
                DataInputStream in=new DataInputStream(is);
                String str = in.readUTF();//读入运行结果

                object = JSONObject.parseObject(str);//转化为json
                String s=object.getString("wordnumber");
                number=Integer.parseInt(s);
                for(int i=0;i<number;i++){
                    wordlist[i]=object.getString("wordenglish"+i);
                }

            }
            catch(Exception e){
                String TAG="word";
                Log.e(TAG,Log.getStackTraceString(e));
            }
        }
    };

}