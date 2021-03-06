package com.api.financeiro.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 3,max = 100)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Produto> produtos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }
    public Usuario(String nome,Long id) {
        this.nome = nome;
        this.id = id;
    }
    public Usuario() {
    }
    public Pagamento realizarPagamento(Pagamento pagamento){
        Pagamento pagamentoRealizado = pagamento.pagar(calcularValorDaCompra(),descricaoCompra());
        pagamentos.add(pagamentoRealizado);
        return pagamentoRealizado;
    }
    public Produto comprarProduto(Produto produto){
        produtos.add(produto);
        return produto;
    }
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    /*Deve ser mandada para pagamento*/
    private Double calcularValorDaCompra(){
        Double valor = 0.0;
        for (Produto produto : produtos) {
            valor += produto.getValor();
        }
        return valor;
    }
    private String descricaoCompra(){
        StringBuilder descricao = new StringBuilder();
        produtos.forEach(produto -> {
            descricao.append(produto.getNome()).append(" ------ ").append(produto.getValor()).append("\n");
        });
        return descricao.toString();
    }
}
