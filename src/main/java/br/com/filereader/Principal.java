package br.com.filereader;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\User\\Documents\\Estudos\\Java\\arquivo.dat";

        ManipuladorArquivo.escritor(path);
        ManipuladorArquivo.leitor(path);
    }
}
