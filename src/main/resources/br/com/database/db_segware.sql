
-- Cria o banco teste
CREATE DATABASE IF NOT EXISTS teste;

-- Cria a tabela atendente
CREATE TABLE IF NOT EXISTS teste.tbl_atendente (
id varchar(10) PRIMARY KEY,
nome varchar(50)
);

-- Cria a tabela cliente
CREATE TABLE IF NOT EXISTS teste.tbl_cliente (
id varchar(10) PRIMARY KEY,
nome varchar(50)
);

-- Cria a tabela ocorrencia
CREATE TABLE IF NOT EXISTS teste.tbl_ocorrencia (
dtinicio datetime,
dtfim datetime,
id integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
codigo_evento varchar(50),
tipo_evento varchar(50),
id_cliente varchar(10),
id_atendente varchar(10),
FOREIGN KEY(id_cliente) REFERENCES tbl_cliente (id),
FOREIGN KEY(id_atendente) REFERENCES tbl_atendente (id)
);

-- Insere os dados dos clientes
INSERT INTO teste.tbl_cliente (id, nome) VALUES ('0001','cliente1'),
('0002','cliente2'),
('0003','cliente3'),
('0004','cliente4'),
('0005','cliente5'),
('0006','cliente6');

-- Insere os dados dos atendentes
INSERT INTO teste.tbl_atendente (id, nome) VALUES ('AT01','atendente1'), ('AT02','atendente2');