package poem.boundary.internal.command_handler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import poem.boundary.driven_port.GetPoetry;

import poem.boundary.driven_port.WritePoems;
import poem.boundary.internal.domain.Poem;
import poem.boundary.internal.domain.RandomPoemPicker;
import poem.command.AskForPoem;

/**
 * The command handler for displaying a random poem.
 *
 */
public class DisplayRandomPoem implements Consumer<AskForPoem> {
	private GetPoetry poemGetter;
	private RandomPoemPicker randomPoemPicker;
	private WritePoems poemWriter;

	public DisplayRandomPoem(GetPoetry poemGetter, WritePoems poenWriter) {
		this.poemGetter = poemGetter;
		this.randomPoemPicker = new RandomPoemPicker();
		this.poemWriter = poenWriter;
	}

	@Override
	public void accept(AskForPoem askForPoem) {
		List<Poem> poems = obtainPoems(askForPoem);
		Optional<Poem> poem = pickRandomPoem(poems);
		writeLines(poem);		
	}

	private List<Poem> obtainPoems(AskForPoem askForPoem) {
		String authorName = askForPoem.getAuthor();
		String[] poems = poemGetter.getPoems(authorName);
		List<Poem> poemDomainObjects = 
			Arrays.stream(poems)
				.map(Poem::new)
				.collect(Collectors.toList());
		return poemDomainObjects;
	}
	
	private Optional<Poem> pickRandomPoem(List<Poem> poemList) {
		Optional<Poem> randomPoem = randomPoemPicker.pickPoem(poemList);
		return randomPoem;
	}
	
	private void writeLines(Optional<Poem> poem) {
		poem.ifPresent(p -> poemWriter.writeLines(p.getVerses()));
	}
}
