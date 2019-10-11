package magnon.hp.banner.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Head;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Script;
import com.hp.gagawa.java.elements.Style;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Title;

import magnon.hp.banner.db.JDBCConncetionProvider;
import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class BannerCreator {
	
	private static final String SAVE_BANNER_DIR = "F:\\Banner\\BannerServlet\\WebContent\\outputBanner";
	
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
	public static String createHTML(BannerModel bannerModel) {
		BannerCreator obj = new BannerCreator();
		Html html = new Html();
	    Head head = new Head();
	    html.appendChild(head);
	    
	    List<FrameModel> frameList = new ArrayList<>();
	    frameList = bannerModel.getFrames();
	    if(!frameList.isEmpty()) {
		    
		    //add banner page title
		    Title title = obj.setPageTitle("HTML 5 Banner");
		    head.appendChild(title);
		    //set body to html
		    Body body = new Body();
		    //wrapper container of banner
		    String cssClass = "wrapper";
		    String id = null;
		    Div wrapperDiv = obj.divContainerCreator(id, cssClass);
		    wrapperDiv = obj.addHyperlinkToBannerWrapperContainer(bannerModel, wrapperDiv);
		    
	    	//FrameModel firstFrame = frameList.get(0);
	    	
		    //style
		    String styleString = ".wrapper{\r\n" + 
		    		"    width: "+bannerModel.getCanvas_width()+"px;\r\n" + 
		    		"    height: "+bannerModel.getCanvas_height()+"px;\r\n" + 
		    		"    background: "+bannerModel.getColorpicker()+";\r\n" + 
		    		"    position: relative;\r\n" + 
		    		"    cursor: pointer;\r\n" + 
		    		"}\r\n";
		    
		    List<ImageModel> frameImageElementList = new ArrayList<>();
	    	//iterate multiple frames of banner
	    	for (FrameModel firstFrame : frameList) {
	    		
	    		frameImageElementList = firstFrame.getImageList();
	    		for (int i = 0; i < frameImageElementList.size(); i++) {
	    			styleString +=  ".child-first-" + (i + 1 ) + "{\r\n" + 
	    		    		"    width: 100px;\r\n" + 
	    		    		"    height: 100px;\r\n" + 
	    		    		"    background: #89fe76;\r\n" + 
	    		    		"    position: absolute;\r\n";
				    styleString += "	 background-image: url('images/" + frameImageElementList.get(i).getImagePath() + "')";
				    styleString += "}\r\n";
				    
				    styleString += ".child-second-" + (i + 1 ) + "{\r\n" + 
				    		"    width: 100px;\r\n" + 
				    		"    height: 100px;\r\n" + 
				    		"    background: #FFC0CB;\r\n" + 
				    		"    position: absolute;\r\n}";
				    //append style to html
				    obj.appendStyle(styleString, html);
	    		}
				    //generate and append inner html to body
				    obj.generateHtml(body, bannerModel, firstFrame, wrapperDiv);
				    //generate and append javascript
				    obj.generateScript(body, firstFrame);
	    		
	    	}
		    //append body to html
		    html.appendChild(body);
		    
		    //print html code
		    System.out.println(html.write());
		    
		    //write html to file
		    try {
		    	String saveBannerPath =  SAVE_BANNER_DIR+File.separator+bannerModel.getUsername()+File.separator+bannerModel.getFoldername();
		    	File fileSaveDir = new File(saveBannerPath);
		    	if (!fileSaveDir.exists()) {
					fileSaveDir.mkdir(); 
				}
		        File output = new File(saveBannerPath + File.separator + "banner.html");
		        PrintWriter out = new PrintWriter(new FileOutputStream(output));
		        out.println(html.write());
		        out.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
		}
	    
		Connection con = new JDBCConncetionProvider().connect();
	    
		String SQL_INSERT = "INSERT INTO banner_details (name, banner_name, created_date) VALUES (?,?,?)";

		// build HTML code
		try {

			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);
			Date sqlDate = new Date(new java.util.Date().getTime());
			
			ps.setString(1, bannerModel.getUsername());
			ps.setString(2, bannerModel.getFoldername());
			ps.setDate(3, sqlDate);
			int i = ps.executeUpdate();


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return html.write();
	}
	
	public Div divContainerCreator(String elementId, String cssClass) {
		Div divContainer = new Div();
		//set element css class
		divContainer.setCSSClass(cssClass);
		//set element id if not null
		if(elementId != null) {
			divContainer.setId(elementId);
		}
		
		return divContainer;
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
	
	public Div addHyperlinkToBannerWrapperContainer(BannerModel bannerModel, Div wrapperDiv) {
		String hpl_link = bannerModel.getHpl_link();
		String target = bannerModel.getTarget();
		if(isValid(hpl_link)) {
			wrapperDiv.setAttribute("onclick", "window.location='"+ hpl_link +"'");
			wrapperDiv.setAttribute("target", target);
		}
		
		return wrapperDiv;
	}
	
	public void generateHtml(Body body, BannerModel bannerModel, FrameModel firstFrame, Div wrapperDiv) {
		String cssClass = "";
		String elementId = "";
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		for (int i = 0; i < frameImageElementList.size(); i++) {
			cssClass = "child-first-" + (i + 1 );
			elementId = "child-first-" + (i + 1 );
			Div childFirstDiv = divContainerCreator(elementId, cssClass);
			
			cssClass = "child-second-" + (i + 1 );
			elementId = "child-second-" + (i + 1 );
			Div childSecondDiv = divContainerCreator(elementId, cssClass);
			
			childFirstDiv.appendText("" + frameImageElementList.get(i).getOnTime() + frameImageElementList.get(i).getOffTime());
			childSecondDiv.appendText(frameTextElementList.get(i).getText() + " " + frameTextElementList.get(i).getOnTime() + " " + frameTextElementList.get(i).getOffTime());
			wrapperDiv.appendChild(childFirstDiv);
			wrapperDiv.appendChild(childSecondDiv);
		}
		
		body.appendChild(wrapperDiv);
	}
	
	private void generateScript(Body body, FrameModel firstFrame) {
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		//image on/off times in sec
		Float imageAnimationStartTime, imageAnimationEndTime;
		
		//image on/off coordinates
		Float imageAnimationStartX, imageAnimationStartY, imageAnimationStopX, imageAnimationStopY;
		
		//text on/off times in sec
		Float textAnimationStartTime, textAnimationEndTime;
		
		//text on/off coordinates
		Float textAnimationStartX, textAnimationStartY, textAnimationStopX, textAnimationStopY;
		
		//TweenMax javascript for animation
		Script scriptTagFirst = new Script("");
		Script scriptTagSecond = new Script("");
		scriptTagFirst.setAttribute("type", "text/javascript");
		scriptTagFirst.setAttribute("src", "https://cdnjs.cloudflare.com/ajax/libs/gsap/1.20.2/TweenMax.min.js");
		scriptTagSecond.setAttribute("type", "text/javascript");
		
		System.out.print("Text Size : " + firstFrame.getTextList().size());
		
		for (int i = 0; i < frameImageElementList.size(); i++) {
			//image on/off times in sec
			imageAnimationStartTime = firstFrame.getImageList().get(i).getOnTime();
			imageAnimationEndTime = firstFrame.getImageList().get(i).getOffTime();
			imageAnimationEndTime = (imageAnimationEndTime - imageAnimationStartTime);
			
			//image on/off coordinates
			imageAnimationStartX = firstFrame.getImageList().get(i).getSartCoordinateX();
			imageAnimationStartY = firstFrame.getImageList().get(i).getSartCoordinateY();
			imageAnimationStopX = firstFrame.getImageList().get(i).getStopCoordinateX();
			imageAnimationStopY = firstFrame.getImageList().get(i).getStopCoordinateY();
			
			//text on/off times in sec
			textAnimationStartTime = firstFrame.getTextList().get(i).getOnTime();
			textAnimationEndTime = firstFrame.getTextList().get(i).getOffTime();
			textAnimationEndTime = (textAnimationEndTime - textAnimationStartTime);
			
			//text on/off coordinates
			textAnimationStartX = firstFrame.getTextList().get(i).getSartCoordinateX();
			textAnimationStartY = firstFrame.getTextList().get(i).getSartCoordinateY();
			textAnimationStopX = firstFrame.getTextList().get(i).getStopCoordinateX();
			textAnimationStopY = firstFrame.getTextList().get(i).getStopCoordinateY();
			
			//TweenMax javascript for animation
			String animateInLoop;
			animateInLoop = "{repeat:-1}";
			scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
					"var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" +
					"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"child-first-" + (i + 1 ) + "\");\r\n" + 
					"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + "});\r\n" +
					"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + "}, " + imageAnimationStartTime + ")\r\n" + 
					"			.to(childDivFirst" + (i + 1 ) + ", 2, {width: 200, height: 200})\r\n" + 
					"			.to(childDivFirst" + (i + 1 ) + ", 2, {css:{borderRadius: \"50%\"}})\r\n" + 
					"			.to(childDivFirst" + (i + 1 ) + ", 2, {width: 100, height: 100});\r\n" +
					"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"child-second-" + (i + 1 ) + "\");\r\n" +
					"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + "});\r\n" +
					"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {x: " + textAnimationStopX + ", y: " + textAnimationStopY + "}, " + textAnimationStartTime + ");"));
		}
		
		//append javascript code in body tag of HTML content of banner
		body.appendChild(scriptTagFirst);
		body.appendChild(scriptTagSecond);
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
	
	/* Returns true if url is valid */
    public static boolean isValid(String url) 
    { 
        /* Try creating a valid URL */
        try { 
            new URL(url).toURI(); 
            return true; 
        }
        catch (Exception e) { 
            return false; 
        } 
    } 

}

