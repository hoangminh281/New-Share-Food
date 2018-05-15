package com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters;

import com.ptit.tranhoangminh.newsharefood.models.CommentModel;

public interface saveCommentImp {
    void saveComment(String key_store, CommentModel commentModel,String link_image);
}
