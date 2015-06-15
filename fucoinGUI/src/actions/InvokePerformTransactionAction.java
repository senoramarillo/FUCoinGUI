package actions;

import fucoin.WalletPointer;

public class InvokePerformTransactionAction {

	private WalletPointer walletPointer;
	private int amount;

	public InvokePerformTransactionAction(WalletPointer walletPointer, int amount) {
		this.walletPointer=walletPointer;
		this.amount=amount;
	}
	
	public WalletPointer getWalletPointer() {
		return walletPointer;
	}
	
	public int getAmount() {
		return amount;
	}

}
