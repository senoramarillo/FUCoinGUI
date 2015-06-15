package fucoin;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListModel;

import actions.FindWalletAction;
import actions.FindWalletResponseAction;
import actions.InvalidateAction;
import actions.InvokeExitAction;
import actions.InvokeFindWalletAction;
import actions.InvokePerformTransactionAction;
import actions.PerformTransactionAction;
import actions.StoreOrUpdateAction;
import actions.JoinAction;
import actions.JoinActionRespond;
import actions.InvokeStoreOrUpdateAction;
import actions.TransactionPerformedAction;
import actions.WaitForAnswerAction;
import actions.WaitForPerformTransactionAction;
import gui.IWalletGuiControl;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.Props;
import akka.actor.UntypedActor;


public class Wallet extends UntypedActor implements IWallet{

	private ActorRef preknownNeighbour;
	private String  address ;
	private int  moneyAmount ;
	private Vector<WalletPointer>	allKnownNeighbors = new Vector<WalletPointer>();
	private Vector<Wallet>			synchronizedNeighbors = new Vector<Wallet>();
	

	public Wallet(String preknownNeighbour) {
		
		if(preknownNeighbour!=null){
			this.preknownNeighbour=stringToActor(preknownNeighbour);
		}
	}

	@Override
	public Vector<WalletPointer> join() {
		Vector<WalletPointer>	allKnownNeighbors = new Vector<WalletPointer>();
		for(WalletPointer neighbors : this.allKnownNeighbors){
			allKnownNeighbors.add(neighbors);
		}
		return allKnownNeighbors;
	}

	@Override
	public void storeOrUpdate(Wallet w) {
		if(!synchronizedNeighbors.contains(w)){
			synchronizedNeighbors.add(w);
		};
	}

	@Override
	public void invalidateWallet(Wallet w) {
		synchronizedNeighbors.remove(w);
	}

	@Override
	public void receiveTransaction(int moneyAmount) {
		addMoneyAmount(moneyAmount);
	}
	private Vector<WalletPointer> answers = new Vector<WalletPointer>();
	@Override
	public Vector<WalletPointer> searchWallet(String adress) {
		for(WalletPointer neighbor : allKnownNeighbors){
			if(!neighbor.address.equals(adress)){
				ActorRef actor = stringToActor(neighbor.address);
				actor.tell(new FindWalletAction(adress), getSelf());
				
			}
		}
		//unschoener workaround
		getSelf().tell(new WaitForAnswerAction(), getSelf());
		return answers;
	}
	
	private boolean isAlive = false;
	private IWalletGuiControl gui;
	void  performTransaction ( WalletPointer w,  int  amount ){
		
		ActorRef destActor = stringToActor(w.address);
		destActor.tell(new PerformTransactionAction(w,amount), getSelf());
		getSelf().tell(new WaitForPerformTransactionAction(w,amount), getSelf());
		//.tell(new Perform<transaction>, sender);
	}
	
