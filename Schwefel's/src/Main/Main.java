/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Algoritmo.AGModificado;
import Algoritmo.AGOriginal;
import Algoritmo.Novo;
import Representacao.Problema;
import Representacao.Util;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author daniel
 */
public class Main {

    public static boolean IMPRIMIR = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nomeArquivoLOG = "execucao_schwefels.csv";

        int numexecucoes = 30;
        Double min = -500.0;
        Double max = 500.0;

        int nvar = 40;
        int geracoes = 300;
        int tamPop = 100;

        Double pSelecionados = 0.15;
        Double pCrossover = 0.7;
        Double pMutacao = 0.05;

        try {
            // Abre o arquivo
            BufferedWriter arq = new BufferedWriter(new FileWriter(nomeArquivoLOG, true));

            // Caso 1
//            AG ag = new AG(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), pSelecionados);
            AGOriginal ag = new AGOriginal(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), 0);

            // Caso 2
            Novo novo = new Novo(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), pSelecionados);

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
