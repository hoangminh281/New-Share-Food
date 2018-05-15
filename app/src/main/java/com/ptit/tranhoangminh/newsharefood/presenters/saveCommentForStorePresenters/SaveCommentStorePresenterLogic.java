package com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters;

import com.ptit.tranhoangminh.newsharefood.models.CommentModel;
import com.ptit.tranhoangminh.newsharefood.views.AddComment.AddCommentImp;

public class SaveCommentStorePresenterLogic implements saveCommentImp {
    AddCommentImp addCommentImp;
    CommentModel commentModel;

    public SaveCommentStorePresenterLogic(AddCommentImp addCommentImp) {
        this.addCommentImp = addCommentImp;
        commentModel = new CommentModel();
    }

    @Override
    public void saveComment(String key_store, CommentModel commentModel, String link_image) {
        GetNotificationInterface getNotificationInterface = new GetNotificationInterface() {
            @Override
            public void getNotification(String content) {
                addCommentImp.getresult(content);
            }
        };
        commentModel.AddComment(getNotificationInterface, key_store, commentModel, link_image);

        
    }
}
