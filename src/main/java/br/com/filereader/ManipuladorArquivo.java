package br.com.filereader;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ManipuladorArquivo {

    public static void leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader("C:\\Users\\User\\Documents\\Estudos\\Java\\In\\arquivo.dat"));
        String linha = buffRead.readLine();
        List<Vendedor> vendedorList = new ArrayList<>();
        List<Cliente> clienteList = new ArrayList<>();
        List<Venda> vendaList = new ArrayList<>();
        while (linha != null) {
            System.out.println(linha);
            String codigo = linha.substring(0, 3);
            switch (codigo) {
                case "001":
                    vendedorList.add(criarVendedor(linha));
                    break;
                case "002":
                    clienteList.add(cirarCliente(linha));
                    break;
                case "003":
                    vendaList.add(criarVenda(linha));
                    break;
                default:
                    throw new IOException("Codigo da linha nao correspondente!");
            }
            linha = buffRead.readLine();
        }
        buffRead.close();

        System.out.println("########## RESULTADO ##########");
        int quantidadeVendedores = contarVendedores(vendedorList);
        System.out.println("Quantidade Vendedores - " + quantidadeVendedores);
        int quantidadeCliente = contarClientes(clienteList);
        System.out.println("Quantidade Clientes - " + quantidadeCliente);
        calcularTotalDaVendaPorItem(vendaList);
        Long idVendaMaisCara = calcularVendaMaisCara(vendaList);
        System.out.println("Id da venda mais cara - " + idVendaMaisCara);
        Vendedor vendedorQueMenosVendeu = calcularVededorQueMenosVendeu(vendedorList, vendaList);
        System.out.println("Pior vendedor - " + vendedorQueMenosVendeu.getNome());

        int v = 1;
    }

    private static Vendedor calcularVededorQueMenosVendeu(List<Vendedor> vendedorList, List<Venda> vendaList) {
        Venda maiorVenda = vendaList.stream().min(Comparator.comparing(Venda::getValorVenda)).orElseThrow(() -> new RuntimeException("Venda mais cara exception!"));
        return vendedorList.stream()
                .filter(vendedor -> vendedor.getNome().equals(maiorVenda.getNomeVendedor()))
                .findFirst().orElseThrow(() -> new RuntimeException(""));
    }

    private static Long calcularVendaMaisCara(List<Venda> vendaList) {
        Venda maiorVenda = vendaList.stream().max(Comparator.comparing(Venda::getValorVenda)).orElseThrow(() -> new RuntimeException("Venda mais cara exception!"));
        return maiorVenda.getIdVenda();
    }

    private static void calcularTotalDaVendaPorItem(List<Venda> vendaList) {
        vendaList.forEach(venda -> {
            List<BigDecimal> totalDeVendaPorItemList = new ArrayList<>();
            venda.getItem().forEach(item -> {
                totalDeVendaPorItemList.add(item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
            });
            venda.setValorVenda(totalDeVendaPorItemList.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        });
    }

    private static int contarClientes(List<Cliente> clienteList) {
        List<String> nomeClienteList = clienteList.stream().map(Cliente::getNome).collect(Collectors.toList());
        Long quantidadeDeClienteDiferente = nomeClienteList.stream().distinct().count();
        return quantidadeDeClienteDiferente.intValue();
    }

    private static int contarVendedores(List<Vendedor> vendedorList) {
        List<String> nomeVendedorList = vendedorList.stream().map(Vendedor::getNome).collect(Collectors.toList());
        Long quantidadeDeVendedorDiferente = nomeVendedorList.stream().distinct().count();
        return quantidadeDeVendedorDiferente.intValue();
    }

    private static Vendedor criarVendedor(String linha) throws IOException {
        System.out.println("===== REGRA 001 =====");
        List<String> dadosList = Arrays.asList(linha.split("ç"));
        if (dadosList.size() != 4) throw new IOException("Layout incorreto dos dados de entrada da regra um!");
        Vendedor vendedor = new Vendedor(dadosList.get(0), dadosList.get(1), dadosList.get(2), dadosList.get(3));
        System.out.println("Codigo - " + vendedor.getCodigo());
        System.out.println("CPF - " + vendedor.getCpf());
        System.out.println("Nome - " + vendedor.getNome());
        System.out.println("Salario - R$" + new BigDecimal(vendedor.getSalario()));
        System.out.println();
        return vendedor;
    }

    private static Cliente cirarCliente(String linha) throws IOException {
        System.out.println("===== REGRA 002 =====");
        List<String> dadosList = Arrays.asList(linha.split("ç"));
        if (dadosList.size() != 4) throw new IOException("Layout incorreto dos dados de entrada da regra um!");
        Cliente cliente = new Cliente(dadosList.get(0), dadosList.get(1), dadosList.get(2), dadosList.get(3));
        System.out.println("Codigo - " + cliente.getCodigo());
        System.out.println("CPF - " + cliente.getCnpj());
        System.out.println("Nome - " + cliente.getNome());
        System.out.println("Area de Negocio - " + cliente.getAreaDeNegocio());
        System.out.println();
        return cliente;
    }

    private static Venda criarVenda(String linha) throws IOException {
        System.out.println("===== REGRA 003 =====");
        List<String> dadosVendaList = Arrays.asList(linha.split("ç\\[|\\]ç|ç"));
        String[] itemList = dadosVendaList.get(2).split(",");
        List<Item> item = new ArrayList<>();

        for (String itemString : itemList) {
            List<String> dadosItemList = Arrays.asList(itemString.split("-"));
            item.add(new Item(
                    Long.valueOf(dadosItemList.get(0)),
                    Integer.parseInt(dadosItemList.get(1)),
                    new BigDecimal(dadosItemList.get(2))
            ));
        }

        if (dadosVendaList.size() != 4) throw new IOException("Layout incorreto dos dados de entrada da regra um!");
        Venda venda = new Venda(dadosVendaList.get(0), Long.valueOf(dadosVendaList.get(1)), item, dadosVendaList.get(3));
        System.out.println("Codigo - " + venda.getCodigo());
        System.out.println("Id Venda - " + venda.getIdVenda());
        System.out.println("Item - " + venda.getItem());
        System.out.println("Nome Vendedor - " + venda.getNomeVendedor());
        System.out.println();
        return venda;
    }

    public static void escritor(String path) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C:\\Users\\User\\Documents\\Estudos\\Java\\Out\\flat_file_name.done.dat"));
        String linha = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Escreva algo: ");
        linha = in.nextLine();
        buffWrite.append(linha).append("\n");
        buffWrite.close();
    }
}
