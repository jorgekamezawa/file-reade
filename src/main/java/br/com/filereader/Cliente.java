package br.com.filereader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {
    private String codigo;
    private String cnpj;
    private String nome;
    private String areaDeNegocio;
}
