package com.marcosinocencio.cursomc.services.validation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.annotation.ElementType; 
import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target; 
 
import javax.validation.Constraint; 
import javax.validation.Payload; 
 
@Constraint(validatedBy = ClienteInsertValidator.class) 
@Target({ ElementType.TYPE }) 
@Retention(RetentionPolicy.RUNTIME) 
public @interface ClienteInsert { 
 
    String message() default "Erro de validação"; 
 
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {}; 
}
