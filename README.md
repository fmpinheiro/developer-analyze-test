Teste para Avaliação de Desenvolvedor
======================

## Considerações Iniciais

A avaliação consiste em fazer a leitura de um arquivo para extrair algumas estatísticas.

Deverá ser desenvolvida uma implementação da interface IAnalisadorRelatorio, para que os testes presentes em AnalisadorRelatorioTest possam ser executados.

A avaliação estará concluída quando todos os testes passarem.

Maiores informações sobre o que deve ser desenvolvido estão no javadoc da própria interface.

## Formato do Arquivo

O arquivo terá o formato abaixo:

```
codigo sequencial,codigo do cliente,codigo do Evento,tipo do evento,data inicio,data fim,codigo do antendente
```

Ex:

```
1,0001,E130,ALARME,2014-06-25 12:00:00,2014-06-25 12:05:32,AT01
2,0002,E131,ARME,2014-06-25 12:01:03,2014-06-25 12:05:36,AT02
```

## Considerações Finais

Implementação dos metodos da Interface IAnalisadorRelatorio.
Implementation of the methods of Interface IAnalisadorReport.

Repositório CSV responsavel por abrir, analisar e fechar o arquivo importado, classe desacoplada da implementação.
CSV repository responsible for opening, parsing, and closing the imported file, decoupled class from the implementation.

Java 8 com Stream foi utilizado para produzir as soluções de contar, agrupar, filtrar, 
Java 8 with Stream was used to produce counting, grouping, filtering, ...

