/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Representacao;

import java.util.Collections;

/**
 *
 * @author daniel
 */
public class BuscaLocal {

    public static void buscaLocal(Individuo individuo, Problema problema) {
        // SWAP - trocar u e v
        trocarUcomV(individuo, problema);

    }

    private static void trocarUcomV(Individuo individuo, Problema problema) {
        double foInicial = individuo.getFuncaoObjetivo();

        for (int u = 0; u < individuo.getCromossomos().size() - 1; u++) {
            for (int v = u + 1; v < individuo.getCromossomos().size(); v++) {
                //Opera SWAP
                Collections.swap(individuo.getCromossomos(), u, v);

                //Calcular FO - Delta
                problema.calcularFuncaoObjetivo(individuo);

                //Se existe melhora
                if (individuo.getFuncaoObjetivo() < foInicial) {
                    //Encerra - first improvement
                    return;
                } else {
                    Collections.swap(individuo.getCromossomos(), u, v);
                }
            }
        }

        //Retorna o valor original da FO
        //Nao aconteceu nenhuma mudança
        individuo.setFuncaoObjetivo(foInicial);

    }
}
