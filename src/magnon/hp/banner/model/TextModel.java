package magnon.hp.banner.model;

public class TextModel {
	
	private int isText;
	private String text;
	private float onTime;
	private float offTime;
	private float endOnTime;
	private float endOffTime;
	private float startCoordinateX;
	private float startCoordinateY;
	private float stopCoordinateX;
	private float stopCoordinateY;
	private float endCoordinateX;
	private float endCoordinateY;
	private String effect;
	private String endEffect;
	private String bannerTextStorkColor;
	private String bannerTextColor;
	private String textFontSize;
	private String textFont;
	private String bannerTextSvg;
	private int elementNumber;
	
	public int getElementNumber() {
		return elementNumber;
	}
	public void setElementNumber(int elementNumber) {
		this.elementNumber = elementNumber;
	}
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
	public int getIsText() {
		return isText;
	}
	public void setIsText(int isText) {
		this.isText = isText;
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
	public float getEndCoordinateX() {
		return endCoordinateX;
	}
	public void setEndCoordinateX(float endCoordinateX) {
		this.endCoordinateX = endCoordinateX;
	}
	public float getEndCoordinateY() {
		return endCoordinateY;
	}
	public void setEndCoordinateY(float endCoordinateY) {
		this.endCoordinateY = endCoordinateY;
	}
	public String getBannerTextStorkColor() {
		return bannerTextStorkColor;
	}
	public void setBannerTextStorkColor(String bannerTextStorkColor) {
		this.bannerTextStorkColor = bannerTextStorkColor;
	}
	public String getBannerTextColor() {
		return bannerTextColor;
	}
	public void setBannerTextColor(String bannerTextColor) {
		this.bannerTextColor = bannerTextColor;
	}
	public String getTextFontSize() {
		return textFontSize;
	}
	public void setTextFontSize(String textFontSize) {
		this.textFontSize = textFontSize;
	}
	public String getTextFont() {
		return textFont;
	}
	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}
	public String getBannerTextSvg() {
		return bannerTextSvg;
	}
	public void setBannerTextSvg(String bannerTextSvg) {
		this.bannerTextSvg = bannerTextSvg;
	}
	public String toString() {
		return "text :"+this.text;
	}
}
