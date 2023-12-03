package fr.uge.entity.enemy;

import java.util.ArrayList;
import java.util.Objects;

public class EnemyList {
  private final ArrayList<Enemy> enemies;

  public EnemyList() {
    enemies = new ArrayList<>();
  }
  
  public void add(Enemy enemy) {
    Objects.requireNonNull(enemy);
    enemies.add(enemy);
  }
  public ArrayList<Enemy> getList(){
  	return enemies;
  }
}
