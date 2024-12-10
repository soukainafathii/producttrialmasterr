# Consignes

- Vous êtes développeur front-end : vous devez réaliser les consignes décrites dans le chapitre [Front-end](#Front-end)

- Vous êtes développeur back-end : vous devez réaliser les consignes décrites dans le chapitre [Back-end](#Back-end) (*)

- Vous êtes développeur full-stack : vous devez réaliser les consignes décrites dans le chapitre [Front-end](#Front-end) et le chapitre [Back-end](#Back-end) (*)

(*) Afin de tester votre API, veuillez proposer une stratégie de test appropriée.

## Front-end

Le site de e-commerce d'Alten a besoin de s'enrichir de nouvelles fonctionnalités.

Mise à jour de l'avancement
    Toutes les tâches liées au backend et au frontend ont été réalisées avec succès. Voici les détails des travaux effectués :

## Front-end
Fonctionnalités implémentées
Liste des produits :

- Affichage des informations pertinentes pour chaque produit dans la liste.
- Possibilité d'ajouter un produit au panier directement depuis la liste.
- Suppression d'un produit depuis le panier.
- Affichage dynamique d'un badge indiquant la quantité de produits dans le panier.
- Visualisation de la liste des produits ajoutés dans le panier.
- Affichage de la liste d'envie
- Ajout d'un produit à la liste d'envie 
- Suppression d'un produit de la liste d'envie

Page Contact :
- Ajout d'un point de menu "Contact" dans la barre latérale.
  - Création d'une page affichant un formulaire de contact permettant de saisir :
        Email (obligatoire).
        Message (obligatoire, limité à 300 caractères).
        Confirmation de l'envoi avec un message à l'utilisateur : "Demande de contact envoyée avec succès."


## Back-end
  Fonctionnalités implémentées
    API pour la gestion des produits :
       Création d'une API REST permettant :
- [GET] Récupération de tous les produits.
- [GET] Récupération d'un produit par id.
- [POST] Ajout d'un nouveau produit.
- [PATCH] Modification des informations d'un produit spécifique.
- [DELETE] Suppression d'un produit spécifique.
- Les détails des produits sont gérés dans une base de données SQL.

Authentification via JWT :
   Mise en place d'un système de connexion basé sur des tokens JWT.
     Routes implémentées :
- [POST] /account : Création d'un compte utilisateur.
- [POST] /token : Connexion avec email et mot de passe pour obtenir un token JWT.

Gestion des droits d'accès :
- Seuls les utilisateurs connectés peuvent accéder aux fonctionnalités restreintes.
- Seul l'utilisateur avec l'email admin@admin.com peut ajouter, modifier ou supprimer des produits.

Gestion du panier et de la liste d'envies :
- Fonctionnalité permettant aux utilisateurs de gérer un panier d'achat et une liste d'envies.

Tests :
- Des tests Postman ont été réalisés pour valider l'ensemble des API.

Base de données et images par défaut :
- La base de données est générée automatiquement lors du démarrage du backend. Vous pouvez directement commencer à tester l'application en utilisant les API ou en interagissant avec l'interface frontend.
- Une image par défaut, appelée test-product-image, est utilisée dans le cas où l'administrateur ne fournit pas d'URL d'image lors de la création d'un produit.
- Sous le dossier assets dans le frontend, 4 images sont disponibles (une pour chaque catégorie de produit). Ces images peuvent être utilisées pour tester la création ou la modification de produits.

Bonus :
Les tests Postman ont été réalisés et partagés.