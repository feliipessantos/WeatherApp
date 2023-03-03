<h1 align="center">
  <br>
  <img src="https://user-images.githubusercontent.com/79548186/221186300-25f23f4c-54c3-4afb-b3d3-3e7abdfa3c4e.png" alt="Weather App" width="150">
  <br>
  Weather App
  <br>
</h1>

<h4 align="center">Um app para checar o clima feito em <a href="https://kotlinlang.org/" target="_blank">kotlin</a> com a arquitetura MVVM.</h4>

## Sobre 

<p align="justify">
  Esse projeto foi feito com a arquitetura MVVM, ele consome a API REST <a href="https://openweathermap.org/">(Open Weather)</a>, chamada pelo Retrofit utilizando corrotinas. Quando o app inicia ele solicita a permissão de acessar a localizaçção utual do usuário, se concedida ele mostrará a temperatura atual, max e min do dia, além da humidade, sensação térmica e velocidade do vento. É possivel também pesquisar o nome de alguma cidade para se verificar o clima. Para a construção do app foram usadas a bibliotecas do Lottie - para animar os icones e Glide - para mostrar as imagens de background. Este app foi construído em inglês porém se o dispositivo de usuário estiver em PT-BR, os textos serão traduzidos automaticamente.</p>

<p align="center">
<a href="https://user-images.githubusercontent.com/79548186/221209905-8dcee868-9ed6-456b-8462-2f22d955dcbe.png" target="_blank"><img src="https://user-images.githubusercontent.com/79548186/221209905-8dcee868-9ed6-456b-8462-2f22d955dcbe.png" alt="app image Rain" width="300" height="500">
<a href="https://user-images.githubusercontent.com/79548186/221209917-762b7a5f-9e9e-42d6-9dbc-8271c8f3449e.png" target="_blank"><img src="https://user-images.githubusercontent.com/79548186/221209917-762b7a5f-9e9e-42d6-9dbc-8271c8f3449e.png" alt="app image Clear" width="300" height="500">
<a href="https://user-images.githubusercontent.com/79548186/221209929-0dead647-b7fd-4e8e-a1dd-7f22df95cd67.png" target="_blank"><img src="https://user-images.githubusercontent.com/79548186/221209929-0dead647-b7fd-4e8e-a1dd-7f22df95cd67.png" alt="app image Clouds" width="300" height="500">
</p>
  
## Feito com

- [Kotlin](https://kotlinlang.org/) - Linguagem utilizada no Android Studio para construção do app;
- [MVVM] - Arquitetura Model View ViewModel;
- [Coroutines] - Corrotinas são um padrão de design usado em Android para simplificar um código executado de forma assíincrona;
- [Open Weather](https://openweathermap.org/) - Api REST usada para receber os dados do clima;
- [Retrofit](https://square.github.io/retrofit/) - Um cliente HTTP utilizado para chamar a API REST;
- [Glide](https://github.com/bumptech/glide) - Um gerenciamento de mídia utilizado para o carregamento de imagens para Android;
- [Lottie](https://lottiefiles.com/) - Uma biblioteca que permite o carregamento de icones animados.
  
  <div align="center">
    <video src="https://user-images.githubusercontent.com/79548186/221211135-f416208a-725d-485e-94a7-ac8de5a1f66f.mp4">
  </div>
