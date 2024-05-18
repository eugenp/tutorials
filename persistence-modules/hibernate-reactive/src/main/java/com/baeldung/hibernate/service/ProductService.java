package main.java.com.baeldung.hibernate.service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }
}