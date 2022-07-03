package nodes;

import core.EncryptionScheme;

public class EncryptNode extends EncryptionAssociatedNode {
	private float latencyPerOperation;
	
	public EncryptNode(EncryptionScheme encryptionScheme) {
		super(encryptionScheme);
	}

	public float getLatencyPerOperation() {
		return latencyPerOperation;
	}

	public void setLatencyPerOperation(float latencyPerOperation) {
		this.latencyPerOperation = latencyPerOperation;
	}
	
	
}
