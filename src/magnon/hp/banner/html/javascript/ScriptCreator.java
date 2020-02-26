package magnon.hp.banner.html.javascript;

import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Script;
import com.hp.gagawa.java.elements.Text;

import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class ScriptCreator {

	public static void main(String[] args) {

	}

	public void generateScript(Body body, FrameModel firstFrame, Script scriptTagSecond, int frameCounter,
			BannerModel bannerModel, int frameSize) {
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		// image on/off times in sec
		Float imageAnimationStartTime, imageAnimationEndTime;

		// image on/off coordinates
		Float imageAnimationStartX, imageAnimationStartY, imageAnimationStopX, imageAnimationStopY, imageAnimationEndX, imageAnimationEndY;

		// text on/off times in sec
		Float textAnimationStartTime, textAnimationEndTime;

		// text on/off coordinates
		Float textAnimationStartX, textAnimationStartY, textAnimationStopX, textAnimationStopY, textAnimationEndX, textAnimationEndY;

		// System.out.print("Text Size : " + firstFrame.getTextList().size());

		// javascript for image element
		for (int i = 0; i < frameImageElementList.size(); i++) {
			// image on/off times in sec
			imageAnimationStartTime = firstFrame.getImageList().get(i).getOnTime();
			imageAnimationEndTime = firstFrame.getImageList().get(i).getOffTime();
			imageAnimationEndTime = (imageAnimationEndTime - imageAnimationStartTime);

			// image on/off coordinates
			imageAnimationStartX = firstFrame.getImageList().get(i).getStartCoordinateX();
			imageAnimationStartY = firstFrame.getImageList().get(i).getStartCoordinateY();
			imageAnimationStopX = firstFrame.getImageList().get(i).getStopCoordinateX();
			imageAnimationStopY = firstFrame.getImageList().get(i).getStopCoordinateY();
			
			// image end on/off coordinates
			imageAnimationEndX = firstFrame.getImageList().get(i).getEndCoordinateX();
			imageAnimationEndY = firstFrame.getImageList().get(i).getEndCoordinateY();
			
			// TweenMax javascript for animation
			/*
			 * if(callBackFlag == 1) { callBackStr = "{onComplete: function(){\r\n" +
			 * "	//delay of 2 sec to start the next loop\r\n" +
			 * "	TweenLite.delayedCall(2, updateContent);\r\n" + "}}";
			 * 
			 * callBackFlag++; }
			 */

			// code for animation effect starts
			String frameElementEffect = firstFrame.getImageList().get(i).getEffect().trim();
			if (frameElementEffect.equals("fadein")) {
				setImageScriptIfFadeInEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY,
						imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i,
						frameSize, firstFrame);
			} else if (frameElementEffect.equals("fadeout")) {
				setImageScriptIfFadeOutEffects(scriptTagSecond, frameCounter, imageAnimationStartX,
						imageAnimationStartY, imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY,
						imageAnimationStartTime, i, frameSize, firstFrame);
			} else {
				setImageScriptIfNoEffects(scriptTagSecond, frameCounter, imageAnimationStartX, imageAnimationStartY,
						imageAnimationEndTime, imageAnimationStopX, imageAnimationStopY, imageAnimationStartTime, i,
						frameSize, firstFrame);
			}
			
			//apply end effect
			String endEffect = firstFrame.getImageList().get(i).getEndEffect().trim();
			if(!endEffect.equals("0")) {
				if (endEffect.equals("fadein")) {
					setImageScriptIfFadeInEndEffects(firstFrame, frameCounter, scriptTagSecond, imageAnimationEndX, imageAnimationEndY, endEffect, i);
				}
				else if (endEffect.equals("fadeout")) {
					setImageScriptIfFadeOutEndEffects(firstFrame, frameCounter, scriptTagSecond, imageAnimationEndX, imageAnimationEndY, endEffect, i);
				}
			}
			// code for animation effect ends
		}

		// javascript for text element
		for (int i = 0; i < frameTextElementList.size(); i++) {
			// text on/off times in sec
			textAnimationStartTime = firstFrame.getTextList().get(i).getOnTime();
			textAnimationEndTime = firstFrame.getTextList().get(i).getOffTime();
			textAnimationEndTime = (textAnimationEndTime - textAnimationStartTime);

			// text on/off coordinates
			textAnimationStartX = firstFrame.getTextList().get(i).getStartCoordinateX();
			textAnimationStartY = firstFrame.getTextList().get(i).getStartCoordinateY();
			textAnimationStopX = firstFrame.getTextList().get(i).getStopCoordinateX();
			textAnimationStopY = firstFrame.getTextList().get(i).getStopCoordinateY();
			
			// text end on/off coordinates
			textAnimationEndX = firstFrame.getTextList().get(i).getEndCoordinateX();
			textAnimationEndY = firstFrame.getTextList().get(i).getEndCoordinateY();

			// code for animation effect starts
			String frameElementEffect = firstFrame.getImageList().get(i).getEffect().trim();
			if (frameElementEffect.equals("fadein")) {
				setTextScriptIfFadeInEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY,
						textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i,
						frameSize, firstFrame);
			} else if (frameElementEffect.equals("fadeout")) {
				setTextScriptIfFadeOutEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY,
						textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i,
						frameSize, firstFrame);
			} else {
				setTextScriptIfNoEffects(scriptTagSecond, frameCounter, textAnimationStartX, textAnimationStartY,
						textAnimationEndTime, textAnimationStopX, textAnimationStopY, textAnimationStartTime, i,
						frameSize, firstFrame);
			}
			
			//apply end effect
			String endEffect = firstFrame.getTextList().get(i).getEndEffect().trim();
			if(!endEffect.equals("0")) {
				if (endEffect.equals("fadein")) {
					setTextScriptIfFadeInEndEffects(firstFrame, frameCounter, scriptTagSecond, textAnimationEndX, textAnimationEndY, endEffect, i);
				}
				else if (endEffect.equals("fadeout")) {
					setTextScriptIfFadeOutEndEffects(firstFrame, frameCounter, scriptTagSecond, textAnimationEndX, textAnimationEndY, endEffect, i);
				}
			}
			// code for animation effect ends
		}

		

	}
	
	private void setImageScriptIfFadeInEndEffects(FrameModel firstFrame,int frameCounter, Script scriptTagSecond, Float imageAnimationEndX, Float imageAnimationEndY, String endEffect, int i) {
		Float startTime = firstFrame.getImageList().get(i).getEndOnTime();
		Float endTime = firstFrame.getImageList().get(i).getEndOffTime();
		String endEffectCoordinates = "";
		
		if((imageAnimationEndX != 0) && (imageAnimationEndY != 0)) {
			endEffectCoordinates = ", x: " + imageAnimationEndX + ", y: " + imageAnimationEndY;
		}
		
		String scriptStr = "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", " + startTime + ", {opacity: 1" + endEffectCoordinates + "}, " + endTime + ");\r\n";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}
	
	private void setImageScriptIfFadeOutEndEffects(FrameModel firstFrame,int frameCounter, Script scriptTagSecond, Float imageAnimationEndX, Float imageAnimationEndY, String endEffect, int i) {
		Float startTime = firstFrame.getImageList().get(i).getEndOnTime();
		Float endTime = firstFrame.getImageList().get(i).getEndOffTime();
		String endEffectCoordinates = "";
		
		if((imageAnimationEndX != 0) && (imageAnimationEndY != 0)) {
			endEffectCoordinates = ", x: " + imageAnimationEndX + ", y: " + imageAnimationEndY;
		}
		
		String scriptStr = "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", " + startTime + ", {opacity: 0" + endEffectCoordinates + "}, " + endTime + ");\r\n";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}
	
	private void setTextScriptIfFadeInEndEffects(FrameModel firstFrame, int frameCounter, Script scriptTagSecond, Float textAnimationEndX, Float textAnimationEndY, String endEffect, int i) {
		Float startTime = firstFrame.getTextList().get(i).getEndOnTime();
		Float endTime = firstFrame.getTextList().get(i).getEndOffTime();
		String endEffectCoordinates = "";
		
		if((textAnimationEndX != 0) && (textAnimationEndY != 0)) {
			endEffectCoordinates = ", x: " + textAnimationEndX + ", y: " + textAnimationEndY;
		}
		
		String scriptStr = "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", " + startTime + ", {opacity: 1" + endEffectCoordinates + "}, " + endTime + ");\r\n";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}
	
	private void setTextScriptIfFadeOutEndEffects(FrameModel firstFrame, int frameCounter, Script scriptTagSecond, Float textAnimationEndX, Float textAnimationEndY, String endEffect, int i) {
		Float startTime = firstFrame.getTextList().get(i).getEndOnTime();
		Float endTime = firstFrame.getTextList().get(i).getEndOffTime();
		String endEffectCoordinates = "";
		
		if((textAnimationEndX != 0) && (textAnimationEndY != 0)) {
			endEffectCoordinates = ", x: " + textAnimationEndX + ", y: " + textAnimationEndY;
		}
		
		String scriptStr = "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", " + startTime + ", {opacity: 0" + endEffectCoordinates + "}, " + endTime + ");\r\n";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}

	private void setImageScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX,
			Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX,
			Float imageAnimationStopY, Float imageAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivFirstFrame" + frameCounter + "Element" + (i + 1)
				+ " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1) + "\");\r\n"
				+ "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".set(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", {x: " + imageAnimationStartX + ", y: "
				+ imageAnimationStartY + "});\r\n" 
				+ "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", "
				+ imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: "
				+ imageAnimationStopY + "}, " + imageAnimationStartTime + ");\r\n";

		/*if (frameCounter != frameSize) {
			scriptStr += "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}
		*/

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setImageScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX,
			Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX,
			Float imageAnimationStopY, Float imageAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivFirstFrame" + frameCounter + "Element" + (i + 1)
				+ " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1) + "\");\r\n"
				+ "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".set(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", {x: " + imageAnimationStartX + ", y: "
				+ imageAnimationStartY + ", opacity: 0});\r\n" + "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element"
				+ (i + 1) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: "
				+ imageAnimationStopY + ", opacity: 1}, " + imageAnimationStartTime + ");\r\n";

		/*if (frameCounter != frameSize) {
			scriptStr += "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}
		*/

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setImageScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX,
			Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX,
			Float imageAnimationStopY, Float imageAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivFirstFrame" + frameCounter + "Element" + (i + 1)
				+ " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1) + "\");\r\n"
				+ "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".set(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", {x: " + imageAnimationStartX + ", y: "
				+ imageAnimationStartY + ", opacity: 1});\r\n" + "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element"
				+ (i + 1) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: "
				+ imageAnimationStopY + ", opacity: 0}, " + imageAnimationStartTime + ");\r\n";

		/*if (frameCounter != frameSize) {
			scriptStr += "timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivFirstFrame" + frameCounter + "Element" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}
		*/

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setTextScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX,
			Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY,
			Float textAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivSecondFrame" + frameCounter + "Element"
				+ (i + 1) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1)
				+ "\");\r\n" + "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".set(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", {x: "
				+ textAnimationStartX + ", y: " + textAnimationStartY + "});\r\n" + "timeLineSecondFrame" + frameCounter + "Element" + (i + 1)
				+ ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", " + textAnimationEndTime + ", {display: 'block', x: "
				+ textAnimationStopX + ", y: " + textAnimationStopY + "}, " + textAnimationStartTime + ");\r\n";

		/*if (frameCounter != frameSize) {
			scriptStr += "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}
		*/

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setTextScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX,
			Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY,
			Float textAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivSecondFrame" + frameCounter + "Element"
				+ (i + 1) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1)
				+ "\");\r\n" + "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".set(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", {x: "
				+ textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 0});\r\n" + "timeLineSecondFrame" + frameCounter + "Element"
				+ (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", " + textAnimationEndTime + ", {display: 'block', x: "
				+ textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 1}, " + textAnimationStartTime + ");\r\n";

		/*if (frameCounter != frameSize) {
			scriptStr += "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}
		*/

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setTextScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX,
			Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY,
			Float textAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivSecondFrame" + frameCounter + "Element"
				+ (i + 1) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1)
				+ "\");\r\n" + "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".set(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", {x: "
				+ textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 1});\r\n" + "timeLineSecondFrame" + frameCounter + "Element"
				+ (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", " + textAnimationEndTime + ", {display: 'block', x: "
				+ textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 0}, " + textAnimationStartTime + ");\r\n";

		/*if (frameCounter != frameSize) {
			scriptStr += "timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".to(childDivSecondFrame" + frameCounter + "Element" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}
		*/

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	public void setPauseOnHover(Body body, Script scriptTagSecond, BannerModel bannerModel) {
		scriptTagSecond.appendChild(
				new Text("" + "$(\".wrapper\").hover(function(){\r\n" + "	TweenMax.pauseAll(true, true, true); \r\n"
						+ "}, function(){\r\n" + "	TweenMax.resumeAll(true, true, true);\r\n" + "});\r\n"));
	}
	
	public String replayBannerBody(Body body, Script scriptTagSecond, BannerModel bannerModel, FrameModel firstFrame, int frameCounter, String restartStr) {
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		
		
		for (int i = 0; i < frameImageElementList.size(); i++) {
			restartStr += "	timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".restart();\r\n" ;
		}
		
		for (int i = 0; i < frameTextElementList.size(); i++) {
			restartStr += "	timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".restart();\r\n" ;
		}
		
		return restartStr;
	}
	
	public void replayBanner(Script scriptTagSecond, String restartStr, int frameCounter) {
		
		
		String scriptStr = "function replayBanner() {\r\n"
				+ restartStr
				+ "}\r\n";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
		scriptStr = "$('.reply-button').on('click', function(event) {\r\n"
				+ "		event.stopPropagation();\r\n"
				+ "		replayBanner();\r\n"
				+ "});\r\n";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
	}
	
	public String timeLineRestartStr(Body body, Script scriptTagSecond, FrameModel firstFrame, int frameCounter, String restsrtStr){
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		
		
		for (int i = 0; i < frameImageElementList.size(); i++) {
			restsrtStr += "	timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".restart();\r\n" ;
		}
		
		for (int i = 0; i < frameTextElementList.size(); i++) {
			restsrtStr += "	timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".restart();\r\n" ;
		}
		
		return restsrtStr;
	}
	
	public String timeLineCleartStr(Body body, Script scriptTagSecond, FrameModel firstFrame, int frameCounter, String clearStr){
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		
		
		for (int i = 0; i < frameImageElementList.size(); i++) {
			clearStr += "			timeLineFirstFrame" + frameCounter + "Element" + (i + 1) + ".clear();\r\n" ;
		}
		
		for (int i = 0; i < frameTextElementList.size(); i++) {
			clearStr += "			timeLineSecondFrame" + frameCounter + "Element" + (i + 1) + ".clear();\r\n" ;
		}
		
		return clearStr;
	}

	public void animateBannerInLoop(Body body, Script scriptTagSecond, BannerModel bannerModel, int animationLoopCount, String clearStr, String restsrtStr) {
		 String callBackStr = "{onComplete: function(){\r\n" +
		 "	//delay of 2 sec to start the next loop\r\n" +
		 "	//TweenLite.delayedCall(2, updateContent);\r\n" + 
		 "	TweenLite.delayedCall(updateContent);\r\n" + "}}\r\n";
		 
		 String scriptStr = "var timeLineToAnimateInLoop = new TimelineLite(" + callBackStr + ");\r\n";
		 
		 //scriptTagSecond.appendChild(new Text(scriptStr));
		 
		 Float maxStopTime = Float.parseFloat(bannerModel.getMax_stop_time().trim());
		 
		 scriptStr += "var invisible = document.getElementById(\"invisible\");\r\n" + 
		 		"timeLineToAnimateInLoop.set(invisible, {x: 1.0, y: 1.0});\r\n" + 
		 		"timeLineToAnimateInLoop.to(invisible, 1.0, {x: 1.0, y: 1.0}, " + maxStopTime + ");\r\n" + 
		 		"timeLineToAnimateInLoop.play();\r\n";
		 
		 scriptTagSecond.appendChild(new Text(scriptStr));
		 
		 
		 
		 String updateContent = "var loopCounter = 1;\r\n" 
			 + "function updateContent(){\r\n";
		 	
		 	 if(animationLoopCount != 0) {
				 updateContent += "		if(loopCounter == " + animationLoopCount + "){\r\n" 
						 				+ clearStr 
										+ "}\r\n";
		 	 }
			 updateContent += "	loopCounter++;\r\n"
			 + restsrtStr
			 + "	timeLineToAnimateInLoop.restart();\r\n"
			 + "}\r\n";
		 
			 scriptTagSecond.appendChild(new Text(scriptStr + updateContent)); 
	}
}
