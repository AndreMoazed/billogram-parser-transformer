package com.billogram.transformer;

import com.billogram.model.cd.catalogue.CDOutputJsonModel;
import com.billogram.model.cd.catalogue.CDXmlModel;
import com.billogram.service.VatService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CDXmlToJsonModelTransformer {
    VatService vatService;

    public CDXmlToJsonModelTransformer() {
        this.vatService = new VatService();
    }
    public List<CDOutputJsonModel> transform(List<CDXmlModel> cdXmlList) {
        List<CDOutputJsonModel> transformedCDList = new ArrayList<>();
        BigDecimal vatAmount;
        BigDecimal priceBeforeVat;
        for (CDXmlModel cdXml : cdXmlList) {
            vatAmount = vatService.getVatAmount(cdXml.getPrice());
            priceBeforeVat = vatService.getPriceBeforeTax(cdXml.getPrice());

            String[] firstLastNames = cdXml.getArtist().split(" ", 2);

            // Build new object and add it to the transformed list
            CDOutputJsonModel jsonModel = new CDOutputJsonModel();
            jsonModel.setTitle(cdXml.getTitle());
            jsonModel.setPublished(cdXml.getYear());
            if (firstLastNames.length>1) {
                jsonModel.setLastname(firstLastNames[1]);
                jsonModel.setFirstname(firstLastNames[0]);
            } else {
                jsonModel.setLastname(cdXml.getArtist());
                jsonModel.setFirstname(" ");
            }
            jsonModel.setOrigin(cdXml.getCountry());
            jsonModel.setPriceNoVat(priceBeforeVat);
            jsonModel.setVat(vatAmount);
            jsonModel.setTotal(cdXml.getPrice());

            transformedCDList.add(jsonModel);
        }

        return transformedCDList;
    }
}
