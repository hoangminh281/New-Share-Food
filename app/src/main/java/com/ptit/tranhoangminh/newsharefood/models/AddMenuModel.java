package com.ptit.tranhoangminh.newsharefood.models;

public class AddMenuModel {
    String cstegory_id;
    MenuStoreModel menuStoreModel;

    public AddMenuModel() {
    }

    public AddMenuModel(String cstegory_id) {
        this.cstegory_id = cstegory_id;

    }

    public String getCstegory_id() {
        return cstegory_id;
    }

    public void setCstegory_id(String cstegory_id) {
        this.cstegory_id = cstegory_id;
    }



    public MenuStoreModel getMenuStoreModel() {
        return menuStoreModel;
    }

    public void setMenuStoreModel(MenuStoreModel menuStoreModel) {
        this.menuStoreModel = menuStoreModel;
    }
}
