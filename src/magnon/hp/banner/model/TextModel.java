package magnon.hp.banner.model;

public class TextModel {
	
	private int isText;
	private String text;
	private float onTime;
	private float offTime;
	private float sartCoordinateX;
	private float sartCoordinateY;
	private float stopCoordinateX;
	private float stopCoordinateY;
	private String effect;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
	public int getIsText() {
		return isText;
	}
	public void setIsText(int isText) {
		this.isText = isText;
	}
	public float getSartCoordinateX() {
		return sartCoordinateX;
	}
	public void setSartCoordinateX(float sartCoordinateX) {
		this.sartCoordinateX = sartCoordinateX;
	}
	public float getSartCoordinateY() {
		return sartCoordinateY;
	}
	public void setSartCoordinateY(float sartCoordinateY) {
		this.sartCoordinateY = sartCoordinateY;
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
	
}
