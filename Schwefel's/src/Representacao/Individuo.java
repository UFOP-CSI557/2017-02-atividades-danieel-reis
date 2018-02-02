/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Representacao;

import Main.Main;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class Individuo implements Comparable<Individuo> {

    private ArrayList<Double> cromossomos;
    private Double funcaoObjetivo;
    private Problema problema;

    public Individuo(Problema problema) {
        this.problema = problema;
        this.cromossomos = new ArrayList<>();
    }

    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public Problema getProblema() {
        return problema;
    }

    public ArrayList<Double> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Double> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    // Cria um indivíduo com um valor entre o mínimo e o máximo do problema
    public void criar() {
        Double min = problema.getMin_intervalo();
        Double max = problema.getMax_intervalo();
        Double valor;

        for (int i = 0; i < problema.getNvariaveis(); i++) {
            // Gera um número entre 0 e 1 multiplicado pelo tamanho do intervalo começando de min
            valor = Math.random() * (max - min) + min;
            this.cromossomos.add(valor);

            // Imprime os cromossomos do indivíduo gerado
            if (Main.IMPRIMIR) {
                if (valor > 0) {
                    System.out.printf(" %.2f ", valor);
                } else {
                    System.out.printf("%.2f ", valor);
                }
            }
        }
        if (Main.IMPRIMIR) {
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return "Individuo{" + "cromossomos=" + cromossomos + ", funcaoObjetivo=" + funcaoObjetivo + ", problema=" + problema + '}';
    }

    // Ordena os indivíduo de acordo com a função objetivo
    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo().compareTo(o.getFuncaoObjetivo());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Individuo individuo = new Individuo(this.getProblema());
        individuo.setCromossomos(new ArrayList<>(this.getCromossomos()));
        individuo.setFuncaoObjetivo(this.getFuncaoObjetivo());
        return individuo;
    }

    // Calcula a função objetivo -> Fórmula (Função de avaliação)
    public void calcularFuncaoObjetivo() {
        Double somatorio = 0.0;

        int n = getProblema().getNvariaveis();
        
        for (int i = 0; i < n; i++) {
            Double xi = getCromossomos().get(i);
            somatorio += -xi * Math.sin(Math.sqrt(Math.abs(xi)));
        }

        this.setFuncaoObjetivo(somatorio);
    }

}
