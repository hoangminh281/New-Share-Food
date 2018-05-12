package com.ptit.tranhoangminh.newsharefood.presenters.polylinePresenters;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.ptit.tranhoangminh.newsharefood.models.DowloadJsonPolyline;
import com.ptit.tranhoangminh.newsharefood.models.ParserPolyline;

import java.util.concurrent.ExecutionException;

public class DirectionMapPresenterLogic implements DirectionMapImp {
    ParserPolyline parserPolyline;

    public DirectionMapPresenterLogic()
    {
        parserPolyline=new ParserPolyline();
    }


    @Override
    public void getRoadToStore(GoogleMap googleMap, String link) {
        DowloadJsonPolyline dowloadJsonPolyline=new DowloadJsonPolyline();
        dowloadJsonPolyline.execute(link);
        try {
            String data=dowloadJsonPolyline.get();
            Log.d("test_map",data);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
