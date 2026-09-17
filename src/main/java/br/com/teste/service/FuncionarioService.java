package br.com.teste.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.teste.model.Funcionario;

public class FuncionarioService {

    public static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    public static final BigDecimal PERCENTUAL_AUMENTO = new BigDecimal("0.10");

    private final List<Funcionario> funcionarios;

    public FuncionarioService() {
        this.funcionarios = criarFuncionarios();
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void removerFuncionario(String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    public void aplicarAumentoDezPorcento() {
        funcionarios.forEach(funcionario -> funcionario.setSalario(
                funcionario.getSalario()
                        .add(funcionario.getSalario().multiply(PERCENTUAL_AUMENTO))));
    }

    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        TreeMap::new,
                        Collectors.toList()));
    }

    public List<Funcionario> aniversariantesNosMeses(int... meses) {
        return funcionarios.stream()
                .filter(funcionario -> IntStream.of(meses)
                        .anyMatch(mes -> mes == funcionario.getDataNascimento().getMonthValue()))
                .collect(Collectors.toList());
    }

    public Funcionario funcionarioMaisVelho() {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElseThrow(() -> new IllegalStateException("Lista de funcionários vazia"));
    }

    public int idadeDe(Funcionario funcionario) {
        return Period.between(funcionario.getDataNascimento(), LocalDate.now()).getYears();
    }

    public List<Funcionario> ordenarPorNome() {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
    }

    public BigDecimal totalSalarios() {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal salariosMinimosDe(Funcionario funcionario) {
        return funcionario.getSalario()
                .divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
    }
}