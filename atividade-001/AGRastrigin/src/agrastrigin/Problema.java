/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agrastrigin;

/**
 *
 * @author daniel
 */
public class Problema {

    private Double min_intervalo;
    private Double max_intervalo;
    private int nvariaveis;

    public Problema(Double min_intervalo, Double max_intervalo, int nvariaveis) {
        this.min_intervalo = min_intervalo;
        this.max_intervalo = max_intervalo;
        this.nvariaveis = nvariaveis;
    }

    public Double getMin_intervalo() {
        return min_intervalo;
    }

    public void setMin_intervalo(Double min_intervalo) {
        this.min_intervalo = min_intervalo;
    }

    public Double getMax_intervalo() {
        return max_intervalo;
    }

    public void setMax_intervalo(Double max_intervalo) {
        this.max_intervalo = max_intervalo;
    }

    public int getNvariaveis() {
        return nvariaveis;
    }

    public void setNvariaveis(int nvariaveis) {
        this.nvariaveis = nvariaveis;
    }

    @Override
    public String toString() {
        return "Problema{" + "min_intervalo=" + min_intervalo + ", max_intervalo=" + max_intervalo + ", nvariaveis=" + nvariaveis + '}';
    }

}
