package fr.uge.weapon;

import java.util.Objects;

public record Sword(int damage, String name, String skin) implements Weapon {
  public Sword {
    if (damage <= 0) {
       throw new IllegalArgumentException("The sword have to have damage > 0");
    }
    
    Objects.requireNonNull(name);
  }
}
