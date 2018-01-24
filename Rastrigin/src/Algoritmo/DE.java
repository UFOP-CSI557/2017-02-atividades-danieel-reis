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
public class DE {

    private ArrayList<Double> funcaoObjetivo;
    private Individuo melhorSolucao;
    private Individuo piorSolucao;

    private int tamPop; // Tamanho da população
    private double pCrossover; // Coeficiente de crossover
    private double pMutacao; // Coeficiente de mutação
    private int geracoes; // Critério de parada
    private Problema problema; // Problema
    private double elitismo; // Coeficiente de elitismo

    public DE(int Np, double Cr, double F, int gmax, Problema problema, double elitismo) {
        this.tamPop = Np;
        this.pCrossover = Cr;
        this.pMutacao = F;
        this.geracoes = gmax;
        this.problema = problema;
        this.elitismo = elitismo;
        this.funcaoObjetivo = new ArrayList<>();
    }

    public DE(int Np, double Cr, double F, int gmax, Problema problema) {
        this.tamPop = Np;
        this.pCrossover = Cr;
        this.pMutacao = F;
        this.geracoes = gmax;
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
            
            // Reproduz pra gerar os (tamPop - numPais) selecionados pelo elitismo
            for (int i = 0; i < (tamPop - numPais); ++i) {

                // Selecionar aleatoriamente r0, r1, r2
                int r0 = rnd.nextInt(tamPop - numPais);
                int r1, r2;
                do {
                    r1 = rnd.nextInt(tamPop - numPais);
                } while (r1 == r0);

                do {
                    r2 = rnd.nextInt(tamPop - numPais);
                } while (r2 == r1 || r2 == r0);

                // Cria um novo indivíduo para realizar uma tentativa
                Individuo trial = new Individuo(problema);
                trial.criar();
                trial.getCromossomos().clear();

                // Seleciona três indivíduos aleatórios
                Individuo xr0 = (Individuo) populacao.getIndividuos().get(r0);
                Individuo xr1 = (Individuo) populacao.getIndividuos().get(r1);
                Individuo xr2 = (Individuo) populacao.getIndividuos().get(r2);

                // Analisa a perturbação entre dois desses indivíduos aleatórios e insere no trial a diferença entre xr1 e xr2
                perturbacao(trial, xr1, xr2);

                // Mutação - Multiplica trial pelo fator de mutação e soma a xro, preenchendo o valor resultante em trial
                mutacao(trial, xr0);

                // Pega o indivíduo target (alvo)
                Individuo target = (Individuo) populacao.getIndividuos().get(i);

                // Crossover - Combina o trial com o target, ou seja, a tentativa com o alvo
                crossover(trial, target);

                // Selecao - Pega o que tiver maior FO entre trial e target
                trial.calcularFuncaoObjetivo();

                try {
                    if (trial.getFuncaoObjetivo() >= target.getFuncaoObjetivo()) {
                        novaPopulacao.getIndividuos().add((Individuo) trial.clone());

                        // Adiciona a função objetivo do descendente gerado
                        funcaoObjetivo.add(trial.getFuncaoObjetivo());
                    } else {
                        novaPopulacao.getIndividuos().add((Individuo) target.clone());

                        // Adiciona a função objetivo do descendente gerado
                        funcaoObjetivo.add(target.getFuncaoObjetivo());
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

            if (Main.IMPRIMIR) {
                if (gen < 10) {
                    System.out.println("Gen = " + gen + "\t\tFO = " + getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                } else {
                    System.out.println("Gen = " + gen + "\tFO = " + getMelhorSolucao().getFuncaoObjetivo() + "\tPop = " + populacao.getIndividuos().size());
                }
            }
        }
    }

    private void perturbacao(Individuo trial, Individuo xr1, Individuo xr2) {
        // Diferença entre r1 e r2
        for (int i = 0; i < problema.getNvariaveis(); i++) {
            double diferenca = xr1.getCromossomos().get(i) - xr2.getCromossomos().get(i);
            trial.getCromossomos().add(diferenca);
        }
    }

    private void mutacao(Individuo trial, Individuo xr0) {
        // Multiplicar por F a diferença e somar com Xr0
        for (int i = 0; i < problema.getNvariaveis(); i++) {
            double valor = xr0.getCromossomos().get(i) + pMutacao * (trial.getCromossomos().get(i));
            trial.getCromossomos().set(i, valor);
        }
    }

    private void crossover(Individuo trial, Individuo target) {
        Random rnd = new Random();
        int jRand = rnd.nextInt(problema.getNvariaveis());

        for (int i = 0; i < problema.getNvariaveis(); i++) {
            if (!(rnd.nextDouble() <= pCrossover || i == jRand)) {
                trial.getCromossomos().set(i, target.getCromossomos().get(i));
            }
        }
    }
}
