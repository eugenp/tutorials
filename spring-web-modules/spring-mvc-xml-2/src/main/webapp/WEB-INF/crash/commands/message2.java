import org.crsh.command.BaseCommand;
import org.crsh.cli.Usage;
import org.crsh.cli.Command;
import org.crsh.cli.Option;

public class message2 extends BaseCommand {
    @Usage("show my own message using java")
    @Command
    public Object main(@Usage("custom message") @Option(names = { "m", "message" }) String message) {
        if (message == null)
            message = "No message given...";
        return message;
    }
}