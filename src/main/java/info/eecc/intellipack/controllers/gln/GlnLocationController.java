package info.eecc.intellipack.controllers.gln;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class GlnLocationController {

    private final GlnDataService service;

    public GlnLocationController(GlnDataService service) {
        this.service = service;
    }

    @GetMapping("/access/gln")
    public List<GlnLocation> getGlnLocation(@RequestParam ("glns") List<String> glns) {
        return service.listAllByGln(glns);
    }
}
