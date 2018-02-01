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

    // Cria um indivíduo com um valor binário
    public void criarBinario() {
        this.cromossomos = new ArrayList<>();
        double valor;
        for (int i = 0; i < problema.getNvariaveis(); i++) {

            // Gera um número entre 0 e 1
            if (Math.random() <= 0.5) {
                valor = 0.0;
            } else {
                valor = 1.0;
            }

            // Insere o valor
            this.getCromossomos().add(valor);

            if (Main.IMPRIMIR) {
                if (valor > 0.0) {
                    System.out.printf("0 ", valor);
                } else {
                    System.out.printf("1 ", valor);
                }
            }
            if (Main.IMPRIMIR) {
                System.out.println();
            }
        }
    }

    // Decodifica indivíduo numa representação com tal precisão
    public Individuo decodificarBinario(int precisao) {
        Individuo ind = new Individuo(problema);

        // nvar = número de variáveis / precisão
        int nvar = this.getProblema().getNvariaveis() / precisao;

        // Pega o máximo e o mínimo
        double max = this.getProblema().getMax_intervalo();
        double min = this.getProblema().getMin_intervalo();

        int valor;
        double real;

        // Transforma o valor binário para um número pertencente ao intervalo
        for (int i = 0; i < nvar; i++) {
            valor = 0;

            for (int j = 0; j < precisao; j++) {
                valor += Math.pow(2, precisao - j - 1) * this.getCromossomos().get(i * precisao + j);
            }

            real = valor * (max - min) / (Math.pow(2, precisao) - 1) + min;
            ind.getCromossomos().add(real);
        }
        return ind;
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
    // Converte o ângulo de graus para radiano -> Função Math.cos exige como entrada o ângulo em radiano

    private Double converterGrausParaRadiano(Double graus) {
        return (Math.PI / 180) * graus;
    }

    // Calcula a função objetivo -> Fórmula (Função de avaliação)
    public void calcularFuncaoObjetivo() {
        int n = getProblema().getNvariaveis();

        Double somatorio = 0.0;
        for (int i = 0; i < n; i++) {
            Double xi = getCromossomos().get(i);
            somatorio += Math.pow(xi, 2) - 10 * Math.cos(converterGrausParaRadiano(2 * Math.PI * xi));
        }

        Double funcao = 10 * n + somatorio;
        this.setFuncaoObjetivo(funcao);
    }

    // Calcula a função objetivo -> Fórmula (Função de avaliação)
    public void calcularFuncaoObjetivoBinario(int precisao) {
        // Decodifica o indivíduo
        Individuo ind_decodificado = ((Individuo) this).decodificarBinario(precisao);

        // Tamanho = n / precisao
        int n = getProblema().getNvariaveis() / precisao;

        // Calcula FO so indivíduo decodificado
        Double somatorio = 0.0;
        for (int i = 0; i < n; i++) {
            Double xi = ind_decodificado.getCromossomos().get(i);
            somatorio += Math.pow(xi, 2) - 10 * Math.cos(converterGrausParaRadiano(2 * Math.PI * xi));
        }

        // Insere a FO do indíviduo
        Double funcao = 10 * n + somatorio;
        this.setFuncaoObjetivo(funcao);
    }

}
