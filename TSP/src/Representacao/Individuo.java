package Representacao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author daniel
 */
public class Individuo implements Comparable<Individuo>{
    
    private ArrayList<Integer> cromossomos; // Representação - Define a ordem de visitação das cidades
    private Double funcaoObjetivo; // Custo associado a cada indivíduo -> Distância entre as cidades
    private int dimensao; // Quantidade de cidades
    
    public Individuo(int dimensao) {
        this.dimensao = dimensao;
        this.cromossomos = new ArrayList<>();
    }

    public ArrayList<Integer> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Integer> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    public int getDimensao() {
        return dimensao;
    }

    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }
    
    
    // Criação dos indivíduos
    public void criar() {
        this.cromossomos = new ArrayList<>();
        for (int i = 1; i <= this.dimensao; i++) {
            this.cromossomos.add(i);
        }
        Collections.shuffle(this.cromossomos);
        
    }

    @Override
    public String toString() {
        return "Individuo{" + "cromossomos=" + cromossomos + ", funcaoObjetivo=" + funcaoObjetivo + ", dimensao=" + dimensao + '}';
    }
    
    @Override
    public int compareTo(Individuo t) {
        return this.getFuncaoObjetivo().compareTo(t.getFuncaoObjetivo());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Individuo individuo = new Individuo(this.getDimensao());
        individuo.setCromossomos(new ArrayList<>(this.getCromossomos()));
        individuo.setFuncaoObjetivo(this.getFuncaoObjetivo());
        return individuo;
    }
}
