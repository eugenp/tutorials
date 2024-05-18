package test.java.com.baeldung.hibernate.service;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @Test
    void testFindById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        Mockito.when(productRepository.findById(1L))
            .thenReturn(Mono.just(product));
        Mono<Product> productMono = productService.findById(1L);
        StepVerifier.create(productMono)
            .expectNextMatches(p -> p.getId()
                .equals(1L) && p.getName()
                .equals("Test Product"))
            .verifyComplete();
    }
}