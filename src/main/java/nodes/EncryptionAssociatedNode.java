package nodes;

import core.EncryptionScheme;

public abstract class EncryptionAssociatedNode extends Node {
	private EncryptionScheme encryptionScheme;
	public EncryptionAssociatedNode(EncryptionScheme encryptionScheme) {
		this.encryptionScheme = encryptionScheme;
	}
	public EncryptionScheme getEncryptionScheme() {
		return this.encryptionScheme;
	}
}
