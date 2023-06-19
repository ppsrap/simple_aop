package cn.lasse.springframework.aop;

/**
 * 被代理的目标对象
 * 是一个目标对象，在目标对象类中提供 Object 入参属性，以及获取目标类 TargetClass 信息。
 * @author Lasse
 */
public class TargetSource {

    private final Object target;

    public Object getTarget(){
        return this.target;
    }

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

}