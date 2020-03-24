package bael.security

@Grab("spring-boot-starter-security")

@RestController
class SampleController {

    @RequestMapping("/")
    public def example() {
        [message: "Hello World!"]
    }
}
