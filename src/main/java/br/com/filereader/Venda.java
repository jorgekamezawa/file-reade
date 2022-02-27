package br.com.filereader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Venda {
    private String codigo;
    private Long idVenda;
    private List<Item> item;
    private String nomeVendedor;
    private BigDecimal valorVenda;

    public Venda(String codigo, Long idVenda, List<Item> item, String nomeVendedor) {
        this.codigo = codigo;
        this.idVenda = idVenda;
        this.item = item;
        this.nomeVendedor = nomeVendedor;
    }
}
