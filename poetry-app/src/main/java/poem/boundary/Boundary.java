package poem.boundary;

import org.requirementsascode.Model;
import org.requirementsascode.ModelRunner;

import poem.boundary.driven_port.GetPoetry;
import poem.boundary.driven_port.WritePoems;
import poem.boundary.driver_port.IReactToCommands;
import poem.boundary.internal.command_handler.DisplayRandomPoem;

/**
 * The boundary class is the only point of communication with left-side driver
 * adapters. It accepts commands, and calls the appropriate command handler.
 * 
 * On creation, this class wires up the dependencies between command types and
 * command handlers, by injecting the command handlers into a use case model.
 * 
 * After creation, this class sends each command it receives to the runner of
 * the use case model. The model runner then dispatches the command to the
 * appropriate command handler, which in turn calls the driven adapters.
 *
 *
 */
public class Boundary implements IReactToCommands {
	private Model model;

	public Boundary(GetPoetry poemObtainer, WritePoems poemWriter) {
		model = buildModel(poemObtainer, poemWriter);
	}

	private Model buildModel(GetPoetry poemObtainer, WritePoems poemWriter) {
		// Create the command handler(s)
		DisplayRandomPoem displayRandomPoem = new DisplayRandomPoem(poemObtainer, poemWriter);

		// Inject command handler(s) into use case model, to tie them to command
		// types.
		Model model = UseCaseModel.build(displayRandomPoem);
		return model;
	}

	@Override
	public void reactTo(Object commandObject) {
		new ModelRunner().run(model).reactTo(commandObject);
	}
}