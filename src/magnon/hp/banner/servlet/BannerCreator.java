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
		
		BannerModel bannerModel = new BannerModel();
		
		List<FrameModel> frameList = new ArrayList<>();
		
		FrameModel frameModel = new FrameModel();
		List<ImageModel> imageList = new ArrayList<>();
		List<TextModel> textList = new ArrayList<>();
		
		ImageModel imageModel = new ImageModel();
		imageModel.setImagePath("Anju.jpg");
		
		imageList.add(imageModel);
		
	/*	ImageModel imageModel1 = new ImageModel();
		imageModel1.setImagePath("Anju1.jpg");

		imageList.add(imageModel1);*/
		TextModel textModel1 = new TextModel();
		textModel1.setText("Text 2");
		
		textList.add(textModel1);
		
		
		frameModel.setImageList(imageList );
		frameModel.setTextList(textList);
		
		frameList.add(frameModel);
		
		
		FrameModel frameModel2 = new FrameModel();
		List<ImageModel> imageList2 = new ArrayList<>();
		List<TextModel> textList2 = new ArrayList<>();
		
		ImageModel imageModel2 = new ImageModel();
		imageModel2.setImagePath("Anju2.jpg");
		
		imageList2.add(imageModel2);
		
		/*ImageModel imageModel12 = new ImageModel();
		imageModel12.setImagePath("Anju12.jpg");
		
		imageList2.add(imageModel12);*/
		
		TextModel textModel2 = new TextModel();
		textModel2.setText("Text 2 2");
		
		textList2.add(textModel2);
		
		frameModel2.setImageList(imageList2 );
		frameModel2.setTextList(textList2);
		
		frameList.add(frameModel2);
		
		bannerModel.setFrames(frameList);
		
		
		bannerModel.setUsername("Anju");
		
		bannerModel.setFoldername("11111111111111");
		
		BannerCreator.createHTML(bannerModel);
		
		
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
		    		"}\r\n"+
		    		".wrapper .child{\r\n"
		    		+ "display:  none;}\r\n";
		    
		    List<ImageModel> frameImageElementList = new ArrayList<>();
		    List<TextModel> frameTextElementList = new ArrayList<>();
	    	//iterate multiple frames of banner
		    int frameCounter = 1;
	    	for (FrameModel firstFrame : frameList) {
		    		//image element css
		    		frameImageElementList = firstFrame.getImageList();
		    		for (int i = 0; i < frameImageElementList.size(); i++) {
		    			styleString +=  ".frame" + frameCounter + "-child-first-" + (i + 1 ) + "{\r\n" + 
		    		    		"    width: 100px;\r\n" + 
		    		    		"    height: 100px;\r\n" + 
		    		    		"    background: #89fe76;\r\n" + 
		    		    		"    position: absolute;\r\n";
					    styleString += "	 background-image: url('images/" + frameImageElementList.get(i).getImagePath() + "')";
					    styleString += "}\r\n";
					    //append style to html
					   // obj.appendStyle(styleString, html);
		    		}
		    		//text element css
		    		frameTextElementList = firstFrame.getTextList();
		    		for (int i = 0; i < frameTextElementList.size(); i++) {
					    styleString += ".frame" + frameCounter + "-child-second-" + (i + 1 ) + "{\r\n" + 
					    		"    width: 100px;\r\n" + 
					    		"    height: 100px;\r\n" + 
					    		"    background: #FFC0CB;\r\n" + 
					    		"    position: absolute;\r\n}";
					    //append style to html
					    //obj.appendStyle(styleString, html);
		    		}
				    //generate and append inner html to body
				    obj.generateHtml(body, bannerModel, firstFrame, wrapperDiv, frameCounter);
	    		
				    frameCounter++;
	    	}
	    		    	
	    	//append css to html
	    	obj.appendStyle(styleString, html);
	    	
	    	//append wrapper div to body
	    	body.appendChild(wrapperDiv);
			
	    	//append TweenMax javascript library for animation
			obj.appendTweenMaxLibrary(body);
			
			//start script tag for custom javascript for animation
			Script scriptTagSecond = new Script("");
			scriptTagSecond.setAttribute("type", "text/javascript");
	    	
	    	//append javascript to html
	    	frameCounter = 1;
	    	for (FrameModel firstFrame : frameList) {
	    		//generate and append javascript
			    obj.generateScript(body, firstFrame, scriptTagSecond, frameCounter, bannerModel);
			    
			    frameCounter++;
	    	}
	    	
	    	//append javascript code in body tag of HTML content of banner
			body.appendChild(scriptTagSecond);
	    	
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
			
		}else {
			System.out.println("Frame Model Is Empty");
		}
		
		
		
		Connection con = new JDBCConncetionProvider().connect();
	    
		String SQL_INSERT = "INSERT INTO banner_details (name, banner_name, created_date) VALUES (?,?,?)";
		// build HTML code
		try {
			PreparedStatement ps = con.prepareStatement
					(SQL_INSERT);
			Date sqlDate = new Date(new java.util.Date().getTime());
								
			ps.setString(1, bannerModel.getUsername());
			System.out.println("Frame Model Is Empty");					ps.setString(2, bannerModel.getFoldername());
			ps.setDate(3, sqlDate);
			int i = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		
	    return html.write();
	}
	
	public void appendTweenMaxLibrary(Body body) {
		//append tweenmax javascript library
		Script scriptTagFirst = new Script("");
		scriptTagFirst.setAttribute("type", "text/javascript");
		scriptTagFirst.setAttribute("src", "https://cdnjs.cloudflare.com/ajax/libs/gsap/1.20.2/TweenMax.min.js");
		body.appendChild(scriptTagFirst);
		
		//append jQuery javascript library
		scriptTagFirst = new Script("");
		scriptTagFirst.setAttribute("type", "text/javascript");
		scriptTagFirst.setAttribute("src", "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js");
		body.appendChild(scriptTagFirst);
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
	
	public Div addHyperlinkToBannerWrapperContainer(BannerModel bannerModel, Div wrapperDiv) {
		String hpl_link = bannerModel.getHpl_link();
		String target = bannerModel.getTarget();
		if(isValid(hpl_link)) {
			wrapperDiv.setAttribute("onclick", "window.location='"+ hpl_link +"'");
			wrapperDiv.setAttribute("target", target);
		}
		
		return wrapperDiv;
	}
	
	public void generateHtml(Body body, BannerModel bannerModel, FrameModel firstFrame, Div wrapperDiv, int frameCounter) {
		String cssClass = "";
		String elementId = "";
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		for (int i = 0; i < frameImageElementList.size(); i++) {
			cssClass = "child frame" + frameCounter + "-child-first-" + (i + 1 );
			elementId = "frame" + frameCounter + "-child-first-" + (i + 1 );
			Div childFirstDiv = divContainerCreator(elementId, cssClass);
			
			childFirstDiv.appendText("" + frameImageElementList.get(i).getOnTime() + frameImageElementList.get(i).getOffTime());
			wrapperDiv.appendChild(childFirstDiv);
		}
		
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		for (int i = 0; i < frameImageElementList.size(); i++) {
			cssClass = "child frame" + frameCounter + "-child-second-" + (i + 1 );
			elementId = "frame" + frameCounter + "-child-second-" + (i + 1 );
			Div childSecondDiv = divContainerCreator(elementId, cssClass);
			
			childSecondDiv.appendText(frameTextElementList.get(i).getText() + " " + frameTextElementList.get(i).getOnTime() + " " + frameTextElementList.get(i).getOffTime());
			wrapperDiv.appendChild(childSecondDiv);
		}
		
		//body.appendChild(wrapperDiv);
	}
	
	private void generateScript(Body body, FrameModel firstFrame, Script scriptTagSecond, int frameCounter, BannerModel bannerModel) {
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		//image on/off times in sec
		Float imageAnimationStartTime, imageAnimationEndTime;
		
		//image on/off coordinates
		Float imageAnimationStartX, imageAnimationStartY, imageAnimationStopX, imageAnimationStopY;
		
		//text on/off times in sec
		Float textAnimationStartTime, textAnimationEndTime;
		
		//text on/off coordinates
		Float textAnimationStartX, textAnimationStartY, textAnimationStopX, textAnimationStopY;
		
		//System.out.print("Text Size : " + firstFrame.getTextList().size());
		
		//javascript for image element
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
			
			//TweenMax javascript for animation
			/*
			 * if(callBackFlag == 1) { callBackStr = "{onComplete: function(){\r\n" +
			 * "	//delay of 2 sec to start the next loop\r\n" +
			 * "	TweenLite.delayedCall(2, updateContent);\r\n" + "}}";
			 * 
			 * callBackFlag++; }
			 */
			
			//code for animation effect starts
			String frameElementEffect = firstFrame.getImageList().get(i).getEffect().trim();
			if(frameElementEffect.equals("fadein")) {
				this.setImageScriptIfFadeInEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i);
			}
			else if(frameElementEffect.equals("fadeout")){
				this.setImageScriptIfFadeOutEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i);
			}else {
				this.setImageScriptIfNoEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i);
			}
			//code for animation effect ends
		}
		
		//javascript for text element
		for (int i = 0; i < frameImageElementList.size(); i++) {
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
			/*
			 * if(callBackFlag == 1) { callBackStr = "{onComplete: function(){\r\n" +
			 * "	//delay of 2 sec to start the next loop\r\n" +
			 * "	TweenLite.delayedCall(2, updateContent);\r\n" + "}}"; }
			 */
			
			//code for animation effect starts
			String frameElementEffect = firstFrame.getImageList().get(i).getEffect().trim();
			if(frameElementEffect.equals("fadein")) {
				this.setTextScriptIfFadeInEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY, textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i);
			}
			else if(frameElementEffect.equals("fadeout")){
				this.setTextScriptIfFadeOutEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY, textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i);
			}else {
				this.setTextScriptIfNoEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY, textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i);
			}
			//code for animation effect ends
			
			//callBackFlag++;
		}
		
		/*
		 * String animationLoopCountStr = bannerModel.getLoop_count().trim(); int
		 * animationLoopCount = 0; if(!animationLoopCountStr.equals("") &&
		 * !animationLoopCountStr.equals("0")) { animationLoopCount =
		 * Integer.parseInt(animationLoopCountStr); }
		 * 
		 * if(animationLoopCount != 0) { scriptTagSecond.appendChild(new Text("" +
		 * "var loopCounter = 0;\r\n" + "function updateContent(){\r\n" +
		 * "	if(loopCounter == " + animationLoopCount + "){\r\n" +
		 * "		timeLineSecond1.clear();\r\n" + "	}\r\n" + "	loopCounter++;\r\n"
		 * + "	\r\n" + "	timeLineFirst1.restart();\r\n" +
		 * "	timeLineSecond1.restart();\r\n" + "}")); }
		 */
		
		//code to pause animation on hover starts
		String pauseOnHoverStr = bannerModel.getPause_on_hover().trim(); 
		int pauseOnHover = 0; 
		if(!pauseOnHoverStr.equals("") && !pauseOnHoverStr.equals("0")) { 
			pauseOnHover = Integer.parseInt(pauseOnHoverStr); 
		}
		
		if(pauseOnHover == 1) {
			this.setPauseOnHover(body, scriptTagSecond, bannerModel);
		}
		//code to pause animation on hover ends
		
	}
	
	public void setImageScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX, Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX, Float imageAnimationStopY, Float imageAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1 ) + "\");\r\n" + 
				"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + "});\r\n" +
				"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + "}, " + imageAnimationStartTime + ")\r\n"));
	}
	
	public void setImageScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX, Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX, Float imageAnimationStopY, Float imageAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1 ) + "\");\r\n" + 
				"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + ", opacity: 0});\r\n" +
				"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + ", opacity: 1}, " + imageAnimationStartTime + ")\r\n"));
	}
	
	public void setImageScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX, Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX, Float imageAnimationStopY, Float imageAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1 ) + "\");\r\n" + 
				"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + ", opacity: 1});\r\n" +
				"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + ", opacity: 0}, " + imageAnimationStartTime + ")\r\n"));
	}
	
	public void setTextScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX, Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY, Float textAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1 ) + "\");\r\n" +
				"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + "});\r\n" +
				"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {display: 'block', x: " + textAnimationStopX + ", y: " + textAnimationStopY + "}, " + textAnimationStartTime + ");"));
	}
	
	public void setTextScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX, Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY, Float textAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1 ) + "\");\r\n" +
				"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 0});\r\n" +
				"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {display: 'block', x: " + textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 1}, " + textAnimationStartTime + ");"));
	}
	
	public void setTextScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX, Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY, Float textAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1 ) + "\");\r\n" +
				"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 1});\r\n" +
				"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {display: 'block', x: " + textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 0}, " + textAnimationStartTime + ");"));
	}
	
	public void setPauseOnHover(Body body, Script scriptTagSecond, BannerModel bannerModel) {
		scriptTagSecond.appendChild(new Text(""
										+ "$(\".wrapper\").hover(function(){\r\n" + 
										"	TweenMax.pauseAll(true, true, true); \r\n" + 
										"}, function(){\r\n" + 
										"	TweenMax.resumeAll(true, true, true);\r\n" + 
										"});"));
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

