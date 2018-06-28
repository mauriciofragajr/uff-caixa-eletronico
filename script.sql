CREATE TABLE CONTA (CPF VARCHAR(11) NOT NULL, SALDO INTEGER NOT NULL, PRIMARY KEY (CPF));
CREATE TABLE MOVIMENTACAO (TIPO VARCHAR(20) NOT NULL, VALOR INTEGER NOT NULL, CRIADO_EM TIMESTAMP NOT NULL, CONTA_ORIGEM VARCHAR(11), CONTA_DESTINO VARCHAR(11));
CREATE TABLE USUARIO (CPF VARCHAR(11) NOT NULL, NOME VARCHAR(100), SENHA VARCHAR(6) NOT NULL, PRIMARY KEY (CPF));

INSERT INTO MAURICIO.USUARIO (CPF, NOME, SENHA) 
	VALUES ('11111111111', 'Fulano da Silva', '123456');
INSERT INTO MAURICIO.USUARIO (CPF, NOME, SENHA) 
	VALUES ('12345678910', 'Rodrigo Santos', '123456');

INSERT INTO MAURICIO.CONTA (CPF, SALDO) 
	VALUES ('13306624748', 0);
INSERT INTO MAURICIO.CONTA (CPF, SALDO) 
	VALUES ('11111111111', 1358039);

INSERT INTO MAURICIO.MOVIMENTACAO (TIPO, VALOR, CRIADO_EM, CONTA_ORIGEM, CONTA_DESTINO) 
	VALUES ('DEPOSITO', 10000, '2018-06-24 18:11:33.0', '12345678910', NULL);
INSERT INTO MAURICIO.MOVIMENTACAO (TIPO, VALOR, CRIADO_EM, CONTA_ORIGEM, CONTA_DESTINO) 
	VALUES ('DEPOSITO', 1500000, '2018-06-28 19:18:18.0', '12345678910', NULL);
INSERT INTO MAURICIO.MOVIMENTACAO (TIPO, VALOR, CRIADO_EM, CONTA_ORIGEM, CONTA_DESTINO) 
	VALUES ('TRANSFERENCIA', 1400000, '2018-06-28 19:20:54.0', '12345678910', '11111111111');
INSERT INTO MAURICIO.MOVIMENTACAO (TIPO, VALOR, CRIADO_EM, CONTA_ORIGEM, CONTA_DESTINO) 
	VALUES ('SAQUE', 50000, '2018-06-28 19:21:39.0', '11111111111', NULL);
INSERT INTO MAURICIO.MOVIMENTACAO (TIPO, VALOR, CRIADO_EM, CONTA_ORIGEM, CONTA_DESTINO) 
	VALUES ('DEPOSITO', 8039, '2018-06-28 19:22:13.0', '11111111111', NULL);