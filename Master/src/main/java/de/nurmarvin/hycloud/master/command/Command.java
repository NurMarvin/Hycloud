package de.nurmarvin.hycloud.master.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author NurMarvin
 */
public interface Command {

    void execute(String[] args);

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface CloudCommand {
        String name();

        String[] alias() default "";

        String description() default "No Description.";
    }
}
