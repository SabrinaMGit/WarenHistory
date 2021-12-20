package info.eecc.intellipack.controller;

import info.eecc.intellipack.controllers.status.ProductDataService;
import info.eecc.intellipack.controllers.status.ProductData;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductDataRepositoryTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ProductDataService productDataService;

    private final String sgtin = "01/04014116000003/21/0221T4YE";
    private final String csv = "Datenquelle;SGTIN;\"Produkt- \n" +
            "bezeichnung\";\"Produktions- \n" +
            "charge\";\"Verpackungs- \n" +
            "datum\";MHD;Aufladewert TTI;\"Ziel- \n" +
            "temperatur\";P1;P2;P3;P4;P5;P6;P7;P8;P9;P10;\"Zeitstempel \n" +
            "(jjjjmmdd_hhmmss)\";Laenge;Breite;Rot;Gruen;Blau;\"erw. \n" +
            "Temperatur\";\"Resthalt-\n" +
            "barkeit (d)\"\n" +
            "Wolf Qualitätskontrolle;01/04014116000003/21/0221T4YE;Original Thueringer Rostbratwurst;4711;2021-05-16;2021-06-30;58;7;0;0;0;0;0;0;0;0;0;0;20210301_204823;6.906;50.834;24;30;20;10;20";

    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mvc = builder.build();
    }

    private ProductData getProductData(){
        ProductData productData = new ProductData();
        productData.setDatenquelle("Wolf Qualitätskontrolle");
        productData.setSgtin(this.sgtin);
        productData.setProduktbezeichnung("Original Thueringer Rostbratwurst");
        productData.setProduktionscharge(4711);
        productData.setVerpackungsdatum(LocalDate.parse("2021-05-16"));
        productData.setMhd(LocalDate.parse("2021-06-30"));
        productData.setAufladewertTTI(58);
        productData.setZieltemperatur(7);
        productData.setP1(0);
        productData.setP2(0);
        productData.setP3(0);
        productData.setP4(0);
        productData.setP5(0);
        productData.setP6(0);
        productData.setP7(0);
        productData.setP8(0);
        productData.setP9(0);
        productData.setP10(0);
        productData.setZeitstempel("20210301_204823");
        productData.setLaenge(6.906);
        productData.setBreite(50.834);
        productData.setRot(24);
        productData.setGrün(30);
        productData.setBlau(20);
        productData.setErwarteteTemperatur(10);
        productData.setResthaltbarkeit(20);
        return productData;
    }

    @Test
    void listAllBySgtin(){
        productDataService.setData(getProductData());
        List<ProductData> product = productDataService.listProductDataBySgtin(sgtin);
        Assertions.assertThat(product.size()).isGreaterThan(0);
        Assertions.assertThat(product.get(0).getSgtin()).isEqualTo(sgtin);
        Assertions.assertThat(product.get(0).getDatenquelle()).isEqualTo(getProductData().getDatenquelle());
        Assertions.assertThat(product.get(0).getProduktbezeichnung()).isEqualTo(getProductData().getProduktbezeichnung());
        Assertions.assertThat(product.get(0).getProduktionscharge()).isEqualTo(getProductData().getProduktionscharge());
        Assertions.assertThat(product.get(0).getVerpackungsdatum()).isEqualTo(getProductData().getVerpackungsdatum());
        Assertions.assertThat(product.get(0).getMhd()).isEqualTo(getProductData().getMhd());
        Assertions.assertThat(product.get(0).getAufladewertTTI()).isEqualTo(getProductData().getAufladewertTTI());
        Assertions.assertThat(product.get(0).getZieltemperatur()).isEqualTo(getProductData().getZieltemperatur());
        Assertions.assertThat(product.get(0).getP1()).isEqualTo(getProductData().getP1());
        Assertions.assertThat(product.get(0).getP2()).isEqualTo(getProductData().getP2());
        Assertions.assertThat(product.get(0).getP3()).isEqualTo(getProductData().getP3());
        Assertions.assertThat(product.get(0).getP4()).isEqualTo(getProductData().getP4());
        Assertions.assertThat(product.get(0).getP5()).isEqualTo(getProductData().getP5());
        Assertions.assertThat(product.get(0).getP6()).isEqualTo(getProductData().getP6());
        Assertions.assertThat(product.get(0).getP7()).isEqualTo(getProductData().getP7());
        Assertions.assertThat(product.get(0).getP8()).isEqualTo(getProductData().getP8());
        Assertions.assertThat(product.get(0).getP9()).isEqualTo(getProductData().getP9());
        Assertions.assertThat(product.get(0).getP10()).isEqualTo(getProductData().getP10());
        Assertions.assertThat(product.get(0).getZeitstempel()).isEqualTo(getProductData().getZeitstempel());
        Assertions.assertThat(BigDecimal.valueOf(6.906)).isEqualTo(BigDecimal.valueOf(6.906));
        Assertions.assertThat(product.get(0).getBreite()).isEqualTo(getProductData().getBreite());
        Assertions.assertThat(product.get(0).getRot()).isEqualTo(getProductData().getRot());
        Assertions.assertThat(product.get(0).getGrün()).isEqualTo(getProductData().getGrün());
        Assertions.assertThat(product.get(0).getBlau()).isEqualTo(getProductData().getBlau());
        Assertions.assertThat(product.get(0).getErwarteteTemperatur()).isEqualTo(getProductData().getErwarteteTemperatur());
        Assertions.assertThat(product.get(0).getResthaltbarkeit()).isEqualTo(getProductData().getResthaltbarkeit());
    }

    @Test
    void integrationTestAccessingController() throws Exception {
        /*
        RequestBuilder request = MockMvcRequestBuilders.get("/api/access")
                .param("digitalLink", "https://id.intelli-pack.de/01/04014116000003/21/0221T4YE");
        ResultActions result = mvc.perform(request);
        String resultString=content().toString();

        result.andDo(print());
        Assert.assertEquals(this.csv, result.toString());

        // tests:
        result.andExpect(status().isOk());

        mvc.perform(get("/api/access").param("digitalLink", "https://id.intelli-pack.de/"+sgtin)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

         */
    }
}
