package com.example.easyenglish.Activity.ExamAcvitity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.easyenglish.R;
import com.example.easyenglish.TreeAdapter;
import com.example.easyenglish.TreePoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ExamSelectActivity extends AppCompatActivity {

    private TreeAdapter adapter;
    private ListView listView;
    private EditText et_filter;
    private TextView tv_title;
    private List<TreePoint> pointList = new ArrayList<>();
    private HashMap<String, TreePoint> pointMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        Intent it=getIntent();
        String data = it.getStringExtra("data");
        tv_title.setText(data);
        addListener(data);
    }

    public void init() {
        adapter = new TreeAdapter(this, pointList, pointMap);
        listView =  findViewById(R.id.listView);
        listView.setAdapter(adapter);
        et_filter =  findViewById(R.id.et_filter);
        tv_title=findViewById(R.id.tv_title);
        initData();
    }
    //初始化数据
    //数据特点：TreePoint 之间的关系特点   id是任意唯一的。    如果为根节点 PARENTID  为"0"   如果没有子节点，也就是本身是叶子节点的时候ISLEAF = "1"
    //  DISPLAY_ORDER 是同一级中 显示的顺序
    //如果需要做选中 单选或者多选，只需要给TreePoint增加一个选中的属性，在ReasonAdapter中处理就好了
    private void initData() {
        pointList.clear();
        int id =1000;
        int parentId = 0;
        int parentId2 = 0;
        int parentId3 = 0;
        for(int i=2016;i<2020;i++){
            id++;
            pointList.add(new TreePoint(""+id,i+"年","" + parentId,"0",i));
            for(int j=1;j<3;j++){
                if(j==1){
                    parentId2 = id;
                    id++;
                    pointList.add(new TreePoint(""+id,"6月",""+parentId2,"0",j));
                }
                else{
                    id++;
                    pointList.add(new TreePoint("" + id, "12月", "" + parentId2, "0", j));
                }
                for(int k=1;k<4;k++){
                    if(k==1){
                        parentId3 = id;
                    }
                    id++;
                    pointList.add(new TreePoint(""+id,"第"+k+"套",""+parentId3,"1",k));
                }
            }
        }
        //打乱集合中的数据
        Collections.shuffle(pointList);
        //对集合中的数据重新排序
        updateData();
    }

    public void addListener(final String s) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (s) {
                    case "四级写作专项练习":
                        adapter.onItemClickwriting(position);
                        break;
                    case "四级阅读专项练习":
                        adapter.onItemClickreading(position);
                        break;
                    case "四级翻译专项练习":
                        adapter.onItemClicktranslating(position);
                        break;
                    default:
                        adapter.onItemClicklistening(position);
                        break;
                }
            }
        });

        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchAdapter(s);
            }
        });
    }

    private void searchAdapter(Editable s) {
        adapter.setKeyword(s.toString());
    }

    //对数据排序 深度优先
    private void updateData() {
        for (TreePoint treePoint : pointList) {
            pointMap.put(treePoint.getID(), treePoint);
        }
        Collections.sort(pointList, new Comparator<TreePoint>() {
            @Override
            public int compare(TreePoint lhs, TreePoint rhs) {
                int llevel = com.example.easyenglish.Util.TreeUtils.getLevel(lhs, pointMap);
                int rlevel =com.example.easyenglish.Util.TreeUtils.getLevel(rhs, pointMap);
                if (llevel == rlevel) {
                    if (lhs.getPARENTID().equals(rhs.getPARENTID())) {  //左边小
                        return lhs.getDISPLAY_ORDER() > rhs.getDISPLAY_ORDER() ? 1 : -1;
                    } else {  //如果父辈id不相等
                        //同一级别，不同父辈
                        TreePoint ltreePoint = com.example.easyenglish.Util.TreeUtils.getTreePoint(lhs.getPARENTID(), pointMap);
                        TreePoint rtreePoint =com.example.easyenglish.Util.TreeUtils.getTreePoint(rhs.getPARENTID(), pointMap);
                        return compare(ltreePoint, rtreePoint);  //父辈
                    }
                } else {  //不同级别
                    if (llevel > rlevel) {   //左边级别大       左边小
                        if (lhs.getPARENTID().equals(rhs.getID())) {
                            return 1;
                        } else {
                            TreePoint lreasonTreePoint =com.example.easyenglish.Util.TreeUtils.getTreePoint(lhs.getPARENTID(), pointMap);
                            return compare(lreasonTreePoint, rhs);
                        }
                    } else {   //右边级别大   右边小
                        if (rhs.getPARENTID().equals(lhs.getID())) {
                            return -1;
                        }
                        TreePoint rreasonTreePoint = com.example.easyenglish.Util.TreeUtils.getTreePoint(rhs.getPARENTID(), pointMap);
                        return compare(lhs, rreasonTreePoint);
                    }
                }
            }
        });
        adapter.notifyDataSetChanged();
    }
}
