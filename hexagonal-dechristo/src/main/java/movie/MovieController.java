package movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieOracleRepositoryAdapter movieOracleRepositoryAdapter;

    @GetMapping("/{title}")
    public ResponseEntity<Movie> getProductById(@PathVariable String title) {
        return new ResponseEntity<Movie>(movieOracleRepositoryAdapter.getByTitle(title), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> add(@RequestBody Movie movie) {
       return new ResponseEntity<Movie>(movieOracleRepositoryAdapter.add(movie), HttpStatus.OK);
    }
}
