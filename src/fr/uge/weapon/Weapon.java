package fr.uge.weapon;

import fr.uge.field.item.Item;

public interface Weapon extends Item {
  int damage();
  String name();
}
