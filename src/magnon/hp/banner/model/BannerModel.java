package magnon.hp.banner.model;

import java.util.List;

public class BannerModel {
	
	private String canvas_height;
	private String canvas_width;
	private String colorpicker;
	private List<FrameModel> frames;
	
	public String getColorpicker() {
		return colorpicker;
	}
	public List<FrameModel> getFrames() {
		return frames;
	}
	public void setFrames(List<FrameModel> frames) {
		this.frames = frames;
	}
	public void setColorpicker(String colorpicker) {
		this.colorpicker = colorpicker;
	}
	public String getCanvas_height() {
		return canvas_height;
	}
	public void setCanvas_height(String canvas_height) {
		this.canvas_height = canvas_height;
	}
	public String getCanvas_width() {
		return canvas_width;
	}
	public void setCanvas_width(String canvas_width) {
		this.canvas_width = canvas_width;
	}

}
