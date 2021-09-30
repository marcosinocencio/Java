/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.services;

import com.marcosinocencio.cursomc.domain.Categoria;
import com.marcosinocencio.cursomc.domain.Cliente;
import com.marcosinocencio.cursomc.domain.ItemPedido;
import com.marcosinocencio.cursomc.domain.PagamentoComBoleto;
import com.marcosinocencio.cursomc.domain.Pedido;
import com.marcosinocencio.cursomc.domain.enums.EstadoPagamento;
import com.marcosinocencio.cursomc.repositories.ItemPedidoRepository;
import com.marcosinocencio.cursomc.repositories.PagamentoRepository;
import com.marcosinocencio.cursomc.repositories.PedidoRepository;
import com.marcosinocencio.cursomc.security.UserSS;
import com.marcosinocencio.cursomc.services.exceptions.AuthorizationException;
import com.marcosinocencio.cursomc.services.exceptions.ObjectNotFoundException;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vinicius
 */
@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repo;
    
    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private BoletoService boletoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EmailService emailService;
    
    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id); 
        return obj.orElseThrow(() -> new ObjectNotFoundException( 
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
    
    @Transactional
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        
        if(obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        } 
        
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        
        for(ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
    
    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso Negado");
        }
        
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy); 
        Cliente cliente = clienteService.find(user.getId());
        return repo.findByCliente(cliente, pageRequest);
    }
}
