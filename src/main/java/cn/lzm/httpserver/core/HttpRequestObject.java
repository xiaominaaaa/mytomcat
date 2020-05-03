package cn.lzm.httpserver.core;

import javaxx.servlet.ServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的请求对象
 * @author laosi
 * @version 1.0
 * @since 1.0
 */
public class HttpRequestObject implements ServletRequest {
    /**
     * 存储请求参数的容器，
     * 根据请求参数名为键，值为value(可能有多个)，所以用数组来包装
     */
    private Map<String,String[]> parameterMap = new HashMap<String, String[]>();

    /**
     * 根据地址来封装请求参数
     * @param uri
     */
    public HttpRequestObject(String uri){
        // 请求的情况
        // /login?
        // /login?username=
        // /login?username=zhangsan
        // /login?username=zhangsan&radio=男
        // /login?username=zhangsan&radio=男&like=篮球&like=游泳

        //判断是否有？，进行虚拟路径名和请求参数列表进行分割
        if(uri.contains("?")){
            String[] parametersAndUri = uri.split("[?]");
            //判断是否带有参数
            if(parametersAndUri.length > 0){
                //获取参数列表
                String parameters = parametersAndUri[1];
                //根据&分割,获得以 key = value 的数组
                String[] parametersArr = parameters.split("&");
               // 判断是否是多个参数
                if(parametersArr.length>0){
                    //封装所有的key=value
                    for(String keyvalue:parametersArr){
                        //将键值分开
                        String[] keyvalueArr = keyvalue.split("=");
                        //判断是否存储过这个key，可能是多选列表
                        if(parameterMap.containsKey(keyvalueArr[0])){
                            //获取老值
                            String[] oldvalue = parameterMap.get(keyvalueArr[0]);
                            //将新值添加进去
                            String[] newValue = new String[oldvalue.length+1];
                            //数组copy先将老值传入其中
                            System.arraycopy(oldvalue,0,newValue,0,oldvalue.length);
                            //将新值添加其中
                            newValue[newValue.length-1] = keyvalueArr.length>0?keyvalueArr[1]:"";
                            //重新放进容器当中
                            parameterMap.put(keyvalueArr[0],newValue);
                        }else {
                            //直接放入
                            parameterMap.put(keyvalueArr[0],keyvalueArr.length>0?new String[]{keyvalueArr[1]}:new String[]{""});
                        }

                    }
                }else{
                    //单个参数将键值对分开，封装进容器
                    String[] keyvalue = parametersArr[0].split("=");
                    //如果是 username= 这种没有值的情况，放进去一个""即可
                    parameterMap.put(keyvalue[0],keyvalue.length>0?new String[]{keyvalue[1]}:new String[]{""});
                }
            }
        }
    }

    /**
     * 根据请求参数名获取参数值，如果值有多个则返回第一个，如果值没有返回一个空串,如果没有这个参数则返回null
     * @return 参数值
     */
    public String getParameter(String parameterName){
        String[] keyvalue = parameterMap.get(parameterName);
        if (keyvalue == null){
            return null;
        }else {
            return keyvalue[0];
        }
    }

    /**
     * 根据请求参数名获取多个值，返回一个数组,没有参数名返回null
     * @return
     */
    public String[] getParameters(String parameterName){
        String[] keyvalue = parameterMap.get(parameterName);
        return keyvalue;
    }
}
