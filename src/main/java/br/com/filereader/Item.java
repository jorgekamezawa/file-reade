package br.com.filereader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private Long id;
    private int quantidade;
    private BigDecimal preco;
}
