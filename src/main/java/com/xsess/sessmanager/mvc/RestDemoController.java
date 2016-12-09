/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xsess.sessmanager.mvc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author diego
 */
@RestController(value = "/")
public class RestDemoController {
    
    @RequestMapping(value = "/{sessId}",  produces = "application/json")
    public Map<String, String> helloUser(Principal principal, HttpSession session, @PathVariable("sessId") String sessId){
        //System.out.println(" init.... " + sessId + " " + session.getId());
        HashMap<String, String> result = new HashMap<>();
        session.setAttribute("sessId", sessId);
        result.put("username", principal.getName());
        result.put("sessId", session.getAttribute("sessId").toString());
        return result;
    }
    
    @RequestMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session){
        System.out.println("... -> out");
        session.invalidate();
    }
    
    @RequestMapping(value="/getSessId", produces="application/json")
    public Map<String, String> getSessId(HttpSession session){
        HashMap<String, String> result = new HashMap<>();
        result.put("sessId", session.getAttribute("sessId").toString());
        return result;
    }
    
    @RequestMapping(value = "/setData/{key}/", method = RequestMethod.POST)
    public void setData(Principal principal, 
            HttpSession session, 
            @RequestBody String value, 
            @PathVariable("key") String key){
        
        System.out.println("Entro en setData ------------->");
        
        try {
            System.out.println("largo del string " + value.length());
            ByteArrayOutputStream out = new ByteArrayOutputStream(value.length());
            GZIPOutputStream gzipos = new GZIPOutputStream(out);
            gzipos.write(value.getBytes());
            //System.out.println(out.toString());
            gzipos.close();
            session.setAttribute(key, out.toString("ISO-8859-1"));
            
        } catch (IOException ex) {
            Logger.getLogger(RestDemoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @RequestMapping(value = "/getData/{key}",  produces = "application/json")
    public Map<String,String> getData(Principal principal,
            HttpSession session,
            @PathVariable("key") String key){
        try {
            String outStr = "";
            GZIPInputStream gzipis = null;
            
            HashMap<String, String> result = new HashMap<>();
            
           
            String out = (String)session.getAttribute(key);
            // System.out.println(out);
            gzipis = new GZIPInputStream(new ByteArrayInputStream(out.getBytes("ISO-8859-1")));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipis));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                outStr += line;
            }
            result.put(key, outStr);
            return result;
        } catch (IOException ex) {
            Logger.getLogger(RestDemoController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
            
        
    }
            
}
