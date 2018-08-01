package controllers;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
/*
* For create instance in runtime
*/
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface Eager {}
