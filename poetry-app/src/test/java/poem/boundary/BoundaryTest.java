package poem.boundary;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import poem.boundary.Boundary;
import poem.command.AskForPoem;
import poem.simple.driven_adapter.LineWriterStub;
import poem.simple.driven_adapter.PoemObtainerStub;

public class BoundaryTest {

	
	private LineWriterStub lineWriter;
	private Boundary boundary;

	@Before
	public void setup() {
		PoemObtainerStub poemObtainerStub = new PoemObtainerStub();
		lineWriter = new LineWriterStub();
		boundary = new Boundary(poemObtainerStub, lineWriter);
	}

	@Test
	public void englishPoem() throws Exception {
		boundary.reactTo(new AskForPoem("John"));
	}




	private void assertPoemIs(String expectedPoemVerse) {
		String[] actualPoemVerses = lineWriter.getLines();
		assertEquals(expectedPoemVerse, actualPoemVerses[0]);
	}

}
