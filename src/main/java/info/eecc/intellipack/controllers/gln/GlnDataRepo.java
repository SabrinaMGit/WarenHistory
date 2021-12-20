package info.eecc.intellipack.controllers.gln;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlnDataRepo extends JpaRepository<GlnLocation, String> {
    GlnLocation findByGlnNumber(String gln);
}
