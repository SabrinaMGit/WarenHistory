package info.eecc.intellipack.controllers.appdaten;

import info.eecc.intellipack.controllers.status.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 12.08.2021
 */

@Repository
public interface ProductDataStringsRepository extends JpaRepository<ProductDataStrings, String> {
    List<ProductDataStrings> findAllByDigitallink(String digitallink);
}
