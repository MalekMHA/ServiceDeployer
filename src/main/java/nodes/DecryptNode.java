package nodes;

import core.EncryptionScheme;

public class DecryptNode extends EncryptionAssociatedNode {
	private float latencyPerOperation;
	
	public DecryptNode(EncryptionScheme encryptionScheme) {
		super(encryptionScheme);
	}

	public float getLatencyPerOperation() {
		return latencyPerOperation;
	}

	public void setLatencyPerOperation(float latencyPerOperation) {
		this.latencyPerOperation = latencyPerOperation;
	}
}
