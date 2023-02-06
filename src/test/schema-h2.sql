CREATE TABLE 'cartao' (
     'id' INT(11) NOT NULL AUTO_INCREMENT,
     'numeroCartao' VARCHAR(255) NULL DEFAULT NULL,
     'senhaCartao' VARCHAR(255) NULL DEFAULT NULL,
     'valor' INT(11) NULL DEFAULT NULL,
     PRIMARY KEY ('id')
);

INSERT INTO cartao ('numeroCartao', 'senhaCartao', 'valor') VALUES ('7549873025634501', '1234', 500.0);
INSERT INTO cartao ('numeroCartao', 'senhaCartao', 'valor') VALUES ('8549873025634501', '2345', 500.0);
INSERT INTO cartao ('numeroCartao', 'senhaCartao', 'valor') VALUES ('9549873025634501', '3456', 500.0);