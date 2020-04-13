package poem.simple.driven_adapter;


import poem.boundary.driven_port.GetPoetry;

public class PoemObtainerStub implements GetPoetry {
	public static final String POET_NAME = "John";

	@Override
	public String[] getPoems(String language) {
		return new String[] { POET_NAME };
	}
}
