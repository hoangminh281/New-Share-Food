package com.ptit.tranhoangminh.newsharefood.models;

public class UtilitiesModel {
    String hinhtienich;
    String tentienich;
    String matienich;

    public UtilitiesModel(String hinhtienich, String tentienich) {
        this.hinhtienich = hinhtienich;
        this.tentienich = tentienich;
    }

    public UtilitiesModel() {
    }

    public String getMatienich() {
        return matienich;
    }

    public void setMatienich(String matienich) {
        this.matienich = matienich;
    }

    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }

    public String getTentienich() {
        return tentienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }
}
