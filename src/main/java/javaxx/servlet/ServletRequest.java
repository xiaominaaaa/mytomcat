package javaxx.servlet;

/**
 * 假装这是规范的ServletRequest
 */
public interface ServletRequest {
    String getParameter(String parameterName);
    String[] getParameters(String parameterName);
}
