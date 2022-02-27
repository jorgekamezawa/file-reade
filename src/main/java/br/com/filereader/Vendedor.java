package br.com.filereader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vendedor {
    private String codigo;
    private String cpf;
    private String nome;
    private String salario;
}
