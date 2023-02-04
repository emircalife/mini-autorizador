# mini-autorizador
Mini-autorizador para criação e lançamentos no cartão VR

01. obterDadosCartao(número do cartão)  - Obtém todos os dados do cartão
02. obterSaldo(número do cartão)        - Obtém número do cartão e saldo do mesmo, sem mostrar a senha
03. novoCartao()                        - Cria um cartão novo com o número passado e a senha, deixando um saldo de 500,00 por padrão
04. transacao(número do cartão)         - Realizando uma transação "consumo" de um valor determinado no JSON, 
                                          necessitando ter numero do cartão e senha corretos para a transação ser realizada
