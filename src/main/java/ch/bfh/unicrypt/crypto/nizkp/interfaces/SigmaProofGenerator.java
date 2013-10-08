package ch.bfh.unicrypt.crypto.nizkp.interfaces;

import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;
import ch.bfh.unicrypt.math.function.classes.HashFunction;

public interface SigmaProofGenerator extends ProofGenerator {

  public Group getCommitmentSpace();

  public Group getResponseSpace();

  public Element getCommitment(Tuple proof);

  public Element getResponse(Tuple proof);

  public HashFunction getHashFunction();

}