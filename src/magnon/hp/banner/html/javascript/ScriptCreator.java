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

	public void generateScript(Body body, FrameModel firstFrame, Script scriptTagSecond, int frameCounter,
			BannerModel bannerModel, int frameSize) {
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		// image on/off times in sec
		Float imageAnimationStartTime, imageAnimationEndTime;

		// image on/off coordinates
		Float imageAnimationStartX, imageAnimationStartY, imageAnimationStopX, imageAnimationStopY;

		// text on/off times in sec
		Float textAnimationStartTime, textAnimationEndTime;

		// text on/off coordinates
		Float textAnimationStartX, textAnimationStartY, textAnimationStopX, textAnimationStopY;

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
					setImageScriptIfFadeInEndEffects(firstFrame, scriptTagSecond, endEffect, i);
				}
				else if (endEffect.equals("fadeout")) {
					setImageScriptIfFadeOutEndEffects(firstFrame, scriptTagSecond, endEffect, i);
				}
			}
			// code for animation effect ends
		}

		// javascript for text element
		for (int i = 0; i < frameImageElementList.size(); i++) {
			// text on/off times in sec
			textAnimationStartTime = firstFrame.getTextList().get(i).getOnTime();
			textAnimationEndTime = firstFrame.getTextList().get(i).getOffTime();
			textAnimationEndTime = (textAnimationEndTime - textAnimationStartTime);

			// text on/off coordinates
			textAnimationStartX = firstFrame.getTextList().get(i).getStartCoordinateX();
			textAnimationStartY = firstFrame.getTextList().get(i).getStartCoordinateY();
			textAnimationStopX = firstFrame.getTextList().get(i).getStopCoordinateX();
			textAnimationStopY = firstFrame.getTextList().get(i).getStopCoordinateY();

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
					setTextScriptIfFadeInEndEffects(firstFrame, scriptTagSecond, endEffect, i);
				}
				else if (endEffect.equals("fadeout")) {
					setTextScriptIfFadeOutEndEffects(firstFrame, scriptTagSecond, endEffect, i);
				}
			}
			// code for animation effect ends
		}

		// code to pause animation on hover starts
		String pauseOnHoverStr = bannerModel.getPause_on_hover().trim();
		int pauseOnHover = 0;
		if (!pauseOnHoverStr.equals("") && !pauseOnHoverStr.equals("0")) {
			pauseOnHover = Integer.parseInt(pauseOnHoverStr);
		}

		if (pauseOnHover == 1) {
			setPauseOnHover(body, scriptTagSecond, bannerModel);
		}
		// code to pause animation on hover ends

		//code to animate banner in loop starts
		String animationLoopStr = bannerModel.getAnimation_loop().trim();
		String animationLoopCountStr = bannerModel.getLoop_count().trim(); 
		int animationLoopCount = 0; 
		if(!animationLoopStr.equals("") && !animationLoopStr.equals("0")) { 
			animationLoopCount = Integer.parseInt(animationLoopCountStr);
			
			animateBannerInLoop(body, scriptTagSecond, bannerModel, animationLoopCount);
		}
		//code to animate banner in loop ends

	}
	
	private void setImageScriptIfFadeInEndEffects(FrameModel firstFrame, Script scriptTagSecond, String endEffect, int i) {
		Float startTime = firstFrame.getImageList().get(i).getEndOnTime();
		Float endTime = firstFrame.getImageList().get(i).getEndOffTime();
		
		String scriptStr = "timeLineFirst" + (i + 1) + ".to(childDivFirst" + (i + 1) + ", " + startTime + ", {opacity: 1}, " + endTime + ");";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}
	
	private void setImageScriptIfFadeOutEndEffects(FrameModel firstFrame, Script scriptTagSecond, String endEffect, int i) {
		Float startTime = firstFrame.getImageList().get(i).getEndOnTime();
		Float endTime = firstFrame.getImageList().get(i).getEndOffTime();
		
		String scriptStr = "timeLineFirst" + (i + 1) + ".to(childDivFirst" + (i + 1) + ", " + startTime + ", {opacity: 0}, " + endTime + ");";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}
	
	private void setTextScriptIfFadeInEndEffects(FrameModel firstFrame, Script scriptTagSecond, String endEffect, int i) {
		Float startTime = firstFrame.getTextList().get(i).getEndOnTime();
		Float endTime = firstFrame.getTextList().get(i).getEndOffTime();
		
		String scriptStr = "timeLineSecond" + (i + 1) + ".to(childDivSecond" + (i + 1) + ", " + startTime + ", {opacity: 1}, " + endTime + ");";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}
	
	private void setTextScriptIfFadeOutEndEffects(FrameModel firstFrame, Script scriptTagSecond, String endEffect, int i) {
		Float startTime = firstFrame.getTextList().get(i).getEndOnTime();
		Float endTime = firstFrame.getTextList().get(i).getEndOffTime();
		
		String scriptStr = "timeLineSecond" + (i + 1) + ".to(childDivSecond" + (i + 1) + ", " + startTime + ", {opacity: 0}, " + endTime + ");";
		
		scriptTagSecond.appendChild(new Text(scriptStr));
		
	}

	private void setImageScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX,
			Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX,
			Float imageAnimationStopY, Float imageAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineFirst" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivFirst" + (i + 1)
				+ " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1) + "\");\r\n"
				+ "timeLineFirst" + (i + 1) + ".set(childDivFirst" + (i + 1) + ", {x: " + imageAnimationStartX + ", y: "
				+ imageAnimationStartY + "});\r\n" 
				+ "timeLineFirst" + (i + 1) + ".to(childDivFirst" + (i + 1) + ", "
				+ imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: "
				+ imageAnimationStopY + "}, " + imageAnimationStartTime + ");\r\n";

		if (frameCounter != frameSize) {
			scriptStr += "timeLineFirst" + (i + 1) + ".to(childDivFirst" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setImageScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX,
			Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX,
			Float imageAnimationStopY, Float imageAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineFirst" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivFirst" + (i + 1)
				+ " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1) + "\");\r\n"
				+ "timeLineFirst" + (i + 1) + ".set(childDivFirst" + (i + 1) + ", {x: " + imageAnimationStartX + ", y: "
				+ imageAnimationStartY + ", opacity: 0});\r\n" + "timeLineFirst" + (i + 1) + ".to(childDivFirst"
				+ (i + 1) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: "
				+ imageAnimationStopY + ", opacity: 1}, " + imageAnimationStartTime + ");\r\n";

		if (frameCounter != frameSize) {
			scriptStr += "timeLineFirst" + (i + 1) + ".to(childDivFirst" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setImageScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float imageAnimationStartX,
			Float imageAnimationStartY, Float imageAnimationEndTime, Float imageAnimationStopX,
			Float imageAnimationStopY, Float imageAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineFirst" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivFirst" + (i + 1)
				+ " = document.getElementById(\"frame" + frameCounter + "-child-first-" + (i + 1) + "\");\r\n"
				+ "timeLineFirst" + (i + 1) + ".set(childDivFirst" + (i + 1) + ", {x: " + imageAnimationStartX + ", y: "
				+ imageAnimationStartY + ", opacity: 1});\r\n" + "timeLineFirst" + (i + 1) + ".to(childDivFirst"
				+ (i + 1) + ", " + imageAnimationEndTime + ", {display: 'block', x: " + imageAnimationStopX + ", y: "
				+ imageAnimationStopY + ", opacity: 0}, " + imageAnimationStartTime + ");\r\n";

		if (frameCounter != frameSize) {
			scriptStr += "timeLineFirst" + (i + 1) + ".to(childDivFirst" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setTextScriptIfNoEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX,
			Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY,
			Float textAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineSecond" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivSecond"
				+ (i + 1) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1)
				+ "\");\r\n" + "timeLineSecond" + (i + 1) + ".set(childDivSecond" + (i + 1) + ", {x: "
				+ textAnimationStartX + ", y: " + textAnimationStartY + "});\r\n" + "timeLineSecond" + (i + 1)
				+ ".to(childDivSecond" + (i + 1) + ", " + textAnimationEndTime + ", {display: 'block', x: "
				+ textAnimationStopX + ", y: " + textAnimationStopY + "}, " + textAnimationStartTime + ");\r\n";

		if (frameCounter != frameSize) {
			scriptStr += "timeLineSecond" + (i + 1) + ".to(childDivSecond" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setTextScriptIfFadeInEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX,
			Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY,
			Float textAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineSecond" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivSecond"
				+ (i + 1) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1)
				+ "\");\r\n" + "timeLineSecond" + (i + 1) + ".set(childDivSecond" + (i + 1) + ", {x: "
				+ textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 0});\r\n" + "timeLineSecond"
				+ (i + 1) + ".to(childDivSecond" + (i + 1) + ", " + textAnimationEndTime + ", {display: 'block', x: "
				+ textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 1}, " + textAnimationStartTime + ");\r\n";

		if (frameCounter != frameSize) {
			scriptStr += "timeLineSecond" + (i + 1) + ".to(childDivSecond" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setTextScriptIfFadeOutEffects(Script scriptTagSecond, int frameCounter, Float textAnimationStartX,
			Float textAnimationStartY, Float textAnimationEndTime, Float textAnimationStopX, Float textAnimationStopY,
			Float textAnimationStartTime, int i, int frameSize, FrameModel firstFrame) {
		String scriptStr = "var timeLineSecond" + (i + 1) + " = new TimelineLite();\r\n" + "var childDivSecond"
				+ (i + 1) + " = document.getElementById(\"frame" + frameCounter + "-child-second-" + (i + 1)
				+ "\");\r\n" + "timeLineSecond" + (i + 1) + ".set(childDivSecond" + (i + 1) + ", {x: "
				+ textAnimationStartX + ", y: " + textAnimationStartY + ", opacity: 1});\r\n" + "timeLineSecond"
				+ (i + 1) + ".to(childDivSecond" + (i + 1) + ", " + textAnimationEndTime + ", {display: 'block', x: "
				+ textAnimationStopX + ", y: " + textAnimationStopY + ", opacity: 0}, " + textAnimationStartTime + ");\r\n";

		if (frameCounter != frameSize) {
			scriptStr += "timeLineSecond" + (i + 1) + ".to(childDivSecond" + (i + 1) + ", 0.0, {\r\n"
					+ "			display: 'none'\r\n" + "		});\r\n";
		}

		scriptTagSecond.appendChild(new Text(scriptStr));
	}

	private void setPauseOnHover(Body body, Script scriptTagSecond, BannerModel bannerModel) {
		scriptTagSecond.appendChild(
				new Text("" + "$(\".wrapper\").hover(function(){\r\n" + "	TweenMax.pauseAll(true, true, true); \r\n"
						+ "}, function(){\r\n" + "	TweenMax.resumeAll(true, true, true);\r\n" + "});\r\n"));
	}

	private void animateBannerInLoop(Body body, Script scriptTagSecond, BannerModel bannerModel,int animationLoopCount) {
		 String callBackStr = "{onComplete: function(){\r\n" +
		 "	//delay of 2 sec to start the next loop\r\n" +
		 "	TweenLite.delayedCall(2, updateContent);\r\n" + "}}";
		 
		 String scriptStr = "var timeLineToAnimateInLoop = new TimelineLite(" + callBackStr + ");\r\n";
		 scriptTagSecond.appendChild(new Text(scriptStr));
		 
		 String maxStopTime = bannerModel.getMax_stop_time().trim();
		 
		 scriptStr = "var invisible = document.getElementById(\"invisible\");\r\n" + 
		 		"timeLineToAnimateInLoop.set(invisible, {x: 1.0, y: 1.0});\r\n" + 
		 		"timeLineToAnimateInLoop.to(invisible, 1.0, {x: 1.0, y: 1.0}, " + maxStopTime + ");\r\n" + 
		 		"timeLineToAnimateInLoop.play();\r\n";
		 
		 String updateContent = "var loopCounter = 0;\r\n" 
			 + "function updateContent(){\r\n";
		 	
		 	 if(animationLoopCount != 0) {
				 updateContent += "		if(loopCounter == " + animationLoopCount + "){\r\n" 
						 				+"			timeLineFirst1.clear();\r\n" 
						 				+"			timeLineSecond1.clear();\r\n" 
										+ "}\r\n";
		 	 }
			 updateContent += "	loopCounter++;\r\n"
			 + "	timeLineFirst1.restart();\r\n" 
			 + "	timeLineSecond1.restart();\r\n"
			 + "	timeLineToAnimateInLoop.restart();\r\n"
			 + "}";
		 
			 scriptTagSecond.appendChild(new Text(scriptStr + updateContent)); 
	}
}
