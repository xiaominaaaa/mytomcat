package cn.lzm.httpserver.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author laosi
 * @version 1.0
 * @since 1.0
 *
 * 解析server.xml文件
 */
public class ServrParser {

    /**
     * 获取端口号
     * @return
     */
    public static int getPort(){
        //创建解析器
        SAXReader saxReader = new SAXReader();
        //默认8080
        int port = 8080;
        try {
            //读取xml，生成document树对象
            Document document = saxReader.read("src/main/resources/conf/server.xml");
            //获取connector节点元素路径: server -> service -> connector
            //获取connector节点元素的xpath路径: /server/service/connector
            //获取connector节点元素的xpath路径:server//connector
            //获取connector节点元素的xpath路径://connector
            Element connectorElt = (Element) document.selectSingleNode("//connector");
            //获取port属性的值
            port = Integer.parseInt(connectorElt.attributeValue("port"));

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return  port;
    }
}
