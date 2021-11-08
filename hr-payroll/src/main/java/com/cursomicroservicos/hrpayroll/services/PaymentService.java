/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cursomicroservicos.hrpayroll.services;

import com.cursomicroservicos.hrpayroll.entities.Payment;
import com.cursomicroservicos.hrpayroll.entities.Worker;
import com.cursomicroservicos.hrpayroll.feignclients.WorkerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius
 */
@Service
public class PaymentService {   
    
    @Autowired
    private WorkerFeignClient workerFeignClient;
    
    public Payment getPayment(long workerId, int days){       
        
        Worker worker = workerFeignClient.findById(workerId).getBody();        
        return new Payment(worker.getNome(), worker.getDailyIncome(), days); 
    }
}
