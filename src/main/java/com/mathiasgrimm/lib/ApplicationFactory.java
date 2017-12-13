package com.mathiasgrimm.lib;

import com.mathiasgrimm.lib.container.Container;
import com.mathiasgrimm.lib.container.di.resolver.ConstructorResolver;
import com.mathiasgrimm.lib.http.HttpHandler;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationFactory {

    // TODO refactor this method
    public Application create() throws Exception {
        Hashtable<String,String> env = new Hashtable<>();

        Properties envProperties = new Properties();
        envProperties.load(this.getClass().getResourceAsStream("/env.properties"));

        for (Object key : envProperties.keySet()) {
            env.put((String) key, (String) envProperties.get(key));
        }

        Path filePath  = Paths.get(this.getClass().getResource("/app.json").toURI());
        String content = new String(Files.readAllBytes(filePath));


        String regex = "\\{\\{\\s*(.*?)\\s*\\}\\}";
        Matcher m = Pattern.compile(regex).matcher(content);

        List<String> vars = new ArrayList<>();
        while (m.find()) {
            vars.add(m.group(1));
        }

        for (String var : vars) {
            if (!env.containsKey(var)) {
                throw new Exception("variable " + var + " not defined in the env.properties");
            }

             content = content.replaceAll("\\{\\{\\s*" + var + "\\s*\\}\\}", env.get(var));
        }

        JSONTokener jsonTokener = new JSONTokener(content);
        JSONObject config = new JSONObject(jsonTokener);

        // System.out.println(config.toMap());

        AppConfig appConfig = new AppConfig(config);


        Container container = new Container(new ConstructorResolver());
        return new Application(container, appConfig, env);
    }

}
