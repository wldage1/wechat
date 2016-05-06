package com.wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import net.sf.json.JSONObject;
public class ReadFileIO {
    //读文件
    public static String read(String path){
        String data = "";
        File file = new File(path);
        if(file.isFile()&&file.exists()){
            try {
                InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),"utf-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    data +=lineTxt;
                }
                read.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    
    public static String getMenuJson(String json) {
        // 最外层解析
        JSONObject object = JSONObject.fromObject(json);
        String result = object.toString();
        return result;
    }
    
    /**
     * 将json转换为map
     * @author wang.l
     * @date 2016年4月20日
     * @return Map<String,Object>
     */
    public static Map<String, Object> getMapByJson(String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject object = JSONObject.fromObject(json);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            map.put(k.toString(), v);
        }
        Map<String, Object> map2 = new HashMap<String, Object>();
        //第二层解析 第二层可能是 也可能不是
        for(Map.Entry<String, Object> entry:map.entrySet()){
            try {
                JSONArray array = new JSONArray(entry.getValue().toString());  //判断是否是json数组
                //是json数组
                for (int i = 0; i < array.length(); i++) {
                    org.json.JSONObject object2 = array.getJSONObject(i);//json数组对象
                    JSONObject object3 = JSONObject.fromObject(object2.toString());  //json对象
                    for (Object k : object3.keySet()) {
                        Object v = object3.get(k);
                        map2.put(k.toString(), v);
                    }
                }
            } catch (Exception e) {  
            	//不是json串数组
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        return map2;
    }


    @SuppressWarnings("static-access")
	public static void main(String[] args) {
         String path="D:\\menu.txt";
         ReadFileIO fo = new ReadFileIO();
         Map<String, Object> map = fo.getMapByJson(fo.read(path));
         for(Map.Entry<String, Object> entry : map.entrySet()){
            System.out.println("key:"+entry.getKey()+"-value:"+entry.getValue());
        }
    }
    
}