package fucoin;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.concurrent.Executors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import actions.JoinActionRespond;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Dispatcher;
import akka.dispatch.ExecutorServiceFactoryProvider;
import akka.dispatch.MessageDispatcherConfigurator;


public class Main {
	private static Main singleton;
	public static Main getSingleton() {
		return singleton;
	}
	
	public final ActorSystem system;
	
	public ActorSystem getSystem() {
		return system;
	}
	
	public Main() {
		this.singleton=this;
		system = ActorSystem.create("MySystem");
		system.actorOf(Wallet.props(null,"Main"),"main");
		
	}
	public void start(){
		//43 tells 42 join
	}
	static int walletCounter = 0;
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
		JFrame frame = new JFrame("Manager");
		frame.setLayout(new GridLayout(3,2));
		frame.add(new JLabel("Connect to:"));
		final JTextField input = new JTextField("akka://MySystem/user/main");
		frame.add(input);
		frame.add(new JLabel("Name:"));
		final JTextField name = new JTextField(""+walletCounter);
		frame.add(name);
		JButton button = new JButton("connect");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*MessageDispatcherConfigurator mc = null;
				String id = input.getText()+"-dispatched";
				int hroughput = 1;
				Duration d = Duration.ofSeconds(2);
				
				
				Dispatcher d = new Dispatcher(mc,id, 1, d, Executors.newSingleThreadExecutor(),1000);*/
				getSingleton().getSystem().actorOf(Wallet.props(input.getText(),name.getText()),name.getText());
			}
		});
		
		frame.add(button);
		frame.setSize(300, 300);
		frame.setVisible(true);
		
	}
}
