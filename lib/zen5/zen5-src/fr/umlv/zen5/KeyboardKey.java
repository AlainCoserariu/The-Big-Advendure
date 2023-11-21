package fr.umlv.zen5;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import fr.umlv.zen5.Event.Modifier;

/**
 * A keyboard key.
 * 
 * @see Event
 * @see Modifier
 */
public enum KeyboardKey {
  META(KeyEvent.VK_META),
  CTRL(KeyEvent.VK_CONTROL),
  ALT(KeyEvent.VK_ALT),
  SHIFT(KeyEvent.VK_SHIFT),
  ALT_GR(KeyEvent.VK_ALT_GRAPH),
  
  UP(KeyEvent.VK_UP),
  DOWN(KeyEvent.VK_DOWN),
  LEFT(KeyEvent.VK_LEFT),
  RIGHT(KeyEvent.VK_RIGHT),
  SPACE(KeyEvent.VK_SPACE),
  
  A(KeyEvent.VK_A),
  B(KeyEvent.VK_B),
  C(KeyEvent.VK_C),
  D(KeyEvent.VK_D),
  E(KeyEvent.VK_E),
  F(KeyEvent.VK_F),
  G(KeyEvent.VK_G),
  H(KeyEvent.VK_H),
  I(KeyEvent.VK_I),
  J(KeyEvent.VK_J),
  K(KeyEvent.VK_K),
  L(KeyEvent.VK_L),
  M(KeyEvent.VK_M),
  N(KeyEvent.VK_N),
  O(KeyEvent.VK_O),
  P(KeyEvent.VK_P),
  Q(KeyEvent.VK_Q),
  R(KeyEvent.VK_R),
  S(KeyEvent.VK_S),
  T(KeyEvent.VK_T),
  U(KeyEvent.VK_U),
  V(KeyEvent.VK_V),
  W(KeyEvent.VK_W),
  X(KeyEvent.VK_X),
  Y(KeyEvent.VK_Y),
  Z(KeyEvent.VK_Z),
  
  UNDEFINED(KeyEvent.VK_UNDEFINED)
  ;
  
  private final int key;
  
  private KeyboardKey(int key) {
    this.key = key;
  }
  
  static KeyboardKey key(int key) {
    if (key>=keys.length)
      return UNDEFINED;
    return keys[key];
  }
  
  private static final KeyboardKey[] keys;
  static {
    int max = 0;
    KeyboardKey[] values = KeyboardKey.values();
    for(KeyboardKey key: values) {
      max = Math.max(max, key.key);
    }
    
    KeyboardKey[] array = new KeyboardKey[max + 1];
    Arrays.fill(array, UNDEFINED);
    for(KeyboardKey key: values) {
      array[key.key] = key;
    }
    keys = array;
  }
}
