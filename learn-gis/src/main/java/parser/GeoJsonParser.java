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
        File dir = new File(GeoJsonParser.class.getResource("/geojson").getFile());
        Collection<File> geoJsons = FileUtils.listFiles(dir, new String[]{ "json", "txt" }, true);

        for (File geoJson : geoJsons) {

            JSONObject geo = JSON.parseObject(FileUtils.readFileToString(geoJson));

            System.out.println("========="+geoJson.getName()+"=========");

            JSONArray arr = geo.getJSONArray("features");
            if (arr != null) {
                for (Object item : arr) {
                    JSONObject jitem = (JSONObject) item;
                    String areaId = jitem.getString("id");
                    JSONObject props = jitem.getJSONObject("properties");
                    String areaName = props.getString("name");
                    JSONArray cp = props.getJSONArray("cp");
                    BigDecimal longitude = cp.getBigDecimal(0);
                    BigDecimal latitude = cp.getBigDecimal(1);

                    System.out.println(areaId+" " + areaName + " => [" + longitude + "," + latitude + "]");
                }
            }

        }
    }
}
