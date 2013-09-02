/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.algebra.general.classes;

import java.math.BigInteger;
import java.util.Random;

import ch.bfh.unicrypt.math.algebra.additive.classes.ZPlusMod;
import ch.bfh.unicrypt.math.algebra.general.abstracts.AbstractSet;
import ch.bfh.unicrypt.math.utility.RandomUtil;

/**
 * This interface represents the group that consists of two elements only, for
 * example TRUE and FALSE. This group is isomorphic to the additive group of
 * integers modulo 2. It is therefore possible to consider and implement it as a
 * specialization of {@link ZPlusMod}.
 *
 * @author R. Haenni
 * @author R. E. Koenig
 * @version 2.0
 */
public class BooleanSet extends AbstractSet<BooleanElement> {

  public static final BooleanElement TRUE = BooleanSet.getInstance().getElement(true);
  public static final BooleanElement FALSE = BooleanSet.getInstance().getElement(false);

  /**
   * Creates and returns the group element that corresponds to a given Boolean
   * value.
   *
   * @param value The given Boolean value
   * @return The corresponding group element
   */
  public final BooleanElement getElement(final boolean bit) {
    return new BooleanElement(this, bit) {
    };
  }

  @Override
  protected BigInteger abstractGetOrder() {
    return BigInteger.valueOf(2);
  }

  @Override
  protected BooleanElement abstractGetElement(BigInteger value) {
    return this.getElement(value.equals(BigInteger.ONE));
  }

  @Override
  protected BooleanElement abstractGetRandomElement(Random random) {
    return this.getElement(RandomUtil.createRandomBoolean(random));
  }

  @Override
  protected boolean abstractContains(BigInteger value) {
    return value.equals(BigInteger.ZERO) || value.equals(BigInteger.ONE);
  }
  //
  // STATIC FACTORY METHODS
  //
  private static BooleanSet instance;

  /**
   * Returns the singleton object of this class.
   *
   * @return The singleton object of this class
   */
  public static BooleanSet getInstance() {
    if (BooleanSet.instance == null) {
      BooleanSet.instance = new BooleanSet();
    }
    return BooleanSet.instance;
  }

}