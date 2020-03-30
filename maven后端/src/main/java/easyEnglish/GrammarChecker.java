package easyEnglish;

import java.util.HashMap;
import java.util.List;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import com.alibaba.fastjson.JSON;

public class GrammarChecker 
{
	
	public HashMap check(String text)
	{
		HashMap<String,String>checker=new HashMap();
		try
		{
			JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
			List<RuleMatch> matches = langTool.check(text);
			for (RuleMatch match : matches) 
			{
				String error="Potential error at characters " +match.getFromPos() + "-" + match.getToPos() + ": " +
						match.getMessage();
				String suggestion="Suggested correction(s): " +
						match.getSuggestedReplacements();
				checker.put(error,suggestion);
			}
			return checker;
		}catch(Exception e)
		{
			checker.clear();
			checker.put("error","error");
			return checker;
		}
		
	}

}
