package parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by liekkas on 15/7/7.
 */
public class GeoJsonParser {

    public static void main(String[] args) throws IOException {
        File dir = new File(GeoJsonParser.class.getResource("/citygeo").getFile());
//        File dir = new File(GeoJsonParser.class.getResource("/geojson").getFile());
        Collection<File> geoJsons = FileUtils.listFiles(dir, new String[]{ "json", "txt" }, true);

        int i = 0;
        for (File geoJson : geoJsons) {

            JSONObject geo = JSON.parseObject(FileUtils.readFileToString(geoJson));

//            System.out.println("========="+i+ " >>>" + geoJson.getName()+"=========");



            JSONArray arr = geo.getJSONArray("features");
            if (arr != null) {
                for (Object item : arr) {
                    i++;

                    JSONObject jitem = (JSONObject) item;
                    String areaId = jitem.getString("id");
                    JSONObject props = jitem.getJSONObject("properties");
                    String areaName = props.getString("name");

                    JSONObject geometry = jitem.getJSONObject("geometry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");

                    double totalLong = 0d;
                    double totalLat = 0d;
                    int counter = 0;
                    for(Object coor:coordinates){
                        JSONArray coors = (JSONArray) coor;

                        for(Object coor2:coors){
                            JSONArray coor2s = (JSONArray) coor2;
                            for(Object coor3:coor2s){
                                JSONArray coor3s = (JSONArray) coor3;
                                BigDecimal longitude = coor3s.getBigDecimal(0);
                                BigDecimal latitude = coor3s.getBigDecimal(1);

                                totalLong += longitude.doubleValue();
                                totalLat += latitude.doubleValue();
                                counter ++;
                            }
                        }
                    }

//                    System.out.println(areaId+" " + areaName + " => [" + longitude + "," + latitude + "]");
                    System.out.println(i+" " + areaName + " " + totalLong/counter + " " + totalLat/counter + "");
                }
            }

        }
    }
}
