package server;

//author:Wei LIN
//number:885536
//id:wlin8
//

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DicMap extends HashMap {
    public static HashMap<String, ArrayList> dicMap;
    String path;


    public DicMap(String path) {
        this.path=path;
        dicMap = new HashMap<String, ArrayList>();
        ArrayList list = new ArrayList<String>();
        //list.add("A value");
        //list.add("Another value");
        //dicMap.put("key", list);


    }

    private boolean containsWord(String word) {
        return dicMap.containsKey(word);
    }

    private ArrayList getMeaning(String word) {
        return dicMap.get(word);
    }

    private int getWordCount() {
        return dicMap.size();
    }

    public void addMeaning(String word, String meaning) throws Exception {
        ArrayList meanings = new ArrayList<String>();

        if (dicMap.containsKey(word)) {
            meanings = dicMap.get(word);
            meanings.add(meaning);
            dicMap.put(word, meanings);
        } else {
            try {
                meanings.add(meaning);
                dicMap.putIfAbsent(word, meanings);
            } catch (Exception e) {
            }
        }

        System.out.println("added" + word);
    }

    public ArrayList searchWord(String word) throws Exception {
       // System.out.println("mapsize"+dicMap.size());
        ArrayList meanings = new ArrayList<String>();
        if (!dicMap.containsKey(word))
            throw new Exception(word + " is not found, please add word first");
        meanings = getMeaning(word);
        return meanings;
    }
    /*
    public void addMeaning(String word,String meaning)throws Exception{
        if (!dicMap.containsKey(word))
            throw new Exception(word + " is not found");
        ArrayList meanings = new ArrayList();
        meanings=getMeaning(word);
        if (meanings.contains(meaning))
            throw new Exception(meaning + " is already added to word" + word);
    }
    */

    public void deleteWord(String word) throws Exception {
        System.out.println(word);
        if (!dicMap.containsKey(word))
            throw new Exception(word + " is not found, please add word first");
        try {
            dicMap.remove(word);
        } catch (Exception e) {
        }

    }

    public void printfile() {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(path);
        try {
            mapper.writeValue(file, dicMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readfile() throws IOException {
        /*
        FileInputStream fileInput = new FileInputStream(new File(path));
        byte[] bs = new byte[10];
        int len = -1;
        StringBuffer sb = new StringBuffer();
        while ((len = fileInput.read(bs)) != -1) {
            String str = new String(bs, 0, len);
            sb.append(str);
        }
        fileInput.close();

        String s = sb.toString();

        JSONObject json = new JSONObject(s);
        */
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);
        try {
            dicMap = mapper.readValue(file,new TypeReference<Map<String,ArrayList>>(){});
            System.out.println("a : " + dicMap.get("a"));
            System.out.println( dicMap.size());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}







