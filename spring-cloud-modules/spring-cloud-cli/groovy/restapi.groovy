@RestController
@RequestMapping('/api')
class api {
    @GetMapping('/get')
    def get() { [message: 'Hello'] }
}