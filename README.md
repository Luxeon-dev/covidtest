# CovidTest

Application de livraison de tests COVID à domicile.

## Mise en place

Avant de lancer les différents projets, une petite configuration doit être faite.

- Avoir une instance Docker de Keycloak sur le port 8081, créer un realm nommé ***covidtest*** (casse à respecter) et importer le JSON de configuration présent à la racine du repository Git
- Créer au moins un utlisateur avec le role ***admin*** dans le realm ***Covidtest***. Cela permettra l'ajout des autres types d'utilisateurs via l'application.
- Copier le dossier ***config-repo*** à la racine du dossier utilisateur (chemin donné par la propriété *user.home* du *application.yml*, variable selon l'OS hôte)
- Une fois copié, initialiser un repository Git local dans ce dossier ainsi qu'un premier commit

## Explications Techniques

Ce projet se découpe en plusieurs sous-projets selon le principe d'architecture microservice:

- 3 microservices REST:
    - product-ms: Contient la gestion des produits
    - shoppingcart-ms: Contient la gestion du panier de chaque utilisateur
    - order-ms: Contient la gestion des commandes
- 1 frontend pour le côté client
- 1 service discovery avec Eureka permettant l'abstraction des adresses IP pour la communication inter-services
- 1 passerelle d'API permettant de centraliser les appels API et de les dispatcher aux bons microservices
- 1 serveur de configuration permettant de centraliser les configurations des différents sous-projets
- 1 serveur Keycloak (Docker) pour la gestion de l'authentification 

Un schéma de l'architecture est présent à la racine du repository Git.

## Lancement 

Afin de démarrer l'application, il est préférable, même si non-obligatoire (hormis le serveur de configuration) de lancer les sous-projets dans un certain ordre:

1. Commencer par le serveur de configuration ***(obligatoirement en 1er)***
2. Lancer ensuite la passerelle d'API et le serveur de discovery
3. Les 4 autres projets peuvent enfin être lancés

Le serveur Keycloak quant à lui peut être démarré à n'importe quel moment. 

Il se peut au démarrage que les services REST ne soit pas instantanément disponibles, l'enregistrement auprès d'Eureka pouvant prendre quelques secondes.

## Utilisation

L'application est disponible sur ***localhost:8080***.

Afin d'y accéder, un utilisateur doit obligatoirement se connecter. Si il n'a pas de compte, un lien pour s'enregistrer est pésent sur la page de connexion. Il devra alors renseigner ses informations avec son **numéro de sécurité sociale** en tant que *username*.

L'utilisateur standard pourra alors voir les produits, les mettre dans son panier et commander. Il pourra donc aussi consulter ses commandes ainsi que leur status de livraison sur son compte.

Celui-ci pourra aussi, toujours via son compte, devenir livreur s'il le souhaite. Dans ce cas là, un nouvel onglet ***Livraison*** sera disponible avec la liste des commandes à prendre en charge et des livraisons qui lui sont assignées.

2 autres types de permissions sont possibles mais seulement obtenable via l'ajout par un administrateur.

Il s'agit pour le premier d'un utilisateur du gouvernement. Celui-ci a accès à un onglet ***Gouvernement*** affichant la totalité des commandes réalisées sur l'application, qu'importe leur statut.

Le deuxième est donc simplement l'administrateur. Celui-ci a accès à un onglet ***Administration*** lui permettant de lire, créer, modifier et supprimer des produits et des utilisateurs (notamment l'ajout des permission *administrateur* et *gouvernement*).