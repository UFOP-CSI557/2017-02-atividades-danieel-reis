#Apaga todas variáveis de testes anteriores
rm(list = ls())

#Seta o caminho
setwd("/home/daniel/CE/TP1/")

#Confere o caminho
getwd()

#Seta o arquivo que não tem cabeçalho e é separado por ;
dados <- read.csv2("execucao_Rastrigin.csv", header = F)

#Renomeia as colunas na visualização
colnames(dados) <- c("Replicacao", "Caso", "MelhorResultado", "PiorResultado", "Media", "DesvioPadrao", "Tempo")

#Permite visualizar os dados
View(dados)

#Filtra e define como um fator -> substitui 1 por Caso1 e 2 por Caso2
dados$Caso[ dados$Caso == 1 ] <- "Caso1"
dados$Caso[ dados$Caso == 2 ] <- "Caso2"

#Transforma esses como elementos -> fatores (é como um casting)
dados$Caso <- as.factor(dados$Caso)
dados$MelhorResultado <- as.numeric(as.character(dados$MelhorResultado))
dados$PiorResultado <- as.numeric(as.character(dados$PiorResultado))
dados$Media <- as.numeric(as.character(dados$Media))
dados$DesvioPadrao <- as.numeric(as.character(dados$DesvioPadrao))





#Resultados
#Caso 1
min(dados$MelhorResultado[ dados$Caso == "Caso1" ])     #Melhor resultado -> menor de todos
max(dados$PiorResultado[ dados$Caso == "Caso1" ])       #Pior resultado -> maior de todos
sd(dados$MelhorResultado[ dados$Caso == "Caso1" ])      #Desvio padrão dos melhores resultados
sd(dados$PiorResultado[ dados$Caso == "Caso1" ])        #Desvio padrão dos piores resultados
mean(dados$MelhorResultado[ dados$Caso == "Caso1" ])    #Média dos melhores resultados
mean(dados$PiorResultado[ dados$Caso == "Caso1" ])      #Média dos piores resultados
mean(dados$Media[ dados$Caso == "Caso1" ])              #Média geral
sd(dados$DesvioPadrao[ dados$Caso == "Caso1" ])         #Desvio padrão dos desvios padrões (geral) -> Diferença entre as execuções
mean(dados$DesvioPadrao[ dados$Caso == "Caso1" ])       #Média entre os desvios padrões

#Caso 2
min(dados$MelhorResultado[ dados$Caso == "Caso2" ])     #Melhor resultado é o menor de todos 
max(dados$PiorResultado[ dados$Caso == "Caso2" ])       #Pior resultado é o maior de todos
sd(dados$MelhorResultado[ dados$Caso == "Caso2" ])      #Desvio padrão dos melhores resultados
sd(dados$PiorResultado[ dados$Caso == "Caso2" ])        #Desvio padrão dos piores resultados
mean(dados$MelhorResultado[ dados$Caso == "Caso2" ])    #Média dos melhores resultados
mean(dados$PiorResultado[ dados$Caso == "Caso2" ])      #Média dos piores resultados
mean(dados$Media[ dados$Caso == "Caso2" ])              #Média geral
sd(dados$DesvioPadrao[ dados$Caso == "Caso2" ])         #Desvio padrão dos desvios padrões (geral) -> Diferença entre as execuções
mean(dados$DesvioPadrao[ dados$Caso == "Caso2" ])       #Média entre os desvios padrões





#Boxplot
boxplot(MelhorResultado ~ Caso, data = dados)           #Melhor resultado em função do caso de teste
boxplot(PiorResultado ~ Caso, data = dados)             #Pior resultado em função do caso de teste
boxplot(Media ~ Caso, data = dados)                     #Media em função do caso de teste
boxplot(DesvioPadrao ~ Caso, data = dados)              #Desvio padrão em função do caso de teste
boxplot(Tempo ~ Caso, data = dados)                     #Tempo em função do caso de teste





#Teste t -> Compara se existe diferenças estatísticas entre os resultados
t.test(MelhorResultado ~ Caso, data = dados)
t.test(MelhorResultado ~ Caso, data = dados, alternative='l')       #Media1 é menor que Media2
t.test(MelhorResultado ~ Caso, data = dados, alternative='g')       #Media1 é maior que Media2

t.test(PiorResultado ~ Caso, data = dados)
t.test(PiorResultado ~ Caso, data = dados, alternative='l')         #Media1 é menor que Media2
t.test(PiorResultado ~ Caso, data = dados, alternative='g')         #Media1 é maior que Media2

t.test(Media ~ Caso, data = dados)          
t.test(Media ~ Caso, data = dados, alternative='l')                 #Media1 é menor que Media2
t.test(Media ~ Caso, data = dados, alternative='g')                 #Media1 é maior que Media2

t.test(DesvioPadrao ~ Caso, data = dados)
t.test(DesvioPadrao ~ Caso, data = dados, alternative='l')          #Media1 é menor que Media2
t.test(DesvioPadrao ~ Caso, data = dados, alternative='g')          #Media1 é maior que Media2

t.test(Tempo ~ Caso, data = dados)
t.test(Tempo ~ Caso, data = dados, alternative='l')                 #Media1 é menor que Media2
t.test(Tempo ~ Caso, data = dados, alternative='g')                 #Media1 é maior que Media2
