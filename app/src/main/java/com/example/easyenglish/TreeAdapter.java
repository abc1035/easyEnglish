package com.example.easyenglish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.example.easyenglish.Activity.ExamAcvitity.ListeningActivity;
import com.example.easyenglish.Activity.ExamAcvitity.ReadingActivity;
import com.example.easyenglish.Activity.ExamAcvitity.TranslatingActivity;
import com.example.easyenglish.Activity.ExamAcvitity.WritingActivity;
import com.example.easyenglish.Util.DensityUtil;
import com.example.easyenglish.Util.TreeUtils;

import java.util.HashMap;
import java.util.List;

public class TreeAdapter extends BaseAdapter {
    private Context mcontext;
    private Activity mActivity;
    private List<TreePoint> pointList;
    private String keyword = "";
    private HashMap<String, TreePoint> pointMap = new HashMap<>();

    private int operateMode = ModeSelect;
    //两种操作模式  点击 或者 选择
    private static final int ModeClick = 1;
    private static final int ModeSelect = 2;

    @IntDef({ModeClick,ModeSelect})
    public @interface Mode{

    }

    //设置操作模式
    public void setOperateMode(@Mode int operateMode){
        this.operateMode = operateMode;
    }

    public TreeAdapter(final Context mcontext, List<TreePoint> pointList, HashMap<String, TreePoint> pointMap) {
        this.mcontext = mcontext;
        this.mActivity = (Activity) mcontext;
        this.pointList = pointList;
        this.pointMap = pointMap;
    }

    /**
     * 搜索的时候，先关闭所有的条目，然后，按照条件，找到含有关键字的数据
     * 如果是叶子节点，
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
        for (TreePoint treePoint : pointList) {
            treePoint.setExpand(false);
        }
        if (!"".equals(keyword)) {
            for (TreePoint tempTreePoint : pointList) {
                if (tempTreePoint.getNNAME().contains(keyword)) {
                    if ("0".equals(tempTreePoint.getISLEAF())) {   //非叶子节点
                        tempTreePoint.setExpand(true);
                    }
                    //展开从最顶层到该点的所有节点
                    openExpand(tempTreePoint);
                }
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 从TreePoint开始一直展开到顶部
     * @param treePoint
     */
    private void openExpand(TreePoint treePoint) {
        if ("0".equals(treePoint.getPARENTID())) {
            treePoint.setExpand(true);
        } else {
            pointMap.get(treePoint.getPARENTID()).setExpand(true);
            openExpand(pointMap.get(treePoint.getPARENTID()));
        }
    }

    //第一要准确计算数量
    @Override
    public int getCount() {
        int count = 0;
        for (TreePoint tempPoint : pointList) {
            if ("0".equals(tempPoint.getPARENTID())) {
                count++;
            } else {
                if (getItemIsExpand(tempPoint.getPARENTID())) {
                    count++;
                }
            }
        }
        return count;
    }

    //判断当前Id的tempPoint是否展开了
    private boolean getItemIsExpand(String ID) {
        for (TreePoint tempPoint : pointList) {
            if (ID.equals(tempPoint.getID())) {
                return tempPoint.isExpand();
            }
        }
        return false;
    }

    @Override
    public Object getItem(int position) {
        return pointList.get(convertPostion(position));
    }

