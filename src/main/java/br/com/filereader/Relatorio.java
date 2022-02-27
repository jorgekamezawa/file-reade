package br.com.filereader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Relatorio {
    private Integer quantidadeVendedores;
    private Integer quantidadeClientes;
    private Long idVendaMaisCara;
    private String nomePiorVendedor;
}
