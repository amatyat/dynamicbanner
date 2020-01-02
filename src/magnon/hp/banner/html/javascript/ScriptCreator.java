package magnon.hp.banner.html.javascript;

import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Script;
import com.hp.gagawa.java.elements.Text;

import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;

public class ScriptCreator {

	public static void main(String[] args) {

	}

	public void generateScript(Body body, FrameModel firstFrame, Script scriptTagSecond, int frameCounter, BannerModel bannerModel) {
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
				setImageScriptIfFadeInEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i);
			}
			else if(frameElementEffect.equals("fadeout")){
				setImageScriptIfFadeOutEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i);
			}else {
				setImageScriptIfNoEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i);
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
				setTextScriptIfFadeInEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY, textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i);
			}
			else if(frameElementEffect.equals("fadeout")){
				setTextScriptIfFadeOutEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY, textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i);
			}else {
				setTextScriptIfNoEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY, textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i);
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
			setPauseOnHover(body, scriptTagSecond, bannerModel);
		}
		//code to pause animation on hover ends
		
	}

	
	private void setImageScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX, Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX, Float imageAnimationStopY, Float imageAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1 ) + "\");\r\n" + 
				"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + "});\r\n" +
				"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + "}, " + imageAnimationStartTime + ")\r\n"));
	}
	
	private void setImageScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX, Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX, Float imageAnimationStopY, Float imageAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1 ) + "\");\r\n" + 
				"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + ", opacity: 0});\r\n" +
				"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + ", opacity: 1}, " + imageAnimationStartTime + ")\r\n"));
	}
	
	private void setImageScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX, Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX, Float imageAnimationStopY, Float imageAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineFirst" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivFirst" + (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1 ) + "\");\r\n" + 
				"timeLineFirst" + (i + 1 ) + ".set(childDivFirst" + (i + 1 ) + ", {x: " + imageAnimationStartX + ", y: " + imageAnimationStartY + ", opacity: 1});\r\n" +
				"timeLineFirst" + (i + 1 ) + ".to(childDivFirst" + (i + 1 ) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: " + imageAnimationStopY + ", opacity: 0}, " + imageAnimationStartTime + ")\r\n"));
	}
	
	private void setTextScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX, Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY, Float textAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1 ) + "\");\r\n" +
				"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + "});\r\n" +
				"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {display: 'block', x: " + textAnimationStopX + ", y: " + textAnimationStopY + "}, " + textAnimationStartTime + ");"));
	}
	
	private void setTextScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX, Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY, Float textAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1 ) + "\");\r\n" +
				"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 0});\r\n" +
				"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {display: 'block', x: " + textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 1}, " + textAnimationStartTime + ");"));
	}
	
	private void setTextScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX, Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY, Float textAnimationStartTime, int i) {
		scriptTagSecond.appendChild(new Text("var timeLineSecond" + (i + 1 ) + " = new TimelineLite();\r\n" + 
				"var childDivSecond"+ (i + 1 ) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1 ) + "\");\r\n" +
				"timeLineSecond" + (i + 1 ) + ".set(childDivSecond" + (i + 1 ) + ", {x: " + textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 1});\r\n" +
				"timeLineSecond" + (i + 1 ) + ".to(childDivSecond" + (i + 1 ) + ", " + textAnimationEndTime + ", {display: 'block', x: " + textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 0}, " + textAnimationStartTime + ");"));
	}
	
	private void setPauseOnHover(Body body, Script scriptTagSecond, BannerModel bannerModel) {
		scriptTagSecond.appendChild(new Text(""
										+ "$(\".wrapper\").hover(function(){\r\n" + 
										"	TweenMax.pauseAll(true, true, true); \r\n" + 
										"}, function(){\r\n" + 
										"	TweenMax.resumeAll(true, true, true);\r\n" + 
										"});"));
	}
}