    private int convertPostion(int position) {
        int count = 0;
        for (int i = 0; i < pointList.size(); i++) {
            TreePoint treePoint = pointList.get(i);
            if ("0".equals(treePoint.getPARENTID())) {
                count++;
            } else {
                if (getItemIsExpand(treePoint.getPARENTID())) {
                    count++;
                }
            }
            if (position == (count - 1)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adapter_treeview, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
            convertView.setBackgroundResource(R.drawable.bg_imagebutton);
        } else {
            holder = (ViewHolder) convertView.getTag();
            convertView.setBackgroundResource(R.drawable.bg_imagebutton);
        }
        final TreePoint tempPoint = (TreePoint) getItem(position);
        int level = TreeUtils.getLevel(tempPoint,pointMap);
        holder.icon.setPadding(25 * level, holder.icon.getPaddingTop(), 20, holder.icon.getPaddingBottom());
        if ("0".equals(tempPoint.getISLEAF())) {  //如果为父节点
            if (!tempPoint.isExpand()) {    //不展开显示向下箭头
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setImageResource(R.drawable.arrow_down);
            } else {                        //展开显示向上箭头
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setImageResource(R.drawable.arrow_up);
            }
        } else {   //如果叶子节点，不占位显示
            holder.icon.setVisibility(View.INVISIBLE);
        }

        //如果存在搜索关键字
        if (keyword != null && !"".equals(keyword) && tempPoint.getNNAME().contains(keyword)) {
            int index = tempPoint.getNNAME().indexOf(keyword);
            int len = keyword.length();
            Spanned temp = Html.fromHtml(tempPoint.getNNAME().substring(0, index)
                    + "<font color=#FF0000>"
                    + tempPoint.getNNAME().substring(index, index + len) + "</font>"
                    + tempPoint.getNNAME().substring(index + len, tempPoint.getNNAME().length()));
            holder.text.setText(temp);
        } else {
            holder.text.setText(tempPoint.getNNAME());
        }
        holder.text.setCompoundDrawablePadding(DensityUtil.dip2px(mcontext, 10));
        return convertView;
    }

    public void onItemClickwriting(int position) {
        TreePoint treePoint = (TreePoint) getItem(position);
        if ("1".equals(treePoint.getISLEAF())) {   //点击叶子节点
            //处理回填
            mcontext.startActivity(new Intent(mcontext, WritingActivity.class));
        } else {  //如果点击的是父类
            if (treePoint.isExpand()) {
                for (TreePoint tempPoint : pointList) {
                    if (tempPoint.getPARENTID().equals(treePoint.getID())) {
                        if ("0".equals(treePoint.getISLEAF())) {
                            tempPoint.setExpand(false);
                        }
                    }
                }
                treePoint.setExpand(false);
            } else {
                treePoint.setExpand(true);
            }
        }
        this.notifyDataSetChanged();
    }

    public void onItemClicklistening(int position) {
        TreePoint treePoint = (TreePoint) getItem(position);
        if ("1".equals(treePoint.getISLEAF())) {   //点击叶子节点
            //处理回填
            mcontext.startActivity(new Intent(mcontext, ListeningActivity.class));
        } else {  //如果点击的是父类
            if (treePoint.isExpand()) {
                for (TreePoint tempPoint : pointList) {
                    if (tempPoint.getPARENTID().equals(treePoint.getID())) {
                        if ("0".equals(treePoint.getISLEAF())) {
                            tempPoint.setExpand(false);
                        }
                    }
                }
                treePoint.setExpand(false);
            } else {
                treePoint.setExpand(true);
            }
        }
        this.notifyDataSetChanged();
    }

    public void onItemClickreading(int position) {
        TreePoint treePoint = (TreePoint) getItem(position);
        if ("1".equals(treePoint.getISLEAF())) {   //点击叶子节点
            //处理回填
            mcontext.startActivity(new Intent(mcontext, ReadingActivity.class));
        } else {  //如果点击的是父类
            if (treePoint.isExpand()) {
                for (TreePoint tempPoint : pointList) {
                    if (tempPoint.getPARENTID().equals(treePoint.getID())) {
                        if ("0".equals(treePoint.getISLEAF())) {
                            tempPoint.setExpand(false);
                        }
                    }
                }
                treePoint.setExpand(false);
            } else {
                treePoint.setExpand(true);
            }
        }
        this.notifyDataSetChanged();
    }

    public void onItemClicktranslating(int position) {
        TreePoint treePoint = (TreePoint) getItem(position);
        if ("1".equals(treePoint.getISLEAF())) {   //点击叶子节点
            //处理回填
            mcontext.startActivity(new Intent(mcontext, TranslatingActivity.class));
        } else {  //如果点击的是父类
            if (treePoint.isExpand()) {
                for (TreePoint tempPoint : pointList) {
                    if (tempPoint.getPARENTID().equals(treePoint.getID())) {
                        if ("0".equals(treePoint.getISLEAF())) {
                            tempPoint.setExpand(false);
                        }
                    }
                }
                treePoint.setExpand(false);
            } else {
                treePoint.setExpand(true);
            }
        }
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        TextView text;
        ImageView icon;
    }

}
