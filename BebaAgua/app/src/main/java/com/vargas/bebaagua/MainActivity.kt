package com.vargas.bebaagua

import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.vargas.bebaagua.model.CalcularQtdDia
import java.util.*

class MainActivity : AppCompatActivity() {

    // edit_peso, edit_idade e btnCalcular vai herdar de EditText
    // lateinit permite que você evite inicializar uma propriedade quando um objeto for construído
    private lateinit var edit_peso: EditText
    private lateinit var edit_idade: EditText
    private lateinit var btnCalcular: Button
    private lateinit var txtResultMl: TextView // txtResultMl vai herdar do TextView
    private lateinit var icRedefinir: ImageView // icRedefinir vai herdar do ImageView pra resetar todos os dados
    private lateinit var btnLembrete: Button // criação de uma variável para o lembrete que herda de um botão
    private lateinit var btnAlarme: Button // criação da variável do alarme que também herda propriedades de um botão
    private lateinit var txtHora: TextView // variável para os dados da hora que herdará de um TextView
    private lateinit var txtMinutos: TextView // variável para dos dados de minutos, herdando do TextView

    private lateinit var calcularQtdDia: CalcularQtdDia // criei uma variável privada para herdar da classe CalcularQtdDia
    private var resultadoMl = 0.0 // essa variável não terá valor fixo e mudará constantemente, por isso nao é 'lateinit' e recebe valor 0.0 pois será double

    lateinit var timePickerDialog: TimePickerDialog // criação de uma variável lateinit, para que seja herdada e inicializada com uma classe chamada TimePickerDialog
    lateinit var calendario: Calendar // criação de uma variável lateinit recbendo a herança da classe Calendar
    var horaAtual = 0 // iniciando as horas como zero
    var minAtual = 0 // iniciando os minutos como zero


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        IniciarDados() // iniciei o método criado para iniciar os dados do app
        calcularQtdDia = CalcularQtdDia() // iniciei a variável recebendo a classe que faz o cálculo, quando for informado todos os dados, o cálculo será feito através da classe CalcularQtdDia

        //quando for clicado no botão, será verificado se os dados foram inseridos
        btnCalcular.setOnClickListener {
            // SE o edit_peso (que pegamos do usuário e convertemos pra string) estiver vazio...
            if (edit_peso.text.toString().isEmpty()) {
                Toast.makeText(this, "Informe o peso.", Toast.LENGTH_SHORT).show() // Toast.makeText vai mostrar NESTA tela (por isso o this) a mensagem pedindo p/ informar peso de forma rapida (lenght_short)
            } else if (edit_idade.text.toString().isEmpty()) { // SE o edit_idade (que pegamos do usuário e convertemos pra string) estiver vazio...
                Toast.makeText(this, "Informe a idade.", Toast.LENGTH_SHORT).show() // Toast.makeText vai mostrar NESTA tela (por isso o this) a mensagem pedindo p/ informar idade de forma rapida (lenght_short)
            } else {
                // precisamos capturar os dados do usuário pra poder calcular, e vamos fazer  o cálculo através de uma classe, pra que ela calcule e devolva o resultado na tela
                // a classe que vai ter os calculos é a CalcularQtdDia
                // val é o tipo de variável em kotlin que nao pode ter seus dados modificados
                val peso = edit_peso.text.toString().toDouble() // criei a variável peso, recebendo o edit_peso e converti pra texto e peguei através do .toString() e converti pra double
                val idade = edit_idade.text.toString().toInt() // criei a variavel idade, recebendo o edit_idade e converti pra texto e peguei atraves do .toString() e converti pra INTEIRO
                calcularQtdDia.CalcularTotalMl(peso,idade) // peguei a variavel já iniciada e puxei o método que tem dentro do objeto e passo os parametros no parentese (TEM Q SER NA MESMA ORDEM DO CONSTRUTOR)
                resultadoMl = calcularQtdDia.resultadoMl() // a variável "resultadoMl()" será guardada aqui
                txtResultMl.text = resultadoMl.toString() + " " + "ml." // o resultadoMl() será convertido para string e será apresentado na tela em formato de frase.
            }
        }

