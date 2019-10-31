package magnon.hp.banner.model;

import java.util.List;

public class FrameModel {
	
	private List<ImageModel> imageList;
	private List<TextModel> textList;
	public List<ImageModel> getImageList() {
		return imageList;
	}
	public void setImageList(List<ImageModel> imageList) {
		this.imageList = imageList;
	}
	public List<TextModel> getTextList() {
		return textList;
	}
	public void setTextList(List<TextModel> textList) {
		this.textList = textList;
	}
	
	public String toString() {
		return "imageList Size:"+this.imageList.size()+"textList Size:"+this.textList.size();
	}

}
