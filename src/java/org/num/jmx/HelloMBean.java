package org.num.jmx;

/**
 * @author : zhuxueke
 * @since : 2018-01-26 17:24
 **/
public interface HelloMBean {
    public String getName();

    public void setName(String name);

    public String getAge();

    public void setAge(String age);

    public void helloWorld();

    public void helloWorld(String str);

    public void getTelephone();
}
