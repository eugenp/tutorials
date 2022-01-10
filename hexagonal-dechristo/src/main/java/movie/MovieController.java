package movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    MovieServiceAdapter movieServiceAdapter;

    @GetMapping("/{title}")
    public ResponseEntity<Movie> getProductById(@PathVariable String title) {
        return new ResponseEntity<Movie>(movieServiceAdapter.getByTitle(title), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> add(@RequestBody Movie movie) {
       return new ResponseEntity<Movie>(movieServiceAdapter.add(movie), HttpStatus.OK);
    }
}
