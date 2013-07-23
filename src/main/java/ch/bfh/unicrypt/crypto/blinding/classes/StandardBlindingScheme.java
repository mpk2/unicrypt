package ch.bfh.unicrypt.crypto.blinding.classes;

import java.util.Random;

import ch.bfh.unicrypt.crypto.blinding.abstracts.AbstractBlindingScheme;
import ch.bfh.unicrypt.math.element.Element;
import ch.bfh.unicrypt.math.function.classes.SelfApplyFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.math.group.classes.ZPlusMod;
import ch.bfh.unicrypt.math.group.interfaces.Group;

public class StandardBlindingScheme extends AbstractBlindingScheme {

  Group blindingSpace;
  ZPlusMod blindingValueSpace;
  SelfApplyFunction blindingFunction;

  public StandardBlindingScheme(final Group blindingSpace) {
    if (blindingSpace == null)
      throw new IllegalArgumentException();
    this.blindingSpace = blindingSpace;
    this.blindingValueSpace = blindingSpace.getOrderGroup();
    this.blindingFunction = new SelfApplyFunction(this.blindingSpace, this.blindingValueSpace);
  }


  // @Override
  // public Element unblind(final Element value, final AdditiveElement
  // blindingValue) {
  // if(blindingValue==null)
  // throw new IllegalArgumentException();
  // return this.blind(value, blindingValue.invert());
  // That does not work, as the blindingValue is an element of an additive
  // group. Hence blinding again with the
  // inverse value always results in 1
  // (x^y)^{-y}=x^{y-y}=x^0=1
  // }


}
