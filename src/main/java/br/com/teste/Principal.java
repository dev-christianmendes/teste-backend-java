package br.com.teste;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.teste.model.Funcionario;
import br.com.teste.service.FuncionarioService;

public class Principal {

    private static final String SEPARADOR =
            "------------------------------------------------------------------";

    public static void main(String[] args) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        FuncionarioService service = new FuncionarioService();

        service.removerFuncionario("João");
        imprimirTitulo("3.2  Funcionário João removido da lista");

        imprimirTitulo("3.3  Lista de funcionários");
        imprimirFuncionarios(service.getFuncionarios(), formatoData, formatoMoeda);

        service.aplicarAumentoDezPorcento();
        imprimirTitulo("3.4  Lista de funcionários após aumento de 10%");
        imprimirFuncionarios(service.getFuncionarios(), formatoData, formatoMoeda);

        Map<String, List<Funcionario>> funcionariosPorFuncao = service.agruparPorFuncao();

        imprimirTitulo("3.6  Funcionários agrupados por função");
        for (Map.Entry<String, List<Funcionario>> entrada : funcionariosPorFuncao.entrySet()) {
            System.out.println("  " + entrada.getKey() + ":");
            for (Funcionario funcionario : entrada.getValue()) {
                System.out.println("    - " + funcionario.getNome());
            }
        }

        List<Funcionario> aniversariantes = service.aniversariantesNosMeses(10, 12);
        imprimirTitulo("3.8  Aniversariantes de outubro (10) e dezembro (12)");
        for (Funcionario funcionario : aniversariantes) {
            System.out.println("  - " + funcionario.getNome()
                    + "  (" + funcionario.getDataNascimento().format(formatoData) + ")");
        }

        Funcionario funcionarioMaisVelho = service.funcionarioMaisVelho();
        imprimirTitulo("3.9  Funcionário com maior idade");
        System.out.println("  " + funcionarioMaisVelho.getNome()
                + " - " + service.idadeDe(funcionarioMaisVelho) + " anos");

        List<Funcionario> ordenados = service.ordenarPorNome();
        imprimirTitulo("3.10  Funcionários em ordem alfabética");
        for (Funcionario funcionario : ordenados) {
            System.out.println("  - " + funcionario.getNome()
                    + "  (" + funcionario.getDataNascimento().format(formatoData) + ")");
        }

        imprimirTitulo("3.11  Total dos salários");
        System.out.println("  " + formatoMoeda.format(service.totalSalarios()));

        imprimirTitulo("3.12  Salários mínimos por funcionário (mínimo R$ 1.212,00)");
        for (Funcionario funcionario : ordenados) {
            System.out.println("  " + funcionario.getNome()
                    + ": " + formatoMoeda.format(funcionario.getSalario())
                    + " = " + service.salariosMinimosDe(funcionario) + " salários mínimos");
        }
    }

    private static void imprimirTitulo(String titulo) {
        System.out.println();
        System.out.println(SEPARADOR);
        System.out.println(titulo);
        System.out.println(SEPARADOR);
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios,
            DateTimeFormatter formatoData, NumberFormat formatoMoeda) {
        System.out.printf("  %-14s %-12s %-16s %s%n",
                "Nome", "Nascimento", "Salário", "Função");
        System.out.printf("  %-14s %-12s %-16s %s%n",
                "----", "----------", "-------", "------");

        for (Funcionario funcionario : funcionarios) {
            System.out.printf("  %-14s %-12s %-16s %s%n",
                    funcionario.getNome(),
                    funcionario.getDataNascimento().format(formatoData),
                    formatoMoeda.format(funcionario.getSalario()),
                    funcionario.getFuncao());
        }
    }
}