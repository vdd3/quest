package cn.easygd.quest.api.annotation;

import java.lang.annotation.*;

/**
 * @author VD
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuestComponent {

    /**
     * component alis
     *
     * @return alis
     */
    String alis();

    /**
     * component description
     *
     * @return description
     */
    String description() default "";
}
