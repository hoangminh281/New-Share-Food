package com.ptit.tranhoangminh.newsharefood.presenters.polylinePresenters;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.ptit.tranhoangminh.newsharefood.models.DowloadJsonPolyline;
import com.ptit.tranhoangminh.newsharefood.models.ParserPolyline;
import com.ptit.tranhoangminh.newsharefood.views.DirectionMap.DirectionImp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DirectionMapPresenterLogic implements DirectionMapImp {
    ParserPolyline parserPolyline;
    DirectionImp directionImp;
    public DirectionMapPresenterLogic(DirectionImp directionImp)
    {
        this.directionImp=directionImp;

    }


    @Override
    public void getRoadToStore(GoogleMap googleMap, String link) {
        parserPolyline=new ParserPolyline();
        DowloadJsonPolyline dowloadJsonPolyline=new DowloadJsonPolyline();
        dowloadJsonPolyline.execute(link);
        try {
            String data=dowloadJsonPolyline.get();
            //Log.d("test_map",data);
            List<LatLng> list=parserPolyline.getListToaDo(data);
            directionImp.GetListToaDo(list);
          /*  for(LatLng value:list)
            {
                Log.d("test_map",value.latitude+"");
            }*/


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
