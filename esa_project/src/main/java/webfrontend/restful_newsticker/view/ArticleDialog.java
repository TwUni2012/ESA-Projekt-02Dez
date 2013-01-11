package webfrontend.restful_newsticker.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import webfrontend.WebFrontend;

public class ArticleDialog extends JDialog {
	
	private JEditorPane htmlpane;
	private JScrollPane scrollpane;

	public ArticleDialog() 
	{
		setSize(600, 800);
		setLayout(new BorderLayout(5, 5));
		
		htmlpane=new JEditorPane("text/html", WebFrontend.loading);
		htmlpane.setEditable(false);
		
		scrollpane=new JScrollPane(htmlpane);
		scrollpane.setAutoscrolls(false);
		
		add(scrollpane,BorderLayout.CENTER);
	}
	
	public void setHtml(String html,String title)
	{
		setTitle(title);
//		html=ArticleFactory.cleanSpecialTokens(html);
		html=html.replaceAll("<p>", "<br><br><font face=verdana>&nbsp;&nbsp;&nbsp;");
		htmlpane.setText("<html><body><center><br><font face=arial size=5><b>" +
				title+
				"</b></font><table width=90%><tr><td align=justify valign=top><font face=verdana>"+
				html+"</font></td></tr></table></body></html>");
		htmlpane.setCaretPosition(0);
	}

}
