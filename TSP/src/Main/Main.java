package Main;

import Algoritmo.AG;
import Algoritmo.Novo;
import Algoritmo.Novo;
import Representacao.Problema;
import Representacao.Util;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniel
 */
public class Main {

    public static Double RL = 10628.0;
//    public static Double RL = 7542.0;
    public static boolean IMPRIMIR = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nomeArquivoLOG = "execucao_TSP.csv";

        int numexecucoes = 30;

        int geracoes = 100;
        int tamPop = 1000;
        Double pSelecionados = 0.05;
        Double pCrossover = 0.7;
        Double pMutacao = 0.05;
        Double pBuscaLocal = 0.75;

        Problema tsp = new Problema("att48.tsp");
//        Problema tsp = new Problema("berlin52.tsp");

        try {
            // Abre o arquivo
            BufferedWriter arq = new BufferedWriter(new FileWriter(nomeArquivoLOG, true));

            // Caso 1
            AG ag = new AG(tamPop, pCrossover, pMutacao, pBuscaLocal, geracoes, tsp);

            // Caso 2
            Novo novo = new Novo(tamPop, pCrossover, pMutacao, pBuscaLocal, geracoes, tsp, pSelecionados);

            // Executa
            Double melhor = 0.0;
            Double pior = 0.0;
            Double media = 0.0;
            Double desvioPadrao = 0.0;

            for (int execucao = 1; execucao <= numexecucoes; execucao++) {
                ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
                Collections.shuffle(casos);

                // Escolhe qual dos dois casos irá executar no momento
                for (Integer caso : casos) {
                    long startTime = System.currentTimeMillis();
                    switch (caso) {
                        case 1:
                            ag.executar();
                            melhor = ag.getMelhorSolucao().getFuncaoObjetivo();
                            pior = ag.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(ag.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(ag.getFuncaoObjetivo(), media);
                            break;
                        case 2:
                            novo.executar();
                            melhor = novo.getMelhorSolucao().getFuncaoObjetivo();
                            pior = novo.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(novo.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(novo.getFuncaoObjetivo(), media);
                            break;
                    }

                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;

                    String texto = execucao + ";" + caso + ";" + melhor + ";" + pior + ";" + media + ";" + desvioPadrao + ";" + totalTime;

                    // Adiciona no fim do arquivo o texto
                    arq.append(texto + "\n");
                    arq.flush();

                    // Imprime o resultado obtido
                    System.out.println(texto);
                    System.out.flush();
                }
            }

            // Fecha o arquivo
            arq.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
