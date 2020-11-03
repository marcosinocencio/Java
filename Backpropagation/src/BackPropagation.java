/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Vinicius
 */
public class BackPropagation {
    
    private int classeDesejada;
    private int classeObtida;
    private float vtNetCamadaOculta[];          //vetor que armazenará os nets da camada oculta
    private float vtNetCamadaSaida[];           //vetor que armazenará os nets da camada de saida
    private float vtSaidaCamadaOculta[];        //vetor que armazenará os valores retornado pela função de transferencia aplicada na camada oculta    
    private float vtSaidaCamadaSaida[];         //vetor que armazenará os valores retornado pela função de transferencia aplicada na camada de saida
    private float vtErroCamadaSaida[];          //vetor que armazenará os erros calculados na camada de saida
    private float vtErroCamadaOculta[];         //vetor que armazenará os erros calculados na camada oculta
    private int vtValorDesejado[];              //vetor que armazenará o valor desejado para entrada
    private float vetorEntrada[];               //vetor que aramzenará os valores de entrada
    private float mtzPesosCamadaOculta[][];     //Matriz que armazenará os pesos da ligação entre a camada oculta e as entradas
    private float mtzPesosCamadaSaida[][];      //Matriz que armazenará os pesos da ligação entre a camada de saida e a camada oculta
    private int funcaoTransferencia;         //veriável que armazenará qual foi a função de transferência selecionada
    private int qtdeNeuronioCamadaOculta;       //variável que armazenará a quantidade de neuronios na camada oculta
    private int qtdeNeuronioCamadaSaida;        //variável que armazenará a quantidade de nueronios na camada de saida
    private int qtdeNeuronioCamadaEntrada;
    private float taxaAprendizado;              //variável que armazenará a taxa de aprendizado 
    private float erroDaRede;                   //variável que armazenará o erro da rede
    
       
    
    public BackPropagation(){
        
    }
    
    
    
    public void setaRede(int qtdeNeuronioCamadaEntrada,
            int qtdeNeuronioCamadaOculta,
            int qtdeNeuronioCamadaSaida,
            float taxaAprendizado, 
            int funcaoTransferencia,
            Tabela tabelaPesosCamadaOculta,
            Tabela tabelaPesosCamadaSaida) {

        //Determina o tamanho de cada vetor baseado no tamanho da rede
        vtNetCamadaOculta = new float[qtdeNeuronioCamadaOculta];
        vtSaidaCamadaOculta = new float[qtdeNeuronioCamadaOculta];
        vtNetCamadaSaida = new float[qtdeNeuronioCamadaSaida];
        vtSaidaCamadaSaida = new float[qtdeNeuronioCamadaSaida];
        vtErroCamadaSaida = new float[qtdeNeuronioCamadaSaida];
        vtErroCamadaOculta = new float[qtdeNeuronioCamadaOculta];

        //instancía uma matriz de tamanho nXm, 
        mtzPesosCamadaOculta = new float[qtdeNeuronioCamadaOculta][qtdeNeuronioCamadaEntrada];
        mtzPesosCamadaSaida = new float[qtdeNeuronioCamadaSaida][qtdeNeuronioCamadaOculta];

        this.funcaoTransferencia = funcaoTransferencia;
        this.qtdeNeuronioCamadaOculta = qtdeNeuronioCamadaOculta; //Qtde de neuronios na camada oculta
        this.qtdeNeuronioCamadaSaida = qtdeNeuronioCamadaSaida;  //Qtde de neuronios na camada de saida
        this.qtdeNeuronioCamadaEntrada = qtdeNeuronioCamadaEntrada;
        this.taxaAprendizado = taxaAprendizado;          //Taxa de aprendizado

        /*
         * Chamada de métodos
         */
        preencheMtzPesosCamadaOculta(tabelaPesosCamadaOculta); //chama o método que preenche a matriz de pesos da camada oculta
        preencheMtzPesosCamadaSaida(tabelaPesosCamadaSaida);//chama o método que preenche a matriz de pesos da camada de saida
    }

