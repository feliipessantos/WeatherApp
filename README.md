<h1 align="center">Weather App</h1>

<p align="center">
<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://android-arsenal.com/api?level=23"><img src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat" border="0" alt="API"></a>
  <br>
  <a href="https://wa.me/+5551981284000"><img alt="WhatsApp" src="https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white"/></a>
  <a href="https://www.linkedin.com/in/feliipessantos/"><img alt="Linkedin" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="mailto:lipe_silva_santos@hotmail.com"><img alt="Hotmail" src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white"/></a>
</p>

<p align="center">  

Esse é um projeto para demonstrar meu conhecimento técnico no desenvolvimento Android nativo com Kotlin. Mais informações técnicas abaixo.

Esse projeto foi feito com a arquitetura MVVM, ele consome a API REST <a href="https://openweathermap.org/">(Open Weather)</a>, chamada pelo Retrofit utilizando corrotinas. Quando o app inicia ele solicita a permissão de acessar a localização atual do usuário, se concedida ele mostrará a temperatura atual, max e min do dia, além da humidade, sensação térmica e velocidade do vento. É possivel também pesquisar o nome de alguma cidade para se verificar o clima. Para a construção do app também foram utilizadas a biblioteca Lottie - para animar os icones e Glide - para mostrar as imagens de background. Este app foi construído em inglês porém se o dispositivo de usuário estiver em PT-BR, os textos serão traduzidos automaticamente.

</p>

</br>

<p float="left" align="center">
<img alt="screenshot" width="30%" src="https://user-images.githubusercontent.com/79548186/224039544-0fd5934f-2184-4e9e-9b46-2da2467919eb.gif"/>
  <img alt="screenshot" width="30%" src="https://user-images.githubusercontent.com/79548186/222921886-b3ceacb1-dbad-4d24-a29d-0eb3e682c860.gif"/>
  <img alt="screenshot" width="30%" src="https://user-images.githubusercontent.com/79548186/222922181-40f99abf-3c3b-4128-926f-611a30b00309.gif"/>
</p>

## Download

<a href='https://play.google.com/store/apps/details?id=com.feliipessantos.weatherapp&pli=1'><img alt='Disponível no Google Play' width="15%" src='https://play.google.com/intl/en_us/badges/static/images/badges/pt_badge_web_generic.png'/></a>

<img alt="screenshot" width="10%" src="https://user-images.githubusercontent.com/79548186/224040304-8620c914-9d75-4b5f-90a2-0d4023423190.png"/>
Ou escaneie o QR com seu celular para ser direcionado para a Google Play 

Você também pode fazer o download da <a href="https://github.com/feliipessantos/WeatherApp/blob/main/app/apk/app-debug.apk">APK</a>. Você pode ver <a href="https://www.google.com/search?q=como+instalar+um+apk+no+android">aqui</a> como instalar uma APK no seu aparelho android.


## Tecnologias utilizadas e bibliotecas de código aberto

  - Android 6.0 Marshmallow (API level 23)
  - [Linguagem Kotlin](https://kotlinlang.org/)
  - Arquitetura MVVM (View - ViewModel - Model)
    - LiveData - utlizado na comunicação da ViewModel com a View 
    - Corrotinas - utilizadas na comunicação da ViewModel com a Model 
    - Repositories - para abstração da comunidação com a camada de dados
    - Factory - para passar argumentos para a ViewModel
  - Location Services - utilizada para obter a atual ou ultima localização do usuário
  - Dialog - para mostrar uma Progess Bar até que os dados da API forem carregados 
  - ViewBinding - liga os componentes do XML no Kotlin através de uma classe
  - Locale Strings - para traduzir o EN para PT-BR se o dispositivo do usuário estiver em PT
  
- Bibliotecas
  - [Retrofit](https://square.github.io/retrofit/) - Um cliente HTTP utilizado para chamar a API REST;
  - [Glide](https://github.com/bumptech/glide) - Um gerenciamento de mídia utilizado para o carregamento de imagens para Android;
  - [Lottie](https://lottiefiles.com/) - Uma biblioteca que permite o carregamento de icones animados.

## API de terceiros

  - [Open Weather](https://openweathermap.org/) - Api REST usada para receber os dados do clima.

# Licença

```xml

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
