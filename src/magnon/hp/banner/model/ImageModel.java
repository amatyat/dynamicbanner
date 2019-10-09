package magnon.hp.banner.model;

public class ImageModel {
	
	private int isImage;
	private String imagePath;
	private float onTime;
	private float offTime;
	private String effect;
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public float getOnTime() {
		return onTime;
	}
	public void setOnTime(float onTime) {
		this.onTime = onTime;
	}
	public float getOffTime() {
		return offTime;
	}
	public void setOffTime(float offTime) {
		this.offTime = offTime;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public int getIsImage() {
		return isImage;
	}
	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}
	

}
