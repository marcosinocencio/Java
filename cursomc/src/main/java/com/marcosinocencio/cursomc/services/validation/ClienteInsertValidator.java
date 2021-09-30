/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services.validation;

/**
 *
 * @author Vinicius
 */
import com.marcosinocencio.cursomc.domain.Cliente;
import com.marcosinocencio.cursomc.domain.enums.TipoCliente;
import com.marcosinocencio.cursomc.dto.ClienteNewDTO;
import com.marcosinocencio.cursomc.repositories.ClienteRepository;
import com.marcosinocencio.cursomc.resources.exceptions.FieldMessage;
import com.marcosinocencio.cursomc.services.validation.utils.BR;
import java.util.ArrayList; 
import java.util.List; 
 
import javax.validation.ConstraintValidator; 
import javax.validation.ConstraintValidatorContext; 
import org.springframework.beans.factory.annotation.Autowired;
 
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { 
 
    @Autowired
    ClienteRepository repo;
    
    @Override 
    public void initialize(ClienteInsert ann) { 
    } 
   
    @Override 
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { 
 
        List<FieldMessage> list = new ArrayList<>(); 
         
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()) ){
            list.add(new FieldMessage("cpfOuCnpj","CPF Inválido"));
        }
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()) ){
            list.add(new FieldMessage("cpfOuCnpj","CNPJ Inválido"));
        }
        
        Cliente aux = repo.findByEmail(objDto.getEmail());
        
        if(aux != null){
            list.add(new FieldMessage("email","Email já existente"));
        }
        
        for (FieldMessage e : list) { 
            context.disableDefaultConstraintViolation(); 
            context.buildConstraintViolationWithTemplate(e.getMessage()) 
            .addPropertyNode(e.getFieldName()).addConstraintViolation(); 
        } 
        return list.isEmpty(); 
    } 
}
