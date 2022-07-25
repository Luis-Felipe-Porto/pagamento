package com.api.financeiro.service;

import com.api.financeiro.dto.PagamentoDto;
import com.api.financeiro.entity.DadosClientePagamento;
import com.api.financeiro.entity.Pagamento;
import com.api.financeiro.exception.UserNotFoundException;
import com.api.financeiro.mappers.PagamentoMapper;
import com.api.financeiro.mappers.impl.PagamentoMapperCustom;
import com.api.financeiro.repository.DadosClientePagamentoRepository;
import com.api.financeiro.repository.PagamentoRepository;
import com.api.financeiro.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final DadosClientePagamentoRepository dadosClientePagamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagamentoMapper pagamentoMapper;

    public PagamentoService(PagamentoRepository pagamentoRepository, DadosClientePagamentoRepository dadosClientePagamentoRepository, UsuarioRepository usuarioRepository, PagamentoMapper pagamentoMapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.dadosClientePagamentoRepository = dadosClientePagamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pagamentoMapper = pagamentoMapper;
    }
    @Transactional
    public PagamentoDto realizarPagamento(@Valid DadosClientePagamento dadosClientePagamento) throws UserNotFoundException, InterruptedException {
        Thread.sleep(300);
        PagamentoMapperCustom pagamentoMapperCustom = new PagamentoMapperCustom();
        dadosClientePagamentoRepository.save(dadosClientePagamento);
        Pagamento pagamento = pagamentoMapperCustom.dadosClienteToPagamento(dadosClientePagamento);
        pagamentoRepository.save(pagamento);
        return new PagamentoDto(pagamento.getId(),pagamento.getValor(),pagamento.getDescricao());
    }
}
