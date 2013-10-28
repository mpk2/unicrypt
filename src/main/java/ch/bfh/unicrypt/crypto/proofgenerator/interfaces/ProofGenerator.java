package ch.bfh.unicrypt.crypto.proofgenerator.interfaces;

import java.util.Random;

import ch.bfh.unicrypt.math.algebra.general.classes.ProductGroup;
import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;
import ch.bfh.unicrypt.math.function.interfaces.Function;

public interface ProofGenerator {
  // For increased convenience, we assume that the ProofSpace is always a
  // ProductGroup
  // and that proofs are always TupleElements

  public Tuple generate(Element secretInput, Element publicInput);

  public Tuple generate(Element secretInput, Element publicInput, Element otherInput);

  public Tuple generate(Element secretInput, Element publicInput, Random random);

  public Tuple generate(Element secretInput, Element publicInput, Element otherInput, Random random);

  public boolean verify(Tuple proof, Element publicInput);

  public boolean verify(Tuple proof, Element publicInput, Element otherInput);

  public ProductGroup getProofSpace();

  public Function getProofFunction();

  public Group getDomain();

  public Group getCoDomain();

}