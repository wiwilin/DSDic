package server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DicMap extends HashMap {
    public static HashMap<String, ArrayList> dicMap;

    public DicMap() {
        dicMap = new HashMap<String, ArrayList>();
        ArrayList list = new ArrayList<String>();
        list.add("A value");
        list.add("Another value");
        dicMap.put("key", list);


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

        File file = new File("result.json");
        try {
            mapper.writeValue(file, dicMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readfile() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, Object> newMap = mapper.readValue(new File(
                    "result.json"), new TypeReference<Map<String, Object>>() {
            });

            //System.out.println("Key : " + newMap.get("key"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}







