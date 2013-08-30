/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.math.concatenative.abstracts;

import ch.bfh.unicrypt.math.concatenative.interfaces.ConcatenativeElement;
import ch.bfh.unicrypt.math.general.interfaces.Element;
import ch.bfh.unicrypt.math.concatenative.interfaces.ConcatenativeMonoid;
import ch.bfh.unicrypt.math.concatenative.interfaces.ConcatenativeSemiGroup;
import ch.bfh.unicrypt.math.general.abstracts.AbstractElement;
import ch.bfh.unicrypt.math.general.interfaces.Group;
import ch.bfh.unicrypt.math.general.interfaces.SemiGroup;
import ch.bfh.unicrypt.math.general.interfaces.Set;
import java.math.BigInteger;

/**
 *
 * @param <E>
 * @author rolfhaenni
 */
public abstract class AbstractConcatenativeElement<S extends ConcatenativeSemiGroup, E extends ConcatenativeElement> extends AbstractElement<S, E> implements ConcatenativeElement {

  protected AbstractConcatenativeElement(final S semiGroup) {
    super(semiGroup);
  }

  protected AbstractConcatenativeElement(final S semiGroup, final BigInteger value) {
    super(semiGroup);
    if (!semiGroup.contains(value)) {
      throw new IllegalArgumentException();
    }
    this.value = value;
  }

  /**
   * @see Group#apply(Element, Element)
   */
  @Override
  public final E concatenate(final Element element) {
    return (E) this.getSet().concatenate(this, element);
  }

  /**
   * @see Group#T(Element, BigInteger)
   */
  @Override
  public final E selfConcatenate(final BigInteger amount) {
    return (E) this.getSet().selfConcatenate(this, amount);
  }

  /**
   * @see Group#selfApply(Element, Element)
   */
  @Override
  public final E selfConcatenate(final Element amount) {
    return (E) this.getSet().selfConcatenate(this, amount);
  }

  /**
   * @see Group#selfApply(Element, int)
   */
  @Override
  public final E selfConcatenate(final int amount) {
    return (E) this.getSet().selfConcatenate(this, amount);
  }

  /**
   * @see Group#selfApply(Element)
   */
  @Override
  public final E selfConcatenate() {
    return (E) this.getSet().selfConcatenate(this);
  }

}