package com.wechat.des;

public class testDes {
	public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub   
//        String key = "20120401";  
//        String text = "abcd";  
//        String result1 = Des.encryptDES(text,key);  
//        String result2 = Des.decryptDES(result1, key);  
        
        
		System.out.println(Des.encryptDES("wAAlmg07d3w","_wechat_"));  
        System.out.println(Des.decryptDES("IoWpkQ9x+z4C7/LL6vkqXA==","_wechat_"));
        
//        System.out.println(result1);  
//        System.out.println(result2);
    }  

}
