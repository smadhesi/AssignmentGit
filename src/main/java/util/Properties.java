package util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import model.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Properties {
    private static Resources resources;
    public static Resources getResources(){
        if(resources==null){
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            URL url = Thread.currentThread().getContextClassLoader().getResource("resource.yml");
            File file = new File(url.getPath());
            try {
                resources = objectMapper.readValue(file,Resources.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return resources;
    }
}
