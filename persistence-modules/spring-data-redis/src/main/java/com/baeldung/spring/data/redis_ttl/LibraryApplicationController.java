package com.baeldung.spring.data.redis_ttl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.data.redis_ttl.entity.Book;
import com.baeldung.spring.data.redis_ttl.entity.Gatekeeper;
import com.baeldung.spring.data.redis_ttl.entity.Librarian;
import com.baeldung.spring.data.redis_ttl.entity.Subscriber;
import com.baeldung.spring.data.redis_ttl.repository.BookRepository;
import com.baeldung.spring.data.redis_ttl.repository.GatekeeperRepository;
import com.baeldung.spring.data.redis_ttl.repository.LibrarianRepository;
import com.baeldung.spring.data.redis_ttl.repository.SubscriberRepository;

@RestController
@RequestMapping("/v1/")
class LibraryApplicationController{
    
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Cacheable(value = "subscribers")
    @GetMapping("subscribers/{id}")
    public Optional<Subscriber> getSubscriber(@PathVariable("id") Long id) throws InterruptedException {
        return subscriberRepository.findById(id);
    }
    
    @PostMapping("subscribers")
    public Subscriber addSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }
    
    @Autowired
    private GatekeeperRepository gatekeeperRepository;

    @Cacheable(value = "gatekeeper")
    @GetMapping("gatekeeper/{id}")
    public Optional<Gatekeeper> getGatekeeper(@PathVariable("id") Long id) throws InterruptedException {
        return gatekeeperRepository.findById(id);
    }

    @PostMapping("gatekeeper")
    public Gatekeeper addGatekeeper(@RequestBody Gatekeeper gatekeeper) {
        return gatekeeperRepository.save(gatekeeper);
    }
    
    @Autowired
    private BookRepository bookRepository;
    
    @GetMapping("/books/{title}")
    public Book getBook(@PathVariable("title") String title) {
        return bookRepository.get(title);
    }
    
    @PostMapping("books/")
    public void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
    
    @Autowired
    private LibrarianRepository librarianRepository;

    @GetMapping("librarians/{id}")
    public Optional<Librarian> getLibrarian(@PathVariable("id") Long id) {
        return librarianRepository.findById(id);
    }
    
    @PostMapping("librarians")
    public Librarian addLibrarian(@RequestBody Librarian librarian) {
        return librarianRepository.save(librarian);
    }
}