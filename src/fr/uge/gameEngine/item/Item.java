package fr.uge.gameEngine.item;

import fr.uge.gameEngine.utility.Hitbox;

public sealed interface Item permits Weapon, Food {
  String getSkin();
  Hitbox getHitbox();
  double getX();
  double getY();
  String getName();
}
