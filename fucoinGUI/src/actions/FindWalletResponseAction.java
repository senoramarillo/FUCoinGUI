package actions;

public class FindWalletResponseAction {

	private String foundneighbour;
	private int moneyAmount;

	public FindWalletResponseAction(String foundneighbour, int moneyAmount) {
		this.foundneighbour=foundneighbour;
		this.moneyAmount=moneyAmount;
	}
	
	public String getFoundneighbour() {
		return foundneighbour;
	}

	public int getMoneyAmount() {
		return moneyAmount;
	}
}
