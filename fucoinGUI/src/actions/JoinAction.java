package actions;

import fucoin.WalletPointer;

public class JoinAction {

	private WalletPointer newMember;

	public JoinAction(WalletPointer walletPointer) {
		this.newMember=walletPointer;
	}
	
	public WalletPointer getNewMember() {
		return newMember;
	}

}

