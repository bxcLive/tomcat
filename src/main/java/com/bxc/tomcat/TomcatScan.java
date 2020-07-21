package com.bxc.tomcat;

import com.bxc.annocation.WebServlet;
import com.bxc.exception.FindNotOnlyWebPath;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class TomcatScan {

    public HashMap<String,HttpServlet> TomcatScan(String basePath) throws ClassNotFoundException, FindNotOnlyWebPath, IllegalAccessException, InstantiationException {
        HashMap<String,HttpServlet> webMap=new HashMap<>();
        String base = basePath.replaceAll("\\.", "/");
        URL resource = TomcatServer.class.getResource("/");
        String realPath=resource.getPath()+base;
        System.out.println(realPath);
        File file=new File(realPath);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile())
            {
                String name = file1.getName();
                if (name.endsWith(".class"))
                {
                    String[] split = name.split("\\.");
                    String filename=split[0];
                    String basename=basePath+"."+filename;
                    Class<?> aClass= Class.forName(basename);
                    WebServlet annotation = aClass.getAnnotation(WebServlet.class);
                    if (annotation!=null)
                    {
                        String uri = annotation.value();
                        if (webMap.get(uri)!=null)
                        {
                            throw new FindNotOnlyWebPath("我们期望找到唯一一个路径为"+uri+"的servlet"+"，但是找到了两个相同路径的servlet");
                        }
                        HttpServlet httpServlet= (HttpServlet) aClass.newInstance();
                        webMap.put(uri,httpServlet);
                    }else {
                        return null;
                    }
                }

            }
        }
        return webMap;
    }

}
