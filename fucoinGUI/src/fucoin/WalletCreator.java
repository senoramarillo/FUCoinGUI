package fucoin;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import actions.InvokePerformTransactionAction;
import gui.IWalletGuiControl;
import gui.WalletGui;
import gui.IWalletGuiControl;
import akka.japi.Creator;


public class WalletCreator implements Creator<Wallet>{

	private String preknownNeighbour;
	private String walletCounter;

	public WalletCreator(String preknownNeighbour, String walletCounter) {
		this.preknownNeighbour=preknownNeighbour;
		this.walletCounter=walletCounter;
	}

	@Override
	public Wallet create() throws Exception {
		
		Wallet tempwallet = new Wallet(preknownNeighbour);
		IWalletGuiControl gui = new WalletGui(tempwallet);
		tempwallet.setGui(gui);
		
		
		/*
		Wallet tempwallet = new Wallet(preknownNeighbour,log);
		JFrame frame = new JFrame("Wallet:"+walletCounter);
		frame.setLayout(new GridLayout(3,1));
		
		
		JList<String> list = new JList<String>(log);
		JTextField dest = new JTextField();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		JButton storebutton = new JButton("Store");
		storebutton.addActionListener(new ActionListener() {
			Wallet wallet = tempwallet;
			@Override
			public void actionPerformed(ActionEvent e) {
				wallet.store();
			}
		});
		JButton exitbutton = new JButton("Exit");
		exitbutton.addActionListener(new ActionListener() {
			Wallet wallet = tempwallet;
			@Override
			public void actionPerformed(ActionEvent e) {
				wallet.exit();
				frame.dispose();
			}
		});
		JButton send10Btn = new JButton("Send10");
		send10Btn.addActionListener(new ActionListener() {
			Wallet wallet = tempwallet;
			@Override
			public void actionPerformed(ActionEvent e) {
				
				wallet.invokePerformTransaction(new WalletPointer(dest.getText()), 10);
			}
		});
		panel.add(storebutton);
		panel.add(exitbutton);
		panel.add(send10Btn);
		frame.add(list);
		frame.add(dest);
		frame.add(panel);
		frame.setSize(300, 300);
		frame.setVisible(true);*/
		return tempwallet;
	} 

}
