# 98Taxis

98Taxis é um sistema baseado em agentes criado para simular um ambiente onde convivem taxis e passageiros. Esse ambiente segue as seguintes regras:

  - O ambiente da simulação é um grid simétrico, uma matriz.
  - Nessa matriz existem bloqueios, ou seja, ruas onde não podem passar nem taxis nem passageiros
  - A matriz é simétrica, portanto todos os movimentos tem o mesmo custo para o cálculo de distância
  - Um passageiro pede um taxi, e somente o taxi livre mais próximo deve atender a este passageiro
  - Taxis só podem se mover em 4 direções (cima, baixo, direita e esquerda), ou seja, **não** podem se mover na diagonal
  - Taxis a caminho de passageiros ou carregando passageiros não podem ser notificados de novas corridas.



-------------

## Sistema

- O sistema foi desenvolvido na plataforma Java, utilizando Spring para injeção de dependências e RESTEasy para o desenvolvimento da API.
- Não existe nenhum tipo de persistência, todo o processamento ocorre em memória
- O Algoritmo de caminho mais curto utilizado foi o A* (A-Star), ignorando movimentos diagonais.

------------

## API

O sistema possui uma API que contem 5 endpoints, e aqui eu vou listar exemplos de como utilizar cada um:

#### Criação de Taxistas
O Endpoint para criação de taxistas recebe uma coordenada (x, y) e criará um taxista disponível para buscar passageiros.

O taxista será criado na posição especificada pela coordenada.

##### Exemplo de uso:
```
POST /api/taxi HTTP/1.1
Host: ec2-52-91-89-230.compute-1.amazonaws.com:8080
Content-Type: application/x-www-form-urlencoded

X=5&Y=5
```
##### CURL
```sh
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'X=5&Y=5' "http://ec2-52-91-89-230.compute-1.amazonaws.com:8080/api/taxi"
```

#### Criação de Passageiros
O Endpoint para criação de passageiros recebe duas coordenadas (x, y).
- A primeira coordenada é a posição original do passageiro (SourceX e SourceY).
- A segunda coordenada é o destino do passageiro (DestinationX e DestinationY).

O passageiro será criado na posição especificada pelas coordenadas SourceX e SourceY

##### Exemplo de uso:
```
POST /api/passenger HTTP/1.1
Host: ec2-52-91-89-230.compute-1.amazonaws.com:8080
Content-Type: application/x-www-form-urlencoded

SourceX=14&SourceY=18&DestinationX=5&DestinationY=5
```
##### CURL
```sh
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'SourceX=14&SourceY=18&DestinationX=5&DestinationY=5' "http://ec2-52-91-89-230.compute-1.amazonaws.com:8080/api/passenger"
```

#### Avançar tempo
Este endpoint irá avançar o tempo da simulação em 1 unidade.

O Endpoint para avanço de tempo não espera nenhum valor.

##### Exemplo de uso:
```
POST /api/step HTTP/1.1
Host: ec2-52-91-89-230.compute-1.amazonaws.com:8080
```
##### CURL
```sh
curl -X POST "http://ec2-52-91-89-230.compute-1.amazonaws.com:8080/api/step"
```

#### Exibir estado atual
Este endpoint responde com o estado atual da simulação em formato HTML.

O Endpoint para exibir estado atual não espera nenhum valor.

##### Exemplo de uso:
```
GET /api/state HTTP/1.1
Host: ec2-52-91-89-230.compute-1.amazonaws.com:8080
```
##### CURL
```sh
curl -X GET "http://ec2-52-91-89-230.compute-1.amazonaws.com:8080/api/state"
```

#### Reiniciar simulação
Este endpoint irá remover completamente todos os taxistas e passageiros do grid.

O Endpoint para reiniciar simulação não espera nenhum valor.

##### Exemplo de uso:
```
POST /api/restart HTTP/1.1
Host: ec2-52-91-89-230.compute-1.amazonaws.com:8080
```
##### CURL
```sh
curl -X POST "http://ec2-52-91-89-230.compute-1.amazonaws.com:8080/api/restart"
```

---------------------

### Exemplo de resposta das APIs

Todas as 5 APIs respondem com o estado atual do sistema em formato HTML.

```
_x____________________________
_x____3_________xxxxxxx______x
_x______________xxxxxxx______x
_x______________xxxxxxx______x
_x____x___P_____x________1___x
_x____x________xxxxxxxxx_____x
_x____x________xx_____x______x
_x____x____2___x_x____x______x
_x____x________x____x__x______
_x____x________x_____x__x_____
______x________x______x__x____
______x________x_______x______
______x________x______________
______xxxxxxxxxxxxxxxxxxxxx___
_____________x_x______________
_____________xxxxxxxxxxxxxx__x
_______________x______________
_______________x______________
xxxxxxxx_______x______________
_______________x______________
_xxxxxxx_______x______________
______________________________
xxxxxxxx______________________
_______x______________________
_______x______________xxxx_xxx
xxxxxxx_xxxxxxxxxxxxxxxxxxxxxx
______________________x_______
______________________x_______
______________________________
x_____________________________
```
#### Legenda
- t = tempo
- _ = rua vazia
- x = rua bloqueada
- 1 = táxi vazio
- 2 = táxi a caminho do passageiro
- 3 = táxi cheio
- P = passageiro

---------------

#### Considerações

As APIs de criação de taxistas e passageiros entendem como inválidas as seguintes coordenadas:
 - Uma coordenada que representa uma rua bloqueada
 - Uma coordenada fora do escopo do grid (números negativos ou maiores do que o limite)
 - Se o caminho do passageiro (de sua origem até seu destino) for impossível (tiver obstáculos intranspassáveis), o sistema responde com uma mensagem de erro e não permite a criação do passageiro

Nos dois casos acima o sistema responde com uma mensagem de erro, e não permite a criação do passageiro/taxista

