package actions;

public class FindWalletAction {

	private String searchedName;

	public FindWalletAction(String searchedName) {
		this.searchedName=searchedName;
	}
	
	public String getSearchedName() {
		return searchedName;
	}

}
