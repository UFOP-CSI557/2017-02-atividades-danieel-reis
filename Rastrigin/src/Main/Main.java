/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Algoritmo.AED;
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
        String nomeArquivoLOG = "execucao_rastrigin_8casos.csv";

        int numexecucoes = 30;
        int tamPop = 100;
        int geracoes = 300;
        Double min = -5.12;
        Double max = 5.12;
        int nvar = 100;
        Double elitismo = 0.05;

        Double pCrossover = 0.8;
        Double pMutacao = 0.05;

        int mu = 10;
        int lambda = nvar;

        int precisao = 10;
        int numBest = 100;
        int numEst = 100;

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

            // Caso 7
            AED aed1 = new AED(tamPop, geracoes, new Problema(min, max, nvar * precisao), precisao, numBest, numEst);

            // Caso 8
            AED aed2 = new AED(tamPop, geracoes, new Problema(min, max, nvar * precisao), precisao, numBest / 2, numEst / 2);

            // Executa
            Double melhor = 0.0;
            Double pior = 0.0;
            Double media = 0.0;
            Double desvioPadrao = 0.0;

            for (int execucao = 1; execucao <= numexecucoes; execucao++) {
                ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
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
                        case 7:
                            aed1.executar();
                            melhor = aed1.getMelhorSolucao().getFuncaoObjetivo();
                            pior = aed1.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(aed1.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(aed1.getFuncaoObjetivo(), media);
                            break;
                        case 8:
                            aed2.executar();
                            melhor = aed2.getMelhorSolucao().getFuncaoObjetivo();
                            pior = aed2.getPiorSolucao().getFuncaoObjetivo();
                            media = Util.getMedia(aed2.getFuncaoObjetivo());
                            desvioPadrao = Util.getDesvioPadrao(aed2.getFuncaoObjetivo(), media);
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
