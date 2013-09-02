/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.algebra.concatenative.classes;

import ch.bfh.unicrypt.math.algebra.concatenative.abstracts.AbstractConcatenativeMonoid;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author rolfhaenni
 */
public class StringMonoid extends AbstractConcatenativeMonoid<StringElement> {

  public static final StringElement EMPTY_STRING = StringMonoid.getInstance().getElement("");

  private StringMonoid() {
  }

  public final StringElement getElement(final String string) {
    if (string == null) {
      throw new IllegalArgumentException();
    }
    return this.standardGetElement(string);
  }

  protected StringElement standardGetElement(String string) {
    return new StringElement(this, string) {
    };
  }

  @Override
  protected StringElement abstractGetElement(BigInteger value) {
    return this.standardGetElement(new String(value.toByteArray()));
  }

  //
  // The following protected methods implement the abstract methods from
  // various super-classes
  //
  @Override
  protected StringElement abstractGetIdentityElement() {
    return this.standardGetElement("");
  }

  @Override
  protected StringElement abstractApply(Element element1, Element element2) {
    return this.standardGetElement(((StringElement) element1).getString() + ((StringElement) element2).getString());
  }

  @Override
  protected BigInteger abstractGetOrder() {
    return Set.INFINITE_ORDER;
  }

  @Override
  protected StringElement abstractGetRandomElement(Random random) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected boolean abstractContains(BigInteger value) {
    return value.signum() >= 0;
  }
  //
  // STATIC FACTORY METHODS
  //
  private static StringMonoid instance;

  /**
   * Returns the singleton object of this class.
   *
   * @return The singleton object of this class
   */
  public static StringMonoid getInstance() {
    if (StringMonoid.instance == null) {
      StringMonoid.instance = new StringMonoid();
    }
    return StringMonoid.instance;
  }

}