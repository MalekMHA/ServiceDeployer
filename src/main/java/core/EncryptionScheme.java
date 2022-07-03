package core;

public class EncryptionScheme {
	private static int idCounter = 0;
	
	private int id;
	private float vulnerability;
	
	public EncryptionScheme(float vulnerability) {
		this.id = idCounter++;
		this.vulnerability = vulnerability;
	}

	public float getVulnerability() {
		return this.vulnerability;
	}

	public int getId() {
		return id;
	}
}
