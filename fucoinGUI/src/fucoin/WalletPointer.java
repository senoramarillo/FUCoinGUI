package fucoin;

import akka.actor.ActorRef;

public class WalletPointer {
	public String address;

	public WalletPointer(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		ActorRef actor = Wallet.stringToActor(address);
		return actor.path().toStringWithAddress(actor.path().address());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WalletPointer) {
			return ((WalletPointer) obj).address.equals(address);
		}
		return false;
	}
}
