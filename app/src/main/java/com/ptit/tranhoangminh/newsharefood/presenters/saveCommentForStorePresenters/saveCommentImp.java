package com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters;

import android.widget.ImageView;

import com.ptit.tranhoangminh.newsharefood.models.CommentModel;

public interface saveCommentImp {
    void saveComment(String key_store, CommentModel commentModel, ImageView link_image);
}
