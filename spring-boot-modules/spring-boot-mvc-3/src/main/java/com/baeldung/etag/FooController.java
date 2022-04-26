package com.baeldung.etag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/foos")
public class FooController {

	@Autowired
	private FooDao fooDao;

	@GetMapping(value = "/{id}")
	public Foo findById(@PathVariable("id") final Long id, final HttpServletResponse response) {
		return fooDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	// Note: the global filter overrides the ETag value we set here. We can still
	// analyze its behaviour in the Integration Test.
	@GetMapping(value = "/{id}/custom-etag")
	public ResponseEntity<Foo> findByIdWithCustomEtag(@PathVariable("id") final Long id,
			final HttpServletResponse response) {
		final Foo foo = fooDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ResponseEntity.ok().eTag(Long.toString(foo.getVersion())).body(foo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Foo create(@RequestBody final Foo resource, final HttpServletResponse response) {
		return fooDao.save(resource);
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id") final Long id, @RequestBody final Foo resource) {
		fooDao.save(resource);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") final Long id) {
		fooDao.deleteById(id);
	}

}