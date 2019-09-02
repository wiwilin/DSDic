import java.util.ArrayList;
import java.util.HashMap;

public class DicMap extends HashMap{
    public static HashMap<String,ArrayList> dicMap;
    public DicMap(){
        dicMap = new HashMap<String, ArrayList>();
        ArrayList list = new ArrayList();
        list.add("value");
        dicMap.put("key",list);

    }
    private boolean containsWord(String word){
        return dicMap.containsKey(word);
    }
    private ArrayList getMeaning(String word){
        return dicMap.get(word);
    }
    private int getWordCount()
    {
        return dicMap.size();
    }
    public void addWord(String word,String meaning) throws Exception{
        if(dicMap!=null) {
            if (containsWord(word))
                throw new Exception(word + " is already added");
        }
        ArrayList meanings = new ArrayList();
        meanings.add(meaning);
        try{
            dicMap.putIfAbsent(word,meanings);
        }catch (Exception e){}
    }
    public void addMeaning(String word,String meaning)throws Exception{
        if (!dicMap.containsKey(word))
            throw new Exception(word + " is not found");
        ArrayList meanings = new ArrayList();
        meanings=getMeaning(word);
        if (meanings.contains(meaning))
            throw new Exception(meaning + " is already added to word" + word);
    }
    public ArrayList searchWord(String word) throws Exception{
        ArrayList meanings = new ArrayList<String>();
        if(!dicMap.containsKey(word))
            throw new Exception(word+" is not found, please add word first");
        meanings= getMeaning(word);
        return meanings;
    }
    public void deleteWord(String word) throws Exception{
        if(!containsWord("word"))
            throw new Exception(word+" is not found, please add word first");
        try{
            dicMap.remove(word);
        }catch (Exception e){}

    }




}



