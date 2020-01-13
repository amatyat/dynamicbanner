package magnon.hp.banner.model;

public class ImageModel {
	
	private int isImage;
	private String imagePath;
	private float onTime;
	private float offTime;
	private float endOnTime;
	private float endOffTime;
	private float startCoordinateX;
	private float startCoordinateY;
	private float stopCoordinateX;
	private float stopCoordinateY;
	private String effect;
	private String endEffect;
	private String width;
	private String height;
	private int elementNumber;
	
	public String getWidth() {
		return width;
	}
	public int getElementNumber() {
		return elementNumber;
	}
	public void setElementNumber(int elementNumber) {
		this.elementNumber = elementNumber;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
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
	public float getEndOnTime() {
		return endOnTime;
	}
	public void setEndOnTime(float endOnTime) {
		this.endOnTime = endOnTime;
	}
	public float getEndOffTime() {
		return endOffTime;
	}
	public void setEndOffTime(float endOffTime) {
		this.endOffTime = endOffTime;
	}
	public String getEndEffect() {
		return endEffect;
	}
	public void setEndEffect(String endEffect) {
		this.endEffect = endEffect;
	}
	public int getIsImage() {
		return isImage;
	}
	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}
	public float getStartCoordinateX() {
		return startCoordinateX;
	}
	public void setStartCoordinateX(float startCoordinateX) {
		this.startCoordinateX = startCoordinateX;
	}
	public float getStartCoordinateY() {
		return startCoordinateY;
	}
	public void setStartCoordinateY(float startCoordinateY) {
		this.startCoordinateY = startCoordinateY;
	}
	public float getStopCoordinateX() {
		return stopCoordinateX;
	}
	public void setStopCoordinateX(float stopCoordinateX) {
		this.stopCoordinateX = stopCoordinateX;
	}
	public float getStopCoordinateY() {
		return stopCoordinateY;
	}
	public void setStopCoordinateY(float stopCoordinateY) {
		this.stopCoordinateY = stopCoordinateY;
	}
	
	public String toString() {
		return "imagePath :"+this.imagePath;
	}

}
