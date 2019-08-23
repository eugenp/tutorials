package baeldung.ledger.framework;


public interface Command {

	public void execute();

	public interface Builder {

		public Command build( final String[] details );
	}
}
