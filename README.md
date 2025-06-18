
# Busca Livre

Aplicativo Android nativo (kotlin), que simula uma busca de produtos semelhante ao Mercado Livre, utilizando dados mockados em arquivos JSON armazenados localmente.

## Funcionalidades

-  Busca de produtos por categoria (iPhone, Café, Arroz, Camisa, Zapatillas).
-  Listagem de produtos com imagem, título e preço.
-  Tela de detalhes do produto com descrição, preço e breadcrumb de categorias.
-  Tratamento de erros (produto ou categoria não encontrados).
-  Dados carregados localmente via arquivos JSON na pasta `assets/mocks`.

## Tecnologias Utilizadas

- Kotlin
- Android SDK
- MVVM Architecture
- ViewModel + LiveData
- Glide (para carregamento de imagens)
- Gson (para parsing de JSON)
- ConstraintLayout e Material Components

## ⚙️ Como Rodar o Projeto

1. Clone este repositório:
```bash
git clone https://github.com/seu-usuario/buscaLivre.git
```
2. Abra no Android Studio.
3. Execute em um dispositivo físico ou emulador.

## Pontos de melhoria

- Implementar integração real com APIs.
- Tratar erros de forma mais amigável, com imagens e textos melhores
- Implementar uma interface mais elaborada
- Adicionar filtros e ordenações.
- Suporte a tema escuro.

## Desenvolvido por Cibele Sthefany 🚀
