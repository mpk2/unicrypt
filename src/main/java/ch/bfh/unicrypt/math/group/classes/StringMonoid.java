/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.group.classes;

import ch.bfh.unicrypt.math.element.Element;
import ch.bfh.unicrypt.math.group.abstracts.AbstractAdditiveMonoid;
import ch.bfh.unicrypt.math.group.interfaces.Set;
import ch.bfh.unicrypt.math.helper.Permutation;
import ch.bfh.unicrypt.math.utility.MathUtil;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author rolfhaenni
 */
public class StringMonoid extends AbstractAdditiveMonoid {

  private StringMonoid() {
  }

  public final String getString(Element element) {
    if (!this.contains(element)) {
      throw new IllegalArgumentException();
    }
    return ((StringMonoid.StringElement) element).getString();
  }

  public final Element getElement(final String string) {
    if (string == null) {
      throw new IllegalArgumentException();
    }
    return this.standardGetElement(string);
  }

  protected Element standardGetElement(String string) {
    return new StringMonoid.StringElement(this, string);
  }

  //
  // The following protected methods implement the abstract methods from
  // various super-classes
  //

  @Override
  protected Element abstractGetIdentityElement() {
    return this.standardGetElement("");
  }

  @Override
  protected Element abstractApply(Element element1, Element element2) {
    return this.standardGetElement(((StringElement) element1).getString() + ((StringElement) element2).getString());
  }

  @Override
  protected BigInteger abstractGetOrder() {
    return Set.INFINITE_ORDER;
  }

  @Override
  protected Element standardGetElement(BigInteger value) {
    return this.standardGetElement(new String(value.toByteArray()));
  }

  @Override
  protected Element abstractGetRandomElement(Random random) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected boolean abstractContains(BigInteger value) {
    return value.signum() >= 0;
  }

  @Override
  protected boolean abstractEquals(Set set) {
    return true;
  }

  //
  // LOCAL ELEMENT CLASS
  //
  final private class StringElement extends Element {

    private static final long serialVersionUID = 1L;
    private final String string;

    private StringElement(final Set set, final String string) {
      super(set);
      this.string = string;
    }

    public String getString() {
      return this.string;
    }

    @Override
    protected BigInteger standardGetValue() {
      return new BigInteger(getString().getBytes());
    }

    @Override
    protected boolean standardEquals(Element element) {
      return this.getString().equals(((StringMonoid.StringElement) element).getString());
    }

    @Override
    protected int standardHashCode() {
      return this.getString().hashCode();
    }

    @Override
    public String standardToString() {
      return this.getString();
    }
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