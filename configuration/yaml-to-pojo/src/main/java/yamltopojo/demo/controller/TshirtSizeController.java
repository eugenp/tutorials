package yamltopojo.demo.controller;

import org.springframework.web.bind.annotation.*;
import yamltopojo.demo.service.SizeConverterService;

@RestController
@RequestMapping(value = "/")
public class TshirtSizeController {

    private SizeConverterService service;

    public TshirtSizeController(SizeConverterService service) {
        this.service = service;
    }

    @RequestMapping(value ="convertSize", method = RequestMethod.GET)
    public int convertSize(@RequestParam(value = "label") final String label,
                                             @RequestParam(value = "countryCode", required = false) final String countryCode) {
        return service.convertSize(label, countryCode);
    }

}
