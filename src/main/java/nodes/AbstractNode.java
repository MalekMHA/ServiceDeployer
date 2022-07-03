package nodes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import core.EncryptionScheme;
import core.Processor;

public class AbstractNode extends Node {
	
	private boolean end = false;
	private Processor processor;
	
	private Set<EncryptionScheme> encryptionSchemes;
	private Map<EncryptionScheme, Float> encryptionSchemeSpeeds;
	
	private Node processNode;
	private Map<EncryptionScheme, Node> outputNodes, inputNodes, decryptNodes, encryptNodes;
	
	public AbstractNode() {
		super();
		encryptionSchemes = new HashSet<>();
		outputNodes = new HashMap<>(); inputNodes = new HashMap<>(); decryptNodes = new HashMap<>(); encryptNodes = new HashMap<>();
		encryptionSchemeSpeeds = new HashMap<>();
	}
	public boolean IsEnd() {
		return this.end;
	}
	public void SetEnd(boolean newState) {
		this.end = newState;
	}
	public void addEncryptionScheme(EncryptionScheme encryptionScheme, float speed) {
		encryptionSchemes.add(encryptionScheme);
		encryptionSchemeSpeeds.put(encryptionScheme, speed);
	}
	public Set<EncryptionScheme> getEncryptionSchemes(){
		return encryptionSchemes;
	}
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}
	public Processor getProcessor() {
		return this.processor;
	}
	
	public void setProcessNode(Node processNode) {
		this.processNode = processNode;
	}
	public Node getProcessNode() {
		return this.processNode;
	}
	public void addOutputNode(EncryptionScheme encryptionScheme, Node output) {
		outputNodes.put(encryptionScheme, output);
	}
	public Map<EncryptionScheme, Node> getOutputNodes(){
		return this.outputNodes;
	}
	public void addInputNode(EncryptionScheme encryptionScheme, Node input) {
		inputNodes.put(encryptionScheme, input);
	}
	public Map<EncryptionScheme, Node> getInputNodes(){
		return this.inputNodes;
	}
	public void addDecryptNode(EncryptionScheme encryptionScheme, Node decryptNode) {
		decryptNodes.put(encryptionScheme, decryptNode);
	}
	public Map<EncryptionScheme, Node> getDecryptNodes(){
		return this.decryptNodes;
	}
	public void addEncryptNode(EncryptionScheme encryptionScheme, Node encryptNode) {
		encryptNodes.put(encryptionScheme, encryptNode);
	}
	public Map<EncryptionScheme, Node> getEncryptNodes(){
		return this.encryptNodes;
	}
	public float getEncryptionSchemeSpeed(EncryptionScheme encryptionScheme) {
		return encryptionSchemeSpeeds.get(encryptionScheme);
	}
	
	public String getNodeDescription(Node n) {
		if (this.getProcessNode() == n) {
			return "**P** (" + this + ")";
		} else {
			for (EncryptionScheme encryptionScheme : this.getDecryptNodes().keySet()) {
				if (n == this.getDecryptNodes().get(encryptionScheme)) {
					return "D E" + encryptionScheme.getId() + " (" + this + ")";
				}
			}
			for (EncryptionScheme encryptionScheme : this.getEncryptNodes().keySet()) {
				if (n == this.getEncryptNodes().get(encryptionScheme)) {
					return "E E" + encryptionScheme.getId() + " (" + this + ")";
				}
			}
			for (EncryptionScheme encryptionScheme : this.getInputNodes().keySet()) {
				if (n == this.getInputNodes().get(encryptionScheme)) {
					return "I E" + encryptionScheme.getId() + " (" + this + ")";
				}
			}
			for (EncryptionScheme encryptionScheme : this.getOutputNodes().keySet()) {
				if (n == this.getOutputNodes().get(encryptionScheme)) {
					return "O E" + encryptionScheme.getId() + " (" + this + ")";
				}
			}
		}
		
		return null;
	}
	
}
