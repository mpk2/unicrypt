package ch.bfh.unicrypt.crypto.signature.interfaces;

import ch.bfh.unicrypt.crypto.keygen.interfaces.KeyPairGenerator;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;
import ch.bfh.unicrypt.math.function.interfaces.Function;

public interface SignatureScheme {

  public Element sign(final Element privateKey, final Element message);

  public boolean verify(final Element publicKey, final Element message, Element signature);

  public KeyPairGenerator getKeyPairGenerator();

  public Function getSignatureFunction();

  public Function getVerificationFunction();

  public ZPlus getMessageSpace();

  public Group getSignatureSpace();

}
