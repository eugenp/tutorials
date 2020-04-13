package poem.boundary;

import java.util.function.Consumer;

import org.requirementsascode.Model;

import poem.command.AskForPoem;

/**
 * The use case model ties each type of command to its appropriate command
 * handler interface.
 * 
 * In business terms, this example model means:
 * 
 * The user asks for a poem. The system displays a random poem.
 *
 *
 */
class UseCaseModel {
	private static final Class<AskForPoem> asksForPoem = AskForPoem.class;

	public static Model build(Consumer<AskForPoem> displaysRandomPoem) {
		Model model = Model.builder()
			.user(asksForPoem).system(displaysRandomPoem)
		.build();

		return model;
	}
}
