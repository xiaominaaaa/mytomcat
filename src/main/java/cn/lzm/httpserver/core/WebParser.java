package cn.lzm.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *解析web.xml
 * @version 1.0
 * @author laosi
 * @since 1.0
 */
public class WebParser {
    /**
     * 以 项目名为key,
     * 项目下的web.xml解析出来url-pattern(key),servlet-class(value)的mpa为value
     */
    public static Map<String,Map<String,String>> servletMaps = new HashMap<String, Map<String, String>>();

    /**
     * 解析所有项目下的web.xml
     * @param webAppNames
     * @return
     * @throws DocumentException
     */
    public static void parser(String[] webAppNames)throws DocumentException{

        Map<String,String> servletMap = null;
        for (String webAppName:webAppNames){
            servletMap = parser(webAppName);
            servletMaps.put(webAppName,servletMap);
        }
    }

    /**
     * 解析单个web.xml,由以下几个步骤来搞定
     * 1. 解析 servlet标签,将标签下的servlet-name和servet-class封装成一个map
     * 2.解析servlet-mapping 标签，将标签下的servlet-name和ur-pattern封装成另一个map
     * 3.获取刚刚解析的任意一个map的ketSet(因为servlet标签和servlet-mapping标签的servlet-name子标签值是要对应的),然后遍历ketset,
     *  取得servlet-name相同的url-pattern(键)和servlet-class(值),封装成一个map，并返回
     *
     * @param webAppName 应用名称
     * @return 由 以url-pattern为key,以servlet-class为value的Map集合
     */
    private static Map<String,String> parser(String webAppName) throws DocumentException {
        //存储servlet-name 和 servlet-class的map
        Map<String,String> servletNameInfo = new HashMap<String, String>();
        //存储servlet-name 和 servlet-pattern的map
        Map<String,String> sevletMappingInfo = new HashMap<String, String>();

        //获取xml路径
        String pathname = "src/main/resources/"+webAppName+"/WEB-INF/web.xml";
        //创建解析器
        SAXReader saxReader = new SAXReader();
        //根据解析器的read方法将配置文件加载到内存中，生成一个Document对象树
        Document document = saxReader.read(pathname);

        //获取servlet节点元素: web-app -> servlet
        List<Element> servletNodes = document.selectNodes("/web-app/servlet");
        //将servlet-name和servlet-class的值key和value来存储
            //开始遍历servlet-name节点元素对象
        for (Element element:servletNodes){
            //获取servlet-name节点元素
            Element servletNameElt = (Element) element.selectSingleNode("servlet-name");
            //获取servlet-name的值
            String servletname = servletNameElt.getStringValue();

            //获取servlet-class节点元素
            Element servletClassElt = (Element) element.selectSingleNode("servlet-class");
            //获取servlet-class值
            String servletclass = servletClassElt.getStringValue();

            //将servlet-name 和 servlet-class放入到容器中
            servletNameInfo.put(servletname,servletclass);
        }

        //获取servlet-mapping节点元素对象:web-app -> servlet-mapping
        List<Element> servletMappingNodes = document.selectNodes("/web-app/servlet-mapping");
        //循环遍历servletMappingNodes
        for (Element element:servletMappingNodes){
            //获取servlet-name节点元素值
            Element servletNameElt = (Element) element.selectSingleNode("servlet-name");
            //获取servletname
            String sername = servletNameElt.getStringValue();

            //获取url-pattern节点元素值
            Element urlElt = (Element) element.selectSingleNode("url-pattern");
            //获取url-pattern元素值
            String urlPattern = urlElt.getStringValue();

            //封装到map集合中
            sevletMappingInfo.put(sername,urlPattern);
        }

        //获取servlet 和 servlet-mapping其中一个的keyset()
        Set<String> servletNames = servletNameInfo.keySet();
        //创建存储servelt-class 和 url-pattern 的map集合
        Map<String,String> servletMap = new HashMap<String, String>();
        //遍历keyset()
        for (String servletname: servletNames){
            //获取servlet-class
            String servletClass = servletNameInfo.get(servletname);
            //获取url-pattern
            String urlPattern = sevletMappingInfo.get(servletname);

            //存储值,以url为key,全限定类名为值
            servletMap.put(urlPattern,servletClass);
        }

        return servletMap;
    }
}
