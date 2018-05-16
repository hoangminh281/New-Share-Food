package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;

/**
 * Created by TramLuc on 5/16/2018.
 */

public interface commentListener {
    void likeAction(int position);
    void unlikeAction(int position);
    void delete(int position);
}

