/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Representacao;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Util {
    
    public static Double getMedia(ArrayList<Double> funcaoObjetivo) {
        Double soma = 0.0;
        for (Double fo : funcaoObjetivo) {
            soma += fo;
        }
        return (soma / funcaoObjetivo.size());
    }

    public static Double getDesvioPadrao(ArrayList<Double> funcaoObjetivo, Double media) {
        Double soma = 0.0;
        Double dif;
        for (Double fo : funcaoObjetivo) {
            // Subtrair a média de cada um dos valores
            dif = (fo - media);

            // Elevar ao quadrado dif e somar
            soma += Math.pow(dif, 2);
        }

        // Dividir a soma dos quadrados por (n-1)
        Double variancia = soma / (funcaoObjetivo.size() - 1);

        // Desvio padrão = raiz quadrada da variância
        return Math.sqrt(variancia);
    }
}