        // peguei o icRedefinir que é o ícone do botão de redefinir os dados e adicionei uma ação de um click, para aparecer uma mensagem de redefinção de peso/altura
        icRedefinir.setOnClickListener {
            val alertaRedefinir = AlertDialog.Builder(this) // criei uma variável imutável (por isso é val) chamada alertaRedefinir que recebe a classe nativa AlertDialog.Builder, com o contexto this (essa mesma tela)
            alertaRedefinir.setTitle(R.string.alertaRedefinir)// peguei por parâmetro de string o alertaRedefinir, que foi criado no arquivo strings.xml para mostrar o TÍTULO do alertDialog
                .setMessage(R.string.alertaText) // peguei por parâmetro de string o alertaText, com a mensagem que vai aparecer perguntando se o usuário quer excluir os dados
                .setPositiveButton("Ok", { dialogInterface, i -> // lambda é uma funcao anonima, ou seja, nao precisa declarar um nome da mesma
                    edit_peso.setText("")
                    edit_idade.setText("")
                    txtResultMl.setText("")
                })
            alertaRedefinir.setNegativeButton("Cancelar", { dialogInterface, i ->

            })
            val resultAlerta = alertaRedefinir.create() // criei a variavel imutável resultAlerta para que ela "crie" o resultado da ação após clicar em algum dos botões criados acima
            resultAlerta.show() // executa/mostra o resultado quando clicado em algum dos botoes (negativo ou positivo)
        }

        // definição de ações para o lembrete, que no caso é usar um clickListener quando o usuário clicar no botao de lembrete
        btnLembrete.setOnClickListener {
            calendario = Calendar.getInstance() // variavel calendario recebe a classe Calendar e recupera a instancia usando o 'getInstance()'
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY) // a variável horaAtual recebe a variável calendario, que está instanciada com a classe Calendar, e através dela pegaremos a hora do dia por parâmetro
            minAtual = calendario.get(Calendar.MINUTE) // mesma coisa que o comentário acima, apenas troca a hora por minuto para efetuar a ação no campo dos minutos
            // a variavel timePicker recebe a classe TimePicker para escolher a hora/minuto, e essa classe solicita diversos parâmetros;
            // primeiro, o contexto (this pois é essa tela mesmo),passei a variavel timePickerDialog que herda a classe TimePickerDialog e passei por parâmetro pra receber hora e minuto do tipo inteiro
            timePickerDialog = TimePickerDialog(this, {timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                    txtHora.text = String.format("%02d",hourOfDay) // pegamos o txtHora e passamos para string para receber o formato do horario em 2 dígitos, recebendo a hora do dia
                    txtMinutos.text = String.format("%02d",minutes) // pegamos o txtMinutos e passamos para string para receber os minutos por 2 dígitos através do parâmetro
            },horaAtual, minAtual,true) // passamos a hora e o minuto, informando que o formato de 24h é verdadeiro (essa propriedade é booleana
            timePickerDialog.show() // executando a Dialog criada

        }

        //início das definições de ações para o botao de alarme
        btnAlarme.setOnClickListener {
            // se o campo da hora e o campo dos minutos NÃO estiverem vazios...
            if (!txtHora.text.toString().isEmpty() && !txtMinutos.text.toString().isEmpty()) {
                // ... vamos setar um alarme usando a classe AlarmClock e passando a ação se setar alarme, após criar uma variáve do tipo intent (que é uma descrição abstrata de uma operaçao que deve ocorrer)
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR,txtHora.text.toString().toInt()) // o putExtra() serve para add dados estendidos À intenção, ou seja, que tipo de dado esta intent deve receber para executar a operação
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txtMinutos.text.toString().toInt()) //temos como intenção pegar através da classe AlarmClock a hora e minutos, converter para string e dps converter para inteiro
                intent.putExtra(AlarmClock.EXTRA_MESSAGE,getString(R.string.txtMessageAlarm)) // linha dedicada para mostrar uma mensagem quando o alarme tocar
                startActivity(intent) // inicia a intent

                // outra estrutura de repetição onde vai testar caso os dados sejam nulos
                // o alarme irá tocar 00:00 mesmo que o app não tenha horario definido, evita que ocorra crash
                if (intent.resolveActivity(packageManager) != null) { // se caso o resolveActivity for diferente de nulo, ele terá um horario pra iniciar
                    startActivity(intent)
                }
            }
        }
    }
        // método privado para iniciar os dados pra calcular
        private fun IniciarDados() {
            edit_peso = findViewById(R.id.edit_peso) // estou dizendo que o edit_peso daqui é referente ao edit_peso da main
            // o mesmo vale para as demais variáveis
            edit_idade = findViewById(R.id.edit_idade)
            btnCalcular = findViewById(R.id.btnCalcular)
            txtResultMl = findViewById(R.id.txtResultMl)
            icRedefinir = findViewById(R.id.ic_redefinir)
            btnLembrete = findViewById(R.id.btnLembrete)
            btnAlarme = findViewById(R.id.btnAlarme)
            txtHora = findViewById(R.id.txtHora)
            txtMinutos = findViewById(R.id.txtMinutos)
        }
    }