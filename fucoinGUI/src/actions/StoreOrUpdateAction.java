package actions;

import fucoin.Wallet;

public class StoreOrUpdateAction {

	private Wallet wallet;

	public void setWallet(Wallet wallet) {
		this.wallet=wallet;
	}

	public Wallet getWallet() {
		return wallet;
	}
	
	@Override
	public String toString() {
		return "store or update";
	}
	
}