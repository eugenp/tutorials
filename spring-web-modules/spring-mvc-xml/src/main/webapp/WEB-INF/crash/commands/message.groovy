import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.crsh.cli.Option;

class message {
	
    @Usage("show my own message")
    @Command
    Object main(@Usage("custom message") @Option(names=["m","message"]) String message) {
    if (message == null)
        message = "No message given...";
    return message;
    }
}