package com.ptit.tranhoangminh.newsharefood.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserPolyline {
    public static List<LatLng> getListToaDo(String json)
    {
        List<LatLng>list=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray routes=jsonObject.getJSONArray("routes");
            for(int i=0;i<routes.length();i++)
            {
                JSONObject overview_polyline=routes.getJSONObject(i);
                String points=overview_polyline.getString("points");
                list= PolyUtil.decode(points);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
