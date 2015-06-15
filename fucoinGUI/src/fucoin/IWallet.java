package fucoin;
import java.util.Vector;

import gui.IWalletControl;

public interface IWallet extends IWalletControl{
	Vector<WalletPointer> join();
	void storeOrUpdate(Wallet w);
	void invalidateWallet(Wallet w);
	void receiveTransaction(int amount);
	Vector<WalletPointer> searchWallet(String adress);
}
