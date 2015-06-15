package actions;

public class InvalidateAction {

	private String invalidWallet;

	public InvalidateAction(String invalidWallet) {
		this.invalidWallet=invalidWallet;
	}
	
	public String getInvalidWallet() {
		return invalidWallet;
	}

}

