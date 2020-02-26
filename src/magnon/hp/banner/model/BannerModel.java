package magnon.hp.banner.model;

import java.util.List;

public class BannerModel {
	
	private String canvas_height;
	private String banner_file_path;
	private String username;
	private String foldername;
	private String canvas_width;
	private String colorpicker;
	private String is_gradient;
	private String gradient_value;
	private String hpl_link;
	private String target;
	private String animation_loop;
	private String loop_count;
	private String pause_on_hover;
	private String replay;
	private String max_stop_time;
	private List<FrameModel> frames;
	
	
	public String getBanner_file_path() {
		return banner_file_path;
	}
	public void setBanner_file_path(String banner_file_path) {
		this.banner_file_path = banner_file_path;
	}
	public String getColorpicker() {
		return colorpicker;
	}
	public String getIs_gradient() {
		return is_gradient;
	}
	public void setIs_gradient(String is_gradient) {
		this.is_gradient = is_gradient;
	}
	public String getGradient_value() {
		return gradient_value;
	}
	public void setGradient_value(String gradient_value) {
		this.gradient_value = gradient_value;
	}
	public String getReplay() {
		return replay;
	}
	public void setReplay(String replay) {
		this.replay = replay;
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
	public String getMax_stop_time() {
		return max_stop_time;
	}
	public void setMax_stop_time(String max_stop_time) {
		this.max_stop_time = max_stop_time;
	}
	public void setCanvas_height(String canvas_height) {
		this.canvas_height = canvas_height;
	}
	public String getAnimation_loop() {
		return animation_loop;
	}
	public void setAnimation_loop(String animation_loop) {
		this.animation_loop = animation_loop;
	}
	public String getLoop_count() {
		return loop_count;
	}
	public void setLoop_count(String loop_count) {
		this.loop_count = loop_count;
	}
	public String getPause_on_hover() {
		return pause_on_hover;
	}
	public void setPause_on_hover(String pause_on_hover) {
		this.pause_on_hover = pause_on_hover;
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
		return "Frame Size:"+this.frames.size()+" ColorPicker:"+this.colorpicker+" Frame Image:"+this.frames.toString();
	}

}
