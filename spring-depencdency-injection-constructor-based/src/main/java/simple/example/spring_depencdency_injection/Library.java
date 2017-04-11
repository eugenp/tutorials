package simple.example.spring_depencdency_injection;

public class Library {
	private Book book;
	
	public Library(Book book){
		this.book = book;
	}
	public void printInjectedBook(){
		book.printBookName();
	}
}
