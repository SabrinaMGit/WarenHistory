package info.eecc.intellipack.controllers.gln;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GlnDataService {

    private final GlnDataRepo repo;

    public GlnDataService(GlnDataRepo repo) {
        this.repo = repo;
    }

    public List<GlnLocation> listAllByGln(List<String> glns){
        List<GlnLocation> glnLocations = new ArrayList<>();
        for (String s : glns) {
            glnLocations.add(repo.findByGlnNumber(s));
        }
        return glnLocations;
    }
}
