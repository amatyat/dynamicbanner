package magnon.hp.banner.model;

import java.util.List;

public class BannerModel {
	
	private String canvas_height;
	private String username;
	private String foldername;
	private String canvas_width;
	private String colorpicker;
	private String hpl_link;
	private String target;
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
	public String getHpl_link() {
		return hpl_link;
	}
	public void setHpl_link(String hpl_link) {
		this.hpl_link = hpl_link;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFoldername() {
		return foldername;
	}
	public void setFoldername(String foldername) {
		this.foldername = foldername;
	}
	
	
	public String toString() {
		return "Frame Size:"+this.frames.size();
	}

}
