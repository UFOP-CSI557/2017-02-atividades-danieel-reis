/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Representacao;

import Main.Main;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author daniel
 */
public class Populacao {

    private int tamanho;
    private Problema problema;
    private ArrayList<Individuo> individuos = new ArrayList<>();

    public Populacao(Problema problema, int tamanho) {
        this.tamanho = tamanho;
        this.problema = problema;
    }

    public int getTamanho() {
        return tamanho;
    }

    public Problema getProblema() {
        return problema;
    }

    public ArrayList<Individuo> getIndividuos() {
        return individuos;
    }

    public void setIndividuos(ArrayList<Individuo> individuos) {
        this.individuos = individuos;
    }

    // Criar a população inicial
    public void criar() {
        individuos = new ArrayList<>();

        for (int i = 0; i < this.getTamanho(); i++) {
            // Cria cada indivíduo
            if (Main.IMPRIMIR) {
                System.out.println("Indivíduo " + (i + 1) + "\t");
            }
            Individuo individuo = new Individuo(getProblema());
            individuo.criar();
            individuos.add(individuo);
        }
    }

    // Avalia a população
    public void avaliar() {
        for (Individuo individuo : this.getIndividuos()) {
            individuo.calcularFuncaoObjetivo();
        }
    }

    // Retorna o melhor indivíduo da população -> Melhor função objetivo (menor valor)
    public Individuo getMelhorIndividuo() {
        return Collections.min(individuos);
    }

    // Retorna o melhor indivíduo da população -> Melhor função objetivo (menor valor)
    public Individuo getPiorIndividuo() {
        return Collections.max(individuos);
    }

}
