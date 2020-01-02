package magnon.hp.banner.html;

import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;

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
