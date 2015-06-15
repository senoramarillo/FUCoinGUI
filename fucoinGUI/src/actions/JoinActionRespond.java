package actions;

import java.util.Vector;

import fucoin.WalletPointer;

public class JoinActionRespond {
	private Vector<WalletPointer> allKnownNeighbors;

	@Override
	public String toString() {
		return "you joined successfully here are your new neighbours "+allKnownNeighbors;
	}

	public void setKnownNeighbors(Vector<WalletPointer> allKnownNeighbors) {
		this.allKnownNeighbors=allKnownNeighbors;
	}
	
	public Vector<WalletPointer> getAllKnownNeighbors() {
		return allKnownNeighbors;
	}
}
