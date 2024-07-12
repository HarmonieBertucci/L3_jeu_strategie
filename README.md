# L3_jeu_strategie
(2021) Jeu de stratégie en Java.

Université de Caen-Normandie, projet à 4 (J.BIRETTE - H.BERTUCCI - H.PARIS - C.FLAGEUL)

# Sujet : Jeu de stratégie au tour par tour
On souhaite réaliser une application de jeu de combat opposant entre 2 et n joueurs sur
une grille en 2 dimensions, de taille paramétrable.
Un combattant dispose initialement d’une certaine énergie et d’armes de différents types,
avec des munitions en nombre limité.
Règles du jeu :
À chaque tour de jeu, les joueurs jouent l’un après l'autre selon un ordre initialement
défini, ou selon un ordre tiré aléatoirement à chaque tour.
Une action possible est :
- déplacement d’une case (4 directions possibles seulement)
- dépôt d'une mine ou d'une bombe sur l’une des 8 cases voisines
- utilisation d'un tir horizontal ou d’un tir vertical.
- déclenchement du bouclier, qui protège des tirs et bombes lors du prochain tour
- ne rien faire pour économiser son énergie
La grille peut contenir, lors de sa création, des murs (cases non utilisables par les combat-
tants et infranchissables par les tirs), et des pastilles d’énergie que les combattants peu-
vent récupérer en se plaçant dessus.
Une mine explose lorsqu’un combattant se place sur la case qu’elle occupe.
Une bombe explose au bout d’un certain délai t (compte à rebours en nombre de tours de
jeu), et impacte les combattants se trouvant sur l’une des 8 cases voisines, ou, comme une
mine, si l’on se place dessus.
Les bombes et les mines peuvent avoir deux types de visibilité : visible de tous les com-
battants, ou visible seulement du combattant qui l’a déposée.
Un tir horizontal impacte les combattants se trouvant sur la même ligne horizontale, à un
nombre de cases inférieur à la distance d (portée limitée). Idem pour un tir vertical (i.e.
sur la même colonne).
Une explosion ou un tir impactant un combattant fait baisser son énergie d’une certaine
valeur qui fera partie des paramètres du jeu.
Université de Caen – page 2L3 Info - INF5A1
Le 4 octobre 2021
Un déplacement et l’utilisation du bouclier ont eux aussi leurs coûts respectifs.
Un combattant perd le jeu lorsque son énergie est négative ou nulle. Il disparaît alors du
jeu. Le gagnant est le dernier survivant.

# Conception :

On utilisera les méthodes de conception et de développement vues en cours, avec pour
objectif majeur la production d'un code propre, facile à lire, modulaire, réutilisable et
facilitant de futures extensions.
En particulier, conformément à la mise en place d’une architecture MVC, la partie mo-
dèle du jeu devra être complètement indépendante de l’interface graphique (vue-
contrôleur), et devrait pouvoir être utilisée selon d’autres modalités (en ligne de com-
mande, via une application en réseau, etc.)
L’application doit être facilement paramétrable, au minimum via des constantes claire-
ment identifiées dans une classe précise du code, ou mieux, via un fichier de paramétrage.
L’une des difficultés particulières de ce jeu est que certaines armes (bombes, mines)
peuvent n'être visibles que pour les joueurs qui les ont déposées. En termes « objet », il
faut donc faire en sorte que les joueurs aient un accès partiel au modèle complet du jeu.
On évitera de dupliquer les modèle complet en modèles partiels (très inefficace). Sugges-
tion : le pattern Proxy (à voir en cours) permettant de ne faire voir qu’une partie des
données réelles. Il y aurait donc un proxy par joueur, configuré pour lui.
De la même façon, concernant les vues, on devra afficher autant de vues que de joueurs,
chaque vue ne montrant que ce que le joueur en question peut voir (bien sûr, dans une
application réelle, le jeu serait multipostes, et chaque joueur ne verrait que sa grille).
On pourra utiliser une Factory (factory method) pour créer différents types de combat-
tants, certains avec beaucoup d’énergie mais peu d’armes, etc.
On pourra utiliser Strategy pour :
- définir la façon dont on remplit initialement la grille (murs, pastilles d’énergie, position
initiale des joueurs)
- définir la stratégie d’un joueur robot (on pourra faire combattre des joueurs ayant des
stratégies différentes sur un grand nombre de parties pour voir quelles sont les meil-
leures). On pourra créer une première stratégie aléatoire, puis créer au moins une stratégie
un peu plus fine.
On pourra se limiter à une version de base ne faisant jouer que des robots (l’interface
graphique ne propose plus alors comme seul contrôleur qu’un bouton « Suivant » qui fait
passer au tour suivant).
