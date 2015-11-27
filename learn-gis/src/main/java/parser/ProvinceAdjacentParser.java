package parser;

/**
 * Created by liekkas on 15/11/24.
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/index.html
 * 获取省相邻名称
 *
 */
public class ProvinceAdjacentParser {
    /** 整数 */
    private static final String V_INTEGER = "^-?[1-9]\\d*$";

    //国家统计局的地理信息
    private static String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/";

    //高德地图的API接口
    private static String mapUrl = "http://restapi.amap.com/v3/config/district?subdistrict=1&extensions=all&level=province&key=9d4f5c2078ba12cb9d9d09c4e81c95d0&s=rsv3&output=json&callback=jsonp_240122_&platform=JS&logversion=2.0&sdkversion=1.3&keywords=";

    //保存路径
    private static String saveUrl = "./provincename/";

    public static void main(String[] args) throws IOException {

        //第一步获取所有地市
//        List<String> cities = getAllCities();

        //第二步把地市的轮廓经纬度获取下来并保存为文件
//        for (String city : cities) {
//            saveCity(city);
//        }

        //读取文件计算相邻省份
        calcAdjacentCity();

    }

    /**
     * 获取全国所有的地级市,直辖市也算
     * @return
     */
    private static List<String> getAllCities() throws IOException{

        List<Region> province = getProvince(url+"index.html");
        //存放所有地级市,直辖市也算
        List<String> allCities = new ArrayList<String>();

        for (Region rp : province) {// 遍历省
            allCities.add(rp.getName());
        }

        System.out.println(allCities);
        return allCities;
    }

    /**
     * 保存每个城市的信息
     * @param city
     */
    private static void saveCity(String city) throws IOException {
        JSONObject object = new JSONObject();

        DefaultHttpClient Client = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(mapUrl+city);
        String encoding = DatatypeConverter.printBase64Binary("admin:admin".getBytes("UTF-8"));

        httpGet.setHeader("Authorization", "Basic " +encoding);

        HttpResponse response = Client.execute(httpGet);

//        System.out.println("response =" + response);

        BufferedReader breader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));
        StringBuilder responseString = new StringBuilder();
        String line = "";
        while ((line = breader.readLine()) !=null) {
            responseString.append(line);
        }
        breader.close();
        String repsonseStr =responseString.toString();

        String jsonStr = StringUtils.substringBetween(repsonseStr,"(",")");

        JSONObject json = JSON.parseObject(jsonStr);

//        object.put("name",city); //城市名称
//        object.put("id",code); //id
//        object.put("center",cr); //中心经纬度
//        object.put("coordinates",bag.get(name)); //轮廓经纬度

        //以名字命名
        String u = saveUrl+city+".json";
        File file = new File(u);
        FileUtils.write(file,json.toJSONString(),"UTF-8");

        //以ID命名
//        String u2 = "./citycode/"+code+".json";
//        File file2 = new File(u2);
//        FileUtils.write(file2,object.toJSONString(),"UTF-8");
    }

    /**
     * 计算相邻地市
     */
    private static void calcAdjacentCity() throws IOException {
        long start = System.currentTimeMillis();
        File dir = new File(saveUrl);
        Collection<File> geoJsons = FileUtils.listFiles(dir, new String[]{ "json" }, true);

        Map<String,String[]> cache = new HashMap<String, String[]>();
        Map<String,String> match = new HashMap<String, String>();

        //把最终结果保存为一个json文件
        JSONArray result = new JSONArray();

        for (File geoJson : geoJsons) {

            JSONObject geo = JSON.parseObject(FileUtils.readFileToString(geoJson));
            JSONArray districts = (JSONArray) geo.get("districts");
            if (districts == null || districts.size() == 0) {
                System.out.println(">>>"+geoJson.getName()+" 数据有问题");
                continue;
            }
            JSONObject district = districts.getJSONObject(0);
            String code = district.getString("adcode");
            String name = district.getString("name");
            String center = district.getString("center");
            String polyline = district.getString("polyline");

            String[] polyArr = polyline.split(";");
            cache.put(name,polyArr);
            match.put(name,polyline);
        }

        int couter = 0;

        //轮廓经纬度数据量较大,设置一个步进偏移量能节省点时间,但有可能会出现误差,别设大了.
        int OFFSET = 1000;

        //计算相邻地市
        for (String key : cache.keySet()) {
            String[] arr = cache.get(key);
            List<String> list = new ArrayList<String>();

            JSONObject item = new JSONObject();
            item.put("name",key);

            for (int i = 0; i < arr.length; i+=OFFSET) {
                for (String m : match.keySet()) {
                    if(m.equals(key)) continue;
                    if(list.contains(m)) continue;

                    if(match.get(m).indexOf(arr[i]) > -1){
                        list.add(m);
                    }
                }
            }
            item.put("adjacents",list);
            result.add(item);

            couter++;
            System.out.println("("+couter+"/"+geoJsons.size()+")>>> ["+key+"] 相邻地市有: "+list.toString());
        }

        String u = "provinceAdjacents.json";
        File file = new File(u);
        FileUtils.write(file,result.toJSONString(),"UTF-8");

        System.out.println(">>> 处理完成!用时:"+(System.currentTimeMillis()-start)/1000+"s");
    }

    /**
     * @说明: url2Document
     * @param @param url
     * @param @return
     * @param @throws IOException
     * @return Document
     * @throws
     */
    public static Document url2Doc(String url) throws IOException {
        // 此种方式403
         return Jsoup.connect(url).get();
//         return Jsoup.connect(url).timeout(600 * 1000)
        // .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
        //
//        return Jsoup.connect(url).timeout(600 * 1000).get();
    }

    /**
     * 验证是不是整数
     *
     * @param value
     *            要验证的字符串 要验证的字符串
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean Integer(String value) {
        return match(V_INTEGER, value);
    }

    /**
     * @param regex
     *            正则表达式字符串
     * @param str
     *            要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * @说明: 获取省份
     * @param @param url
     * @param @return
     * @param @throws IOException
     * @return List<Region>
     * @throws
     */
    private static List<Region> getProvince(String url) throws IOException {
        List<Region> list = new ArrayList<Region>();
        Document doc = url2Doc(url);
        Elements proviceTr = doc.getElementsByAttributeValue("class", "provincetr");// 通过css获取tr
        for (Element e : proviceTr) {
            Elements tds = e.select("a[href]");
            for (Element element : tds) {
                Region region = new ProvinceAdjacentParser().new Region();
                // region.setCode("13");
                region.setCode(element.attr("href").substring(0, 2));
                region.setName(element.text().replaceAll("<br />", ""));
                region.setType("");
                region.setParentId(0);
                list.add(region);
            }
        }
        return list;
    }

    class Region {
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parenId;
        }

        public void setParentId(int parenId) {
            this.parenId = parenId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String code;
        private String name;
        private String type;// 乡镇类型
        private int parenId;


    }
}
