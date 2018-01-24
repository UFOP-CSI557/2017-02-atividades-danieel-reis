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
public class ES {

    private ArrayList<Double> funcaoObjetivo;
    private Individuo melhorSolucao;
    private Individuo piorSolucao;

    private int tamPop; // Tamanho da população
    private double pMutacao; // Coeficiente de mutação
    private int geracoes; // Critério de parada
    private Problema problema; // Problema
    private double elitismo; // Coeficiente de elitismo
    private int mu;
    private int lambda;

    public ES(int tamPop, double pMutacao, int mu, int lambda, int geracoes, Problema problema, double elitismo) {
        this.tamPop = tamPop;
        this.pMutacao = pMutacao;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.problema = problema;
        this.elitismo = elitismo;
        this.funcaoObjetivo = new ArrayList<>();
    }

    public ES(int tamPop, double pMutacao, int mu, int lambda, int geracoes, Problema problema) {
        this.tamPop = tamPop;
        this.pMutacao = pMutacao;
        this.mu = mu;
        this.lambda = lambda;
        this.geracoes = geracoes;
        this.problema = problema;
        this.elitismo = 0;
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

        // Será selecionado os x% melhores indivíduos da população
        int numPais = (int) (elitismo * tamPop);

        // Gerações - Enquanto o critério de parada não for atingido...
        for (int gen = 1; gen <= geracoes; gen++) {

            // Selecionando os x% melhores indivíduos da população e move para a nova população
            ArrayList<Individuo> pais = new ArrayList<>();

            for (int i = 0; i < numPais; i++) {
                pais.add(melhorSolucao);
                populacao.getIndividuos().remove(melhorSolucao);
                populacao.avaliar();
            }

            novaPopulacao.getIndividuos().addAll(pais);

            // Para cada pai, gerar lambda/mu filhos - Reproduz pra gerar os (tamPop - numPais) selecionados pelo elitismo     
            for (int p = 0; p < (tamPop - numPais); ++p) {
                for (int i = 0; i < lambda / mu; i++) {
                    // Gera um número aleatório entre 0 e 1 -> Somente realiza a mutação se o valor gerado for menor que a taxa de mutação
                    double valor = rnd.nextDouble();
                    if (valor <= this.pMutacao) {
                        try {
                            // Insere os dados do pai no descendente
                            Individuo descendente = (Individuo) populacao.getIndividuos().get(p).clone();

                            // Operar -> Mutacao no descendente
                            mutacaoPorBit(descendente, problema.getMin_intervalo(), problema.getMax_intervalo(), pMutacao);

                            // Avalia o descendente criado
                            descendente.calcularFuncaoObjetivo();

                            // Adiciona a função objetivo do descendente gerado a lista de FO
                            funcaoObjetivo.add(descendente.getFuncaoObjetivo());

                            // Adiciona o novo indivíduo na população
                            novaPopulacao.getIndividuos().add(descendente);
                            
                            // Busca local
//                            BuscaLocalCombinatorio.buscaLocal(descendente);

                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }
                    }
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

            if (Main.IMPRIMIR) {
                if (gen < 10) {
                    System.out.println("Gen = " + gen + "\t\tFO = " + getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                } else {
                    System.out.println("Gen = " + gen + "\tFO = " + getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                }
            }
        }

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
