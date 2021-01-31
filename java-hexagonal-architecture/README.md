<H1>Hexagonal Architecture Example in Java</H1>
<p>
This is a small Java application based on Hexagonal Architecture. It provides a list of movie names based upon a specified genre. The ports and adapters of the application are provided in ports and adapters directories.
</p>
<p>
There are 3 ports :</br></br>
  <i>InputPort.java</i> - Provides a port for handling user inputs. Can be implemented to handle requests through GUI, HTTP etc.</br>
  <i>GetMovieNamesPort.java</i> - Provides a port for fetching movie names from DB. Can be implemented to fetch from SQL, PostgreSQL, MySQL or in memory database etc.</br>
  <i>PrintOutputPort.java</i> - Provides a port for printing the output. Can be implemented to print output to console or file.
</p>
<p>
There are 3 adapters as well :</br></br>
  <i>UserInputAdapter.java</i> - Implements InputPort.</br>
  <i>InMemoryMoviesAdapter.java</i> - Implements GetMovieNamesPort.</br>
  <i>FileOutputAdapter</i> - Implements PrintOutputPort.</br>
 </p>
 The <i>Application.java</i> is the main driver file.
