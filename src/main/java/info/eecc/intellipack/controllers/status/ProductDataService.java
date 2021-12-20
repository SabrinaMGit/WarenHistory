package info.eecc.intellipack.controllers.status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 21.06.2021
 */

@Service
@Transactional
public class ProductDataService {

    private final ProductDataRepository productDataRepository;

    public ProductDataService(ProductDataRepository productDataRepository) {
        this.productDataRepository = productDataRepository;
    }

    public List<ProductData> listAll(){
        return productDataRepository.findAll();
    }

    public void setData(ProductData productData){
        productDataRepository.save(productData);
    }

    public List<ProductData> listProductDataBySgtin(String sgtin) {
        return this.productDataRepository.findBySgtin(sgtin);
    }

    public void insertProductData(ProductData productData){
        this.productDataRepository.save(productData);
    }

    public boolean useRegex(String input) {
        // Compile regular expression
        //Pattern pattern = Pattern.compile("^[a-zA-Z]+://id\\.intelli-pack\\.[a-zA-Z]+/01/[0-9]{14}+/21/[0-9]{0,30}+$" ,Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("^https://id\\.intelli-pack\\.de/01/[0-9]+/21/[a-zA-Z0-9]{1,30}$" ,Pattern.CASE_INSENSITIVE);
        // Match regex against input
        Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }
}