	@Override
	public void onReceive(Object action) throws Exception {
		log(""+action);
		String senderName = actorToString(getSender());
		String selfName = actorToString(getSelf());		
		
		if(action instanceof JoinAction){
			JoinAction joinAction = (JoinAction) action;
			JoinActionRespond joinactionrespond = new JoinActionRespond();
			joinactionrespond.setKnownNeighbors(join());
			addKnownaddress(joinAction.getNewMember());
			
			
			getSender().tell(joinactionrespond, getSelf());
		}else if(action instanceof JoinActionRespond){
			JoinActionRespond joinaction = (JoinActionRespond) action;
			
			//allKnownNeighbors.addAll(joinaction.getAllKnownNeighbors());
			for(WalletPointer neighbor:joinaction.getAllKnownNeighbors()){
				addKnownaddress(neighbor);
			}
			log(actorToString(getSelf())+"ah there are new neighbours"+joinaction.getAllKnownNeighbors());
			log("now i know these "+allKnownNeighbors);
			getSelf().tell(new InvokeFindWalletAction(), getSelf());
		}else if(action instanceof StoreOrUpdateAction){
			StoreOrUpdateAction storeOrUpdateAction = (StoreOrUpdateAction) action;
			storeOrUpdate(storeOrUpdateAction.getWallet());
		}else if(action instanceof InvokeStoreOrUpdateAction){
			StoreOrUpdateAction storeOrUpdateAction = new StoreOrUpdateAction();
			storeOrUpdateAction.setWallet(this);
			for(WalletPointer neighbor : allKnownNeighbors){
				stringToActor(neighbor.address).tell(storeOrUpdateAction, getSelf());
			}
		}else if(action instanceof InvokeExitAction){
			getContext().stop(getSelf());
		}else if(action instanceof InvokeFindWalletAction){
			InvokeFindWalletAction invokeFindWalletAction = (InvokeFindWalletAction) action;
			
			Vector<WalletPointer> wallets = searchWallet(selfName);
			
		}else if(action instanceof FindWalletAction){
			FindWalletAction findWalletAction = (FindWalletAction) action;
			for(Wallet s : synchronizedNeighbors){
				log(findWalletAction.getSearchedName());
				log(s.address);
				if(s.address.equals(findWalletAction.getSearchedName())){
					log(""+s.moneyAmount);
					getSender().tell(new FindWalletResponseAction(selfName,s.moneyAmount), getSelf());
				}
			}
			
		}else if(action instanceof FindWalletResponseAction){
			FindWalletResponseAction findWalletResponseAction = (FindWalletResponseAction) action;
			answers.add(new WalletPointer(findWalletResponseAction.getFoundneighbour()));
			
			moneyAmount=findWalletResponseAction.getMoneyAmount();
			gui.setAmount(moneyAmount);
		}else if(action instanceof WaitForAnswerAction){
			log(""+answers);
			for(WalletPointer answer : answers){
				addKnownaddress(answer);
				stringToActor(answer.address).tell(new InvalidateAction(actorToString(getSelf())),getSelf());
			}
		}else if(action instanceof InvalidateAction){
			InvalidateAction invalidateAction = (InvalidateAction) action;
			
			Wallet delWallet = null;
			
			for(Wallet w : synchronizedNeighbors){
				if(w.address.equals(invalidateAction.getInvalidWallet())){
					delWallet=w;
				}
			}
			log(""+synchronizedNeighbors);
			invalidateWallet(delWallet);
			log(""+synchronizedNeighbors);
		}else if(action instanceof InvokePerformTransactionAction){
			InvokePerformTransactionAction invokePerformTransactionAction = (InvokePerformTransactionAction) action;
			performTransaction(invokePerformTransactionAction.getWalletPointer(),invokePerformTransactionAction.getAmount());
			addMoneyAmount(-invokePerformTransactionAction.getAmount());
		}else if(action instanceof PerformTransactionAction){
			PerformTransactionAction performTransactionAction = (PerformTransactionAction) action;
			if(performTransactionAction.getWalletPointer().address.equals(actorToString(getSelf()))){
				getSender().tell(new TransactionPerformedAction(), getSelf());
				receiveTransaction(performTransactionAction.getAmount());
			}else{
				for(Wallet syncwallet : synchronizedNeighbors){
					if(syncwallet.address.equals(performTransactionAction.getWalletPointer().address)){
						syncwallet.addMoneyAmount(performTransactionAction.getAmount());
					}
				}
			}
		}else if(action instanceof WaitForPerformTransactionAction){
			WaitForPerformTransactionAction waitForPerformTransactionAction = (WaitForPerformTransactionAction) action;
			if(!isAlive){
				for(Wallet neighbor : synchronizedNeighbors){
					if(neighbor.address.equals(waitForPerformTransactionAction.getWalletPointer().address)){
						neighbor.addMoneyAmount(waitForPerformTransactionAction.getAmount());
						log("have to update "+neighbor.address+"amount because he is offline");
					}
					
				}
				
			}
		}else if(action instanceof TransactionPerformedAction){
			isAlive=true;
		}
	}

	private void addKnownaddress(WalletPointer senderPointer) {
		if(!allKnownNeighbors.contains(senderPointer)
				&&getSender().compareTo(getSelf())!=0){
			
		
			this.allKnownNeighbors.add(senderPointer);
			gui.addKnownAddress(senderPointer.address);
		}
	}
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		log("prestart"+preknownNeighbour);
		this.address=actorToString(getSelf());
		gui.setAddress(address);
		addMoneyAmount(100);
		if(preknownNeighbour!=null){
			String p = actorToString(preknownNeighbour);
			addKnownaddress(new WalletPointer(p));
			preknownNeighbour.tell(new JoinAction(new WalletPointer(address)), getSelf());
		}
	}
	
	private void log(String string) {
		String logMsg = actorToString(getSender()).replace("akka://MySystem/user/", "")+" said "+ string+" to me("+actorToString(getSelf()).replace("akka://MySystem/user/", "")+")";
		System.out.println(logMsg);
		gui.addLogMsg(string);
		
	}

	public static Props props(String preknownNeighbour, String walletCounter) {
		return Props.create(new WalletCreator(preknownNeighbour,walletCounter));
	}
	
	public static String actorToString(ActorRef actor){
		return actor.path().toStringWithAddress(actor.path().address());
	}
	
	public static ActorRef stringToActor(String path){
		return Main.getSingleton().getSystem().actorFor(path);
	}
	
	public void addMoneyAmount(int moneyAmount) {
		this.moneyAmount += moneyAmount;
		this.gui.setAmount(this.moneyAmount);
	}
	@Override
	public String toString() {
		return address+"'s wallet";
	}

	public void invokePerformTransaction(WalletPointer walletPointer, int i) {
		getSelf().tell(new InvokePerformTransactionAction(walletPointer, i),getSelf());
	}

	@Override
	public void send(String address, int amount) {
		invokePerformTransaction(new WalletPointer(address), amount);
	}
	
	public void store() {
		getSelf().tell(new InvokeStoreOrUpdateAction(), getSelf());
	}

	public void leave() {
		store();
		getSelf().tell(new InvokeExitAction(), getSelf());
	}

	public void setGui(IWalletGuiControl gui) {
		this.gui=gui;
	}

}
