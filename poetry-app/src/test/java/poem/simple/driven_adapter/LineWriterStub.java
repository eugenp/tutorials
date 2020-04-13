package poem.simple.driven_adapter;


import poem.boundary.driven_port.WritePoems;

public class LineWriterStub implements WritePoems {
	private String[] lines;

	@Override
	public void writeLines(String[] lines) {
		this.lines = lines;
	}

	public String[] getLines() {
		return lines;
	}
}
