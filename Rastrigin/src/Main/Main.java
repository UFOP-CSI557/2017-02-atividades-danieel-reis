/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Algoritmo.AG;
import Algoritmo.DE;
import Algoritmo.ES;
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
    public static final int CROSSOVER_UM_PONTO = 1;
    public static final int CROSSOVER_DOIS_PONTOS = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filePath = "/home/daniel/CE/";
        String nomeArquivoLOG = filePath + "execucao_rastrigin_6casos.csv";

        int numexecucoes = 30;
        int tamPop = 100;
        int geracoes = 300;
        Double min = -5.12;
        Double max = 5.12;
        int nvar = 100;
        Double elitismo = 0.2;

        Double pCrossover = 0.8;
        Double pMutacao = 0.05;

        int mu = 100;
        int lambda = 1000;

        try {
            // Abre o arquivo
            BufferedWriter arq = new BufferedWriter(new FileWriter(nomeArquivoLOG, true));

            // Caso 1
            AG ag1 = new AG(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), elitismo, CROSSOVER_UM_PONTO);

            // Caso 2
            AG ag2 = new AG(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), elitismo, CROSSOVER_DOIS_PONTOS);

            // Caso 3
            ES es1 = new ES(tamPop, pMutacao, mu, lambda, geracoes, new Problema(min, max, nvar));
            
            // Caso 4
            ES es2 = new ES(tamPop, pMutacao, mu, lambda, geracoes, new Problema(min, max, nvar), elitismo);
            
            // Caso 5
            DE de1 = new DE(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar));

            // Caso 6
            DE de2 = new DE(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), elitismo);

            // Executa
            Double melhor = 0.0;
            Double pior = 0.0;
            Double media = 0.0;
            Double desvioPadrao = 0.0;

            for (int execucao = 1; execucao <= numexecucoes; execucao++) {
                ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
                Collections.shuffle(casos);

                // Escolhe qual dos dois casos irá executar no momento
                for (Integer caso : casos) {
                    long startTime = System.currentTimeMillis();
                    switch (caso) {
                        case 1:
                            ag1.executar();
                            melhor = ag1.getMelhorSolucao().getFuncaoObjetivo();
                            pior = ag1.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(ag1.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(ag1.getFuncaoObjetivo(), media);
                            break;
                        case 2:
                            ag2.executar();
                            melhor = ag2.getMelhorSolucao().getFuncaoObjetivo();
                            pior = ag2.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(ag2.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(ag2.getFuncaoObjetivo(), media);
                            break;
                        case 3:
                            es1.executar();
                            melhor = es1.getMelhorSolucao().getFuncaoObjetivo();
                            pior = es1.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(es1.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(es1.getFuncaoObjetivo(), media);
                            break;
                        case 4:
                            es2.executar();
                            melhor = es2.getMelhorSolucao().getFuncaoObjetivo();
                            pior = es2.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(es2.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(es2.getFuncaoObjetivo(), media);
                            break;
                        case 5:
                            de1.executar();
                            melhor = de1.getMelhorSolucao().getFuncaoObjetivo();
                            pior = de1.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(de1.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(de1.getFuncaoObjetivo(), media);
                            break;
                        case 6:
                            de2.executar();
                            melhor = de2.getMelhorSolucao().getFuncaoObjetivo();
                            pior = de2.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(de2.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(de2.getFuncaoObjetivo(), media);
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
