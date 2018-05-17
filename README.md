Ce projet est une implémentation d'un système de notification et d'aide active afin d'aider les personnes qui pouraient avoir des problèmes avec l'utilisation de l'outil informatique via un ensemble d'assistants humains.

liste des modules
- Un front en NODEJS simulant un ensemble de pages type d'un site classique ( contenant le système d'analyse actif ).
- Un back en Java pour recevoir les informations du front, et a envoyer les informations aux assistants.
- Une application mobile Android/Java pour recevoir les notifications envoyées par le back.
- Une application de monitoring permettant d'afficher des graphiques de synthèses concernant les informations envoyées par le back.

voici le schéma correcpondant a ce projet ( ce qui sert de poc ).
![Image for poc](https://github.com/Bananes/PoleEmploi/tree/master/images/poc.png)

Et voici le schéma correcpondant a une possible mise en production de ce projet.
![Image for prod](https://github.com/Bananes/PoleEmploi/tree/master/images/prod.png)