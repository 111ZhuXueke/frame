package org.script.javascript;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * javascript脚本引擎测试类
 * @author : zhuxueke
 * @since : 2017-12-12 9:50
 **/
public class InvocableTest {
    public static void main(String[] args) throws Exception{
        URL url = InvocableTest.class.getResource("test.js");
        URI uri = url.toURI();
        String path = uri.getPath();
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        engine.eval(new FileReader(new File(path)));
        Greeter greeter = ((Invocable)engine).getInterface(Greeter.class);
        String string = greeter.greet("world");
        System.out.println(string);
    }
}