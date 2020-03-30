package javabackground;

import java.util.List;

import org.languagetool.*;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
public class CompositionScore 
{
	public static void main(String[] args) throws Exception
	{
		JLanguageTool langTool = new JLanguageTool(new BritishEnglish());
		List<RuleMatch> matches = langTool.check("A sentence with a error in the Hitchhiker's Guide tot he Galaxy");
		for (RuleMatch match : matches) {
		  System.out.println("Potential error at characters " +match.getFromPos() + "-" + match.getToPos() + ": " +
		      match.getMessage());
		  System.out.println("Suggested correction(s): " +
		      match.getSuggestedReplacements());
		}
	}
}
