package cn.easygd.quest.runtime.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author VD
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface AgentMethod {
}
