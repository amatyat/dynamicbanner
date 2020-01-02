package magnon.hp.banner.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Head;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Script;
import com.hp.gagawa.java.elements.Title;

import magnon.hp.banner.db.DBOperations;
import magnon.hp.banner.html.javascript.ScriptCreator;
import magnon.hp.banner.html.javascript.TweenMaxLibrary;
import magnon.hp.banner.html.stylesheet.StyleSheetCreator;
import magnon.hp.banner.html.util.BannerUtil;
import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class BannerCreator {
	
	private static final String SAVE_BANNER_DIR = "F:\\Banner\\BannerServlet\\WebContent\\outputBanner";
	/**
	 * @param args
	 */
	public static String createBanner(BannerModel bannerModel) {
		//BannerCreator obj = new BannerCreator();
		Html html = new Html();
	    Head head = new Head();
	    html.appendChild(head);
	    
	    List<FrameModel> frameList = new ArrayList<>();
	    frameList = bannerModel.getFrames();
	    if(!frameList.isEmpty()) {
	    	//add banner page title
		    Title title = BannerUtil.setPageTitle("HTML 5 Banner");
		    head.appendChild(title);
		    //set body to html
		    Body body = new Body();
		    
		    //wrapper container of banner
		    String cssClass = "wrapper";
		    String id = null;
		    Div wrapperDiv = HTMLCreator.divContainerCreator(id, cssClass);
		    wrapperDiv =  BannerUtil.addHyperlinkToBannerWrapperContainer(bannerModel, wrapperDiv);
		    
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
				    HTMLCreator.generateHtml(body, bannerModel, firstFrame, wrapperDiv, frameCounter);
	    		
				    frameCounter++;
	    	}
	    		    	
	    	//append css to html
	    	StyleSheetCreator.appendStyle(styleString, html);
	    	
	    	//append wrapper div to body
	    	body.appendChild(wrapperDiv);
			
	    	//append TweenMax javascript library for animation
	    	TweenMaxLibrary.appendTweenMaxLibrary(body);
			
			//start script tag for custom javascript for animation
			Script scriptTagSecond = new Script("");
			scriptTagSecond.setAttribute("type", "text/javascript");
	    	
	    	//append javascript to html
	    	frameCounter = 1;
	    	for (FrameModel firstFrame : frameList) {
	    		//generate and append javascript
			    new ScriptCreator().generateScript(body, firstFrame, scriptTagSecond, frameCounter, bannerModel);
			    
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
		
	    //Insert Banner Folder Records in Database
	    DBOperations.insert(bannerModel);
	    
	    return html.write();
	}
	

	

}

