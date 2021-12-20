package com.billogram.model.cd.catalogue;

import java.util.HashSet;

public class Catalogue {
    public HashSet<CDXmlModel> getCd() {
        return cd;
    }

    public void setCd(HashSet<CDXmlModel> cd) {
        this.cd = cd;
    }

    private HashSet<CDXmlModel> cd;
}
