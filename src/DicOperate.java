import com.alibaba.fastjson.*;

import java.util.HashMap;
import java.util.Map;

public class DicOperate {
    public Map<String,String> map;
    public JSONObject dicJson;
    public void initJsonDic(){
        map = (String,String>)dicJson;
        for (String key : map.keySet()) {
            System.out.println(key+":"+map.get(key));
        }
    }
    public void initDatDic(){

    }
    public void updateDic(){
        dicJson = new JSONObject(map);
        System.out.println(JSON.toJSONString(dicJson));
    }
    public void closeDic(){

    }
    public String searchDic(String vocabulary){
        String explanation=map.getString(vocabulary);
        return explanation;
    }
    public void addDic(String vocabulary,String explanation){
        map.putIfAbsent(vocabulary,explanation);
    }
    public void deleteDic(String vocabulary){
        map.remove(vocabulary);
    }

}
