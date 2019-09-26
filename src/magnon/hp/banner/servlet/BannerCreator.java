package magnon.hp.banner.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Head;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Script;
import com.hp.gagawa.java.elements.Style;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;

public class BannerCreator {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String param1Before = "https://about.att.com/csr/home.html?p=1&q=1%22%3E%3Cimg%20src=X%20Onerror=prompt(document.domain)%3E&search_category=graphic";
		String param1After = URLDecoder.decode(param1Before, "UTF-8");
		
		System.out.println("param1 before:" + param1Before);
		System.out.println("param1 after:" + param1After);

		String param2Before = "1\"><img src=X Onerror=prompt(document.domain)>&search_category=graphic";
		String param2After = URLEncoder.encode(param2Before, "UTF-8");
		System.out.println("param2 before:" + param2Before);
		System.out.println("param2 after:" + param2After);
		
	}
	/**
	 * @param args
	 */
	public static String createHTML() {
		BannerCreator obj = new BannerCreator();
		Html html = new Html();
	    Head head = new Head();
	    html.appendChild(head);
	    
	    //add banner page title
	    Title title = obj.setPageTitle("HTML 5 Banner");
	    head.appendChild(title);
	    //set body to html
	    Body body = new Body();
	    //style
	    String styleString = ".wrapper{\r\n" + 
	    		"    width: 800px;\r\n" + 
	    		"    height: 400px;\r\n" + 
	    		"    background: #77b5fe;\r\n" + 
	    		"    position: relative;\r\n" + 
	    		"}\r\n" + 
	    		".child-first{\r\n" + 
	    		"    width: 100px;\r\n" + 
	    		"    height: 100px;\r\n" + 
	    		"    background: #89fe76;\r\n" + 
	    		"    position: absolute;\r\n" + 
	    		"}";
	    //append style to html
	    obj.appendStyle(styleString, html);
	    //generate and append inner html to body
	    obj.generateHtml(body);
	    //generate and append javascript
	    obj.generateScript(body);
	    //append body to html
	    html.appendChild(body);
	    
	    //print html code
	    System.out.println(html.write());
	    
	    //write html to file
	    try {
	        File output = new File("banner.html");
	        PrintWriter out = new PrintWriter(new FileOutputStream(output));
	        out.println(html.write());
	        out.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    return html.write();

	}
	
	public Title setPageTitle(String pageTitle) {
		Title title = new Title();
	    title.appendChild(new Text("HTML 5 Banner"));
	    
	    return title;
	}
	
	public void appendStyle(String styleString, Html html) {
		Style s = new Style("");
		s.appendChild(new Text(styleString));
		
		html.appendChild(s);
	}
	
	public void generateHtml(Body body) {
		Div wrapperDiv = new Div();
		Div childFirstDiv = new Div();
		wrapperDiv.setCSSClass("wrapper");
		childFirstDiv.setCSSClass("child-first");
		
		wrapperDiv.appendChild(childFirstDiv);
		
		body.appendChild(wrapperDiv);
	}
	
	private void generateScript(Body body) {
		Script scriptTagFirst = new Script("");
		Script scriptTagSecond = new Script("");
		scriptTagFirst.setAttribute("src", "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js");
		scriptTagSecond.appendChild(new Text("$(document).ready(function(){\r\n" + 
				"	$('.child-first').animate({left:\"250px\"}, \"slow\")\r\n" + 
				"    					.animate({top:\"250px\"}, \"slow\")\r\n" + 
				"    					.animate({left:\"300px\"}, \"slow\")\r\n" + 
				"    					.animate({top:\"0px\"}, \"slow\");\r\n" + 
				"});"));
		
		body.appendChild(scriptTagFirst);
		body.appendChild(scriptTagSecond);
	}

}

