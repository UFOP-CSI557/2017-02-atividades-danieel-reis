/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo;

import Main.Main;
import Representacao.Individuo;
import Representacao.Populacao;
import Representacao.Problema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class AED {

    private ArrayList<Double> funcaoObjetivo;
    private Individuo melhorSolucao;
    private Individuo piorSolucao;

    private int tamPop; // Tamanho da população
    private Problema problema; // Problema
    private int precisao; // Precisão do problema
    private int geracoes; // Critério de parada
    private int numBest; // Número de melhores
    private int numEst;

    public AED(int tamPop, int geracoes, Problema problema, int precisao, int numBest, int numEst) {
        this.tamPop = tamPop;
        this.geracoes = geracoes;
        this.problema = problema;
        this.precisao = precisao;
        this.numBest = numBest;
        this.numEst = numEst;
        this.funcaoObjetivo = new ArrayList<>();
    }

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    private void setMelhorSolucao(Individuo melhorSolucao) {
        this.melhorSolucao = melhorSolucao;
    }

    public Individuo getPiorSolucao() {
        return piorSolucao;
    }

    private void setPiorSolucao(Individuo piorSolucao) {
        this.piorSolucao = piorSolucao;
    }

    public ArrayList<Double> getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(ArrayList<Double> funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    // Executa o algoritmo
    public void executar() {
        Populacao populacao = new Populacao(problema, tamPop);
        Populacao novaPopulacao = new Populacao(problema, tamPop);

        // Cria a população
        populacao.criarBinario();

        // Avalia a população inicial
        populacao.avaliarBinario(precisao);

        try {
            // Recupera o melhor individuo
            melhorSolucao = (Individuo) populacao.getMelhorIndividuo().clone();

            // Recupera o pior individuo
            piorSolucao = (Individuo) populacao.getPiorIndividuo().clone();

            // Guarda os valores das funções objetivo
            for (Individuo i : populacao.getIndividuos()) {
                funcaoObjetivo.add(i.getFuncaoObjetivo());
            }

        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }

        // Imprime a solução inicial
        if (Main.IMPRIMIR) {
            System.out.println("\nSolução inicial: " + melhorSolucao.getFuncaoObjetivo());
        }

        // Gerações - Enquanto o critério de parada não for atingido...
        for (int gen = 1; gen <= geracoes; gen++) {
            double proporcao[] = new double[problema.getNvariaveis()];

            // Selecionar melhores -> numBest (percentual)
            for (int p = 0; p < problema.getNvariaveis(); p++) { // Percorrer cada posicao
                // Calcula a quantidade de 1 encontrada -> analisa cada cromossomos de todos juntos
                int soma = 0;
                for (int b = 0; b < this.numBest; b++) { // Percorrer cada individuo
                    if (populacao.getIndividuos().get(b).getCromossomos().get(p) == 1) {
                        soma++;
                    }
                }

                // Definir as proporçõees em relação a estes melhores)
                proporcao[p] = (double) soma / this.numBest;
            }

            Random rnd = new Random();

            // Limpa a população
            novaPopulacao.getIndividuos().clear();
            
            // Estimar (gerar soluções considerando a proporção)
            for (int i = 0; i < this.numEst; i++) {
                Individuo descendente = new Individuo(problema);

                // Gera todos cromossomos do indivíduo de acordo com a proporção
                for (int p = 0; p < problema.getNvariaveis(); p++) {
                    if (rnd.nextDouble() <= proporcao[p]) {
                        descendente.getCromossomos().add(1.0);
                    } else {
                        descendente.getCromossomos().add(0.0);
                    }
                }

                // Calcular FO do individuo descendente
                descendente.calcularFuncaoObjetivoBinario(precisao);

                // Adiciona o individuo a nova população
                novaPopulacao.getIndividuos().add(descendente);

                // Adiciona a função objetivo do descendente gerado a lista de FO
                funcaoObjetivo.add(descendente.getFuncaoObjetivo());
            }

            // Define os sobreviventes -> Primeiro: adiciona todos individuos da nova populacao (pop + novaPop)
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            // Define os sobreviventes -> Segundo: ordena a populacao pela FO
            Collections.sort(populacao.getIndividuos());

            // Define os sobreviventes -> Terceiro: corta em tamPop
            populacao.getIndividuos().subList(this.tamPop, populacao.getIndividuos().size()).clear();

            try {
                // Atualiza a variável que armazena o melhor indivíduo da população
                if (populacao.getMelhorIndividuo().getFuncaoObjetivo() < getMelhorSolucao().getFuncaoObjetivo()) {
                    setMelhorSolucao((Individuo) populacao.getMelhorIndividuo().clone());
                }

                // Atualiza a variável que armazena o pior indivíduo da população
                if (populacao.getPiorIndividuo().getFuncaoObjetivo() > getPiorSolucao().getFuncaoObjetivo()) {
                    setPiorSolucao((Individuo) populacao.getPiorIndividuo().clone());
                }

            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }

            if (Main.IMPRIMIR) {
                if (gen < 10) {
                    System.out.println("Gen = " + gen + "\t\tFO = " + getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                } else {
                    System.out.println("Gen = " + gen + "\tFO = " + getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                }
            }
        }

    }
}
