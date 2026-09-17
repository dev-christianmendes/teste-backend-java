# Teste Técnico (Java) — Prothera

> **Lógica de raciocínio > Christian Mendes.**

![CI](https://github.com/dev-christianmendes/teste-backend-java/actions/workflows/ci.yml/badge.svg)
![Java 17](https://img.shields.io/badge/Java-17-ED8B00)
![Build](https://img.shields.io/badge/Maven-3.9-C71A36)
![JUnit 5](https://img.shields.io/badge/Testes-JUnit%205-25A162)

## Sobre

Projeto desenvolvido para o teste técnico de programação. Antes de escrever qualquer
linha de código, minha prioridade foi **entender o problema** e mapear exatamente o que
estava sendo avaliado. A partir disso, dividi o trabalho em etapas pequenas e fui
construindo a solução de forma incremental, validando cada passo até chegar no resultado
final.

## Enunciado do teste

```text
TESTE PRÁTICO PROGRAMAÇÃO

Considerando que uma indústria possui as pessoas/funcionários abaixo:

Diante disso, você deve desenvolver um projeto java, com os seguintes requisitos:

1 - Classe Pessoa com os atributos: nome (String) e data nascimento (LocalDate).

2 - Classe Funcionário que estenda a classe Pessoa, com os atributos:
    salário (BigDecimal) e função (String).

3 - Deve conter uma classe Principal para executar as seguintes ações:
  3.1 - Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
  3.2 - Remover o funcionário "João" da lista.
  3.3 - Imprimir todos os funcionários com todas suas informações, sendo que:
        - informação de data deve ser exibida no formato dd/mm/aaaa;
        - informação de valor numérico deve ser exibida no formatado com separador
          de milhar como ponto e decimal como vírgula.
  3.4 - Os funcionários receberam 10% de aumento de salário, atualizar a lista de
        funcionários com novo valor.
  3.5 - Agrupar os funcionários por função em um MAP, sendo a chave a "função" e o
        valor a "lista de funcionários".
  3.6 - Imprimir os funcionários, agrupados por função.
  3.8 - Imprimir os funcionários que fazem aniversário no mês 10 e 12.
  3.9 - Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
  3.10 - Imprimir a lista de funcionários por ordem alfabética.
  3.11 - Imprimir o total dos salários dos funcionários.
  3.12 - Imprimir quantos salários mínimos ganha cada funcionário, considerando que
         o salário mínimo é R$ 1212.00.

Orientações gerais:
- você poderá utilizar a ferramenta que tem maior domínio (exemplos: eclipse, netbeans etc);
- após finalizado o desenvolvimento, exportar o projeto e encaminhar o link do seu teste;
- assim que recebermos seu projeto desenvolvido, será agendada uma entrevista com nosso
  time técnico para avaliação.
```

### Funcionários

| Nome | Data de nascimento | Salário | Função |
|---|---|---|---|
| Maria | 18/10/2000 | R$ 2.009,44 | Operador |
| João | 12/05/1990 | R$ 2.284,38 | Operador |
| Caio | 02/05/1961 | R$ 9.836,14 | Coordenador |
| Miguel | 14/10/1968 | R$ 19.119,88 | Diretor |
| Alice | 05/01/1995 | R$ 2.234,68 | Recepcionista |
| Heitor | 19/11/1999 | R$ 1.582,72 | Operador |
| Arthur | 31/03/1993 | R$ 4.071,84 | Contador |
| Laura | 08/07/1994 | R$ 3.017,45 | Gerente |
| Heloísa | 24/05/2003 | R$ 1.606,85 | Eletricista |
| Helena | 02/09/1996 | R$ 2.799,93 | Gerente |

## Requisitos implementados

| # | Requisito | Como foi implementado |
|---|---|---|
| 3.1 | Inserir os funcionários | Lista `ArrayList<Funcionario>` na ordem da tabela |
| 3.2 | Remover "João" | `removeIf` filtrando pelo nome |
| 3.3 | Imprimir funcionários | Data em `dd/MM/yyyy` e salário formatado com `NumberFormat` pt-BR |
| 3.4 | Aumento de 10% | Multiplicação com `BigDecimal` e `setSalario` |
| 3.5 | Agrupar por função | `Map<String, List<Funcionario>>` via `computeIfAbsent` |
| 3.6 | Imprimir agrupados | Iteração sobre o `Map` (funções em ordem alfabética com `TreeMap`) |
| 3.8 | Aniversariantes mês 10 e 12 | Filtro pelo mês com `LocalDate.getMonthValue()` |
| 3.9 | Funcionário mais velho | Comparação de datas + idade via `Period` |
| 3.10 | Ordem alfabética | `sort` com `Comparator.comparing` |
| 3.11 | Total dos salários | Soma com `BigDecimal` |
| 3.12 | Salários mínimos | Divisão com `RoundingMode.HALF_UP` (mínimo R$ 1.212,00) |

## Diferenciais e decisões de projeto

Além do que foi pedido, agreguei práticas que considero essenciais em qualquer
código profissional:

- **Camada de serviço** — `FuncionarioService` concentra as regras de negócio e o
  `Principal` fica responsável apenas por orquestrar a execução e apresentar os
  resultados (SRP).
- **Testes automatizados (JUnit 5)** — 9 testes cobrindo cada regra do enunciado:
  cadastro, remoção, aumento de 10%, agrupamento, aniversariantes, funcionário mais
  velho, ordem alfabética, total de salários e salários mínimos.
- **Stream API** — `filter`, `sorted`, `groupingBy` e `reduce` nos pontos onde o
  código fica mais expressivo que o loop tradicional.
- **`BigDecimal` para valores financeiros** — evita erro de arredondamento em
  operações monetárias; formatação com `NumberFormat` pt-BR.
- **CI com GitHub Actions** — o build e os testes rodam automaticamente a cada push
  (badge no topo deste README).
- **Maven Wrapper** — o projeto roda com `./mvnw` sem exigir Maven instalado.

## Minha abordagem

Todo problema grande parece maior do que é. Por isso, dividi o teste em etapas que
pudessem ser concluídas uma por vez, sempre validando antes de seguir:

1. **Entender o problema** — identificar o que estava sendo avaliado.
2. **Preparar o ambiente** — Java 17 e Maven, por serem simples de reproduzir.
3. **Criar o projeto** — estrutura Maven padrão (`src/main/java`).
4. **Criar a classe `Pessoa`** — `nome` e `dataNascimento`.
5. **Criar a classe `Funcionario`** — herdar de `Pessoa` e adicionar `salario` e `funcao`.
6. **Criar a `Principal`** — ponto de entrada com a sequência de ações.
7. **Cadastrar os funcionários** — montar a lista na ordem da tabela.
8. **Remover o João** — reduzir a lista para 9 funcionários.
9. **Exibir os funcionários** — com data e valores formatados.
10. **Aplicar aumento de 10%** — atualizar os salários com `BigDecimal`.
11. **Agrupar por função** — montar o `Map` de funcionários por função.
12. **Filtrar aniversariantes** — outubro e dezembro.
13. **Encontrar o funcionário mais velho** — nome e idade.
14. **Ordenar alfabeticamente** — lista pelo nome.
15. **Calcular o total dos salários** e **os salários mínimos** por funcionário.
16. **Testar tudo** — compilar e executar conferindo cada requisito.
17. **Organizar Git/GitHub** — projeto versionado e publicado.
18. **Revisar o projeto antes da entrega** — checagem final de todo o checklist.

## Tecnologias e fundamentos

- **Java 17** — versão LTS utilizada no projeto.
- **Maven** — gerenciamento de build e dependências.
- **POO** — herança entre `Pessoa` e `Funcionario`.
- **Collections** — `List`, `Map`, `ArrayList`, `TreeMap`.
- **API de datas** — `LocalDate`, `DateTimeFormatter` e `Period`.
- **Valores financeiros** — `BigDecimal` e `NumberFormat` pt-BR.
- **Stream/Lambda** — `removeIf`, `computeIfAbsent`, method references e `Comparator`.

## Estrutura do projeto

```
teste-pratico-java
├── pom.xml
├── mvnw
├── .gitignore
├── .github/workflows/ci.yml
├── README.md
└── src/
    ├── main/java/br/com/teste/
    │   ├── Principal.java
    │   ├── model/
    │   │   ├── Pessoa.java
    │   │   └── Funcionario.java
    │   └── service/
    │       └── FuncionarioService.java
    └── test/java/br/com/teste/service/
        └── FuncionarioServiceTest.java
```

## Como compilar e executar

Requisitos: **JDK 17+** e **Maven** (ou apenas o JDK, usando o wrapper).

```bash
./mvnw clean package
java -cp target/classes br.com.teste.Principal
```

## Como testar

```bash
./mvnw test
```

## Saída esperada

Trecho ilustrativo da execução, já com o formato visual das seções:

```text
------------------------------------------------------------------
3.2  Funcionário João removido da lista
------------------------------------------------------------------

------------------------------------------------------------------
3.3  Lista de funcionários
------------------------------------------------------------------
  Nome         Nascimento   Salário         Função
  ----         ----------   -------         ------
  Maria        18/10/2000   R$ 2.009,44     Operador
  Caio         02/05/1961   R$ 9.836,14     Coordenador
  Miguel       14/10/1968   R$ 19.119,88    Diretor
  ...
```

Ao final da execução, o programa apresenta o **total de salários de R$ 50.906,82**
após o aumento de 10%, o funcionário mais velho (**Caio**, 65 anos) e os salários
mínimos correspondentes a cada funcionário.

---

Desenvolvido por **Christian Mendes**.