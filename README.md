# The Big Adventure
The Big Adventure is a zelda-like game where you can design your own maps !
Many mechanics allows you to make various challenges !

![image](https://github.com/AlainCoserariu/The-Big-Advendure/assets/111073916/8f110be2-81e9-4291-b834-5899a0e2902c)
Assets are from the game [Baba Is You](https://babaiswiki.fandom.com/wiki/Category:Nouns)

## Introduction
This project is the final project to complete java module at Université Gustave Eiffel.
Here is the subject : [https://monge.univ-mlv.fr/ens/Licence/L3/2023-2024/Java/index.php](https://monge.univ-mlv.fr/ens/Licence/L3/2023-2024/Java/index.php)

## How to play
### Download
You can directly clone the repository or get the archive as zip :
  - [direct download](https://github.com/AlainCoserariu/The-Big-Advendure/archive/refs/heads/main.zip)
  - ```bash
    git clone https://github.com/AlainCoserariu/The-Big-Advendure.git
    ```

### Compile
The project use Java 21  
To compile the project use the command `ant` in the TheBigAdventure directory, a new file `thebigadventure.jar` should appear.  

For developers, Javadoc is available in doc/api.

### Run
Once the project is compiled you can run it by using the following command :  
```bash
java -java thebigadventure.jar
```

### Making and select levels
You can create your own levels following project's specific grammar. In order to work maps are supposely placed in `maps` directory  
To run a specific map you have to run this specific command :  
```bash
java -java thebigadventure.jar --levels [level-name]
```
The default level is adventure.map

Please refers to the docs inside `docs` to create your own maps
