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
import com.hp.gagawa.java.elements.Style;
import com.hp.gagawa.java.elements.Title;

import magnon.hp.banner.db.DBOperations;
import magnon.hp.banner.db.DBOperationsImpl;
import magnon.hp.banner.html.javascript.ScriptCreator;
import magnon.hp.banner.html.javascript.TweenMaxLibrary;
import magnon.hp.banner.html.stylesheet.StyleSheetCreator;
import magnon.hp.banner.html.util.BannerUtil;
import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class BannerCreator {
	
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
		    
		    //style
		    String ifReplay = bannerModel.getReplay().trim();
			if(!ifReplay.equals("") && !ifReplay.equals("0")) {
			    Style s = new Style("");
			    s.setType("text/css");
			    s.setAttribute("href", "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");
			    html.appendChild(s);
			}
			
			String isGradient = bannerModel.getIs_gradient().trim();
			String backgroundOrGradientStr = "    background: " + bannerModel.getColorpicker() + ";\r\n";
			if(!isGradient.equals("") && !isGradient.equals("0")) {
				//backgroundOrGradientStr = "		background-image: " + bannerModel.getGradient_value();
				backgroundOrGradientStr = bannerModel.getGradient_value();
			}
		    
		    //box-shadow is used for 1px inner border of wrapper
		    String styleString = ".wrapper{\r\n" + 
		    		"    width: "+bannerModel.getCanvas_width()+"px;\r\n" + 
		    		"    height: "+bannerModel.getCanvas_height()+"px;\r\n" + 
		    		backgroundOrGradientStr + 
		    		"    position: relative;\r\n" + 
		    		"    cursor: pointer;\r\n" + 
		    		"	 box-shadow: 0px 0px 0px 1px #000000 inset;\r\n" +
		    		"	 overflow: hidden;\r\n" +
		    		"}\r\n"+
		    		".wrapper .child{\r\n"
		    		+ "display:  none;}\r\n"
		    		+ ".invisible {\r\n"
		    		+ "	//display: none;\r\n"
		    		+ "}"
		    		+ ".wrapper .reply-button{"
		    		+ "		padding: 5px;"
		    		+ "}";
		    
		    List<ImageModel> frameImageElementList = new ArrayList<>();
		    List<TextModel> frameTextElementList = new ArrayList<>();
		    
		    //iterate multiple frames of banner
		    int frameSize = frameList.size();
		    
	    	//iterate multiple frames of banner
		    int frameCounter = 1;
		    String width = "";
		    String height = "";
	    	for (FrameModel firstFrame : frameList) {
		    		//image element css
		    		frameImageElementList = firstFrame.getImageList();
		    		for (int i = 0; i < frameImageElementList.size(); i++) {
		    			
		    			width = frameImageElementList.get(i).getWidth().trim();
			    		height = frameImageElementList.get(i).getHeight().trim();
			    		if(width.equals("")) {
			    			width = "100";
			    		}
			    		if(height.equals("")) {
			    			height = "100";
			    		}
		    			
		    			styleString +=  ".frame" + frameCounter + "-child-first-" + (i + 1 ) + "{\r\n" + 
		    		    		"    width: " + width + "px;\r\n" + 
		    		    		"    height: " + height + "px;\r\n" + 
		    		    	//	"    background: #89fe76;\r\n" + 
		    		    		"    position: absolute;\r\n";
					   // styleString += "	 background-image: url('images/" + frameImageElementList.get(i).getImagePath() + "')";
					    styleString += "}\r\n";
					    //append style to html
					   // obj.appendStyle(styleString, html);
		    		}
		    		//text element css
		    		frameTextElementList = firstFrame.getTextList();
		    		for (int i = 0; i < frameTextElementList.size(); i++) {
					    styleString += ".frame" + frameCounter + "-child-second-" + (i + 1 ) + "{\r\n" + 
					    		"    width: auto;\r\n" + 
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
			ScriptCreator scriptCreatorObj = new ScriptCreator();
			String replayStr = "";
			String clearStr = "";
			String restartStart = "";
			String restartStr = "";
			String timeLineRestsrtStr = "";
			String timeLineRlearStr = "";
	    	frameCounter = 1;
	    	for (FrameModel firstFrame : frameList) {
	    		//generate and append javascript
	    		scriptCreatorObj.generateScript(body, firstFrame, scriptTagSecond, frameCounter, bannerModel, frameSize);
	    		
	    		replayStr += scriptCreatorObj.replayBannerBody(body, scriptTagSecond, bannerModel, firstFrame, frameCounter, restartStr);
	    		
	    		clearStr += scriptCreatorObj.timeLineCleartStr(body, scriptTagSecond, firstFrame, frameCounter, timeLineRestsrtStr);
	    		
	    		restartStart += scriptCreatorObj.timeLineRestartStr(body, scriptTagSecond, firstFrame, frameCounter, timeLineRlearStr);
			    
			    frameCounter++;
	    	}
	    	
	    	// code to pause animation on hover starts
			String pauseOnHoverStr = bannerModel.getPause_on_hover().trim();
			int pauseOnHover = 0;
			if (!pauseOnHoverStr.equals("") && !pauseOnHoverStr.equals("0")) {
				pauseOnHover = Integer.parseInt(pauseOnHoverStr);
			}

			if (pauseOnHover == 1) {
				scriptCreatorObj.setPauseOnHover(body, scriptTagSecond, bannerModel);
			}
			// code to pause animation on hover ends
			
			// code to replay banner starts
			int replay = Integer.parseInt(bannerModel.getReplay().trim());
			if (replay == 1) {
				scriptCreatorObj.replayBanner(scriptTagSecond, replayStr, frameCounter);
			}

			if (pauseOnHover == 1) {
				scriptCreatorObj.setPauseOnHover(body, scriptTagSecond, bannerModel);
			}
			// code to pause animation on hover ends

			//code to animate banner in loop starts
			String animationLoopStr = bannerModel.getAnimation_loop().trim();
			String animationLoopCountStr = bannerModel.getLoop_count().trim(); 
			int animationLoopCount = 0; 
			if(!animationLoopStr.equals("") && !animationLoopStr.equals("0")) { 
				animationLoopCount = Integer.parseInt(animationLoopCountStr);
				
				scriptCreatorObj.animateBannerInLoop(body, scriptTagSecond, bannerModel, animationLoopCount, clearStr, restartStart);
			}
			//code to replay banner ends
	    	
	    	//append javascript code in body tag of HTML content of banner
			body.appendChild(scriptTagSecond);
	    	
		    //append body to html
		    html.appendChild(body);
		    
		    //print html code
		    System.out.println(html.write());
		    
		    //write html to file
		    try {
		    	String saveBannerPath =  bannerModel.getBanner_file_path();
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
		
	    DBOperations dbOperations = new DBOperationsImpl();
	    //Insert Banner Folder Records in Database
	    dbOperations.insert(bannerModel);
	    
	    return html.write();
	}
	

	

}

