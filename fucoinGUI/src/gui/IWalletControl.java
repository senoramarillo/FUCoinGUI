package gui;

public interface IWalletControl {
	public void leave();
	public void store();
	public void send(String address, int amount);
}
