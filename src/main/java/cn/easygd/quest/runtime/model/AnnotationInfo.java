package cn.easygd.quest.runtime.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解信息模型类
 * 用于存储扫描到的@AgentComment类和@AgentMethod方法的信息
 */
public class AnnotationInfo {
    private String className;
    private String classSimpleName;
    private String packageName;
    private Class<?> clazz;
    private List<MethodInfo> agentMethods;

    public AnnotationInfo() {
        this.agentMethods = new ArrayList<>();
    }

    public AnnotationInfo(String className, String classSimpleName, String packageName, Class<?> clazz) {
        this.className = className;
        this.classSimpleName = classSimpleName;
        this.packageName = packageName;
        this.clazz = clazz;
        this.agentMethods = new ArrayList<>();
    }

    // Getters and Setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<MethodInfo> getAgentMethods() {
        return agentMethods;
    }

    public void setAgentMethods(List<MethodInfo> agentMethods) {
        this.agentMethods = agentMethods;
    }

    public void addAgentMethod(MethodInfo methodInfo) {
        this.agentMethods.add(methodInfo);
    }

    /**
     * 方法信息内部类
     */
    public static class MethodInfo {
        private String methodName;
        private Method method;
        private Class<?>[] parameterTypes;
        private Class<?> returnType;

        public MethodInfo() {}

        public MethodInfo(String methodName, Method method) {
            this.methodName = methodName;
            this.method = method;
            this.parameterTypes = method.getParameterTypes();
            this.returnType = method.getReturnType();
        }

        // Getters and Setters
        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public void setReturnType(Class<?> returnType) {
            this.returnType = returnType;
        }
    }

    @Override
    public String toString() {
        return "AnnotationInfo{" +
                "className='" + className + '\'' +
                ", classSimpleName='" + classSimpleName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", agentMethodsCount=" + agentMethods.size() +
                '}';
    }
}