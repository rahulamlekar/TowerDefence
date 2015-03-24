package helpers;

public class EntityColor {
	private float r;
	private float g;
	private float b;
	
	public EntityColor(){
		r = 0.5f;
		g = 0.5f;
		b = 0.5f;
	}
	public EntityColor(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public EntityColor(String name){
		switch(name){
		case "Red":
			this.setRGB(1.0f,  0.0f, 0.0f);
			break;
		case "Blue":
			this.setRGB(0.0f,  0.0f, 1.0f);
			break;
		case "Yellow":
			this.setRGB(1.0f,  1.0f, 0.0f);
			break;
		case "Green":
			this.setRGB(0.0f,  1.0f, 0.0f);
			break;
		case "Cyan":
			this.setRGB(0.0f,  1.0f, 1.0f);
			break;
		case "Purple":
			this.setRGB(1.0f,  0.0f, 1.0f);
			break;
		default:
			this.setRGB(1.0f, 1.0f, 1.0f);
			break;
		}
		
		
	}
	public float getR() {
		return r;
	}
	public void setR(float r) {
		this.r = r;
	}
	public float getG() {
		return g;
	}
	public void setG(float g) {
		this.g = g;
	}
	public float getB() {
		return b;
	}
	public void setB(float b) {
		this.b = b;
	}
	public void setRGB(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
}
