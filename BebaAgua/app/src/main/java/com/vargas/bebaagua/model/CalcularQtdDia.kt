package com.vargas.bebaagua.model

// CRIADA ESTA CLASSE PRA FAZER O CÁLCULO DA QUANTIDADE DIARIA

class CalcularQtdDia {

    // criei variáveis privadas do tipo val (que o valor nao pode ser alterado) recebendo a quantidade de ml padrao pra cada
    // coloquei .0 nos números todos pois são números decimais
    private val primeiraIdade = 40.0
    private val segundaIdade = 35.0
    private val terceiraIdade = 30.0
    private val quartaIdade = 25.0

    // variáveis iniciando como 0.0 para receber o primeiro dado do usuário, e outra pra receber o resultado do cálculo
    private var resultMl = 0.0
    private var resultMlTotal = 0.0

    // método CalcularTotalMl com parâmetros dentro do parênteses
    fun CalcularTotalMl(peso: Double, idade: Int){

        // estruturas para calcular a quantidade de água
        if(idade <= 17){
            resultMl = peso * primeiraIdade
            resultMlTotal = resultMl
        }else if(idade <= 55){
            resultMl = peso * segundaIdade
            resultMlTotal = resultMl
        }else if(idade <= 65){
            resultMl = peso * terceiraIdade
            resultMlTotal = resultMl
        }else{
            // última verificação nao precisa de leitura, vai pegar o que sobrar das variáveis val criadas
            resultMl = peso * quartaIdade
            resultMlTotal = resultMl
        }
    }

    // método público que retorna a variavel resultMlTotal em formato double com base no que foi calculado na estrutura acima
    fun resultadoMl(): Double {
        return resultMlTotal
    }

}

/*
Até 17 anos: 40ml por kg
18 - 55 anos: 35ml por kg
56 - 65 anos: 30ml por kg
66 ou mais: 25ml por kg
Multiplica o peso (em kg) pela quantidade de ml para saber o resultado total
Meu peso hoje: 65kg
Minha idade hoje: 27 anos
65kg x 35ml (conforme a tabela com base na minha idade) = 2.275 ltros de água por dia
*/