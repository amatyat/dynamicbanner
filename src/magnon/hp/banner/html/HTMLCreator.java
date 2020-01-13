package magnon.hp.banner.html;

import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Img;

import magnon.hp.banner.model.BannerModel;
import magnon.hp.banner.model.FrameModel;
import magnon.hp.banner.model.ImageModel;
import magnon.hp.banner.model.TextModel;

public class HTMLCreator {


	public static void generateHtml(Body body, BannerModel bannerModel, FrameModel firstFrame, Div wrapperDiv, int frameCounter) {
		String cssClass = "";
		String elementId = "";
		List<ImageModel> frameImageElementList = firstFrame.getImageList();
		for (int i = 0; i < frameImageElementList.size(); i++) {
			cssClass = "child frame" + frameCounter + "-child-first-" + (i + 1 );
			elementId = "frame" + frameCounter + "-child-first-" + (i + 1 );
			/* Div childFirstDiv = divContainerCreator(elementId, cssClass); */

			Img image = new Img( "", "images/" + frameImageElementList.get(i).getImagePath());
			image.setCSSClass(cssClass).setId(elementId);

			wrapperDiv.appendChild(image);
		}
		
		List<TextModel> frameTextElementList = firstFrame.getTextList();
		for (int i = 0; i < frameImageElementList.size(); i++) {
			cssClass = "child frame" + frameCounter + "-child-second-" + (i + 1 );
			elementId = "frame" + frameCounter + "-child-second-" + (i + 1 );
			Div childSecondDiv = divContainerCreator(elementId, cssClass);
			
			childSecondDiv.appendText(frameTextElementList.get(i).getText());
			wrapperDiv.appendChild(childSecondDiv);
		}
		
		//code to animate banner in loop starts
		String animationLoopStr = bannerModel.getAnimation_loop().trim();
		String animationLoopCountStr = bannerModel.getLoop_count().trim(); 
		int animationLoopCount = 0; 
		if(!animationLoopStr.equals("") && !animationLoopStr.equals("0")) { 
			cssClass = "invisible";
			elementId = "invisible";
			Div invisibleDiv = divContainerCreator(elementId, cssClass);
			
			wrapperDiv.appendChild(invisibleDiv);
		}
		//code to animate banner in loop ends
		
		//body.appendChild(wrapperDiv);
	}
	
	public static Div divContainerCreator(String elementId, String cssClass) {
		Div divContainer = new Div();
		//set element css class
		divContainer.setCSSClass(cssClass);
		//set element id if not null
		if(elementId != null) {
			divContainer.setId(elementId);
		}
		
		return divContainer;
	}
}
