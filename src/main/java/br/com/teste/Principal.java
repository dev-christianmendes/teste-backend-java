package br.com.teste;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import br.com.teste.model.Funcionario;

public class Principal {

    private static final String SEPARADOR =
            "------------------------------------------------------------------";

    public static void main(String[] args) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        List<Funcionario> funcionarios = criarFuncionarios();

        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));
        imprimirTitulo("3.2  Funcionário João removido da lista");

        imprimirTitulo("3.3  Lista de funcionários");
        imprimirFuncionarios(funcionarios, formatoData, formatoMoeda);

        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            funcionario.setSalario(funcionario.getSalario().add(aumento));
        }
        imprimirTitulo("3.4  Lista de funcionários após aumento de 10%");
        imprimirFuncionarios(funcionarios, formatoData, formatoMoeda);

        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        imprimirTitulo("3.6  Funcionários agrupados por função");
        for (Map.Entry<String, List<Funcionario>> entrada : funcionariosPorFuncao.entrySet()) {
            System.out.println("  " + entrada.getKey() + ":");
            for (Funcionario funcionario : entrada.getValue()) {
                System.out.println("    - " + funcionario.getNome());
            }
        }

        imprimirTitulo("3.8  Aniversariantes de outubro (10) e dezembro (12)");
        for (Funcionario funcionario : funcionarios) {
            int mes = funcionario.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.println("  - " + funcionario.getNome()
                        + "  (" + funcionario.getDataNascimento().format(formatoData) + ")");
            }
        }

        Funcionario funcionarioMaisVelho = funcionarios.get(0);
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getDataNascimento()
                    .isBefore(funcionarioMaisVelho.getDataNascimento())) {
                funcionarioMaisVelho = funcionario;
            }
        }
        int idadeMaisVelho = Period.between(
                funcionarioMaisVelho.getDataNascimento(),
                LocalDate.now()).getYears();
        imprimirTitulo("3.9  Funcionário com maior idade");
        System.out.println("  " + funcionarioMaisVelho.getNome() + " - " + idadeMaisVelho + " anos");

        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        imprimirTitulo("3.10  Funcionários em ordem alfabética");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("  - " + funcionario.getNome()
                    + "  (" + funcionario.getDataNascimento().format(formatoData) + ")");
        }

        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        imprimirTitulo("3.11  Total dos salários");
        System.out.println("  " + formatoMoeda.format(totalSalarios));

        imprimirTitulo("3.12  Salários mínimos por funcionário (mínimo R$ 1.212,00)");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidade = funcionario.getSalario()
                    .divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("  " + funcionario.getNome()
                    + ": " + formatoMoeda.format(funcionario.getSalario())
                    + " = " + quantidade + " salários mínimos");
        }
    }

    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario(
                "Maria",
                LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"),
                "Operador"));

        funcionarios.add(new Funcionario(
                "João",
                LocalDate.of(1990, 5, 12),
                new BigDecimal("2284.38"),
                "Operador"));

        funcionarios.add(new Funcionario(
                "Caio",
                LocalDate.of(1961, 5, 2),
                new BigDecimal("9836.14"),
                "Coordenador"));

        funcionarios.add(new Funcionario(
                "Miguel",
                LocalDate.of(1968, 10, 14),
                new BigDecimal("19119.88"),
                "Diretor"));

        funcionarios.add(new Funcionario(
                "Alice",
                LocalDate.of(1995, 1, 5),
                new BigDecimal("2234.68"),
                "Recepcionista"));

        funcionarios.add(new Funcionario(
                "Heitor",
                LocalDate.of(1999, 11, 19),
                new BigDecimal("1582.72"),
                "Operador"));

        funcionarios.add(new Funcionario(
                "Arthur",
                LocalDate.of(1993, 3, 31),
                new BigDecimal("4071.84"),
                "Contador"));

        funcionarios.add(new Funcionario(
                "Laura",
                LocalDate.of(1994, 7, 8),
                new BigDecimal("3017.45"),
                "Gerente"));

        funcionarios.add(new Funcionario(
                "Heloísa",
                LocalDate.of(2003, 5, 24),
                new BigDecimal("1606.85"),
                "Eletricista"));

        funcionarios.add(new Funcionario(
                "Helena",
                LocalDate.of(1996, 9, 2),
                new BigDecimal("2799.93"),
                "Gerente"));

        return funcionarios;
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new TreeMap<>();

        for (Funcionario funcionario : funcionarios) {
            funcionariosPorFuncao
                    .computeIfAbsent(funcionario.getFuncao(), funcao -> new ArrayList<>())
                    .add(funcionario);
        }
        return funcionariosPorFuncao;
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