package easyEnglish;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CompositionScore 
{
	public static void main(String[] args) throws Exception
	{
		CompositionScore a=new CompositionScore();
		FileInputStream  file_dict=new FileInputStream ("C:\\Users\\apple\\Desktop\\writting.txt");
		InputStreamReader  f=new InputStreamReader (file_dict,"UTF-8");
		BufferedReader buf1=new BufferedReader(f);
		String text="";
		String str;
		while((str=buf1.readLine())!=null)
		{
			text+=str;
			text+=" ";
		}
		JSONObject object=a.judgecet6(text);
		System.out.println("单词数为："+object.getString("WordNumber"));
		System.out.println("分数为："+object.getString("WritingScore"));
		System.out.println("参考信息："+object.getString("WritingMessage"));
		buf1.close();
	}
	public JSONObject judgecet6(String text)
	{
		Getprototype b=new Getprototype();
		GrammarChecker a=new GrammarChecker();
		HashMap<String,String>checker=a.check(text);
		Iterator iter = checker.entrySet().iterator();
		String Message="";
		while (iter.hasNext()) 
		{
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			Message+=key;
			Message+="%";
			Message+=val;
			Message+="%";
		}
		int grammarscore=60;
		/*if(checker.size()==1&&checker.get("error").toString().equals("error"))
		{
			JSONObject object=new JSONObject();
			object.put("WritingScore","0");
			object.put("WritingMessage","unknown error");
			return object;
		}*/
		grammarscore-=checker.size()*10;
		if(grammarscore<0)grammarscore=0;
		int vocabularyscore=0;
		text=text.toLowerCase();
		char ch[]=text.toCharArray();
		int average=47;
		int len=text.length();
		int i=0,j=0;
		int cnt=0;
		for(i=0;i<len;i++)
		{
			if(ch[i]>='a'&&ch[i]<='z')
			{
				for(j=i;j<len;j++)
				{
					if(ch[j]>='a'&&ch[j]<='z')
					{
						continue;
					}
					else
					{
						String word=text.substring(i,j);
						word=b.wordJudge(word);
						int score=Integer.parseInt(b.getwordscore(word));
						if(score>average)
						{
							vocabularyscore+=(score-average);
						}
						i=j;
						cnt++;
						break;
					}
				}
			}
		}
		
		vocabularyscore/=10;
		if(vocabularyscore>40)
		{
			vocabularyscore=40;
		}
		JSONObject object=new JSONObject();
		object.put("WordNumber",""+cnt);
		double score=vocabularyscore+grammarscore;
		double number=150;
		if(cnt<=number)
		{
			score*=cnt/number;
		}
		else if(cnt>=number+50)
		{
			cnt-=(number+50);
			score*=(number-cnt)/number;
		}
		else
		{
			
		}
		object.put("WritingScore",""+(int)score);
		object.put("WritingMessage",Message);
		return object;
	}
}
