#Apaga todas variáveis de testes anteriores
rm(list = ls())

#Seta o caminho
setwd("/home/daniel/NetBeansProjects/Rastrigin/dados/")

#Confere o caminho
getwd()

#Seta o arquivo que não tem cabeçalho e é separado por ;
dados <- read.csv2("execucao_rastrigin_8casos.csv", header = F)

#Renomeia as colunas na visualização
colnames(dados) <- c("Replicacao", "Caso", "MelhorResultado", "PiorResultado", "Media", "DesvioPadrao", "Tempo")

#Permite visualizar os dados
#View(dados)

#Filtra e define como um fator
dados$Caso[ dados$Caso == 1 ] <- "Caso1" #AG crossover 1pt
dados$Caso[ dados$Caso == 2 ] <- "Caso2" #AG crossover 2pt
dados$Caso[ dados$Caso == 3 ] <- "Caso3" #ES
dados$Caso[ dados$Caso == 4 ] <- "Caso4" #ES elitismo
dados$Caso[ dados$Caso == 5 ] <- "Caso5" #DE
dados$Caso[ dados$Caso == 6 ] <- "Caso6" #DE elitismo
dados$Caso[ dados$Caso == 7 ] <- "Caso7" #AED
dados$Caso[ dados$Caso == 8 ] <- "Caso8" #AED elitismo

#Transforma esses como elementos -> fatores (é como um casting)
dados$Caso <- as.factor(dados$Caso)
dados$MelhorResultado <- as.numeric(as.character(dados$MelhorResultado))
dados$PiorResultado <- as.numeric(as.character(dados$PiorResultado))
dados$Media <- as.numeric(as.character(dados$Media))
dados$DesvioPadrao <- as.numeric(as.character(dados$DesvioPadrao))

#Permite visualizar os dados
#View(dados)
write.csv(dados, "dados.csv") #Escreve no txt



#Resultados
#Caso 1
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso1" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso1" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso1" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso1" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso1" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso1" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso1" ])              #Média geral
df <- data.frame(Caso="1", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7) #Escreve na tabela

#Caso 2
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso2" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso2" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso2" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso2" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso2" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso2" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso2" ])              #Média geral
df <- rbind(df, data.frame(Caso="2", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

#Caso 3
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso3" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso3" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso3" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso3" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso3" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso3" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso3" ])              #Média geral
df <- rbind(df, data.frame(Caso="3", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

#Caso 4
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso4" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso4" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso4" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso4" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso4" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso4" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso4" ])              #Média geral
df <- rbind(df, data.frame(Caso="4", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

#Caso 5
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso5" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso5" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso5" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso5" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso5" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso5" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso5" ])              #Média geral
df <- rbind(df, data.frame(Caso="5", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

#Caso 6
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso6" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso6" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso6" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso6" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso6" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso6" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso6" ])              #Média geral
df <- rbind(df, data.frame(Caso="6", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

#Caso 7
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso7" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso7" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso7" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso7" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso7" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso7" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso7" ])              #Média geral
df <- rbind(df, data.frame(Caso="7", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

#Caso 8
r1 <- min(dados$MelhorResultado[ dados$Caso == "Caso8" ])     #Melhor resultado -> menor de todos
r2 <- max(dados$PiorResultado[ dados$Caso == "Caso8" ])       #Pior resultado -> maior de todos
r3 <- sd(dados$MelhorResultado[ dados$Caso == "Caso8" ])      #Desvio padrão dos melhores resultados
r4 <- sd(dados$PiorResultado[ dados$Caso == "Caso8" ])        #Desvio padrão dos piores resultados
r5 <- mean(dados$MelhorResultado[ dados$Caso == "Caso8" ])    #Média dos melhores resultados
r6 <- mean(dados$PiorResultado[ dados$Caso == "Caso8" ])      #Média dos piores resultados
r7 <- mean(dados$Media[ dados$Caso == "Caso8" ])              #Média geral
df <- rbind(df, data.frame(Caso="8", melhor=r1, pior=r2, dpmelhor=r3, dppior=r4, mediamelhor=r5, mediapior=r6, mediageral=r7)) #Escreve na tabela

write.csv(df, "resultados.csv") #Escreve no txt



#Boxplot
jpeg(filename = "MelhorResultado.jpg")
boxplot(MelhorResultado ~ Caso, data = dados)           #Melhor resultado em função do caso de teste
dev.off()

jpeg(filename = "PiorResultado.jpg")
boxplot(PiorResultado ~ Caso, data = dados)             #Pior resultado em função do caso de teste
dev.off()

jpeg(filename = "Media.jpg")
boxplot(Media ~ Caso, data = dados)                     #Media em função do caso de teste
dev.off()

jpeg(filename = "DesvioPadrao.jpg")
boxplot(DesvioPadrao ~ Caso, data = dados)              #Desvio padrão em função do caso de teste
dev.off()

jpeg(filename = "Tempo.jpg")
boxplot(Tempo ~ Caso, data = dados)                     #Tempo em função do caso de teste
dev.off()



#Teste t -> Compara se existe diferenças estatísticas entre os resultados
dadoscaso12 <- (dados[dados$Caso == "Caso1" | dados$Caso == "Caso2", ])  #Seleciona os dados com label Caso1 e Caso2
dadoscaso34 <- (dados[dados$Caso == "Caso3" | dados$Caso == "Caso4", ])  #Seleciona os dados com label Caso3 e Caso4
dadoscaso56 <- (dados[dados$Caso == "Caso5" | dados$Caso == "Caso6", ])  #Seleciona os dados com label Caso5 e Caso6
dadoscaso78 <- (dados[dados$Caso == "Caso7" | dados$Caso == "Caso8", ])  #Seleciona os dados com label Caso7 e Caso8

#Comparando 1 com 2
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso12)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso12, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso12, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso12)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso12, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso12, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso12)          
t8 <- t.test(Media ~ Caso, data = dadoscaso12, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso12, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso12)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso12, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso12, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso12)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso12, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso12, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- data.frame(Caso="1-2", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value))
dfp <- rbind(dfp, data.frame(Caso="1-2", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="1-2", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="1-2", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="1-2", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))

#Comparando 3 com 4
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso34)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso34, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso34, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso34)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso34, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso34, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso34)          
t8 <- t.test(Media ~ Caso, data = dadoscaso34, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso34, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso34)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso34, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso34, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso34)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso34, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso34, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- rbind(dfp, data.frame(Caso="3-4", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-4", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-4", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-4", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-4", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))

#Comparando 5 com 6
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso56)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso56, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso56, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso56)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso56, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso56, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso56)          
t8 <- t.test(Media ~ Caso, data = dadoscaso56, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso56, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso56)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso56, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso56, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso56)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso56, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso56, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- rbind(dfp, data.frame(Caso="5-6", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-6", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-6", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-6", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-6", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))

