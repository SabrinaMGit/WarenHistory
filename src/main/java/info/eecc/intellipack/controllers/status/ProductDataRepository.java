package info.eecc.intellipack.controllers.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 21.06.2021
 */

@Repository
public interface ProductDataRepository extends JpaRepository<ProductData, Integer> {

    //List<ProductData> listAllBySgtin(String sgtin);
    List<ProductData> findBySgtin(String sgtin);
}