    //Método que defini as características da rede e os itens necessários para o calculo 
    public void inserirEntradas(float vetorEntrada[], int valorDesejadoDeSaida[], int classeDesejada) {
        this.vetorEntrada = vetorEntrada;             //Vetor com os valores de entrada 
        this.vtValorDesejado = valorDesejadoDeSaida;  //Vetor com os valores dejado
        this.classeDesejada = classeDesejada;
    }

    //Método que treina a rede
    public float treinarRede() {
        passo2();
        passo3();
        passo4();
        passo5();
        passo6();
        passo7();
        passo8();
        passo9();
        passo10();

        return erroDaRede;
    }

    //O método de testarRede executa somente até o passo 6, pois no teste não se ajusta os pesos
    public float testarRede() {
        passo2();
        passo3();
        passo4();
        passo5();
        passo6();
        return erroDaRede;
    }
    

    //Método calcula os nets da camada oculta
    private void passo2() {
        float somaNet = 0;

        for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) { //percorre a quantidade de neuronios na camada oculta
            for (int i = 0; i < qtdeNeuronioCamadaEntrada; i++) { //percorre a quantidade de entradas
                somaNet += mtzPesosCamadaOculta[j][i] * vetorEntrada[i]; //somatória do produto peso pelo vetor de entrada
            }
            vtNetCamadaOculta[j] = somaNet; //Atribui o resultado da somatória ao net de cada neurônio
            somaNet = 0; //reinicia o somatório
        }
    }

    //Método aplica a função de transferencia da Camada Oculta, para obter a saída na camada oculta
    private void passo3() {
        for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) { //percorre a quantidade de neuronios da camada oculta
            vtSaidaCamadaOculta[j] = funcaoTransferencia(vtNetCamadaOculta[j]);//chama o método que retorna 
        }                                                                           //o valor da função de transf
    }

    //Método que calcula os nets da camada de saida
    private void passo4() {
        float somaNet = 0;

        for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) { //percorre a qtde de neuronios da camada de saida
            for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) { //percorre a qtde de neuronios da camada oculta
                somaNet += mtzPesosCamadaSaida[k][j] * vtSaidaCamadaOculta[j]; //somatória do produto do peso pelo pela função de transferencia
            }
            vtNetCamadaSaida[k] = somaNet; //Atribui o resultado da somatória ao net de cada neurônio
            somaNet = 0; //reinicia o somatório
        }
    }

    //Método que aplica a função de transferência na camada de Saida, para se obter os valores de saida
    private void passo5() {
        float vtTemporario[] = new float[qtdeNeuronioCamadaSaida];
        
        for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) { //percorre a qtde de neuronios da camada de saida
            vtSaidaCamadaSaida[k] = funcaoTransferencia(vtNetCamadaSaida[k]); //chama o método que retorna 
        }   
        
        verificaClasseObtida();
    }

    //Metodo que calcula o erro da camada de saída
    private void passo6() {
        for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) { //percorre a qtde de neuronios da camada de saida
            //efetua o calculo do erro da camada de saida
            vtErroCamadaSaida[k] = (vtValorDesejado[k] - vtSaidaCamadaSaida[k])*derivadaFuncaoTransf(vtSaidaCamadaSaida[k]);
        }
    }

    //Método que calcula o erro dos neuronios na camada oculta
    private void passo7() {
        float somaErro = 0;        
        for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) { //percorre a quantidade de neuronios da camada oculta
            for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) { //percorre a qtde de neuronios da camada de saida
                somaErro += vtErroCamadaSaida[k]* mtzPesosCamadaSaida[k][j]; //somatória do produto do erro pelo peso
            }
            vtErroCamadaOculta[j] = derivadaFuncaoTransf(vtSaidaCamadaOculta[j]) * somaErro; //calculo do erro
            somaErro = 0; //reinicia o somatório
        }
    }

    //Método que ajusta os pesos da camada de saída
    private void passo8() {
        for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) { //percorre a qtde de neuronios da camada de saida
            for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) { //percorre a qtde de neuronios da camada oculta
                mtzPesosCamadaSaida[k][j] += taxaAprendizado * vtErroCamadaSaida[k] * vtSaidaCamadaOculta[j];
            }
        }
    }

    //Método que ajusta os pesos da camada de saída
    private void passo9() {
        for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) { //percorre a qtde de neuronios da camada oculta
            for (int i = 0; i < vetorEntrada.length; i++) {  //percorre a qtde de entradas 
                mtzPesosCamadaOculta[j][i] += taxaAprendizado * vtErroCamadaOculta[j] * vetorEntrada[i];
            }
        }
    }

    //Método que obtem o erro da rede
    private void passo10() {
        float somaErro = 0;
        for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) { //percorre a quantidade de neuronios da camada de saida
            somaErro += vtErroCamadaSaida[k] * vtErroCamadaSaida[k]; //Erro ao quadrado
        }   
        erroDaRede = somaErro / 2;
    }

    //Método que calcula a função de transferência
    private float funcaoTransferencia(float x) {
        double y;
        
        if (funcaoTransferencia == 1) { //No caso da função de transferencia selecionada seja a Logística
            y = (1 / (1 + Math.exp(-x)));
        } else { //No caso da função de transferencia selecionada seja a Hiperbólica
            y = ((1 - Math.exp(-2 * x))
                    / (1 + Math.exp(-2 * x)));
        }
        return (Float.parseFloat(String.valueOf(y))); //Converte o Double em String para depois converter em Float
    }

    //Método que calcula a derivada da função de transferência
    private float derivadaFuncaoTransf(float x) {
        float y;
        if (funcaoTransferencia == 1) {
            y = x * (1 - x);
        } else {
            y = 1 - (x * x);
        }
        return y;
    }

    /*
     * 
     */
    //Método que preenche a mtz de pesos da camadao culta
    private void preencheMtzPesosCamadaOculta(Tabela tabelaPeso) {
        int contador = 0;
        for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) {
            for (int i = 0; i < qtdeNeuronioCamadaEntrada; i++) {
                mtzPesosCamadaOculta[j][i] = Float.parseFloat(String.valueOf(tabelaPeso.getValueAt(contador, 2)).replace(",", "."));
                contador++;
            }
        }
    }

    ////Método que preenche a mtz de pesos da camadao de saida
    private void preencheMtzPesosCamadaSaida(Tabela tabelaPeso) {
        int contador = 0;
        for (int k = 0; k < qtdeNeuronioCamadaSaida; k++) {
            for (int j = 0; j < qtdeNeuronioCamadaOculta; j++) {
                mtzPesosCamadaSaida[k][j] = Float.parseFloat(String.valueOf(tabelaPeso.getValueAt(contador, 2)).replace(",", "."));
                contador++;
            }
        }
    }

    public float[][] getMtzPesosCamadaOculta() {
        return mtzPesosCamadaOculta;
    }

    public float[][] getMtzPesosCamadaSaida() {
        return mtzPesosCamadaSaida;
    }

    public int getClasseObtida() {
        float maior = vtSaidaCamadaSaida[0];
        int classeObtida = 0;

        for (int i = 1; i < qtdeNeuronioCamadaSaida; i++) {
            if (vtSaidaCamadaSaida[i] > maior) {
                maior = vtSaidaCamadaSaida[i];
                classeObtida = i;
            }
        }     
        return classeObtida+1;

    }
    
    public void verificaClasseObtida(){
        float maior = vtSaidaCamadaSaida[0];
        classeObtida = 1;
        
        for (int k = 1; k < qtdeNeuronioCamadaSaida; k++) { 
            if (vtSaidaCamadaSaida[k] > maior){
                classeObtida = k+1;
                maior = vtSaidaCamadaSaida[k];
            } 
        }  
    }

    public int getclasseObtida() {
        return this.classeObtida;
    }
    
}
