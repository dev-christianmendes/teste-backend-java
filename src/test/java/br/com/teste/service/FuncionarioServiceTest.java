package br.com.teste.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.teste.model.Funcionario;

class FuncionarioServiceTest {

    private FuncionarioService service;

    @BeforeEach
    void setUp() {
        service = new FuncionarioService();
    }

    @Test
    void deveIniciarComDezFuncionarios() {
        assertEquals(10, service.getFuncionarios().size());
    }

    @Test
    void deveRemoverFuncionarioJoao() {
        service.removerFuncionario("João");

        assertEquals(9, service.getFuncionarios().size());
        assertFalse(service.getFuncionarios().stream()
                .anyMatch(funcionario -> funcionario.getNome().equals("João")));
    }

    @Test
    void deveAplicarAumentoDeDezPorcento() {
        service.aplicarAumentoDezPorcento();

        Funcionario maria = funcionarioPorNome("Maria");
        assertEquals(0, new BigDecimal("2210.384").compareTo(maria.getSalario()));
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() {
        service.removerFuncionario("João");

        Map<String, List<Funcionario>> porFuncao = service.agruparPorFuncao();

        assertEquals(7, porFuncao.size());
        assertTrue(porFuncao.keySet().containsAll(List.of("Operador", "Coordenador", "Diretor",
                "Recepcionista", "Contador", "Gerente", "Eletricista")));
        assertEquals(2, porFuncao.get("Operador").size());
        assertEquals(2, porFuncao.get("Gerente").size());
        assertEquals("Maria", porFuncao.get("Operador").get(0).getNome());
        assertEquals("Heitor", porFuncao.get("Operador").get(1).getNome());
    }

    @Test
    void deveRetornarAniversariantesDeOutubroEDezembro() {
        List<Funcionario> aniversariantes = service.aniversariantesNosMeses(10, 12);

        assertEquals(2, aniversariantes.size());
        assertTrue(aniversariantes.stream()
                .anyMatch(funcionario -> funcionario.getNome().equals("Maria")));
        assertTrue(aniversariantes.stream()
                .anyMatch(funcionario -> funcionario.getNome().equals("Miguel")));
        assertTrue(aniversariantes.stream()
                .noneMatch(funcionario -> funcionario.getNome().equals("Caio")));
    }

    @Test
    void deveEncontrarFuncionarioMaisVelho() {
        Funcionario maisVelho = service.funcionarioMaisVelho();

        assertEquals("Caio", maisVelho.getNome());
        int idadeEsperada = Period.between(maisVelho.getDataNascimento(), LocalDate.now())
                .getYears();
        assertEquals(idadeEsperada, service.idadeDe(maisVelho));
    }

    @Test
    void deveOrdenarFuncionariosPorNome() {
        service.removerFuncionario("João");

        List<String> nomes = service.ordenarPorNome().stream()
                .map(Funcionario::getNome)
                .collect(Collectors.toList());

        assertEquals(List.of("Alice", "Arthur", "Caio", "Heitor", "Helena",
                "Heloísa", "Laura", "Maria", "Miguel"), nomes);
    }

    @Test
    void deveCalcularTotalDosSalariosComAumento() {
        service.removerFuncionario("João");
        service.aplicarAumentoDezPorcento();

        assertEquals(0, new BigDecimal("50906.823").compareTo(service.totalSalarios()));
    }

    @Test
    void deveCalcularSalariosMinimosPorFuncionario() {
        service.removerFuncionario("João");
        service.aplicarAumentoDezPorcento();

        Funcionario maria = funcionarioPorNome("Maria");
        assertEquals(0, new BigDecimal("1.82").compareTo(service.salariosMinimosDe(maria)));
    }

    private Funcionario funcionarioPorNome(String nome) {
        return service.getFuncionarios().stream()
                .filter(funcionario -> funcionario.getNome().equals(nome))
                .findFirst()
                .orElseThrow();
    }
}