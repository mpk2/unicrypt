package ch.bfh.unicrypt.crypto.signature.abstracts;

import java.util.Random;

import ch.bfh.unicrypt.crypto.signature.interfaces.RandomizedSignatureScheme;
import ch.bfh.unicrypt.math.element.Element;

public abstract class AbstractRandomizedSignatureScheme extends AbstractSignatureScheme implements RandomizedSignatureScheme {

  @Override
  public Element sign(final Element privateKey, final Element message) {
    return this.sign(privateKey, message, (Random) null);
  }

  @Override
  public Element sign(final Element privateKey, final Element message, final Random random) {
    return this.sign(privateKey, message, this.getRandomizationSpace().getRandomElement(random));
  }

  @Override
  public Element sign(final Element privateKey, final Element message, final Element randomization) {
    return this.getSignatureFunction().apply(privateKey, message, randomization);
  }

}
