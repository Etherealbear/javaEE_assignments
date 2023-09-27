package edu.whu;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InitMethod {
  //  enum SEX_TYPE {MAN, WOMAN}
   // SEX_TYPE sex() default  SEX_TYPE.WOMAN;

}