#Comparando 7 com 8
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso78)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso78, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso78, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso78)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso78, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso78, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso78)          
t8 <- t.test(Media ~ Caso, data = dadoscaso78, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso78, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso78)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso78, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso78, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso78)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso78, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso78, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- rbind(dfp, data.frame(Caso="7-8", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value)))
dfp <- rbind(dfp, data.frame(Caso="7-8", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="7-8", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="7-8", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="7-8", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))



#Permite visualizar os resultados de p-valor
#View(dfp)
write.csv(dfp, "pvalor1.csv") #Escreve no txt





dadoscaso23 <- (dados[dados$Caso == "Caso2" | dados$Caso == "Caso3", ])  #Seleciona os dados com label Caso2 e Caso3
dadoscaso57 <- (dados[dados$Caso == "Caso5" | dados$Caso == "Caso7", ])  #Seleciona os dados com label Caso5 e Caso7

#Comparando 2 com 3
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso23)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso23, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso23, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso23)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso23, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso23, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso23)          
t8 <- t.test(Media ~ Caso, data = dadoscaso23, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso23, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso23)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso23, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso23, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso23)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso23, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso23, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- data.frame(Caso="2-3", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value))
dfp <- rbind(dfp, data.frame(Caso="2-3", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="2-3", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="2-3", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="2-3", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))





#Comparando 5 com 7
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso57)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso57, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso57, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso57)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso57, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso57, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso57)          
t8 <- t.test(Media ~ Caso, data = dadoscaso57, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso57, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso57)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso57, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso57, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso57)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso57, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso57, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- rbind(dfp, data.frame(Caso="5-7", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-7", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-7", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-7", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="5-7", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))


write.csv(dfp, "pvalor2.csv") #Escreve no txt




dadoscaso37 <- (dados[dados$Caso == "Caso3" | dados$Caso == "Caso7", ])  #Seleciona os dados com label Caso3 e Caso7

#Comparando 3 com 7
t1 <- t.test(MelhorResultado ~ Caso, data = dadoscaso37)
t2 <- t.test(MelhorResultado ~ Caso, data = dadoscaso37, alternative='l')       #Media1 é menor que Media2
t3 <- t.test(MelhorResultado ~ Caso, data = dadoscaso37, alternative='g')       #Media1 é maior que Media2

t4 <- t.test(PiorResultado ~ Caso, data = dadoscaso37)
t5 <- t.test(PiorResultado ~ Caso, data = dadoscaso37, alternative='l')         #Media1 é menor que Media2
t6 <- t.test(PiorResultado ~ Caso, data = dadoscaso37, alternative='g')         #Media1 é maior que Media2

t7 <- t.test(Media ~ Caso, data = dadoscaso37)          
t8 <- t.test(Media ~ Caso, data = dadoscaso37, alternative='l')                 #Media1 é menor que Media2
t9 <- t.test(Media ~ Caso, data = dadoscaso37, alternative='g')                 #Media1 é maior que Media2

t10 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso37)
t11 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso37, alternative='l')          #Media1 é menor que Media2
t12 <- t.test(DesvioPadrao ~ Caso, data = dadoscaso37, alternative='g')          #Media1 é maior que Media2

t13 <- t.test(Tempo ~ Caso, data = dadoscaso37)
t14 <- t.test(Tempo ~ Caso, data = dadoscaso37, alternative='l')                 #Media1 é menor que Media2
t15 <- t.test(Tempo ~ Caso, data = dadoscaso37, alternative='g')                 #Media1 é maior que Media2

#Escreve na tabela
dfp <- data.frame(Caso="3-7", Tipo="MelhorResultado ~ Caso", pvalor=(t1$p.value), pvalorL=(t2$p.value), pvalorG=(t3$p.value))
dfp <- rbind(dfp, data.frame(Caso="3-7", Tipo="PiorResultado ~ Caso", pvalor=(t4$p.value), pvalorL=(t5$p.value), pvalorG=(t6$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-7", Tipo="Media ~ Caso", pvalor=(t7$p.value), pvalorL=(t8$p.value), pvalorG=(t9$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-7", Tipo="DesvioPadrao ~ Caso", pvalor=(t10$p.value), pvalorL=(t11$p.value), pvalorG=(t12$p.value)))
dfp <- rbind(dfp, data.frame(Caso="3-7", Tipo="Tempo ~ Caso", pvalor=(t13$p.value), pvalorL=(t14$p.value), pvalorG=(t15$p.value)))

write.csv(dfp, "pvalor3.csv") #Escreve no txt



#fileConn<-file("resultado_testet_12.txt")
#writeLines(c(as.character(t1$p.value)), fileConn)
#close(fileConn)
