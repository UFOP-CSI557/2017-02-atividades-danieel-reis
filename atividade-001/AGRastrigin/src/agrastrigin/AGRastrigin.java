/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

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
public class AGRastrigin {

    public static boolean IMPRIMIR = false;
    public static final int CROSSOVER_UM_PONTO = 1;
    public static final int CROSSOVER_DOIS_PONTOS = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filePath = "/home/daniel/CE/";
        String nomeArquivoLOG = filePath + "execucao_Rastrigin.txt";

        int tamPop = 500;
        int geracoes = 300;
        Double min = -5.12;
        Double max = 5.12;
        int nvar = 100;
        Double elitismo = 0.2;

        Double pCrossover = 0.8;
        Double pMutacao = 0.05;

        try {
            // Abre o arquivo
            BufferedWriter arq = new BufferedWriter(new FileWriter(nomeArquivoLOG, true));
            
            // Caso 1
            AlgoritmoGenetico ag1 = new AlgoritmoGenetico(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), elitismo, CROSSOVER_UM_PONTO);

            // Caso 2
            AlgoritmoGenetico ag2 = new AlgoritmoGenetico(tamPop, pCrossover, pMutacao, geracoes, new Problema(min, max, nvar), elitismo, CROSSOVER_DOIS_PONTOS);

            // Executa
            Double melhor = 0.0;
            Double pior = 0.0;
            Double media = 0.0;
            Double desvioPadrao = 0.0;
            
            for (int execucao = 1; execucao <= 30; execucao++) {
                ArrayList<Integer> casos = new ArrayList<>(Arrays.asList(1, 2));
                Collections.shuffle(casos);

                // Escolhe qual dos dois casos irá executar no momento
                for (Integer caso : casos) {
                    long startTime = System.currentTimeMillis();
                    switch (caso) {
                        case 1:
                            ag1.executar();
                            melhor = ag1.getMelhorSolucao().getFuncaoObjetivo();
                            pior = ag1.getPiorSolucao().getFuncaoObjetivo();
                            media = ag1.getMedia();
                            desvioPadrao = ag1.getDesvioPadrao(media);
                            break;
                        case 2:
                            ag2.executar();
                            melhor = ag2.getMelhorSolucao().getFuncaoObjetivo();
                            pior = ag2.getPiorSolucao().getFuncaoObjetivo();
                            media = ag2.getMedia();
                            desvioPadrao = ag2.getDesvioPadrao(media);
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
