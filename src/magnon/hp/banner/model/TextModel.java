package magnon.hp.banner.model;

public class TextModel {
	
	private int isText;
	private String text;
	private float onTime;
	private float offTime;
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
	
}
