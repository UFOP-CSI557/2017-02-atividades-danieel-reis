/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo;

import Main.Main;
import Representacao.BuscaLocal;
import Representacao.Individuo;
import Representacao.Operacoes;
import Representacao.Populacao;
import Representacao.Problema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author daniel
 */
public class Novo {

    private ArrayList<Double> funcaoObjetivo;
    private Individuo melhorSolucao;
    private Individuo piorSolucao;

    private int tamPop; // Tamanho da população
    private double pCrossover; // Coeficiente de crossover
    private double pMutacao; // Coeficiente de mutação
    private Double pBuscaLocal;
    private int geracoes; // Critério de parada
    private Problema problema; // Problema
    private double pSelecionados; // Coeficiente de selecionados

    private Populacao populacao;
    private Populacao novaPopulacao;

    public Novo(int tamPop, double pCrossover, double pMutacao, Double pBuscaLocal, int geracoes, Problema problema, double pSelecionados) {
        this.tamPop = tamPop;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.pBuscaLocal = pBuscaLocal;
        this.geracoes = geracoes;
        this.problema = problema;
        this.pSelecionados = pSelecionados;
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

        this.populacao = new Populacao(tamPop, problema);
        this.novaPopulacao = new Populacao(tamPop, problema);

        // Geração da população inicial
        this.populacao.criar();

        // Avaliar a população inicial
        this.populacao.avaliar();

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

        // Informação sobre a solução inicial
        if (Main.IMPRIMIR) {
            System.out.println("\nSolução inicial: " + getMelhorSolucao().getFuncaoObjetivo());
        }

        Random rnd = new Random();
        int pai1, pai2;

        // Será selecionado os x% melhores indivíduos da população
        int numPais = (int) (pSelecionados * tamPop);

        // Gerações - Enquanto o critério de parada não for atingido...
        for (int gen = 1; gen <= geracoes; gen++) {

            // Selecionando os x% melhores indivíduos da população e move para a nova população
            ArrayList<Individuo> melhores = new ArrayList<>();

            for (int i = 0; i < numPais; i++) {
                melhores.add(melhorSolucao);
                populacao.getIndividuos().remove(melhorSolucao);
                populacao.avaliar();
            }

            novaPopulacao.getIndividuos().addAll(melhores);

            // Reproduz pra gerar os (tamPop - numPais) selecionados
            for (int i = 0; i < (tamPop - numPais); i++) {

                Individuo descendente1, descendente2, descendente3;

                try {

                    // Gera um número aleatório entre 0 e 1 -> Somente realiza o crossover se o valor gerado for menor que a taxa de crossover
                    if (rnd.nextDouble() <= pCrossover) {

                        // Seleciona dois pais aleatórios -> diferentes
                        pai1 = rnd.nextInt(numPais);
                        do {
                            pai2 = rnd.nextInt(numPais);
                        } while (pai1 == pai2);

                        descendente1 = new Individuo(problema.getDimensao());
                        descendente2 = new Individuo(problema.getDimensao());

                        // Operar -> Crossover -> Insere no descendente parte do pai1 e parte do pai2
                        Operacoes.crossoverOX(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), descendente1, descendente2, problema);

                        // Operar -> Mutacao no descendente
                        Operacoes.mutacaoSWAP(descendente1, problema, pMutacao);

                        // Avalia o descendente criado
                        problema.calcularFuncaoObjetivo(descendente1);
                        problema.calcularFuncaoObjetivo(descendente2);
                        
                        // Busca local
                        if (rnd.nextDouble() <= pBuscaLocal) {
                            BuscaLocal.buscaLocal(descendente1, problema);
                        }
                        if (rnd.nextDouble() <= pBuscaLocal) {
                            BuscaLocal.buscaLocal(descendente2, problema);
                        }

                        // Adiciona a função objetivo do descendente gerado a lista de FO
                        funcaoObjetivo.add(descendente1.getFuncaoObjetivo());
                        funcaoObjetivo.add(descendente2.getFuncaoObjetivo());

                        // Adiciona o novo indivíduo na população
                        novaPopulacao.getIndividuos().add(descendente1);
                        novaPopulacao.getIndividuos().add(descendente2);
                    }

                    if (rnd.nextDouble() >= pMutacao) {

                        // Seleciona pais aleatório
                        pai1 = rnd.nextInt(tamPop - numPais);

                        // Insere os dados do pai no descendente
                        descendente3 = (Individuo) populacao.getIndividuos().get(pai1).clone();

                        // Operar -> Mutacao no descendente
                        Operacoes.mutacaoSWAP(descendente3, problema, pMutacao);

                        // Avalia o descendente criado
                        problema.calcularFuncaoObjetivo(descendente3);

                        if (rnd.nextDouble() <= pBuscaLocal) {
                            BuscaLocal.buscaLocal(descendente3, problema);
                        }
                        
                        // Adiciona a função objetivo do descendente gerado a lista de FO
                        funcaoObjetivo.add(descendente3.getFuncaoObjetivo());

                        // Adiciona o novo indivíduo na população
                        novaPopulacao.getIndividuos().add(descendente3);
                    }

                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
            }

            // Define os sobreviventes -> Primeiro: adiciona todos indivíduos da novaPop na pop
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

            // Define os sobreviventes -> Segundo: ordena os indivíduos da população pela função objetivo
            Collections.sort(populacao.getIndividuos());

            // Define os sobreviventes -> Terceiro: seleciona apenas os x primeiros, mantendo o tamanho da população
            populacao.getIndividuos().subList(tamPop, populacao.getIndividuos().size()).clear();

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

            Double FO = this.getMelhorSolucao().getFuncaoObjetivo();
            if (Main.IMPRIMIR) {
                if (gen < 10) {
                    System.out.println("Gen = " + gen + "\t\tFO = " + FO + " "
                            + "\tPop = " + populacao.getIndividuos().size()
                            + "\tGAP = " + Operacoes.gap(Main.RL, FO));
                } else {
                    System.out.println("Gen = " + gen + "\tFO = " + FO
                            + " \tPop = " + populacao.getIndividuos().size()
                            + "\tGAP = " + Operacoes.gap(Main.RL, FO));
                }
            }
        }

    }
}
