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
public class AGModificado {

    private ArrayList<Double> funcaoObjetivo;
    private Individuo melhorSolucao;
    private Individuo piorSolucao;

    private int tamPop; // Tamanho da população
    private double pCrossover; // Coeficiente de crossover
    private double pMutacao; // Coeficiente de mutação
    private int geracoes; // Critério de parada
    private Problema problema; // Problema
    private double pSelecionadas; // Coeficiente de selecionados

    public AGModificado(int tamPop, double pCrossover, double pMutacao, int geracoes, Problema problema, double pSelecionados) {
        this.tamPop = tamPop;
        this.pCrossover = pCrossover;
        this.pMutacao = pMutacao;
        this.geracoes = geracoes;
        this.problema = problema;
        this.pSelecionadas = pSelecionados;
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
        populacao.criar();

        // Avalia a população inicial
        populacao.avaliar();

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

        Random rnd = new Random();
        int pai1, pai2;

        // Será selecionado os x% melhores indivíduos da população
        int numPais = (int) (pSelecionadas * tamPop);

        // Gerações - Enquanto o critério de parada não for atingido...
        for (int gen = 1; gen <= geracoes; gen++) {

            // Selecionando os x% melhores indivíduos da população e remove
            ArrayList<Individuo> removidos = new ArrayList<>();
            try {
                for (int i = 0; i < numPais; i++) {
                    removidos.add((Individuo) melhorSolucao.clone());
                    populacao.getIndividuos().remove(melhorSolucao);
                    populacao.avaliar();
                }
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }

            // Adiciona todos indivíduos da populaçao a nova população
            novaPopulacao.getIndividuos().addAll(populacao.getIndividuos());

            // Reproduz pra gerar os (tamPop - numPais) selecionados pelo elitismo
            for (int i = 0; i < (tamPop - numPais); i++) {

                // Gera um número aleatório entre 0 e 1 -> Somente realiza o crossover se o valor gerado for menor que a taxa de crossover
                if (rnd.nextDouble() <= pCrossover) {

                    // Seleciona dois pais aleatórios -> diferentes
                    pai1 = rnd.nextInt((tamPop - numPais));
                    do {
                        pai2 = rnd.nextInt((tamPop - numPais));
                    } while (pai1 == pai2);

                    try {
                        // Copiar o pai1 nos descendente
                        Individuo descendente = (Individuo) populacao.getIndividuos().get(pai1).clone();

                        // Operar -> Crossover -> Insere no descendente parte do pai1 e parte do pai2
                        crossoverUmPonto(populacao.getIndividuos().get(pai1), populacao.getIndividuos().get(pai2), descendente);

                        // Operar -> Mutacao no descendente
                        mutacaoPorBit(descendente, problema.getMin_intervalo(), problema.getMax_intervalo(), pMutacao);

                        // Avalia o descendente criado
                        descendente.calcularFuncaoObjetivo();

                        // Adiciona a função objetivo do descendente gerado a lista de FO
                        funcaoObjetivo.add(descendente.getFuncaoObjetivo());

                        // Adiciona o novo indivíduo na população
                        novaPopulacao.getIndividuos().add(descendente);

                    } catch (CloneNotSupportedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            // Define os sobreviventes -> Primeiro: ordena os indivíduos da nova população pela função objetivo
            Collections.sort(novaPopulacao.getIndividuos());

            // Define os sobreviventes -> Segundo: seleciona apenas os x primeiros, mantendo o tamanho da população
            novaPopulacao.getIndividuos().subList(tamPop - numPais, novaPopulacao.getIndividuos().size()).clear();

            // Define os sobreviventes -> Terceiro: limpa a população
            populacao.getIndividuos().clear();

            // Define os sobreviventes -> Quarto: adiciona a elite na população
            populacao.getIndividuos().addAll(removidos);

            // Define os sobreviventes -> Quinto: adiciona a nova população na população
            populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());

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

    // Cria um filho que tem parte do pai1 e parte do pai2
    private void crossoverUmPonto(Individuo pai1, Individuo pai2, Individuo filho) {
        Random rnd = new Random();

        // Escolhe uma posição de corte no pai1
        int corte = rnd.nextInt(pai1.getCromossomos().size());

        // Limpa todos cromossomos do filho
        filho.getCromossomos().clear();

        // Insere no filho a parte esquerda a posição de corte no pai1
        filho.getCromossomos().addAll(pai1.getCromossomos().subList(0, corte));

        // Insere no filho a parte direita a posição de corte no pai2
        filho.getCromossomos().addAll(pai2.getCromossomos().subList(corte, pai2.getCromossomos().size()));
    }

    // Realiza a mutacao por Bit
    private void mutacaoPorBit(Individuo filho, Double min_intervalo, Double max_intervalo, Double pMutacao) {
        Random rnd = new Random();

        double valor;
        double p;
        Boolean r;
        // Tenta realizar a mutação sob cada cromossomo do indivíduo
        for (int i = 0; i < filho.getCromossomos().size(); ++i) {

            // Gera um número aleatório entre 0 e 1 -> Somente realiza a mutação se o valor gerado for menor que a taxa de mutação
            p = rnd.nextDouble();
            if (p <= pMutacao) {

                // Gera um número aleatório entre 0 e 1 e multiplica pelo valor do cromossomo
                valor = filho.getCromossomos().get(i) * rnd.nextDouble();

                // Gera um valor aleatório (V/F) -> Possibilita a chance de criar um valor negativo ou não
                r = rnd.nextBoolean();
                if (r == false) {
                    valor = -valor;
                }

                // Soma o valor gerado ao cromossomo em questão
                valor += filho.getCromossomos().get(i);

                // Se o valor final estiver dentro do intervalo, realiza a mutação
                if (valor >= min_intervalo && valor <= max_intervalo) {
                    filho.getCromossomos().set(i, valor);
                }
            }
        }
    }

}
