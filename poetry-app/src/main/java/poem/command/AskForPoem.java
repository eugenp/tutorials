package poem.command;

/**
 * Command object representing the user request for a poem by author
 *
 *
 */
public class AskForPoem {
	private String author;

	public AskForPoem(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}
}
