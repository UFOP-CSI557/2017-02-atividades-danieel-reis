/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Representacao;

import java.util.Collections;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class Operacoes {

    public static Double gap(Double RL, Double RO) {
        return (RO - RL) / RL * 100;
    }

    public static void copiarPaiNoFilho(Individuo pai, Individuo filho, int indice, Problema problema) {
        int idx = indice;
        int k = indice;
        while (k < problema.getDimensao()) {
            if (!filho.getCromossomos().contains(pai.getCromossomos().get(k))) {
                filho.getCromossomos().add(pai.getCromossomos().get(k));
                idx++;

                if (idx == problema.getDimensao() || filho.getCromossomos().size() == problema.getDimensao()) {
                    break;
                }
            }

            k++;
            if (k == problema.getDimensao()) {
                k = 0;
            }
        }

        idx = 0; // Começa a inserir do início
        k = 0;
        while (k < problema.getDimensao()) {
            if (!filho.getCromossomos().contains(pai.getCromossomos().get(k))) {
                filho.getCromossomos().add(idx, pai.getCromossomos().get(k));
                idx++;

                if (idx == problema.getDimensao()) {
                    break;
                }
            }

            k++;
        }
    }

    public static void crossoverOX(Individuo pai1, Individuo pai2, Individuo filho1, Individuo filho2, Problema problema) {
        Random rnd = new Random();

        int i = rnd.nextInt(problema.getDimensao() / 2);
        int j;

        do {
            j = rnd.nextInt(problema.getDimensao());
        } while (i == j || i > j);

        // Parte central entre i e j
        filho1.getCromossomos().addAll(pai1.getCromossomos().subList(i, j));
        filho2.getCromossomos().addAll(pai2.getCromossomos().subList(i, j));

        // Copiar de P2 para F1
        copiarPaiNoFilho(pai2, filho1, j, problema);

        // Copiar de P1 para F2
        copiarPaiNoFilho(pai1, filho2, j, problema);

    }

    public static void mutacaoSWAP(Individuo individuo, Problema problema, Double pMutacao) {
        Random rnd = new Random();
        // Verifica o processo de mutação pra cada gene do cromossomo
        for (int i = 0; i < individuo.getCromossomos().size(); i++) {
            if (rnd.nextDouble() <= pMutacao) {
                int j;
                do {
                    j = rnd.nextInt(problema.getDimensao());
                } while (i == j);

                Collections.swap(individuo.getCromossomos(), i, j);
            }
        }
    }
}
