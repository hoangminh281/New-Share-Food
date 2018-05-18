package com.ptit.tranhoangminh.newsharefood.presenters.registerPresenter;

import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.views.Register.RegisterViewImp;

/**
 * Created by Dell on 5/7/2018.
 */

public class RegisterPresenterLogic implements RegisterImp {
    RegisterViewImp registerViewImp;
    MemberModel memberModel;

    public RegisterPresenterLogic(RegisterViewImp registerViewImp) {
        this.registerViewImp = registerViewImp;
        memberModel = new MemberModel();
    }

    @Override
    public void AddInfoMember(MemberModel m, String uid) {
        memberModel.AddMember(m, uid);
    }
}
